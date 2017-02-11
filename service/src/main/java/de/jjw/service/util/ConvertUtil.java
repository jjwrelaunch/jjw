/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConvertUtil.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.service.util;

import java.beans.PropertyDescriptor;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.aop.support.AopUtils;

import de.jjw.model.BaseObject;
import de.jjw.model.LabelValue;

/**
 * Utility class to convert one object to another.
 * <p/>
 * <p>
 * <a href="ConvertUtil.java.html"><i>View Source</i></a>
 * </p>
 */
public final class ConvertUtil
{
    // ~ Static fields/initializers =============================================

    protected final static Logger log = Logger.getRootLogger();

    // ~ Methods ================================================================

    /**
     * Method to convert a ResourceBundle to a Map object.
     * 
     * @param rb a given resource bundle
     * @return Map a populated map
     */
    public static Map convertBundleToMap( ResourceBundle rb )
    {
        Map map = new HashMap();

        for ( Enumeration keys = rb.getKeys(); keys.hasMoreElements(); )
        {
            String key = (String) keys.nextElement();
            map.put( key, rb.getString( key ) );
        }

        return map;
    }

    public static Map convertListToMap( List list )
    {
        Map map = new LinkedHashMap();

        for ( Iterator it = list.iterator(); it.hasNext(); )
        {
            LabelValue option = (LabelValue) it.next();
            map.put( option.getLabel(), option.getValue() );
        }

        return map;
    }

    /**
     * Method to convert a ResourceBundle to a Properties object.
     * 
     * @param rb a given resource bundle
     * @return Properties a populated properties object
     */
    public static Properties convertBundleToProperties( ResourceBundle rb )
    {
        Properties props = new Properties();

        for ( Enumeration keys = rb.getKeys(); keys.hasMoreElements(); )
        {
            String key = (String) keys.nextElement();
            props.put( key, rb.getString( key ) );
        }

        return props;
    }

    /**
     * Convenience method used by tests to populate an object from a ResourceBundle
     * 
     * @param obj an initialized object
     * @param rb a resource bundle
     * @return a populated object
     */
    public static Object populateObject( Object obj, ResourceBundle rb )
    {
        try
        {
            Map map = convertBundleToMap( rb );

            BeanUtils.copyProperties( obj, map );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            log.error( "Exception occured populating object: " + e.getMessage() );
        }

        return obj;
    }

    /**
     * This method inspects a POJO or Form and figures out its pojo/form equivalent.
     * 
     * @param o the object to inspect
     * @return the Class of the persistable object
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static Object getOpposingObject( Object o )
        throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        String name = o.getClass().getName();

        if ( o instanceof BaseObject )
        {
            if ( log.isDebugEnabled() )
            {
                log.debug( "getting form equivalent of pojo..." );
            }

            name = StringUtils.replace( name, ".model.", ".webapp.form." );
            if ( AopUtils.isCglibProxy( o ) )
            {
                name = name.substring( 0, name.indexOf( "$$" ) );
            }
            name += "Form";
        }
        else
        {
            if ( log.isDebugEnabled() )
            {
                log.debug( "getting pojo equivalent of form..." );
            }
            name = StringUtils.replace( name, ".webapp.form.", ".model." );
            name = name.substring( 0, name.lastIndexOf( "Form" ) );
        }

        Class obj = Class.forName( name );

        if ( log.isDebugEnabled() )
        {
            log.debug( "returning className: " + obj.getName() );
        }

        return obj.newInstance();
    }

    /**
     * Convenience method to convert a form to a POJO and back again
     * 
     * @param o the object to tranfer properties from
     * @return converted object
     */
    public static Object convert( Object o )
        throws Exception
    {
        if ( o == null )
        {
            return null;
        }
        Object target = getOpposingObject( o );
        BeanUtils.copyProperties( target, o );
        return target;
    }

    /**
     * Convenience method to convert Lists (in a Form) from POJOs to Forms. Also checks for and formats dates.
     * 
     * @param o
     * @return Object with converted lists
     * @throws Exception
     */
    public static Object convertLists( Object o )
        throws Exception
    {
        if ( o == null )
        {
            return null;
        }

        Object target = null;

        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors( o );

        for ( int i = 0; i < origDescriptors.length; i++ )
        {
            String name = origDescriptors[i].getName();

            if ( origDescriptors[i].getPropertyType().equals( List.class ) )
            {
                List list = (List) PropertyUtils.getProperty( o, name );
                for ( int j = 0; j < list.size(); j++ )
                {
                    Object origin = list.get( j );
                    target = convert( origin );
                    list.set( j, target );
                }
                PropertyUtils.setProperty( o, name, list );
            }
        }
        return o;
    }
}
