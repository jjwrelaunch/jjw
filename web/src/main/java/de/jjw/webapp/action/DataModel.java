/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DataModel.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIData;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import de.jjw.model.fighting.UserFightingclass;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.util.JJWTwoLongDTO;
import de.jjw.webapp.action.validation.ErrorElement;

public class DataModel
{

    protected final Logger log = Logger.getRootLogger();
    
    ListDataModel modelHead = new ListDataModel();

    ListDataModel modelUserValue = new ListDataModel();

    ListDataModel modelValue = new ListDataModel();

    ListDataModel estimatedTatamiTime = new ListDataModel();

    Map<JJWTwoLongDTO, UserFightingclass> assignMap = new HashMap<JJWTwoLongDTO, UserFightingclass>();

    UIData dataTable = null;


    public Map<JJWTwoLongDTO, UserFightingclass> getAssignMap()
    {
        return assignMap;
    }

    public void setAssignMap( Map<JJWTwoLongDTO, UserFightingclass> assignMap )
    {
        this.assignMap = assignMap;
    }

    public ListDataModel getModelHead()
    {
        return modelHead;
    }

    public void setModelHead( ListDataModel modelHead )
    {
        this.modelHead = modelHead;
    }


    public ListDataModel getModelValue()
    {
        return modelValue;
    }

    public void setModelValue( ListDataModel modelValue )
    {
        this.modelValue = modelValue;
    }

    public FightingclassWeb getModelDescriptionValue()
    {
        try
        {
            return ( (FightingclassWeb) modelValue.getRowData() );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public String getModelHeadValue()
    {
        try
        {
            return ( (String) this.modelHead.getRowData() );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public String getModelCellValue()
    {
        try
        {
            FightingclassWeb fc = ( (FightingclassWeb) modelValue.getRowData() );
            // FightingclassWeb fc = ((List<FightingclassWeb>)modelValue.getWrappedData()).get(modelHead.getRowIndex());
            // UserWeb user = ((UserWeb) modelUserValue.getRowData());
            UserWeb user = ( (List<UserWeb>) modelUserValue.getWrappedData() ).get( modelHead.getRowIndex() );
            if ( modelValue.isRowAvailable() )
            {
                return assignMap.get( new JJWTwoLongDTO( fc.getId(), user.getId() ) ) != null ? "true" : "false";

            }
            return null;
        }
        catch ( Exception e )
        {
            log.error( e.getStackTrace() );
            return null;
        }
    }
    
    public String getModelCellValueOrder()
    {
        try
        {
            FightingclassWeb fc = ( (FightingclassWeb) modelValue.getRowData() );
            // FightingclassWeb fc = ((List<FightingclassWeb>)modelValue.getWrappedData()).get(modelHead.getRowIndex());
            // UserWeb user = ((UserWeb) modelUserValue.getRowData());
            UserWeb user = ( (List<UserWeb>) modelUserValue.getWrappedData() ).get( modelHead.getRowIndex() );
            if ( modelValue.isRowAvailable() )
            {
                return String.valueOf( ( (UserFightingclass) assignMap.get( new JJWTwoLongDTO( fc.getId(),
                                                                                               user.getId() ) ) ).getOrderNumber() );

            }
            return null;
        }
        catch ( Exception e )
        {
            
            return null;
        }
    }

    public void setModelCellValue( String value )
    {
        return;
    }

    public ListDataModel getModelUserValue()
    {
        return modelUserValue;
    }

    public void setModelUserValue( ListDataModel modelUserValue )
    {
        this.modelUserValue = modelUserValue;
    }

    public UIData getDataTable()
    {
        return dataTable;
    }

    public void setDataTable( UIData dataTable )
    {
        this.dataTable = dataTable;
    }

    public ListDataModel getEstimatedTatamiTime()
    {
        return estimatedTatamiTime;
    }

    public void setEstimatedTatamiTime( ListDataModel estimatedTatamiTime )
    {
        this.estimatedTatamiTime = estimatedTatamiTime;
    }

    public String getEstimatedTatamiTimeValue()
    {
        try
        {
            return ( (List<String>) this.estimatedTatamiTime.getWrappedData() ).get( modelHead.getRowIndex() );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

}
