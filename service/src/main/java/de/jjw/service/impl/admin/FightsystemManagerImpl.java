/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightsystemManagerImpl.java
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

package de.jjw.service.impl.admin;


import java.util.List;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.admin.FightsystemDao;
import de.jjw.model.admin.Fightsystem;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.FightsystemManager;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.admin.FightsystemMapper;


public class FightsystemManagerImpl
    extends BaseManager
    implements FightsystemManager, IDatabaseTableConstants
{

    private FightsystemDao fightsystemDao;

    public void setFightsystemDao( FightsystemDao fightsystemDao )
    {
        this.fightsystemDao = fightsystemDao;

    }

    public Fightsystem getFightsystem( Long fightsystemId )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Fightsystem> getAllFightsystems()
    {
        return FightsystemMapper.mapFightsystemListFromDB( fightsystemDao.getAllFightsystems() );
    }

    public void saveFightsystem( Fightsystem fightsystem )
    {
        // TODO Auto-generated method stub

    }

    public void removeFightsystem( Fightsystem fightsystem )
    {
        // TODO Auto-generated method stub

    }

    public void removeFightsystem( Long fightsystemId )
    {
        // TODO Auto-generated method stub

    }

    public void removeAllFightsystems()
    {
        // TODO Auto-generated method stub

    }

    public void saveFightsystems( ServiceExchangeContext ctx, List<Fightsystem> fightsystemList )
    {
        //       dao.removeAllFightsystems();

        if ( fightsystemList != null )
        {
            List<Fightsystem> dbList = fightsystemDao.getAllFightsystems();
            for ( Fightsystem fightsystemDB : dbList )
            {
                for ( Fightsystem fightsystem : fightsystemList )
                {
                    if ( fightsystemDB.getId().equals( fightsystem.getId() ) )
                    {
                        fightsystem.setModificationId( ctx.getUserId() );
                        FightsystemMapper.mapFightsystemToDB( fightsystem, fightsystemDB );
                    }
                }
            }

//        	for (Fightsystem fightsystem : fightsystemList) {
//				
//        		// remove all incomplete Entries from List
//        		if (!TypeUtil.isEmptyOrDefault(fightsystem
//						.getMaxParticipantString())
//						&& !TypeUtil.isEmptyOrDefault(fightsystem
//								.getMinParticipantString()))
//					fightsystemList.remove(fightsystem);
//			}   
//        dao.saveFightsystems(FightsystemMapper.mapFightsystemListToDB(fightsystemList));
        }
    }

    public Fightsystem getFightsystem( Integer numberOfParticiapantsInClass )
    {
        // TODO Auto-generated method stub
        return null;
    }

}
