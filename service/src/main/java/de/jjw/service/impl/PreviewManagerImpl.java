/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PreviewManagerImpl.java
 * Created : 08 Feb 2011
 * Last Modified: Tue, 08 Feb 2011 20:55:48
 *
 * Ju Jutsu Web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ju Jutsu Web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ju Jutsu Web.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jjw.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.jjw.dao.PreviewDao;
import de.jjw.dao.fighting.UserFightingclassDao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.Preview;
import de.jjw.model.admin.UserToClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.PreviewManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.mapper.PreviewMapper;
import de.jjw.service.modelWeb.PreviewWeb;
import de.jjw.service.modelWeb.PreviewWebTatamiClasses;
import de.jjw.util.Utility;
import de.jjw.util.ICodestableTypeConstants;

public class PreviewManagerImpl
    extends BaseManager
    implements PreviewManager
{

    private PreviewDao previewDao;

    private UserFightingclassDao userFightingclassDao;

    @Override
    public List<List<PreviewWeb>> getAllPreviews( Locale local )
        throws JJWManagerException
    {
        try
        {
            return PreviewMapper.mapPreviewCompleteListFromDB( previewDao.getAllPreviews(), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public Map<Long, List<PreviewWebTatamiClasses>> getAllPreviewTatamiClasses(Locale local)
        throws JJWManagerException
    {
        try
        {
            Map<Long, List<PreviewWebTatamiClasses>> ret = new HashMap<Long, List<PreviewWebTatamiClasses>>();
            List<Fightingclass> fightingclasses;
            List<Duoclass> duoclasses;
            List<Newaclass> newaclasses;
            List<PreviewWebTatamiClasses> tatamiClassesList = new ArrayList<PreviewWebTatamiClasses>();
            Map<String,UserToClass> utcMap =  userFightingclassDao.getallUsersToClasses();
            PreviewWebTatamiClasses previewClass;
            long openTime = 0;
            List<PreviewItem> previewItemList;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            for ( Long userId : previewDao.getUserWithAssignedClasses() )
            {
                previewItemList= new ArrayList<PreviewItem>();
                tatamiClassesList = new ArrayList<PreviewWebTatamiClasses>();
                PreviewItem previewItem;
                fightingclasses = userFightingclassDao.getCurrentAndPlanedFightingClassesForUser( userId );

                long remainingTimeOnTatamiFighting = 0;
                for ( Fightingclass item : fightingclasses )
                {
                    if ( null != item.getStartTime() )
                    {
                        remainingTimeOnTatamiFighting +=
                            item.getNumberOfOpenFightsInClass() * item.getAge().getEstimatedTime();
                    }
                    previewItem = new PreviewItem();
                    if ( item.getStartTime() != null ) previewItem.started=true;
                    previewItem.classId=item.getId();
                    previewItem.className=PreviewWeb.DISCEPLINE_FIGHTING + " " +item.getAge().getAgeShort() +" " +
                                    CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX_SHORT, item.getSex(), local  )
                                    +" "+ item.getWeightclass();
                    previewItem.discipline="F";
                    previewItem.estimatedTimePerFight=item.getAge().getEstimatedTime();
                    previewItem.openFights=item.getNumberOfOpenFightsInClass();
                    previewItem.openFights=item.getNumberOfOpenFightsInClass();
                    previewItem.order=utcMap.get("F"+userId+item.getId()).getOrder();
                    previewItem.tatamiId=userId;
                    previewItem.totalFights=item.getNumberOfFightsInClass() ;
                    previewItemList.add( previewItem );
                }
                
                duoclasses = userFightingclassDao.getCurrentAndPlanedDuoClassesForUser( userId );
                
                for ( Duoclass item : duoclasses )
                {
                    if ( null != item.getStartTime() )
                    {
                        remainingTimeOnTatamiFighting +=
                            item.getNumberOfOpenFightsInClass() * item.getAge().getEstimatedTime();
                    }
                    previewItem = new PreviewItem();
                    if ( item.getStartTime() != null ) previewItem.started=true;
                    previewItem.classId=item.getId();
                    previewItem.className=item.getDuoclassName();
                    previewItem.discipline="D";
                    previewItem.estimatedTimePerFight=item.getAge().getEstimatedTime();
                    previewItem.openFights=item.getNumberOfOpenFightsInClass();
                    previewItem.openFights=item.getNumberOfOpenFightsInClass();
                    previewItem.order=utcMap.get("D"+userId+item.getId()).getOrder();
                    previewItem.tatamiId=userId;
                    previewItem.totalFights=item.getNumberOfFightsInClass() ;
                    previewItemList.add( previewItem );
                }

                
                newaclasses = userFightingclassDao.getCurrentAndPlanedNewaClassesForUser( userId );
                
                for ( Newaclass item : newaclasses )
                {
                    if ( null != item.getStartTime() )
                    {
                        remainingTimeOnTatamiFighting +=
                            item.getNumberOfOpenFightsInClass() * item.getAge().getEstimatedTime();
                    }
                    previewItem = new PreviewItem();
                    if ( item.getStartTime() != null ) previewItem.started=true;
                    previewItem.classId=item.getId();
                    previewItem.className= PreviewWeb.DISCEPLINE_NEWA +" " +item.getAge().getAgeShort() +" " +
                                    CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX_SHORT, item.getSex(), local  )
                                    +" "+ item.getWeightclass();
                    previewItem.discipline="N";
                    previewItem.estimatedTimePerFight=item.getAge().getEstimatedTime();
                    previewItem.openFights=item.getNumberOfOpenFightsInClass();
                    previewItem.openFights=item.getNumberOfOpenFightsInClass();
                    previewItem.order=utcMap.get("N"+userId+item.getId()).getOrder();
                    previewItem.tatamiId=userId;
                    previewItem.totalFights=item.getNumberOfFightsInClass() ;
                    previewItemList.add( previewItem );
                } 
                
                Collections.sort(previewItemList);
                
                for ( PreviewItem item : previewItemList )
                {
                    previewClass = new PreviewWebTatamiClasses();
                    previewClass.setClassName( item.className );
                    previewClass.setTatamiUserId( item.tatamiId );
                    previewClass.setFights( item.openFights + " / " + item.totalFights );

                    openTime = item.openFights * item.estimatedTimePerFight;
                    if ( !item.started ){                        
                        previewClass.setEstStart(simpleDateFormat.format( new Date(System.currentTimeMillis()+remainingTimeOnTatamiFighting*1000) ));                                               
                    }
                    else
                        previewClass.setEstStart(" " );

                    previewClass.setRestOfNeededTime( Utility.getInstance().formatTimeFromSeconds( openTime ) );

                    tatamiClassesList.add( previewClass );
                    remainingTimeOnTatamiFighting+= openTime;
                }
                ret.put( userId, tatamiClassesList );
            }
            return ret;

        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public List<PreviewWeb> getPreviewForTatami( long userId, Locale local )
        throws JJWManagerException
    {
        try
        {
            return PreviewMapper.mapPreviewListFromDB( previewDao.getPreviewForTatami( userId ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void savePreview( Preview preview )
        throws JJWManagerException
    {
        try
        {
            previewDao.savePreview( preview );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void removePreview( long previewId )
        throws JJWManagerException
    {
        try
        {
            previewDao.removePreview( previewId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void movePreviewUp( long previewId )
        throws JJWManagerException
    {
        try
        {
            previewDao.movePreviewUp( previewId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * @return the previewDao
     */
    public PreviewDao getPreviewDao()
    {
        return previewDao;
    }

    /**
     * @param previewDao the previewDao to set
     */
    public void setPreviewDao( PreviewDao previewDao )
    {
        this.previewDao = previewDao;
    }

    /**
     * @return the userFightingclassDao
     */
    public UserFightingclassDao getUserFightingclassDao()
    {
        return userFightingclassDao;
    }

    /**
     * @param userFightingclassDao the userFightingclassDao to set
     */
    public void setUserFightingclassDao( UserFightingclassDao userFightingclassDao )
    {
        this.userFightingclassDao = userFightingclassDao;
    }

    @Override
    public List<PreviewWeb> getPreviewForTatamiClock( long fightId, String discepline, long userId, Locale locale )
        throws JJWManagerException
    {
        try
        {
            return PreviewMapper.mapPreviewListFromDB( previewDao.getPreviewForTatamiClock( fightId, discepline, userId ),
                                                       locale );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }


    private class PreviewItem  implements Comparable<PreviewItem>
    {       
        long classId;
        int estimatedTimePerFight;
        long tatamiId;
        String className="";
        int order;
        String discipline="";
        long openFights;
        long totalFights;
        boolean started=false;
        
        @Override
        public int compareTo( PreviewItem o )
        {
            if  (order > o.order) return 1;
            if  (order == o.order) return 0;
            else return -1;
        }
    }
    
}
