/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionConverter.java
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

package de.jjw.webapp.converter;

import de.jjw.model.admin.Region;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.BasePageMother;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

public class RegionConverter
    implements Converter
{
    public static final String CONVERTER_ID = "jjw.webapp.converter.RegionConverter";

    private static String SPLIT_CONSTANT_FOR_ACTION = ":";

    public Object getAsObject( FacesContext arg0, UIComponent arg1, String arg2 )
        throws ConverterException
    {

        String action = arg1.getClientId( arg0 ).split( SPLIT_CONSTANT_FOR_ACTION )[1];
        List regions =
            ( (BasePageMother) ( ( (HttpServletRequest) arg0.getExternalContext().getRequest() ).getAttribute(
                action ) ) ).getRegionListALL();

        // arg1.getParent().findComponent("country").getValueExpression("#{adminTeamAction.team.country}")

        Iterator it = regions.iterator();

        SelectItem selItem;
        while ( it.hasNext() )
        {
            selItem = (SelectItem) it.next();

            Region regionFromList = (Region) selItem.getValue();
            if ( TypeUtil.isEmpty( arg2 ) )
            {
                return null;
            }
            else
            {
                if ( regionFromList.getId().equals( new Long( arg2 ) ) )
                {
                    return regionFromList;
                }
            }
        }
        return null;
    }

    public String getAsString( FacesContext arg0, UIComponent arg1, Object arg2 )
        throws ConverterException
    {
        if ( !TypeUtil.isEmpty( arg2 ) )
        {
            if ( TypeUtil.isEmpty( ( (Region) arg2 ).getId() ) )
            {
                return TypeUtil.STRING_DEFAULT;
            }
            return ( (Region) arg2 ).getId().toString();
        }
        return null;
    }
}
