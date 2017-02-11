/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CodestableMain.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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

package de.jjw.model.generalhelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;

public class CodestableMain
{
    protected final Logger log = Logger.getRootLogger();

    private static CodestableMain instance = new CodestableMain();

    private static Map<String, TreeMap> ALL_ENTRIES = null;

    public static final String DEFAULT_LOCAL = Locale.GERMAN.toString();

    public static CodestableMain getInstance()
    {
        return instance;
    }

    private CodestableMain()
    {

    }

    /**
     * @return Returns the aLL_ENTRIES.
     */
    public Map getALL_ENTRIES()
    {
        return ALL_ENTRIES;
    }

    /**
     * @param all_entries The aLL_ENTRIES to set.
     */
    public void setALL_ENTRIES( Map all_entries )
    {
        ALL_ENTRIES = all_entries;
    }

    public String getCodestableErrorValueById( String id, Locale locale )
    {
        Map<String, de.jjw.model.generalhelper.Codestable> localeMap = getLocaleCodestableError( locale );
        return localeMap.get( id ).getValue();
    }

    /**
     * returns only values of the CodestableMain
     * 
     * @param type
     * @param locale
     * @return
     */

    public Vector getCodestableCodeValuesByType( String type, Locale locale )
    {
        Map<String, List<de.jjw.model.generalhelper.Codestable>> localeMap = getLocaleCodestableCodes( locale );

        List<de.jjw.model.generalhelper.Codestable> codeList = localeMap.get( type );
        Vector vector = new Vector();
        Iterator<de.jjw.model.generalhelper.Codestable> it = codeList.iterator();
        de.jjw.model.generalhelper.Codestable entry = null;
        while ( it.hasNext() )
        {
            entry = it.next();
            if ( type.equals( entry.getType() ) )
            {
                vector.add( entry.getValue() );
            }
        }
        return vector;
    }

    /**
     * returns only a value of the CodestableMain
     * 
     * @param type
     * @param locale
     * @return
     */

    public String getCodestableCodeValueByType( String type, String id, Locale locale )
    {
        if ( type == null || id == null || locale == null )
        {
            return null;
        }

        Map<String, List<de.jjw.model.generalhelper.Codestable>> localeMap = getLocaleCodestableCodes( locale );

        List<de.jjw.model.generalhelper.Codestable> codeList = localeMap.get( type );
        Iterator<de.jjw.model.generalhelper.Codestable> it = codeList.iterator();
        de.jjw.model.generalhelper.Codestable entry = null;
        while ( it.hasNext() )
        {
            entry = it.next();
            if ( type.equals( entry.getType() ) && ( entry.getId().equals( id ) || entry.getId().equals( 10 + id ) ) ) // string
                                                                                                                       // concat
                                                                                                                       // no
                                                                                                                       // arathmetik
            {
                return ( entry.getValue() );
            }
        }
        return null;
    }

    public List<de.jjw.model.generalhelper.Codestable> getCodestableCodeByTypeAndLevel( String type, Locale locale,
                                                                                        int level )
    {
        Map<String, List<de.jjw.model.generalhelper.Codestable>> localeMap = getLocaleCodestableCodes( locale );
        List<de.jjw.model.generalhelper.Codestable> list = new ArrayList<de.jjw.model.generalhelper.Codestable>();

        for ( de.jjw.model.generalhelper.Codestable item : ( localeMap.get( type ) ) )
        {
            if ( item.getLevel() <= level )
                list.add( item );

        }
        return list;
    }

    /**
     * returns a list of CodestableObjects
     * 
     * @param type
     * @param locale
     * @return
     */
    public List<de.jjw.model.generalhelper.Codestable> getCodestableCodeByType( String type, Locale locale )
    {
        Map<String, List<de.jjw.model.generalhelper.Codestable>> localeMap = getLocaleCodestableCodes( locale );

        return localeMap.get( type );
        //        
        // List <de.jjw.model.generalhelper.Codestable> ret = new ArrayList();
        // Iterator<de.jjw.model.generalhelper.Codestable> it = localeMap.values().toArray().iterator();
        // de.jjw.model.generalhelper.Codestable entry = null;
        // while (it.hasNext()) {
        // entry = it.next();
        // if (type.equals(entry.getType())) {
        // ret.add(entry);
        // }
        // }
        // return ret;
    }

    private static Map<String, de.jjw.model.generalhelper.Codestable> getLocaleCodestableError( Locale locale )
    {
        Map<String, de.jjw.model.generalhelper.Codestable> result = null;

        try
        {
            result =
                ( (TreeMap<String, Map>) ALL_ENTRIES.get( locale.getLanguage() ) ).get( de.jjw.model.generalhelper.Codestable.ERROR );
        }
        catch ( Exception e )
        {
            result =
                ( (TreeMap<String, Map>) ALL_ENTRIES.get( DEFAULT_LOCAL ) ).get( de.jjw.model.generalhelper.Codestable.ERROR );
        }

        return result;
    }

    private static Map<String, List<de.jjw.model.generalhelper.Codestable>> getLocaleCodestableCodes( Locale locale )
    {
        if ( locale == null || ALL_ENTRIES.get( locale.getLanguage() ) == null )
        {
            return ( (TreeMap<String, Map>) ALL_ENTRIES.get( DEFAULT_LOCAL ) ).get( de.jjw.model.generalhelper.Codestable.CODE );
        }

        return ( (TreeMap<String, Map>) ALL_ENTRIES.get( locale.getLanguage().toString() ) ).get( de.jjw.model.generalhelper.Codestable.CODE );
    }
}
