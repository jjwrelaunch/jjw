/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DataModelDuo.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

import de.jjw.model.duo.UserDuoclass;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.util.JJWTwoLongDTO;

public class DataModelDuo
{

    ListDataModel modelHead = new ListDataModel();

    ListDataModel modelUserValue = new ListDataModel();

    ListDataModel modelValue = new ListDataModel();

    ListDataModel estimatedTatamiTime = new ListDataModel();

    Map<JJWTwoLongDTO, UserDuoclass> assignMap = new HashMap<JJWTwoLongDTO, UserDuoclass>();

    UIData dataTable = null;


    public Map<JJWTwoLongDTO, UserDuoclass> getAssignMap()
    {
        return assignMap;
    }

    public void setAssignMap( Map<JJWTwoLongDTO, UserDuoclass> assignMap )
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

    public DuoclassWeb getModelDescriptionValue()
    {
        try
        {
            return ( (DuoclassWeb) modelValue.getRowData() );
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
        DuoclassWeb fc = ( (DuoclassWeb) modelValue.getRowData() );
        UserWeb user = ( (List<UserWeb>) modelUserValue.getWrappedData() ).get( modelHead.getRowIndex() );
        if ( modelValue.isRowAvailable() )
        {
            return assignMap.get( new JJWTwoLongDTO( fc.getId(), user.getId() ) ) != null ? "true" : "false";

        }
        return null;
    }
    
    public String getModelCellValueOrder()
    {
        try
        {
            DuoclassWeb fc = ( (DuoclassWeb) modelValue.getRowData() );
           
            UserWeb user = ( (List<UserWeb>) modelUserValue.getWrappedData() ).get( modelHead.getRowIndex() );
            if ( modelValue.isRowAvailable() )
            {
                return String.valueOf( ( (UserDuoclass) assignMap.get( new JJWTwoLongDTO( fc.getId(),
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
