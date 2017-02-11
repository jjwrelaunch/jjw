/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PreviewDaoHibernateo.java
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

package de.jjw.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.PreviewDao;
import de.jjw.dao.hibernate.duo.IUserDuoclassDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IUserFightingclassDatabaseConstants;
import de.jjw.dao.hibernate.newa.IUserNewaclassDatabaseConstants;
import de.jjw.model.Preview;
import de.jjw.model.User;
import de.jjw.model.duo.UserDuoclass;
import de.jjw.model.fighting.UserFightingclass;
import de.jjw.model.newa.UserNewaclass;
import de.jjw.util.TypeUtil;

public class PreviewDaoHibernate
    extends BaseDaoHibernate
    implements PreviewDao
{
    private static String GET_ALL_PREVIEWS_SQL = "from Preview p order by p.tatami, p.orderNumber";
    @Override
    public List<List<Preview>> getAllPreviews()
        throws JJWDataLayerException
    {
        List<List<Preview>> retlist = new ArrayList<List<Preview>>( 5 );
        try
        {
            Query q = getSession().createQuery( GET_ALL_PREVIEWS_SQL );
            List<Preview> allList = (List<Preview>) q.list();

            List<Preview> tempList = null;
            Preview tempPreview = null;
            for ( Preview item : allList )
            {
                if ( tempPreview == null )
                {
                    tempList = new ArrayList<Preview>( 5 );
                    tempList.add( item );
                    tempPreview = item;
                }
                else
                {
                    if ( item.getTatami().equals( tempPreview.getTatami() ) )
                    {
                        tempList.add( item );
                        tempPreview = item;
                    }
                    else
                    {
                        retlist.add( tempList );
                        tempList = new ArrayList<Preview>( 5 );
                        tempList.add( item );
                        tempPreview = item;
                    }
                }
            }
            if ( tempList != null )
                retlist.add( tempList );

        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate", e );
            throw new JJWDataLayerException( e );
        }
        return retlist;
    }

    private static String GET_PREVIEW_FOR_TATAMI_SQL =
        "from Preview p where p.modificationId =? order by p.tatami, p.orderNumber";
    @Override
    public List<Preview> getPreviewForTatami( long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( GET_PREVIEW_FOR_TATAMI_SQL );
            q.setLong( 0, userId );
            return (List<Preview>) q.list();
        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_NEXT_ORDERNUMBER_SQL = "Select max(p.orderNumber) from Preview p";

    private static String EXIST_FIGHT_IN_PREVIEW_SQL =
        "Select id from Preview where duoclassId =0 and newaclassId =0  and fightId=?";

    private static String EXIST_DUOFIGHT_IN_PREVIEW_SQL =
        "Select id from Preview where fightingclassId =0 and newaclassId =0 and fightId=?";

    private static String EXIST_NEWAFIGHT_IN_PREVIEW_SQL =
        "Select id from Preview where fightingclassId =0 and duoclassId =0 and fightId=?";
    @Override
    public void savePreview( Preview preview )
        throws JJWDataLayerException
    {
        try
        {
            Query q2 = null;
            if ( TypeUtil.isEmptyOrDefault( preview.getDuoclassId() )
                && TypeUtil.isEmptyOrDefault( preview.getNewaclassId() ) )
                q2 = getSession().createQuery( EXIST_FIGHT_IN_PREVIEW_SQL );

            if ( TypeUtil.isEmptyOrDefault( preview.getFightingclassId() )
                && TypeUtil.isEmptyOrDefault( preview.getNewaclassId() ) )
                q2 = getSession().createQuery( EXIST_DUOFIGHT_IN_PREVIEW_SQL );

            if ( TypeUtil.isEmptyOrDefault( preview.getFightingclassId() )
                && TypeUtil.isEmptyOrDefault( preview.getDuoclassId() ) )
                q2 = getSession().createQuery( EXIST_NEWAFIGHT_IN_PREVIEW_SQL );

            q2.setLong( 0, preview.getFightId() );
            if ( q2.uniqueResult() != null )
                return; // fight already exists in preview

            Query q = getSession().createQuery( GET_NEXT_ORDERNUMBER_SQL );
            if ( q.uniqueResult() == null )
                preview.setOrderNumber( 1 );
            else
                preview.setOrderNumber( ( (Integer) q.uniqueResult() ) + 1 );

            getHibernateTemplate().saveOrUpdate( preview );
        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate", e );
            throw new JJWDataLayerException( e );
        }
    }

    private Preview getPreview( long previewId )
        throws JJWDataLayerException
    {
        try
        {
            return (Preview) getHibernateTemplate().get( Preview.class, previewId );
        }
        catch ( Exception e )
        {
            throw new JJWDataLayerException( e );
        }
    }

    @Override
    public void removePreview( long previewId )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().delete( getPreview( previewId ) );
        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_PREVIEW_BY_FIGHTID_FIGHTING_SQL =
        "from Preview p where p.fightingclassId > 0 and p.fightId=?";

    private static String GET_PREVIEW_BY_FIGHTID_DUO_SQL = "from Preview p where p.duoclassId > 0 and p.fightId=?";

    private static String GET_PREVIEW_BY_FIGHTID_NEWA_SQL = "from Preview p where p.newaclassId > 0 and p.fightId=?";

    public List<Preview> getPreviewByFightId( long fightId, String dicepline )
    throws JJWDataLayerException
    {
        try
        {
            if ( Preview.DISCEPLINE_FIGHTING.equals( dicepline ) )
            {
                Query q = getSession().createQuery( GET_PREVIEW_BY_FIGHTID_FIGHTING_SQL );
                q.setLong( 0, fightId );
                return (List<Preview>) q.list();
            }
            if ( Preview.DISCEPLINE_DUO.equals( dicepline ) )
            {
                Query q = getSession().createQuery( GET_PREVIEW_BY_FIGHTID_DUO_SQL );
                q.setLong( 0, fightId );
                return (List<Preview>) q.list();
            }

            if ( Preview.DISCEPLINE_NEWA.equals( dicepline ) )
            {
                Query q = getSession().createQuery( GET_PREVIEW_BY_FIGHTID_NEWA_SQL );
                q.setLong( 0, fightId );
                return (List<Preview>) q.list();
            }
            return new ArrayList<Preview>();
        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void removePreview( long fightId, String dicepline )
        throws JJWDataLayerException
    {
        try
        {
            boolean isPreview = true;
            List<Preview> list = new ArrayList<Preview>();
            try
            {
                list = getPreviewByFightId( fightId, dicepline );
            }
            catch ( Exception e )
            {
                isPreview = false;
            }
            if ( isPreview )// only delete when a preview was found
            {
                for ( Preview item : list )
                {
                    getHibernateTemplate().delete( getPreview( item.getId() ) );
                }
            }
        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_DECESTOR_PREVIEW_SQL =
        "Select id from Preview where orderNumber =( Select max(p.orderNumber) from Preview p where p.modificationId =? and p.orderNumber<?)";
    @Override
    public void movePreviewUp( long previewId )
        throws JJWDataLayerException
    {
        try
        {
            Preview previewToMoveUp = getPreview( previewId );
            Query q = getSession().createQuery( GET_DECESTOR_PREVIEW_SQL );
            q.setLong( 0, previewToMoveUp.getModificationId() );
            q.setInteger( 1, previewToMoveUp.getOrderNumber() );

            if ( q.uniqueResult() == null )
                return;

            Preview previewToMoveDown = getPreview( (Long) q.uniqueResult() );
            int temp = previewToMoveUp.getOrderNumber();
            previewToMoveUp.setOrderNumber( previewToMoveDown.getOrderNumber() );
            previewToMoveDown.setOrderNumber( temp );
        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate",e );
            throw new JJWDataLayerException( e );
        }

    }

    private static String GET_PREVIEW_FOR_TATAMI_CLOCK_FIGHTING_SQL =
    // "from Preview p where p.modificationId =? and p.fightId <>?  order by p.tatami, p.orderNumber";
        "from Preview p where p.id not in (select pp.id from Preview pp where pp.fightId =? and fightingclassId>0 ) and p.modificationId =? order by p.tatami, p.orderNumber";

    private static String GET_PREVIEW_FOR_TATAMI_CLOCK_DUO_SQL =
        "from Preview p where p.id not in (select pp.id from Preview pp where pp.fightId =? and duoclassId>0 ) and p.modificationId =? order by p.tatami, p.orderNumber";

    private static String GET_PREVIEW_FOR_TATAMI_CLOCK_NEWA_SQL =
        "from Preview p where p.id not in (select pp.id from Preview pp where pp.fightId =? and newaclassId>0 ) and p.modificationId =? order by p.tatami, p.orderNumber";

    @Override
    public List<Preview> getPreviewForTatamiClock( long fightId, String discepline, long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            if ( Preview.DISCEPLINE_FIGHTING.equals( discepline ) )
                q = getSession().createQuery( GET_PREVIEW_FOR_TATAMI_CLOCK_FIGHTING_SQL );
            if ( Preview.DISCEPLINE_DUO.equals( discepline ) )
                q = getSession().createQuery( GET_PREVIEW_FOR_TATAMI_CLOCK_DUO_SQL );
            if ( Preview.DISCEPLINE_NEWA.equals( discepline ) )
                q = getSession().createQuery( GET_PREVIEW_FOR_TATAMI_CLOCK_NEWA_SQL );
            q.setLong( 1, userId );
            q.setLong( 0, fightId );
            return (List<Preview>) q.list();
        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_FIGHTING_USER = SQL_FROM + IUserFightingclassDatabaseConstants.TABLE_USERFIGHTINGCLASS;

    private static String GET_DUO_USER = SQL_FROM + IUserDuoclassDatabaseConstants.TABLE_USERDUOGCLASS;

    private static String GET_NEWA_USER = SQL_FROM + IUserNewaclassDatabaseConstants.TABLE_USERNEWACLASS;

    @SuppressWarnings( "unchecked" )
    public List<Long> getUserWithAssignedClasses()
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            q = getSession().createQuery( GET_FIGHTING_USER );
            Map<Long, Long> userMap = new HashMap<Long, Long>();

            for ( UserFightingclass item : (List<UserFightingclass>) q.list() )
            {
                userMap.put( item.getUserId(), item.getUserId() );
            }

            q = getSession().createQuery( GET_DUO_USER );
            for ( UserDuoclass item : (List<UserDuoclass>) q.list() )
            {
                userMap.put( item.getUserId(), item.getUserId() );
            }

            q = getSession().createQuery( GET_NEWA_USER );
            for ( UserNewaclass item : (List<UserNewaclass>) q.list() )
            {
                userMap.put( item.getUserId(), item.getUserId() );
            }

            List<Long> ret = new ArrayList<Long>();
            for ( Long item : userMap.values() )
            {
                ret.add( item );
            }

            return ret;
        }
        catch ( Exception e )
        {
            log.error( "PreviewDaoHibernate", e );
            throw new JJWDataLayerException( e );
        }

    }

}
