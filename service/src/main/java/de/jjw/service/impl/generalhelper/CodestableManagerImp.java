/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CodestableManagerImp.java
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

package de.jjw.service.impl.generalhelper;

import de.jjw.dao.generalhelper.CodestableDao;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.service.generalhelper.CodestableManager;
import de.jjw.service.impl.BaseManager;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CodestableManagerImp
    extends BaseManager
    implements CodestableManager
{

    private CodestableDao dao = null;


    /**
     * @return Returns the dao.
     */
    public CodestableDao getDao()
    {
        return dao;
    }

    /**
     * @param dao The dao to set.
     */
    public void setDao( CodestableDao dao )
    {
        this.dao = dao;
    }

    public void setCodestableDao( CodestableDao dao )
    {
        this.dao = dao;

    }

    public List<Codestable> getAllEntries()
    {
        return dao.getAllEntries();
    }

    public Map<String, TreeMap> getAllEntriesByLocaleList()
    {
        return dao.getAllEntriesByLocaleList();
    }

}
