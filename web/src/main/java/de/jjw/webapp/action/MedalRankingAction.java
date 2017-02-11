/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MedalRankingAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

package de.jjw.webapp.action;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

import de.jjw.model.MedalRanking;
import de.jjw.model.admin.Age;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.MedalRankingManager;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.pdf.MedalsPDF;

public class MedalRankingAction
    extends BasePage
    implements IGlobalWebConstants
{

    private static final String ALL_RANKINGS = "all_rankinggs";

    private static String ALL_AGES = "allAges";

    private MedalRankingManager medalRankingManager;

    private AgeManager ageManager;

    private List<MedalRanking> medalRanking;

    private String org;

    private Age age;

    private List<Age> ages = null;

    public List<MedalRanking> getMedalRanking()
    {
        if ( medalRankingManager == null )
        {
            log.warn( "keine medalRankingManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_RANKINGS ) == null )
        {
            try
            {
                medalRanking = medalRankingManager.getMedalRankingByTeam();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_RANKINGS, ALL_RANKINGS );
        }
        return medalRanking;
    }

    public String showWeb()
    {
        try
        {
            switch ( Integer.valueOf( org ) )
            {
                case MEDALRANKING_ORG_TEAM:
                    if ( age == null )
                    {
                        medalRanking = medalRankingManager.getMedalRankingByTeam();
                    }
                    else
                    {
                        medalRanking = medalRankingManager.getMedalRankingByTeamAndAge( age.getId() );
                    }
                    break;

                case MEDALRANKING_ORG_REGION:
                    if ( age == null )
                    {
                        medalRanking = medalRankingManager.getMedalRankingByRegion();
                    }
                    else
                    {
                        medalRanking = medalRankingManager.getMedalRankingByRegionAndAge( age.getId() );
                    }
                    break;

                case MEDALRANKING_ORG_COUNTRY:
                    if ( age == null )
                    {
                        medalRanking = medalRankingManager.getMedalRankingByCountry();
                    }
                    else
                    {
                        medalRanking = medalRankingManager.getMedalRankingByCountryAndAge( age.getId() );
                    }
                    break;
            }


        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String showPdf()
    {
        try
        {
            switch ( Integer.valueOf( org ) )
            {
                case MEDALRANKING_ORG_TEAM:
                    if ( age == null )
                    {
                        medalRanking = medalRankingManager.getMedalRankingByTeam();
                    }
                    else
                    {
                        medalRanking = medalRankingManager.getMedalRankingByTeamAndAge( age.getId() );
                    }
                    break;

                case MEDALRANKING_ORG_REGION:
                    if ( age == null )
                    {
                        medalRanking = medalRankingManager.getMedalRankingByRegion();
                    }
                    else
                    {
                        medalRanking = medalRankingManager.getMedalRankingByRegionAndAge( age.getId() );
                    }
                    break;

                case MEDALRANKING_ORG_COUNTRY:
                    if ( age == null )
                    {
                        medalRanking = medalRankingManager.getMedalRankingByCountry();
                    }
                    else
                    {
                        medalRanking = medalRankingManager.getMedalRankingByCountryAndAge( age.getId() );
                    }
                    break;
            }
            HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

            response.setHeader( "Expires", "0" );
            response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
            response.setHeader( "Pragma", "public" );
            response.setContentType( "application/pdf" );

            new MedalsPDF( "de.jjw.webapp.messages.medalRanking", response, medalRanking, org, age ).showMedals();

            getFacesContext().responseComplete();
        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;

    }

    public MedalRankingManager getMedalRankingManager()
    {
        return medalRankingManager;
    }

    public void setMedalRankingManager( MedalRankingManager medalRankingManager )
    {
        this.medalRankingManager = medalRankingManager;
    }

    public void setMedalRanking( List<MedalRanking> medalRankings )
    {
        this.medalRanking = medalRankings;
    }

    public AgeManager getAgeManager()
    {
        return ageManager;
    }

    public void setAgeManager( AgeManager ageManager )
    {
        this.ageManager = ageManager;
    }

    public List getAgeListALL()
    {
        List ret = new ArrayList();
        ret.add( new SelectItem( new Age(), TypeUtil.STRING_DEFAULT, TypeUtil.STRING_0 ) );

        try
        {
            for ( Age age : ageManager.getAllAges() )
            {
                ret.add( new SelectItem( age, age.getAgeShort(), String.valueOf( age.getId() ) ) );
            }

            return ret;
        }
        catch ( JJWManagerException e )
        {
            return new ArrayList();
        }
    }

    public List<Age> getAges()
    {
        if ( ageManager == null )
        {
            log.warn( "keine ageManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_AGES ) == null )
        {
            try
            {
                ages = ageManager.getAllAges();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_AGES, ALL_AGES );
        }
        return ages;
    }

    public String getOrg()
    {
        return org;
    }

    public void setOrg( String org )
    {
        this.org = org;
    }

    public Age getAge()
    {
        return age;
    }

    public void setAge( Age age )
    {
        this.age = age;
    }

    public List getOrgALL()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_ORGANISATION, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( entry.getId(), entry.getValue() ) );
        }
        return ret;
    }

    public void setAges( List<Age> ages )
    {
        this.ages = ages;
    }

}
