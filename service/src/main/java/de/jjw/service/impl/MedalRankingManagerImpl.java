/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MedalRankingManagerImpl.java
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

package de.jjw.service.impl;

import java.util.List;

import de.jjw.dao.MedalRankingDao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.MedalRanking;
import de.jjw.service.MedalRankingManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.util.TypeUtil;

public class MedalRankingManagerImpl
    extends BaseManager
    implements MedalRankingManager
{

    private MedalRankingDao medalRankingDao;

    public List<MedalRanking> getMedalRankingByRegion()
        throws JJWManagerException
    {
        try
        {
            List<MedalRanking> list = medalRankingDao.getMedalRankingByRegion();

            return doRanking( list );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<MedalRanking> getMedalRankingByCountry()
        throws JJWManagerException
    {
        try
        {
            List<MedalRanking> list = medalRankingDao.getMedalRankingByCountry();

            return doRanking( list );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<MedalRanking> getMedalRankingByTeam()
        throws JJWManagerException
    {
        try
        {
            List<MedalRanking> list = medalRankingDao.getMedalRankingByTeam();

            return doRanking( list );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    private List<MedalRanking> doRanking( List<MedalRanking> list )
    {
        for ( int i = 0; i < list.size(); i++ )
        {
            if ( i == 0 )
            {
                list.get( 0 ).setRank( TypeUtil.STRING_1 );
            }
            else
            {
                if ( list.get( i ).getFirstPlace() == list.get( i - 1 ).getFirstPlace() &&
                    list.get( i ).getSecondPlace() == list.get( i - 1 ).getSecondPlace() &&
                    list.get( i ).getThirdPlace() == list.get( i - 1 ).getThirdPlace() )
                {
                    list.get( i ).setRank( list.get( i - 1 ).getRank() );
                }
                else
                {
                    list.get( i ).setRank( String.valueOf( i + 1 ) );
                }
            }
        }
        return list;
    }

    public List<MedalRanking> getMedalRankingByRegionAndAge( long ageId )
        throws JJWManagerException
    {
        try
        {
            List<MedalRanking> list = medalRankingDao.getMedalRankingByRegionAndAge( ageId );

            return doRanking( list );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<MedalRanking> getMedalRankingByCountryAndAge( long ageId )
        throws JJWManagerException
    {
        try
        {
            List<MedalRanking> list = medalRankingDao.getMedalRankingByCountryAndAge( ageId );

            return doRanking( list );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<MedalRanking> getMedalRankingByTeamAndAge( long ageId )
        throws JJWManagerException
    {
        try
        {
            List<MedalRanking> list = medalRankingDao.getMedalRankingByTeamAndAge( ageId );

            return doRanking( list );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public MedalRankingDao getMedalRankingDao()
    {
        return medalRankingDao;
    }

    public void setMedalRankingDao( MedalRankingDao medalRankingDao )
    {
        this.medalRankingDao = medalRankingDao;
    }

}
