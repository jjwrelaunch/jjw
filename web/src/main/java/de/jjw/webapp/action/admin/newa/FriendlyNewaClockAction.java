/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyNewaClockAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:54:40
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

package de.jjw.webapp.action.admin.newa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIData;

import de.jjw.model.admin.ConfigJJW;
import de.jjw.service.PreviewManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.NewaFightWeb;
import de.jjw.service.modelWeb.PreviewWeb;
import de.jjw.service.newa.FriendlyNewaFightManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IAgeConstants;
import de.jjw.webapp.util.ConfigMain;

public class FriendlyNewaClockAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IAgeConstants
{
    private FriendlyNewaFightManager friendlyNewaFightManager;

    private PreviewManager previewManager;

    private static String TATAMI_PREVIEW = "tatamiPreview";

    private static String FIGHT = "_fight_";

    NewaFightWeb fight = null;

    long fightId;

    protected UIData dataTable2;

    public NewaFightWeb getFight()
    {
        NewaFightWeb fight = null;
        if ( friendlyNewaFightManager == null )
        {
            log.warn( "keine friendlyNewaFightManager Injection" );
        }
        if ( getRequest().getAttribute( FIGHT ) == null )
        {
            try
            {
                fight =
                    friendlyNewaFightManager.getFightForClock( Long.valueOf( getRequest().getParameter( "fightId" ) ),
                                                           getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( FIGHT, fight );
            return fight;
        }
        else
        {
            return (NewaFightWeb) getRequest().getAttribute( FIGHT );
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
        try
        {
            switch ( ConfigMain.getInstance().getEventConfiguration().getLogo() )
            {
                case ConfigJJW.TEAM_LOGO:
                    StringBuffer sbRed = new StringBuffer( 50 );

                    if ( ( (NewaFightWeb) getRequest().getAttribute( FIGHT ) ).getFighterRed().getTeam().getLogo() != null )
                    {
                        sbRed.append( "/image?id=" );
                        sbRed.append( ( (NewaFightWeb) getRequest().getAttribute( FIGHT ) ).getFighterRed().getTeam().getId() );
                    }
                    else
                    {
                        sbRed.append( "/images/country/" );
                        sbRed.append( ( (NewaFightWeb) getRequest().getAttribute( FIGHT ) ).getFighterRed().getTeam().getCountry().getCountryShort() );
                        sbRed.append( ".gif" );
                    }
                    return sbRed.toString();

                case ConfigJJW.COUNTRY_LOGO:
                    StringBuffer sb = new StringBuffer( 50 );
                    sb.append( "/images/country/" );
                    sb.append( ( (NewaFightWeb) getRequest().getAttribute( FIGHT ) ).getFighterRed().getTeam().getCountry().getCountryShort() );
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
        try
        {
            switch ( ConfigMain.getInstance().getEventConfiguration().getLogo() )
            {
                case ConfigJJW.TEAM_LOGO:
                    StringBuffer sbBlue = new StringBuffer( 50 );

                    if ( ( (NewaFightWeb) getRequest().getAttribute( FIGHT ) ).getFighterBlue().getTeam().getLogo() != null )
                    {
                        sbBlue.append( "/image?id=" );
                        sbBlue.append( ( (NewaFightWeb) getRequest().getAttribute( FIGHT ) ).getFighterBlue().getTeam().getId() );
                    }
                    else
                    {
                        sbBlue.append( "/images/country/" );
                        sbBlue.append( ( (NewaFightWeb) getRequest().getAttribute( FIGHT ) ).getFighterBlue().getTeam().getCountry().getCountryShort() );
                        sbBlue.append( ".gif" );
                    }
                    return sbBlue.toString();

                case ConfigJJW.COUNTRY_LOGO:
                    StringBuffer sb = new StringBuffer( 50 );
                    sb.append( "/images/country/" );
                    sb.append( ( (NewaFightWeb) getRequest().getAttribute( FIGHT ) ).getFighterBlue().getTeam().getCountry().getCountryShort() );
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
                                                             PreviewWeb.DISCEPLINE_FIGHTING,
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
     * @param fightManager The fightManager to set.
     */
    public void setFriendlyNewaFightManager( FriendlyNewaFightManager friendlyNewaFightManager )
    {
        this.friendlyNewaFightManager = friendlyNewaFightManager;
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

}
