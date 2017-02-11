/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : JSONExportPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.webapp.pdf.admin;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.util.ConfigMain;

public class JSONExportPDF
{
    protected final Logger log = Logger.getRootLogger();
    private ByteArrayOutputStream baos;

    private List<FighterWeb> fighter;
    
    private List<DuoTeamWeb> duos;
    
    private List<NewaFighterWeb> newaFighter;

    private PdfContentByte cb;

    private Document doc;

    private int rowIndex = 0;

    private ResourceBundle rb;

    HttpServletResponse response;

    Locale locale;

    public JSONExportPDF( String ressourceBundle , HttpServletResponse response , List<FighterWeb> fighter ,
                               List<DuoTeamWeb> duos , List<NewaFighterWeb> newaFighter , Locale locale  )
    {
        this.rb = ResourceBundle.getBundle( ressourceBundle, locale );
        this.response = response;
        this.fighter = fighter;
        this.duos = duos;
        this.newaFighter = newaFighter;
        this.locale = locale;
    }

    public void showExport()
    {
        try
        {
            ByteArrayOutputStream baos = createContentAsBAOS();

            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();
        }
        catch ( Exception e )
        {
            log.error( "JSONExportPDF.showExport: Exception: \ncan not create pdf document \n" + e.getMessage(),
                       e );
        }
    }

