/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionConverterForFighter.java
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

package de.jjw.webapp.converter;

import de.jjw.model.admin.Region;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.admin.FighterAction;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

public class RegionConverterForFighter
    extends RegionConverter
{

    public static final String CONVERTER_ID = "jjw.webapp.converter.RegionConverterForFighter";

    private static String SPLIT_CONSTANT_FOR_ACTION = ":";

    public Object getAsObject( FacesContext arg0, UIComponent arg1, String arg2 )
        throws ConverterException
    {

        String action = arg1.getClientId( arg0 ).split( SPLIT_CONSTANT_FOR_ACTION )[1];
        List regions = ( (FighterAction) ( ( (HttpServletRequest) arg0.getExternalContext().getRequest() ).getAttribute(
            action ) ) ).getRegionList();

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


}
