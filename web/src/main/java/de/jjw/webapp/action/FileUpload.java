/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FileUpload.java
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

package de.jjw.webapp.action;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import de.jjw.util.Constants;

public class FileUpload
    extends BasePage
    implements Serializable
{
    private UploadedFile file;

    private String name;

    public UploadedFile getFile()
    {
        return file;
    }

    public void setFile( UploadedFile file )
    {
        this.file = file;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String upload()
        throws IOException
    {
        HttpServletRequest request = getRequest();

        // write the file to the filesystem
        // the directory to upload to
        String uploadDir = getServletContext().getRealPath( "/resources" ) + "/" + request.getRemoteUser() + "/";

        // Create the directory if it doesn't exist
        File dirPath = new File( uploadDir );

        if ( !dirPath.exists() )
        {
            dirPath.mkdirs();
        }

        //retrieve the file data
        InputStream stream = file.getInputStream();

        //write the file to the file specified
        OutputStream bos = new FileOutputStream( uploadDir + file.getName() );
        int bytesRead = 0;
        byte[] buffer = new byte[8192];

        while ( ( bytesRead = stream.read( buffer, 0, 8192 ) ) != -1 )
        {
            bos.write( buffer, 0, bytesRead );
        }

        bos.close();

        //close the stream
        stream.close();

        // place the data into the request for retrieval on next page
        request.setAttribute( "friendlyName", name );
        request.setAttribute( "fileName", file.getName() );
        request.setAttribute( "contentType", file.getContentType() );
        request.setAttribute( "size", file.getSize() + " bytes" );
        request.setAttribute( "location", dirPath.getAbsolutePath() + Constants.FILE_SEP + file.getName() );

        String link = request.getContextPath() + "/resources" + "/" + request.getRemoteUser() + "/";

        request.setAttribute( "link", link + file.getName() );

        return "success";
    }
}