    public ByteArrayOutputStream createContentAsBAOS()
    {
        try
        {
            String headLine1 = rb.getString( "export_headline" );
            String headLine2 = " ";

            int numberOfRankedFighterInAClass = 0;
            doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 4096 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );
            doc.open();
            cb = writer.getDirectContent();
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            rowIndex = 800;
            writeText( 300, rowIndex, headLine1, true, 16, 0 );
            rowIndex -= 30;
            writeText( 300, rowIndex, headLine2, true, 16, 0 );
            rowIndex -= 20;

            if ( fighter.isEmpty() && duos.isEmpty() && newaFighter.isEmpty() )
            {
                rowIndex -= 20;
                writeText( 300, rowIndex, rb.getString( "results.noresults" ), true, 16, 0 );
            }

            if ( !fighter.isEmpty() )
                doFighterEntries( locale );
            if ( !duos.isEmpty() )
                doDuoEntries( locale );
            if ( !newaFighter.isEmpty() )
                doNewaFighterEntries( locale );

            doc.close();
            return baos;
        }
        catch ( Exception e )
        {
            log.error( "JSONExportPDF.createContentAsBAOS: Exception: \ncan not create pdf document \n" + e.getMessage(),
                       e );
            return null;
        }
    }

    
    private void doFighterEntries( Locale locale )
        throws Exception
    {
        rowIndex -= 15;

        writeText( 300, rowIndex, rb.getString( "export_fighting" ), true, 16, 0 );
        rowIndex -= 20;
        StringBuffer text;

        for ( int i = 0; i < fighter.size(); i++ )
        {
            text = new StringBuffer( 60 );

            text.append( fighter.get( i ).getFirstname() ).append( TypeUtil.SPACE );
            text.append( fighter.get( i ).getName() );
            writeText( 50, rowIndex, text.toString(), false, 8, 1 );
            writeText( 150, rowIndex, String.valueOf( fighter.get( i ).getYearOfBirth()), false, 8, 1 );
            writeText( 180,
                       rowIndex,
                       CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                  fighter.get( i ).getSex(), locale ),
                       false, 8, 1 );
            writeText( 230, rowIndex, fighter.get( i ).getTeam().getTeamName(), false, 8, 1 );
            writeText( 350, rowIndex, fighter.get( i ).getTeam().getRegion().getRegionShort(), false, 8, 1 );
            writeText( 380, rowIndex, fighter.get( i ).getTeam().getCountry().getCountryShort(), false, 8, 1 );
            writeText( 410, rowIndex, String.valueOf( fighter.get( i ).getWeight()), false, 8, 1 );
            rowIndex -= 13;
            if (i%10==0)
                rowIndex -= 13;
            if ( !isEnoughSpaceOnPage() )
            {
                newPage();
            }
        }
    }
    
    private void doDuoEntries( Locale locale )
                    throws Exception
    {
        rowIndex -= 15;

        writeText( 300, rowIndex, rb.getString( "export_duo" ), true, 16, 0 );
        rowIndex -= 20;
        StringBuffer text;

        for ( int i = 0; i < duos.size(); i++ )
        {
            text = new StringBuffer( 60 );
            text.append( duos.get( i ).getFirstname() ).append( TypeUtil.SPACE );
            text.append( duos.get( i ).getName() );
            writeText( 50, rowIndex, text.toString(), false, 8, 1 );
            text = new StringBuffer( 60 );
            text.append( duos.get( i ).getFirstname2() ).append( TypeUtil.SPACE );
            text.append( duos.get( i ).getName2() );
            writeText( 150, rowIndex, text.toString(), false, 8, 1 );
            writeText( 250, rowIndex, String.valueOf( duos.get( i ).getYearOfBirth() ), false, 8, 1 );
            writeText( 280, rowIndex, String.valueOf( duos.get( i ).getYearOfBirth() ), false, 8, 1 );
            writeText( 310,
                       rowIndex,
                       CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                  duos.get( i ).getSex(), locale ),
                       false, 8, 1 );

            writeText( 360, rowIndex, duos.get( i ).getTeam().getTeamName(), false, 8, 1 );
            writeText( 480, rowIndex, duos.get( i ).getTeam().getRegion().getRegionShort(), false, 8, 1 );
            writeText( 510, rowIndex, duos.get( i ).getTeam().getCountry().getCountryShort(), false, 8, 1 );
            
            rowIndex -= 13;
            if (i%10==0)
                rowIndex -= 13;
            if ( !isEnoughSpaceOnPage() )
            {
                newPage();
            }
        }
    }

    
    private void doNewaFighterEntries( Locale locale )
                    throws Exception
    {
        rowIndex -= 15;

        writeText( 300, rowIndex, rb.getString( "export_newa" ), true, 16, 0 );
        rowIndex -= 20;
        StringBuffer text;

        for ( int i = 0; i < newaFighter.size(); i++ )
        {
            text = new StringBuffer( 60 );

            text.append( newaFighter.get( i ).getFirstname() ).append( TypeUtil.SPACE );
            text.append( newaFighter.get( i ).getName() );
            writeText( 50, rowIndex, text.toString(), false, 8, 1 );
            writeText( 150, rowIndex, String.valueOf( newaFighter.get( i ).getYearOfBirth()), false, 8, 1 );
            writeText( 180,
                       rowIndex,
                       CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                  newaFighter.get( i ).getSex(), locale ),
                       false, 8, 1 );
            writeText( 230, rowIndex, newaFighter.get( i ).getTeam().getTeamName(), false, 8, 1 );
            writeText( 350, rowIndex, newaFighter.get( i ).getTeam().getRegion().getRegionShort(), false, 8, 1 );
            writeText( 380, rowIndex, newaFighter.get( i ).getTeam().getCountry().getCountryShort(), false, 8, 1 );
            writeText( 410, rowIndex, String.valueOf( newaFighter.get( i ).getWeight()), false, 8, 1 );
            rowIndex -= 13;
            if (i%10==0)
                rowIndex -= 13;
            if ( !isEnoughSpaceOnPage() )
            {
                newPage();
            }
        }
    }

    private boolean isEnoughSpaceOnPage( )
    {     
        if ( rowIndex > 30 )
        {
            return true;
        }
        return false;
    }

    private void newPage()
    {
        try
        {
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            doc.add( new Paragraph( TypeUtil.SPACE ) );
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            rowIndex = 800;
        }
        catch ( Exception e )
        {
            log.error( "FightingResultsPDF.newPage: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
        }
    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position mit der Groesse size
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text AusgabeText
     * @param bold Fettdruck
     * @param size Schriftgroesse
     * @param loc 0 fuer center, 1 fuer left
     * @throws Exception
     */
    private void writeText( int x, int y, String text, boolean bold, int size, int loc )
        throws Exception
    {
        BaseFont bf;
        try
        {
            if ( bold )
            {
                bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            }
            else
            {
                bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            }
            if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( "-1" ) ) )
            {
                text = "";
            }
            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, size );
            if ( loc == 0 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, 0 );
            }
            if ( loc == 1 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, 0 );
            }
            cb.endText();
        }
        catch ( Exception e )
        {
            log.error( "JSONExportPDF.writeText: Exception: \n can not create pdf document \n"
                           + e.getMessage(), e );
        }
        return;
    }

}
