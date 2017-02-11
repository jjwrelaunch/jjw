package de.jjw.webapp.pdf;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.service.TeamResultDTO;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.webapp.util.ConfigMain;

public class TeamResultsPDF
    extends BasePDF
{
    private ResourceBundle rb;

    private HttpServletResponse response;

    private Document doc;

    public TeamResultsPDF( String resourceBundle, HttpServletResponse response, Locale local )
    {
        this.rb = ResourceBundle.getBundle( resourceBundle, local );
        this.response = response;
    }

    public void showResults( TeamResultDTO result )
    {
        try
        {
            String headLine1 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1();
            String headLine2 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine2();
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );
            doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );
            doc.open();
            cb = writer.getDirectContent();
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            int rowIndex = 800;
            writeText( 300, rowIndex, headLine1, true, 16, 0 );
            rowIndex -= 30;
            writeText( 300, rowIndex, headLine2, true, 16, 0 );
            rowIndex -= 50;

            if (!result.isResultsForTeam()){
                writeText( 300, rowIndex, rb.getString( "pdf.noResults" ), true, 16, 0 );
            }else
            {
                if ( !result.getFighterList().isEmpty() )
                    writeText( 300, rowIndex, rb.getString( "pdf.Headline" ) + " "
                        + result.getFighterList().get( 0 ).getTeam().getTeamName(), true, 16, 0 );
                else
                    writeText( 300, rowIndex, rb.getString( "pdf.Headline" ) + " "
                        + result.getDuoteamList().get( 0 ).getTeam().getTeamName(), true, 16, 0 );
                rowIndex -= 40;

                StringBuffer sb;
                int place = 0;
                for(FighterWeb fighter: result.getFighterList()){

                    if ( place != fighter.getPlace() )
                    {
                        place = fighter.getPlace();
                        rowIndex -= 5;
                    }
                    sb= new StringBuffer(100);
                    sb.append( rb.getString( "pdf.place." + fighter.getPlaceWeb() ) );
                    sb.append( " " );
                    sb.append( fighter.getFirstname() );
                    sb.append( " " );
                    sb.append( fighter.getName());
                    writeText( 24, rowIndex, sb.toString(), false, 10, 1 );

                    writeText( 250, rowIndex, fighter.getAge().getDescription(), false, 10, 1 );
                    writeText( 350, rowIndex, fighter.getSexWeb(), false, 10, 1 );
                    writeText( 420, rowIndex, fighter.getFightingclass().getWeightclass(), false, 10, 1 );

                    rowIndex -= 10;
                }

                place = 0;
                for ( DuoTeamWeb team : result.getDuoteamList() )
                {
                    if ( place != team.getPlace() )
                    {
                        place = team.getPlace();
                        rowIndex -= 5;
                    }
                    sb = new StringBuffer( 100 );
                    sb.append( rb.getString( "pdf.place." + team.getPlaceWeb() ) );
                    sb.append( " " );
                    sb.append( team.getFirstname() );
                    sb.append( " " );
                    sb.append( team.getName() );
                    sb.append( " / " );
                    sb.append( team.getFirstname2() );
                    sb.append( " " );
                    sb.append( team.getName2() );
                    writeText( 24, rowIndex, sb.toString(), false, 10, 1 );

                    writeText( 250, rowIndex, team.getAge().getDescription(), false, 10, 1 );
                    writeText( 350, rowIndex, team.getSexWeb(), false, 10, 1 );
                    writeText( 420, rowIndex, team.getDuoclass().getDuoclassName(), false, 10, 1 );

                    rowIndex -= 10;
                }
            }
                      
            
            doc.close();
            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();
        }
        catch ( Exception e )
        {
            System.err.println( "TeamResultsPDF.showResults: Exception: \ncan not create pdf document \n"
                + e.getMessage() );
        }
    }
}
