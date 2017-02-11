/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassManager.java
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

package de.jjw.service.fighting;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.model.fighting.FightingCertification;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.FightingclassCreateException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.util.dto.SortDTO;

public interface FightingclassManager
{

    public void createFightingclasses( ServiceExchangeContext ctx )
        throws FightingclassCreateException;

    /**
     * get all fightingclasses, that are in Use all parameter in the list, which correspond to a column will be use for
     * order by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Fightingclasses
     * @throws JJWManagerException
     */
    public List<FightingclassWeb> getInUseFightingclasses( Map<Integer, SortDTO> sortParameter, Locale locale )
        throws JJWManagerException;

    public List<FightingclassWeb> getInUseFightingclassesForTatami( Map<Integer, SortDTO> sortParameter, Locale locale,
                                                                    ServiceExchangeContext ctx )
        throws JJWManagerException;

    public FightingSimplePoolClass getFightingclassSimplePool( Long fightingclassId )
        throws JJWManagerException;

    public FightingDoublePoolClass getFightingclassDoublePool( Long fightingclassId )
        throws JJWManagerException;

    public FightingKoClass getFightingclassKo( Long fightingclassId )
        throws JJWManagerException;

    public void toggleDeleteStop( Fightingclass fightingclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;

    public void toggleComplete( Fightingclass fightingclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;

    public int getFightsystemOfFightingclass( long fightingclassId )
        throws JJWManagerException;

    public FightingCertification getFightingCertification( Long fightingclassId )
        throws JJWManagerException;

    public void changeFighterInClass( int fighter1, int fighter2, long fightingclassId )
        throws JJWManagerException;

    public int getNumberOfFighterInFightingclass( long fightingclassId )
        throws JJWManagerException;

    public void createAutomaticFightingclasses( Long ageId, Long userId )
        throws JJWManagerException;

    public void sortFighterIntoClasses( Long userId )
        throws JJWManagerException;

    public List<Age> getAgesForAutomaticFightingclassCreation()
        throws JJWManagerException;

    public Map<Integer, Fightingclass> getAllFightingclasses( boolean onlyCompleted )
        throws JJWManagerException;
    
    public List<FightingclassWeb> getChildFightingclasses( Long fightingclassId, Locale locale )
    	throws JJWManagerException;
    
    public List<FightingclassWeb> getCombinableFightingclasses( Long fightingclassId, Locale locale )
    	throws JJWManagerException;
    
    public void addFightingclassToClass(long parentClass, long childClass)
    	throws JJWManagerException;
    
    public void removeFightingclassFromParent(long parentClass, long childClass)
    	throws JJWManagerException;

    public void startClass( Fightingclass fightingclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;
}
