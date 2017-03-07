/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : JJWVideoServlet.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:32:01
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
import java.io.InputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.jjw.service.VideoManager;
import de.jjw.model.VideoItem;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.newa.NewaFight;
import de.jjw.service.duo.DuoFightManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightManager;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.fighting.WeightclassManager;
import de.jjw.service.newa.NewaFightManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JJWVideoServlet
    extends HttpServlet
{

    private static final String FILENAME_SEPERATOR = "_";
    private static final String VIDEO_EXTENSION = ".webm";
    ApplicationContext context= null;
    private static final String DATA_DIRECTORY = "videos";
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 1024;  //1GB;
    
    protected void doPost( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException
    {
        context = WebApplicationContextUtils.getWebApplicationContext( getServletContext() );
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        Map<String,Object> map = new HashMap<String,Object>();

        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
         // Set overall request size constraint
            upload.setSizeMax(MAX_REQUEST_SIZE);
            
            FileItem videoToDisc= null;
            try
            {
             // Parse the request
                List<FileItem> items = upload.parseRequest(request);
                
                Iterator<FileItem> iterator = items.iterator();
                while ( iterator.hasNext() )
                {
                    FileItem item = iterator.next();
                    //InputStream stream = item.openStream();

                    if ( item.isFormField() )
                        map.put( item.getFieldName(), item.getString() );
                    else
                    {
                       // map.put( item.getFieldName(), IOUtils.toByteArray( stream ) );
                        videoToDisc =item;
                    }
                }
                
                
                
                String uploadFolder = getServletContext().getRealPath("")
                                + File.separator + DATA_DIRECTORY;
                File fileSaveDir = new File(uploadFolder);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdirs();
                }
                
                VideoItem video = buildVideoItem(map);                
                File discItem = null;
                int i = 1;
                while ((discItem = new File(uploadFolder + File.separator + 
                		video.getFilename() + FILENAME_SEPERATOR + 
                		String.valueOf(i) + VIDEO_EXTENSION)).exists() && 
                		i < 100)
                {
                	i++;
                }
                videoToDisc.write( discItem );
                
                safeVideo(video);
            }
            catch ( FileUploadException e )
            {
                e.printStackTrace();
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }
        }
        System.out.println( "JJWVideoServlet" );
    }
    
    private void safeVideo(VideoItem item ) throws JJWManagerException
    {
        VideoManager videoManager = (VideoManager) context.getBean( "videoManager" );
        videoManager.saveVideo( item );
    }
    
    private VideoItem buildVideoItem(Map<String,Object> map) throws JJWManagerException 
    {               
        
        VideoItem item = new VideoItem();
        item.setFightId( Long.valueOf((String)map.get("fightId") ));
        item.setDiscepline( (String)map.get("discipline") );
        item.setScreenVideo( Boolean.valueOf( (String)map.get("isScreen") ));
        
       // item.setVideo( (byte[]) map.get("blob") );
        
        if("F".equals( item.getDiscepline() )){
            FightManager fightManager = (FightManager) context.getBean( "fightManager" );            
            Fight fight = fightManager.getFight( item.getFightId() );
            
            item.setParticipanIdRed( fight.getFighterIdRed() );
            item.setParticipanIdBlue( fight.getFighterIdBlue() );
            item.setNameRed( fight.getFighterRed().getName() );
            item.setNameBlue( fight.getFighterBlue().getName() );
            item.setClassname( fight.getFighterRed().getFightingclass().getWeightclass() );
            item.setSex( fight.getFighterRed().getSex() );
            item.setAgeDescription( fight.getFighterRed().getAge().getAgeShort() );
            StringBuilder sb = new StringBuilder(200);
            if(item.isScreenVideo()) sb.append( "__Screen__" );
            sb.append( item.getDiscepline() ).append( FILENAME_SEPERATOR ).append( item.getAgeDescription() ).append( FILENAME_SEPERATOR ).append( item.getSex()).append( FILENAME_SEPERATOR );
            sb.append( item.getClassname()).append( FILENAME_SEPERATOR ).append( item.getNameRed() ).append( FILENAME_SEPERATOR ).append( item.getNameBlue() );
            item.setFilename( sb.toString() );
        } else if("D".equals( item.getDiscepline() )){
            DuoFightManager fightManager = (DuoFightManager) context.getBean( "duoFightManager" );
            DuoFight fight = fightManager.getDuoFight( item.getFightId() );
            item.setParticipanIdRed( fight.getTeamIdRed() );
            item.setParticipanIdBlue( fight.getTeamIdBlue() );
            item.setNameRed( fight.getDuoTeamRed().getName()+"_"+fight.getDuoTeamRed().getName2() );
            item.setNameBlue( fight.getDuoTeamBlue().getName() +"_"+fight.getDuoTeamBlue().getName2() );
            item.setClassname( fight.getDuoTeamRed().getDuoclass().getDuoclassName() );
            item.setSex( fight.getDuoTeamRed().getSex() );
            item.setAgeDescription( fight.getDuoTeamRed().getAge().getAgeShort() );
            StringBuilder sb = new StringBuilder(200);
            if(item.isScreenVideo()) sb.append( "__Screen__" );
            sb.append( item.getDiscepline() ).append( FILENAME_SEPERATOR ).append( item.getAgeDescription() ).append( FILENAME_SEPERATOR ).append( item.getSex()).append( FILENAME_SEPERATOR );
            sb.append( item.getClassname()).append( FILENAME_SEPERATOR ).append( item.getNameRed() ).append( FILENAME_SEPERATOR ).append( item.getNameBlue() );
            item.setFilename( sb.toString() );
        } else if("N".equals( item.getDiscepline() )){
            NewaFightManager fightManager = (NewaFightManager) context.getBean( "newaFightManager" );
            NewaFight fight = fightManager.getFight( item.getFightId() );
            item.setParticipanIdRed( fight.getFighterIdRed() );
            item.setParticipanIdBlue( fight.getFighterIdBlue() );
            item.setNameRed( fight.getFighterRed().getName() );
            item.setNameBlue( fight.getFighterBlue().getName() );
            item.setClassname( fight.getFighterRed().getNewaclass().getWeightclass() );
            item.setSex( fight.getFighterRed().getSex() );
            item.setAgeDescription( fight.getFighterRed().getAge().getAgeShort() );
            StringBuilder sb = new StringBuilder(200);
            if(item.isScreenVideo()) sb.append( "__Screen__" );
            sb.append( item.getDiscepline() ).append( FILENAME_SEPERATOR ).append( item.getAgeDescription() ).append( FILENAME_SEPERATOR ).append( item.getSex()).append( FILENAME_SEPERATOR );
            sb.append( item.getClassname()).append( FILENAME_SEPERATOR ).append( item.getNameRed() ).append( FILENAME_SEPERATOR ).append( item.getNameBlue() );
            item.setFilename( sb.toString() );
        }
        
        return item;
    }

}
