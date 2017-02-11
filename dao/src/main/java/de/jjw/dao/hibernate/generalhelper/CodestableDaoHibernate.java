/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CodestableDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

package de.jjw.dao.hibernate.generalhelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.hibernate.Query;

import de.jjw.dao.generalhelper.CodestableDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.util.TypeUtil;

public class CodestableDaoHibernate
    extends BaseDaoHibernate
    implements CodestableDao
{
    private static String CT_LOCALE_ENTRIES = "select distinct(ct.locale) from Codestable ct";

    private static String CT_ENTRIES_WITH_GIVEN_LOCALE =
        "from Codestable ct where ct.locale=? order by type, ordernb, id ";

    private static String ASCENDING = "ASC";

    private static String DESCENDING = "DSC";

    private static String CT_ALL = "from Codestable";

    private static String CT_ENTRIES_BY_TYPE_ORDER = "from Codestable ct where type=? order by ordernb ?";

    private static String CT_ENTRIES_VALUE_BY_TYPE_ORDER =
        "select ct.value from Codestable ct where type=? order by ordernb ?";

    public List<Codestable> getAllEntries()
    {
        return getHibernateTemplate().find( CT_ALL );
    }


    @SuppressWarnings("unchecked")
    public Map<String, TreeMap> getAllEntriesByLocaleList()
    {

        Map<String, Codestable> entrymap = null;
        Map<String, List<Codestable>> entrymapCodes = null;
        TreeMap<String, Map> localeMap = null;

        List<Codestable> codesList = null;
        Map<String, TreeMap> retMap = new TreeMap<String, TreeMap>();
        Codestable ct;

        List localList = getSession().createQuery( CT_LOCALE_ENTRIES ).list();

        for ( Object aLocalList : localList )
        {
            localeMap = new TreeMap();
            String locale = (String) aLocalList;
            retMap.put( locale, localeMap );
            retMap.get( locale ).put( Codestable.ERROR, new TreeMap() );
            retMap.get( locale ).put( Codestable.CODE, new TreeMap() );
        }
        Query q = getSession().createQuery( CT_ALL );

        for ( Object o : q.list() )
        {
            ct = (Codestable) o;

            if ( null == ct.getType() || "FM".equals( ct.getType() ) )
            {
                ( (Map) retMap.get( ct.getLocale() ).get( Codestable.ERROR ) ).put( ct.getId(), ct );
            }
            else
            {
                ct.setId( ct.getId().substring( 4 ) );// type description cut
                if ( ( (Map) retMap.get( ct.getLocale() ).get( Codestable.CODE ) ).get( ct.getType() ) == null )
                // if ( entrymapCodes.get( ct.getType() ) == null )
                {
                    codesList = new ArrayList<Codestable>();
                    codesList.add( ct );
                    ( (Map) retMap.get( ct.getLocale() ).get( Codestable.CODE ) ).put( ct.getType(), codesList );
                    // entrymapCodes.put( ct.getType(), codesList );
                }
                else
                {
                    ( (List<Codestable>) ( (Map) retMap.get( ct.getLocale() ).get( Codestable.CODE ) ).get( ct.getType() ) ).add( ct );
                    // entrymapCodes.get( ct.getType() ).add( ct );
                }
            }
        }

        return retMap;
    }

    public List<Codestable> getEntriesByTypeAndOrder( String type, int order, Locale locale )
    {
        Query q = getSession().createQuery( CT_ENTRIES_BY_TYPE_ORDER );
        q.setString( 0, type );
        if ( NO_ORDER == order )
        {
            q.setString( 1, TypeUtil.STRING_DEFAULT );
        }
        else
        {
            if ( ASCENDING_ORDER == order )
            {
                q.setString( 1, ASCENDING );
            }
            else
            {
                q.setString( 1, DESCENDING );
            }
        }
        return q.list();
    }

    public List<Codestable> getEntriesByTypeAndLevel( String type, int level, Locale locale )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Codestable> getEntriesByTypeOrderAndLevel( String type, int order, int level, Locale locale )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Codestable getEntryById( String id, Locale locale )
    {
        return (Codestable) getHibernateTemplate().get( Codestable.class, id );
    }

    public String getEntryValueById( String id, Locale locale )
    {
        return ( (Codestable) getHibernateTemplate().get( Codestable.class, id ) ).getValue();
    }

    public List<String> getEntriesValuesByTypeAndOrder( String type, int order, Locale locale )
    {
        Query q = getSession().createQuery( CT_ENTRIES_VALUE_BY_TYPE_ORDER );
        q.setString( 0, type );
        if ( NO_ORDER == order )
        {
            q.setString( 1, TypeUtil.STRING_DEFAULT );
        }
        else
        {
            if ( ASCENDING_ORDER == order )
            {
                q.setString( 1, ASCENDING );
            }
            else
            {
                q.setString( 1, DESCENDING );
            }
        }
        return q.list();
        /*List<Codestable> entries = q.list();
        List<String> ret = new ArrayList<String>();
        for (Codestable entry: entries) {
            ret.add(entry.getValue());            
        }
        return ret;*/
    }

    public List<String> getEntriesValuesByTypeAndLevel( String type, int level, Locale locale )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<String> getEntriesValuesByTypeOrderAndLevel( String type, int order, int level, Locale locale )
    {
        // TODO Auto-generated method stub
        return null;
    }

}
