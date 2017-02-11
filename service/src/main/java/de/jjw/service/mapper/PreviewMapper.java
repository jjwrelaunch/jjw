/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PreviewMapper.java
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
package de.jjw.service.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.jjw.model.Preview;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.modelWeb.PreviewWeb;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;

public class PreviewMapper
    implements ICodestableTypeConstants
{

    public static List<List<PreviewWeb>> mapPreviewCompleteListFromDB( List<List<Preview>> previews, Locale local )
    {
        if ( previews == null )
        {
            return null;
        }

        List<List<PreviewWeb>> ret = new ArrayList<List<PreviewWeb>>( previews.size() + 1 );
        List<PreviewWeb> item;
        for ( List<Preview> preview : previews )
        {
            item = mapPreviewListFromDB( preview, local );
            ret.add( item );
        }
        return ret;
    }

    public static List<PreviewWeb> mapPreviewListFromDB( List<Preview> previews, Locale local )
    {
        if ( previews == null )
        {
            return null;
        }

        List<PreviewWeb> ret = new ArrayList<PreviewWeb>( previews.size() + 1 );
        PreviewWeb item;
        for ( Preview preview : previews )
        {
            item = mapPreviewFromDB( preview, local );

            ret.add( item );
        }

        return ret;
    }

    public static PreviewWeb mapPreviewFromDB( Preview preview, Locale local )
    {

        if ( preview == null )
        {
            return null;
        }
        PreviewWeb ret = new PreviewWeb();

        ret.setAgeId( preview.getAgeId() );
        ret.setDuoclassId( preview.getDuoclassId() );
        ret.setFighterIdBlue( preview.getFighterIdBlue() );
        ret.setFighterIdRed( preview.getFighterIdRed() );
        ret.setFightId( preview.getFightId() );
        ret.setFightingclassId( preview.getFightingclassId() );
        ret.setNewaclassId( preview.getNewaclassId() );
        ret.setNameBlue( new String( preview.getNameBlue() ) );
        ret.setNameRed( new String( preview.getNameRed() ) );
        ret.setOrderNumber( preview.getOrderNumber() );
        ret.setSex( new String( preview.getSex() ) );
        ret.setTatami( new String( preview.getTatami() ) );

        if ( !TypeUtil.isEmptyOrDefault( preview.getFightingclassId() ) )
        {
            ret.setDiscepline( PreviewWeb.DISCEPLINE_FIGHTING );
            ret.setClassDescription( PreviewWeb.DISCEPLINE_FIGHTING +" "+ preview.getAge().getAgeShort() +" " 
                + CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX_SHORT, preview.getSex(), local )
                + " "
                                     +preview.getFightingclass().getWeightclass() );
        }
        if ( !TypeUtil.isEmptyOrDefault( preview.getDuoclassId() ) )
        {
            ret.setDiscepline( PreviewWeb.DISCEPLINE_DUO );
            ret.setClassDescription( preview.getDuoclass().getDuoclassName() );
        }

        if ( !TypeUtil.isEmptyOrDefault( preview.getNewaclassId() ) )
        {
            ret.setDiscepline( PreviewWeb.DISCEPLINE_NEWA );
            ret.setClassDescription( PreviewWeb.DISCEPLINE_NEWA +" "+preview.getAge().getAgeShort() + " "
                + CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX_SHORT, preview.getSex(), local )
                + " " + preview.getNewaclass().getWeightclass() );
        }

        ret.setUserIdOfTatami( Long.valueOf( preview.getUserIdOfTatami() ) );

        if ( preview.getCreateDate() != null )
        {
            ret.setCreateDate( new Timestamp( preview.getCreateDate().getTime() ) );
        }
        if ( preview.getCreateId() != null )
        {
            ret.setCreateId( Long.valueOf( preview.getCreateId() ) );
        }
        if ( preview.getId() != null )
        {
            ret.setId( Long.valueOf( preview.getId() ) );
        }
        if ( preview.getModificationDate() != null )
        {
            ret.setModificationDate( new Timestamp( preview.getModificationDate().getTime() ) );
        }
        if ( preview.getModificationId() != null )
        {
            ret.setModificationId( Long.valueOf( preview.getModificationId() ) );
        }
        return ret;
    }

    public static void mapPreviewToDB( Preview preview, Preview previewDB, boolean deepMapping )
    {

        previewDB.setAgeId( preview.getAgeId() );
        previewDB.setDuoclassId( preview.getDuoclassId() );
        previewDB.setFighterIdBlue( preview.getFighterIdBlue() );
        previewDB.setFighterIdRed( preview.getFighterIdRed() );
        previewDB.setFightId( preview.getFightId() );
        previewDB.setFightingclassId( preview.getFightingclassId() );
        previewDB.setNewaclassId( preview.getNewaclassId() );
        previewDB.setNameBlue( new String( preview.getNameBlue() ) );
        previewDB.setNameRed( new String( preview.getNameRed() ) );
        previewDB.setOrderNumber( preview.getOrderNumber() );
        previewDB.setSex( preview.getSex() );
        previewDB.setTatami( new String( preview.getTatami() ) );

        previewDB.setUserIdOfTatami( Long.valueOf( preview.getUserIdOfTatami() ) );
        previewDB.setCreateDate( new Timestamp( preview.getCreateDate().getTime() ) );
        previewDB.setCreateId( Long.valueOf( preview.getCreateId() ) );
        previewDB.setId( Long.valueOf( preview.getId() ) );
        previewDB.setModificationDate( new Timestamp( preview.getModificationDate().getTime() ) );
        previewDB.setModificationId( Long.valueOf( preview.getModificationId() ) );
    }

}
