/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CodestableDao.java
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

package de.jjw.dao.generalhelper;

import de.jjw.dao.Dao;
import de.jjw.model.generalhelper.Codestable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public interface CodestableDao
    extends Dao
{

    public static int NO_ORDER = -1;
    public static int ASCENDING_ORDER = 0;
    public static int DESCENDING_ORDER = 1;


    public Map<String, TreeMap> getAllEntriesByLocaleList();


    public List<Codestable> getAllEntries();

    /**
     * @param type
     * @param order NO_ORDER, ASCENDING_ODER, DESCENDING_ORDER
     * @return
     */
    public List<Codestable> getEntriesByTypeAndOrder( String type, int order, Locale locale );

    /**
     * get only entries which corredponden binary to the level
     *
     * @param type
     * @param level
     * @return
     */
    public List<Codestable> getEntriesByTypeAndLevel( String type, int level, Locale locale );

    /**
     * get only entries which corredponden binary to the level
     *
     * @param type
     * @param order NO_ORDER, ASCENDING_ODER, DESCENDING_ORDER
     * @return
     */
    public List<Codestable> getEntriesByTypeOrderAndLevel( String type, int order, int level, Locale locale );

    public Codestable getEntryById( String id, Locale locale );

    public String getEntryValueById( String id, Locale locale );

    /**
     * @param type
     * @param order NO_ORDER, ASCENDING_ODER, DESCENDING_ORDER
     * @return
     */
    public List<String> getEntriesValuesByTypeAndOrder( String type, int order, Locale locale );

    /**
     * get only entries which corredponden binary to the level
     *
     * @param type
     * @param level
     * @return
     */
    public List<String> getEntriesValuesByTypeAndLevel( String type, int level, Locale locale );

    /**
     * get only entries which corredponden binary to the level
     *
     * @param type
     * @param order NO_ORDER, ASCENDING_ODER, DESCENDING_ORDER
     * @return
     */
    public List<String> getEntriesValuesByTypeOrderAndLevel( String type, int order, int level, Locale locale );
}
