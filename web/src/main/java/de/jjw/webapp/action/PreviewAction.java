/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PreviewAction.java
 * Created : 06 Feb 2011
 * Last Modified: Sun, 06 Feb 2011 20:55:45
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

package de.jjw.webapp.action;

import java.util.ArrayList;
import java.util.List;

import de.jjw.service.PreviewManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.PreviewWeb;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.validation.ErrorElement;

public class PreviewAction
    extends BasePage
{
    private PreviewManager previewManager;

    private static String ALL_PREVIEWS = "allPreviews";

    private static String TATAMI_PREVIEW = "tatamiPreview";

    private static String RELOAD_TATAMI_PREVIEW = "tatamiPreviewReload";

    private List<List<PreviewWeb>> previews;

    private List<PreviewWeb> preview = new ArrayList<PreviewWeb>();
    
    public List<List<PreviewWeb>> getPreviews()
    {
        if ( previewManager == null )
        {
            log.warn( "keine previewManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_PREVIEWS ) == null )
        {
            try
            {
                previews = previewManager.getAllPreviews( getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_PREVIEWS, previews );
        }
        else
            previews = (List<List<PreviewWeb>>) getRequest().getAttribute( ALL_PREVIEWS );
        return previews;
    }

    
    public List<PreviewWeb> getPreviewForTatami()
    {
        if ( previewManager == null )
        {
            log.warn( "keine previewManager Injection" );
        }
        if ( getRequest().getAttribute( TATAMI_PREVIEW ) == null )
        {
            try
            {
                preview =
                    previewManager.getPreviewForTatami( WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId(),
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

    public boolean isRenderPreviewForTatami()
    {
        if ( getPreviewForTatami().isEmpty() )
            return false;
        else
            return true;
    }

    public String movePreviewUp()
    {
        try
        {
            previewManager.movePreviewUp( ( (PreviewWeb) dataTable.getRowData() ).getId() );
        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            return null;
        }
        return RELOAD_TATAMI_PREVIEW;
    }

    public String deletePreview()
    {
        try
        {
            previewManager.removePreview( ( (PreviewWeb) dataTable.getRowData() ).getId() );
        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            return null;
        }
        return RELOAD_TATAMI_PREVIEW;
    }

    public String getPreview()
    {
        return null;
    }

    /**
     * @return the previewManager
     */
    public PreviewManager getPreviewManager()
    {
        return previewManager;
    }

    /**
     * @param previewManager the previewManager to set
     */
    public void setPreviewManager( PreviewManager previewManager )
    {
        this.previewManager = previewManager;
    }

}
