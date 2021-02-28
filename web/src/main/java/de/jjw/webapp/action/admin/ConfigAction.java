/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConfigAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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
import java.util.Vector;

import javax.faces.model.SelectItem;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.util.ConfigMain;

public class ConfigAction
    extends BasePage
    implements Serializable, IGlobalWebConstants
{

    ConfigManager configManager;

    ConfigJJW config = new ConfigJJW();

    public ConfigJJW getConfig()
    {
        if ( configManager == null )
        {
            log.warn( "keine configManager Injection" );
        }

        config = ConfigMain.getInstance().getEventConfiguration();

        return config;
    }


    public String configSave()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {
        	String[] deadline= config.getDeadline().split("/");
        	int day =Integer.valueOf(deadline[0]);
        	int month =Integer.valueOf(deadline[1]);
            if (day<1 || day> 31 || month < 1 || month> 12) throw new Exception("wrong deadline"); 
            
        	configManager.saveConfig( new ServiceExchangeContext(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ), config );

        }
        catch ( JJWManagerException e )
        {
        	errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( OptimisticLockingException e )
        {
            errors.add( new ErrorElement( GLO_OPTIMISTIC_LOCKING ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }

        return SUCCESS;
    }

    public ConfigManager getConfigManager()
    {
        return configManager;
    }

    public void setConfigManager( ConfigManager configManager )
    {
        this.configManager = configManager;
    }

    public void setConfig( ConfigJJW config )
    {
        this.config = config;
    }

    public List getCertificationTypeList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_CER, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( Integer.valueOf( entry.getId() ), entry.getValue(), entry.getValue() ) );
        }
        return ret;
    }

    public List getLogoList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_LOG, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( Integer.valueOf( entry.getId() ), entry.getValue(), entry.getValue() ) );
        }
        return ret;
    }

    public List getRevengeList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_REV, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( Integer.valueOf( entry.getId() ), entry.getValue(), entry.getValue() ) );
        }
        return ret;
    }

}
