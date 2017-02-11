package de.jjw.webapp.pdf.admin;

import java.io.ByteArrayOutputStream;
import java.util.List;
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

import de.jjw.model.admin.FightTimes;
import de.jjw.util.IValueConstants;
import de.jjw.webapp.util.ConfigMain;

public class FightTimesPDF
    implements IValueConstants
{
    protected final Logger log = Logger.getRootLogger();
    private ResourceBundle rb;

    private List<FightTimes> fightTimesList;

    private HttpServletResponse response;

    PdfContentByte cb;



    public FightTimesPDF( String resourceBundle, HttpServletResponse response, List<FightTimes> fightTimesList )
    {
        this.rb = ResourceBundle.getBundle( resourceBundle );
        this.fightTimesList = fightTimesList;
        this.response = response;

    }

    public void createContent()
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
            log.error( "FightTimesPDF.createContent: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
        }
    }

    public ByteArrayOutputStream createContentAsBAOS()
    {
        try
        {
            String headLine1 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1();
            String headLine2 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine2();

            Document doc = new Document( PageSize.A4, _36, _36, _36, _36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 10000 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );
            doc.open();
            cb = writer.getDirectContent();

            int cursorY = 800;
            cursorY = doPageAndTableHead( headLine1, headLine2, cursorY );
            cursorY -= 20;

            for ( int i = 0; i < fightTimesList.size(); i++ )
            {
                writeText( 36, cursorY, fightTimesList.get( i ).getTatami(), false, 10, 1 );
                writeText( 100, cursorY, fightTimesList.get( i ).getAgeDescription(), false, 10, 1 );
                writeText( 180, cursorY, fightTimesList.get( i ).getPauseAsSting(), false, 10, 1 );
                writeText( 270, cursorY, fightTimesList.get( i ).getTotalFightTimeAsSting(), false, 10, 1 );
                writeText( 350, cursorY, fightTimesList.get( i ).getFightTimeAsSting(), false, 10, 1 );
                writeText( 425, cursorY, fightTimesList.get( i ).getDiffTimeBreakAsSting(), false, 10, 1 );
                writeText( 515, cursorY, String.valueOf( fightTimesList.get( i ).getFightCount() ), false, 10, 1 );

                cursorY -= 15;

                if ( cursorY < 100 )
                {
                    doc.setPageSize( PageSize.A4 );
                    doc.newPage();
                    doc.add( new Paragraph( SPACE ) );
                    cursorY = 800;
                    cursorY = doPageAndTableHead( headLine1, headLine2, cursorY );
                    cursorY -= 20;

                }
            }
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            doc.close();
            return baos;
        }
        catch ( Exception e )
        {
            log.error( "FightTimesPDF.createContent: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
            return null;
        }
    }

    /**
     * @param headLine1
     * @param headLine2
     * @param cursorY
     * @return
     * @throws Exception
     */
    private int doPageAndTableHead( String headLine1, String headLine2, int cursorY )
        throws Exception
    {
        writeText( 300, cursorY, headLine1, true, 16, 0 );
        cursorY -= 30;
        writeText( 300, cursorY, headLine2, true, 16, 0 );
        cursorY -= 50;
        writeText( 300, cursorY, rb.getString( "pdf.fightTimes.Headline" ), true, 14, 0 );
        cursorY -= 50;

        writeText( 36, cursorY, rb.getString( "pdf.fightTimes.user" ), true, 10, 1 );
        writeText( 100, cursorY, rb.getString( "pdf.fightTimes.ageclass" ), true, 10, 1 );
        writeText( 180, cursorY, rb.getString( "pdf.fightTimes.pauseTime" ), true, 10, 1 );
        writeText( 270, cursorY, rb.getString( "pdf.fightTimes.totalFightTime" ), true, 10, 1 );
        writeText( 350, cursorY, rb.getString( "pdf.fightTimes.fightTime" ), true, 10, 1 );
        writeText( 425, cursorY, rb.getString( "pdf.fightTimes.pauseTimeInFight" ), true, 10, 1 );
        writeText( 515, cursorY, rb.getString( "pdf.fightTimes.fights" ), true, 10, 1 );
        return cursorY;
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
                bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            else
                bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( "-1" ) ) )
                text = "";
            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, size );
            if ( loc == 0 )
                cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, 0 );
            if ( loc == 1 )
                cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, 0 );
            if ( loc == 2 )
                cb.showTextAligned( PdfContentByte.ALIGN_RIGHT, text, x, y, 0 );
            cb.endText();
        }
        catch ( Exception e )
        {
            log.error( "showMedalsPDF.writeText: Exception: \n can not create pdf document \n" + e.getMessage(), e );
        }
        return;
    }

}
