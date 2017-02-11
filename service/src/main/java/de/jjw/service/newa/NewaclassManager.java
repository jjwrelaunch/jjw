/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassManager.java
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

package de.jjw.service.newa;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.model.newa.NewaCertification;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.NewaclassCreateException;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.util.dto.SortDTO;

public interface NewaclassManager
{

    public void createNewaclasses( ServiceExchangeContext ctx )
        throws NewaclassCreateException;

    /**
     * get all fightingclasses, that are in Use all parameter in the list, which correspond to a column will be use for
     * order by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Fightingclasses
     * @throws JJWManagerException
     */
    public List<NewaclassWeb> getInUseNewaclasses( Map<Integer, SortDTO> sortParameter, Locale locale )
        throws JJWManagerException;

    public List<NewaclassWeb> getInUseNewaclassesForTatami( Map<Integer, SortDTO> sortParameter, Locale locale,
                                                                    ServiceExchangeContext ctx )
        throws JJWManagerException;

    public NewaSimplePoolClass getNewaclassSimplePool( Long newaclassId )
        throws JJWManagerException;

    public NewaDoublePoolClass getNewaclassDoublePool( Long newaclassId )
        throws JJWManagerException;

    public NewaKoClass getNewaclassKo( Long newaclassId )
        throws JJWManagerException;

    public void toggleDeleteStop( Newaclass newaclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;

    public void toggleComplete( Newaclass newaclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;

    public int getFightsystemOfNewaclass( long newaclassId )
        throws JJWManagerException;

    public NewaCertification getNewaCertification( Long newaclassId )
        throws JJWManagerException;

    public void changeFighterInClass( int fighter1, int fighter2, long newaclassId )
        throws JJWManagerException;

    public int getNumberOfFighterInNewaclass( long newaclassId )
        throws JJWManagerException;

    public void createAutomaticNewaclasses( Long ageId, Long userId )
        throws JJWManagerException;

    public void sortNewaFighterIntoClasses( Long userId )
        throws JJWManagerException;

    public List<Age> getAgesForAutomaticNewaclassCreation()
        throws JJWManagerException;

    public Map<Integer, Newaclass> getAllNewaclasses( boolean onlyCompleted )
        throws JJWManagerException;
    
    public List<NewaclassWeb> getChildNewaclasses( Long newaclassId, Locale locale )
    	throws JJWManagerException;
    
    public List<NewaclassWeb> getCombinableNewaclasses( Long newaclassId, Locale locale )
    	throws JJWManagerException;
    
    public void addNewaclassToClass( long parentClass, long childClass )
    	throws JJWManagerException;
    
    public void removeNewaclassFromParent( long parentClass, long childClass )
    	throws JJWManagerException;

    public void startClass( Newaclass Newaclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;
}
