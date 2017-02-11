/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MedalRankingDao.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.dao;

import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.MedalRanking;

import java.util.List;

public interface MedalRankingDao
    extends Dao
{
    public List<MedalRanking> getMedalRankingByRegion()
        throws JJWDataLayerException;

    public List<MedalRanking> getMedalRankingByCountry()
        throws JJWDataLayerException;

    public List<MedalRanking> getMedalRankingByTeam()
        throws JJWDataLayerException;

    public List<MedalRanking> getMedalRankingByTeamAndAge( long ageId )
        throws JJWDataLayerException;

    public List<MedalRanking> getMedalRankingByRegionAndAge( long ageId )
        throws JJWDataLayerException;

    public List<MedalRanking> getMedalRankingByCountryAndAge( long ageId )
        throws JJWDataLayerException;
}
