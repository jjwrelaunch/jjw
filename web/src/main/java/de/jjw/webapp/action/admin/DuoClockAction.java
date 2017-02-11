/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoClockAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:54:41
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

package de.jjw.webapp.action.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIData;

import de.jjw.model.admin.ConfigJJW;
import de.jjw.service.PreviewManager;
import de.jjw.service.duo.DuoFightManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.DuoFightWeb;
import de.jjw.service.modelWeb.PreviewWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IAgeConstants;
import de.jjw.webapp.util.ConfigMain;

public class DuoClockAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IAgeConstants
{
    DuoFightManager duoFightManager;

    public static final String FIGHT = "_fight_";

    long fightId;

    private PreviewManager previewManager;

    protected UIData dataTable2;

    private static String TATAMI_PREVIEW = "tatamiPreview";

    public DuoFightWeb getFight()
    {
        DuoFightWeb fight = null;
        if ( duoFightManager == null )
        {
            log.warn( "keine duoFightManager Injection" );
        }
        if ( getRequest().getAttribute( FIGHT ) == null )
        {
            try
            {
                fight = duoFightManager.getDuoFightForClock( Long.valueOf( getRequest().getParameter( "fightId" ) ) );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            catch ( Exception e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( FIGHT, fight );
            return fight;
        }
        else
        {
            return (DuoFightWeb) getRequest().getAttribute( FIGHT );
        }
    }

    public String getImageCommandRedWithContext()
    {
        String retValue = getImageCommandRed();
        if ( null == retValue )
            return null;
        return getFacesContext().getExternalContext().getRequestContextPath() + retValue;
    }

    public String getImageCommandRed()
    {
        StringBuffer sbRed = new StringBuffer( 50 );

        try
        {
            switch ( ConfigMain.getInstance().getEventConfiguration().getLogo() )
            {
                case ConfigJJW.TEAM_LOGO:
                    if ( ( (DuoFightWeb) getRequest().getAttribute( FIGHT ) ).getDuoTeamRed().getTeam().getLogo() != null )
                    {
                        sbRed.append( "/image?id=" );
                        sbRed.append( ( (DuoFightWeb) getRequest().getAttribute( FIGHT ) ).getDuoTeamRed().getTeam().getId() );
                    }
                    else
                    {
                        sbRed.append( "/images/country/" );
                        sbRed.append( ( (DuoFightWeb) getRequest().getAttribute( FIGHT ) ).getDuoTeamRed().getTeam().getCountry().getCountryShort() );
                        sbRed.append( ".gif" );
                    }
                    return sbRed.toString();

                case ConfigJJW.COUNTRY_LOGO:
                    StringBuffer sb = new StringBuffer( 50 );
                    sb.append( "/images/country/" );
                    sb.append( ( (DuoFightWeb) getRequest().getAttribute( FIGHT ) ).getDuoTeamRed().getTeam().getCountry().getCountryShort() );
                    sb.append( ".gif" );
                    return sb.toString();
            }
        }
        catch ( NullPointerException e )
        {
            return null;
        }
        return null;
    }

    public String getImageCommandBlueWithContext()
    {
        String retValue = getImageCommandBlue();
        if ( null == retValue )
            return null;
        return getFacesContext().getExternalContext().getRequestContextPath() + retValue;
    }

    public String getImageCommandBlue()
    {
        StringBuffer sbBlue = new StringBuffer( 50 );

        try
        {
            switch ( ConfigMain.getInstance().getEventConfiguration().getLogo() )
            {
                case ConfigJJW.TEAM_LOGO:
                    if ( ( (DuoFightWeb) getRequest().getAttribute( FIGHT ) ).getDuoTeamBlue().getTeam().getLogo() != null )
                    {
                        sbBlue.append( "/image?id=" );
                        sbBlue.append( ( (DuoFightWeb) getRequest().getAttribute( FIGHT ) ).getDuoTeamBlue().getTeam().getId() );
                    }
                    else
                    {
                        sbBlue.append( "/images/country/" );
                        sbBlue.append( ( (DuoFightWeb) getRequest().getAttribute( FIGHT ) ).getDuoTeamBlue().getTeam().getCountry().getCountryShort() );
                        sbBlue.append( ".gif" );
                    }
                    return sbBlue.toString();

                case ConfigJJW.COUNTRY_LOGO:
                    StringBuffer sb = new StringBuffer( 50 );
                    sb.append( "/images/country/" );
                    sb.append( ( (DuoFightWeb) getRequest().getAttribute( FIGHT ) ).getDuoTeamBlue().getTeam().getCountry().getCountryShort() );
                    sb.append( ".gif" );
                    return sb.toString();
            }
        }
        catch ( NullPointerException e )
        {
            return null;
        }
        return null;
    }

    public List<PreviewWeb> getPreviewForTatami()
    {
        List<PreviewWeb> preview = null;
        if ( previewManager == null )
        {
            log.warn( "keine previewManager Injection" );
        }
        if ( getRequest().getAttribute( TATAMI_PREVIEW ) == null )
        {
            try
            {
                preview =
                    previewManager.getPreviewForTatamiClock( getFight().getId(),
                                                             PreviewWeb.DISCEPLINE_DUO,
                                                             WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId(),
                                                             getLocale() );
                if ( preview == null )
                    preview = new ArrayList<PreviewWeb>();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }

            getRequest().setAttribute( TATAMI_PREVIEW, preview );
        }
        else
            preview = (List<PreviewWeb>) getRequest().getAttribute( TATAMI_PREVIEW );
        return preview;
    }


    /**
     * @param fightManager The ageManager to set.
     */
    public void setDuoFightManager( DuoFightManager fightManager )
    {
        this.duoFightManager = fightManager;
    }

    /**
     * @param previewManager the previewManager to set
     */
    public void setPreviewManager( PreviewManager previewManager )
    {
        this.previewManager = previewManager;
    }

    /**
     * @return the dataTable2
     */
    public UIData getDataTable2()
    {
        return dataTable2;
    }

    /**
     * @param dataTable2 the dataTable2 to set
     */
    public void setDataTable2( UIData dataTable2 )
    {
        this.dataTable2 = dataTable2;
    }
    
    public boolean isVideoWeb()
    {
        return (ConfigMain.getInstance().getEventConfiguration().isVideoOn()?true:false);
    }
}
