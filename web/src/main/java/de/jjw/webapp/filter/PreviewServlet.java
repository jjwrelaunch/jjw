/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PreviewServlet.java
 * Created : 06 Feb 2011
 * Last Modified: Sun, 06 Feb 2011 21:32:01
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

package de.jjw.webapp.filter;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.jjw.model.Preview;
import de.jjw.model.User;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.fighting.Fight;
import de.jjw.model.newa.NewaFight;
import de.jjw.service.PreviewManager;
import de.jjw.service.UserManager;
import de.jjw.service.duo.DuoFightManager;
import de.jjw.service.fighting.FightManager;
import de.jjw.service.newa.NewaFightManager;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.WebExchangeContextHelper;


public class PreviewServlet
    extends HttpServlet
{
    protected final Logger log = Logger.getRootLogger();

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException
    {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext( getServletContext() );
        String id = request.getParameter( "id" );
        String system = request.getParameter( "system" );
        if ( TypeUtil.isEmptyOrDefault( id ) || TypeUtil.isEmptyOrDefault( system ) )
        {
            // Do your thing if the ID is not supplied to the request.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError( HttpServletResponse.SC_NOT_FOUND ); // 404.
            return;
        }
        PreviewManager previewManager = (PreviewManager) context.getBean( "previewManager" );
        FightManager fightManager = (FightManager) context.getBean( "fightManager" );
        DuoFightManager duoFightManager = (DuoFightManager) context.getBean( "duoFightManager" );
        NewaFightManager newaFightManager = (NewaFightManager) context.getBean( "newaFightManager" );
        UserManager userManager = (UserManager) context.getBean( "userManager" );

        try
        {
            Preview preview = new Preview();

            if ( Preview.DISCEPLINE_FIGHTING_SHORT.equals( system ) )
            {
                Fight fight = fightManager.getFight( Long.valueOf( id ) );
                preview.setAgeId( fight.getFighterRed().getFightingclass().getAge().getId() );
                preview.setFighterIdBlue( fight.getFighterIdBlue() );
                preview.setFighterIdRed( fight.getFighterIdRed() );
                preview.setFightId( fight.getId() );
                preview.setFightingclassId( fight.getFightingclassId() );
                preview.setNameBlue( fight.getFighterBlue().getFirstname() + " " + fight.getFighterBlue().getName() );
                preview.setNameRed( fight.getFighterRed().getFirstname() + " " + fight.getFighterRed().getName() );
                preview.setSex( fight.getFighterRed().getFightingclass().getSex() );
            }
            if ( Preview.DISCEPLINE_DUO_SHORT.equals( system ) )
            {
                DuoFight fight = duoFightManager.getDuoFight( Long.valueOf( id ) );
                preview.setAgeId( fight.getDuoTeamRed().getDuoclass().getAge().getId() );
                preview.setFighterIdBlue( fight.getTeamIdBlue() );
                preview.setFighterIdRed( fight.getTeamIdRed() );
                preview.setFightId( fight.getId() );
                preview.setDuoclassId( fight.getDuoclassId() );
                preview.setNameBlue( fight.getDuoTeamBlue().getName() + "/" + fight.getDuoTeamBlue().getName2() );
                preview.setNameRed( fight.getDuoTeamRed().getName() + "/" + fight.getDuoTeamRed().getName2() );
                preview.setSex( fight.getDuoTeamRed().getDuoclass().getSex() );
            }
            if ( Preview.DISCEPLINE_NEWA_SHORT.equals( system ) )
            {
                NewaFight fight = newaFightManager.getFight( Long.valueOf( id ) );
                preview.setAgeId( fight.getFighterRed().getNewaclass().getAge().getId() );
                preview.setFighterIdBlue( fight.getFighterIdBlue() );
                preview.setFighterIdRed( fight.getFighterIdRed() );
                preview.setFightId( fight.getId() );
                preview.setFightingclassId( fight.getNewaclassId() );
                preview.setNameBlue( fight.getFighterBlue().getFirstname() + " " + fight.getFighterBlue().getName() );
                preview.setNameRed( fight.getFighterRed().getFirstname() + " " + fight.getFighterRed().getName() );
                preview.setSex( fight.getFighterRed().getNewaclass().getSex() );
            }

            long userId = WebExchangeContextHelper.getWebExchangeContext( request.getSession() ).getUserId();
            User user = userManager.getUser( userId );
            if ( null == user.getFirstName() )
                preview.setTatami( "" );
            else
                preview.setTatami( user.getFirstName() );

            preview.setUserIdOfTatami( userId );
            preview.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
            preview.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            preview.setCreateId( userId );
            preview.setModificationId( userId );

            previewManager.savePreview( preview );
        }
        catch ( Exception e )
        {
            log.error( "PreviewServlet: ", e );
        }
        // request.getUserPrincipal()
        System.currentTimeMillis();
    }
}

