package de.jjw.webapp.pdf.newa;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;

public class NewaKo64ComplexListPDF
    extends NewaKo16ComplexListPDF
{
    private static String[] loserRoundAArray = { "1", "", "3", "", "5", "", "7", "", "9", "", "11", "", "13", "", "15",
        "", "17", "", "19", "", "21", "", "23", "", "25", "", "27", "", "29", "", "31", "" };

    private static String[] loserRoundBArray = { "2", "", "4", "", "6", "", "8", "", "10", "", "12", "", "14", "",
        "16", "", "18", "", "20", "", "22", "", "24", "", "26", "", "28", "", "30", "", "32", "" };

    public NewaKo64ComplexListPDF( String ressourceBundle, NewaKoClass newaclass,
                                       HttpServletResponse response, String headLine, Locale local )
        throws Exception
    {
        super( ressourceBundle, newaclass, response, headLine, local );
    }

    public NewaKo64ComplexListPDF( String ressourceBundle, HttpServletResponse response, String headLine,
                                       Locale local )
    {
        super( ressourceBundle, response, headLine, local );
    }

    public void createPoolBlanco()
        throws Exception
    {
        Document doc = new Document( PageSize.A4.rotate(), _36, _36, _36, _36 );

        ByteArrayOutputStream baos = new ByteArrayOutputStream( 20000 );

        PdfWriter writer = PdfWriter.getInstance( doc, baos );

        doc.open();

        cb = writer.getDirectContent();
        doc.setPageSize( PageSize.A4 );
        doc.newPage();
        doc.add( new Paragraph( SPACE ) );

        constMainRound = 800;
        constLoserRound = 450;

        writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
        createMainRoundTable( constMainRound );
        createMainRoundAStandardText( constMainRound );
        createFinal( constLoserRound );
        cb.stroke();
        // Pool B
        doc.setPageSize( PageSize.A4 );
        doc.newPage();
        writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
        createMainRoundTable( constMainRound );
        createMainRoundBStandardText( constMainRound );
        createFinal( constLoserRound );
        cb.stroke();

        // LoserRound A
        doc.setPageSize( PageSize.A4.rotate() );
        doc.newPage();
        createLoserRoundTable( constLoserRound, loserRoundAArray );
        createLoserRoundAStandardText( constLoserRound );
        writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
        cb.stroke();

        // LoserRound B
        doc.setPageSize( PageSize.A4.rotate() );
        doc.newPage();
        createLoserRoundTable( constLoserRound, loserRoundBArray );
        createLoserRoundBStandardText( constLoserRound );
        writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
        cb.stroke();

        doc.close();

        response.setContentLength( baos.size() );

        ServletOutputStream out = response.getOutputStream();
        baos.writeTo( out );
        baos.flush();

    }

    @Override
    public void createTables( NewaKoClass newaclass )
        throws Exception
    {
        try
        {
            doc = new Document( PageSize.A4.rotate(), _36, _36, _36, _36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 50000 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );

            doc.open();
            cb = writer.getDirectContent();
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            doc.add( new Paragraph( SPACE ) );

            constMainRound = 800;
            constLoserRound = 450;

            writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
            createMainRoundTable( constMainRound );
            createMainRoundAStandardText( constMainRound );
            createFinal( constLoserRound );

            String headline2 = "";

            if ( newaclass != null )
            {
                writeText( 340, 790, headLine, true, _16 );
                headline2 =
                    newaclass.getAge().getDescription()
                        + SPACE
                        + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                     newaclass.getSex(),
                                                                                     response.getLocale() ) + SPACE
                        + newaclass.getWeightclass() + KG;
                writeText( 340, 760, headline2, true, 14 );
                fillMainRoundTable( newaclass.getFightListMapPoolA() );
                fillFinal( newaclass );
            }
            cb.stroke();
            // Pool B
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
            createMainRoundTable( constMainRound );
            createMainRoundBStandardText( constMainRound );
            createFinal( constLoserRound );
            if ( newaclass != null )
            {
                writeText( 340, 790, headLine, true, _16 );
                writeText( 340, 760, headline2, true, 14 );
                fillMainRoundTable( newaclass.getFightListMapPoolB() );
                fillFinal( newaclass );
            }
            cb.stroke();

            // LoserRound A
            doc.setPageSize( PageSize.A4.rotate() );
            doc.newPage();
            createLoserRoundTable( constLoserRound, loserRoundAArray );
            createLoserRoundAStandardText( constLoserRound );
            writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
            if ( newaclass != null )
            {
                writeText( 600, 540, headLine, true, _16 );
                writeText( 600, 510, headline2, true, 14 );
                fillLoserRoundTable( newaclass.getFightListLooserMapPoolA() );
            }
            cb.stroke();

            // LoserRound B
            doc.setPageSize( PageSize.A4.rotate() );
            doc.newPage();
            createLoserRoundTable( constLoserRound, loserRoundBArray );
            createLoserRoundBStandardText( constLoserRound );
            writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
            if ( newaclass != null )
            {
                writeText( 600, 540, headLine, true, _16 );
                writeText( 600, 510, headline2, true, 14 );
                fillLoserRoundTable( newaclass.getFightListLooserMapPoolB() );
            }
            cb.stroke();

            if ( newaclass != null )
            {
                manageFightListForKo64( newaclass, writer );
            }
            doc.close();

            response.setContentLength( baos.size() ); 
            
            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();
        }
        catch ( Exception e )
        {
            log.error( "NewaKo64ComplexListPDF.createMainRoundTable: Exception: \ncan not create pdf document \n", e );
            throw e;
        }
    }

    private int createMainRoundTable( int konst )
        throws Exception
    {
        cb.setColorStroke( new BaseColor( 0x0, 0x0, 0x0 ) );
        cb.setLineWidth( 1f );
        int height = 12; // konst

        for ( int i = 0; i < 63; i++ )
        {
            if ( i % 2 == 0 )
            {
                cb.rectangle( 20, konst - i * height, 16, height );
                cb.rectangle( 36, konst - i * height, 130, height );
                // writeText(28,konst-i*height+2,String.valueOf (i), false,10,0);
            }
            if ( i % 4 == 1 )
            {
                cb.rectangle( 150, konst - i * height, 16, height );
                cb.rectangle( 166, konst - i * height, 80, height );
            }

            if ( i % 8 == 3 )
            {
                cb.rectangle( 230, konst - i * height - height, 16, 3 * height );
                cb.rectangle( 246, konst - i * height, 80, height );
            }
            if ( i % 16 == 7 )
            {
                cb.rectangle( 310, konst - i * height - 3 * height, 16, 7 * height );
                cb.rectangle( 326, konst - i * height, 80, height );
            }
            if ( i == 15 || i == 47 )
            {
                cb.rectangle( 390, konst - i * height - 7 * height, 16, 15 * height );
                cb.rectangle( 406, konst - i * height, 80, height );
            }
            if ( i == 31 )
            {
                cb.rectangle( 470, konst - i * height - 15 * height, 16, 31 * height );
                cb.rectangle( 486, konst - i * height, 80, height );
            }
            cb.stroke();
        }
        return konst;
    }


    private void createFinal( int konst )
        throws Exception
    {
        int height = 12;
        int konstFinal = 4;
        writeText( 406, konstFinal + 8 * height + 2, rb.getString( "pdf.Finale" ), true, 18, 1 );

        cb.rectangle( 406, konstFinal + 6 * height, 80, height );
        cb.rectangle( 406, konstFinal + 4 * height, 80, height );
        cb.rectangle( 470, konstFinal + 5 * height, 16, height );
        cb.rectangle( 486, konstFinal + 5 * height, 80, height );
        writeText( 478, konstFinal + 5 * height + 2, String.valueOf( 123 ), false, 10, 0 );
        cb.stroke();
    }

    private void createMainRoundAStandardText( int konst )
        throws Exception
    {
        int height = 12;
        writeText( 28, konst + 2, String.valueOf( 1 ), false, 10, 0 );
        writeText( 28, konst - 32 * height + 2, String.valueOf( 3 ), false, 10, 0 );
        writeText( 28, konst - 2 * height + 2, String.valueOf( 33 ), false, 10, 0 );
        writeText( 28, konst - 34 * height + 2, String.valueOf( 35 ), false, 10, 0 );
        writeText( 28, konst - 4 * height + 2, String.valueOf( 17 ), false, 10, 0 );
        writeText( 28, konst - 36 * height + 2, String.valueOf( 19 ), false, 10, 0 );
        writeText( 28, konst - 6 * height + 2, String.valueOf( 49 ), false, 10, 0 );
        writeText( 28, konst - 38 * height + 2, String.valueOf( 51 ), false, 10, 0 );
        writeText( 28, konst - 8 * height + 2, String.valueOf( 9 ), false, 10, 0 );
        writeText( 28, konst - 40 * height + 2, String.valueOf( 11 ), false, 10, 0 );
        writeText( 28, konst - 10 * height + 2, String.valueOf( 41 ), false, 10, 0 );
        writeText( 28, konst - 42 * height + 2, String.valueOf( 43 ), false, 10, 0 );
        writeText( 28, konst - 12 * height + 2, String.valueOf( 25 ), false, 10, 0 );
        writeText( 28, konst - 44 * height + 2, String.valueOf( 27 ), false, 10, 0 );
        writeText( 28, konst - 14 * height + 2, String.valueOf( 57 ), false, 10, 0 );
        writeText( 28, konst - 46 * height + 2, String.valueOf( 59 ), false, 10, 0 );
        writeText( 28, konst - 16 * height + 2, String.valueOf( 5 ), false, 10, 0 );
        writeText( 28, konst - 48 * height + 2, String.valueOf( 7 ), false, 10, 0 );
        writeText( 28, konst - 18 * height + 2, String.valueOf( 37 ), false, 10, 0 );
        writeText( 28, konst - 50 * height + 2, String.valueOf( 39 ), false, 10, 0 );
        writeText( 28, konst - 20 * height + 2, String.valueOf( 21 ), false, 10, 0 );
        writeText( 28, konst - 52 * height + 2, String.valueOf( 23 ), false, 10, 0 );
        writeText( 28, konst - 22 * height + 2, String.valueOf( 53 ), false, 10, 0 );
        writeText( 28, konst - 54 * height + 2, String.valueOf( 55 ), false, 10, 0 );
        writeText( 28, konst - 24 * height + 2, String.valueOf( 13 ), false, 10, 0 );
        writeText( 28, konst - 56 * height + 2, String.valueOf( 15 ), false, 10, 0 );
        writeText( 28, konst - 26 * height + 2, String.valueOf( 45 ), false, 10, 0 );
        writeText( 28, konst - 58 * height + 2, String.valueOf( 47 ), false, 10, 0 );
        writeText( 28, konst - 28 * height + 2, String.valueOf( 29 ), false, 10, 0 );
        writeText( 28, konst - 60 * height + 2, String.valueOf( 31 ), false, 10, 0 );
        writeText( 28, konst - 30 * height + 2, String.valueOf( 61 ), false, 10, 0 );
        writeText( 28, konst - 62 * height + 2, String.valueOf( 63 ), false, 10, 0 );

        writeText( 158, konst - height + 2, String.valueOf( 1 ), false, 10, 0 );
        writeText( 158, konst - 33 * height + 2, String.valueOf( 17 ), false, 10, 0 );
        writeText( 158, konst - 5 * height + 2, String.valueOf( 3 ), false, 10, 0 );
        writeText( 158, konst - 37 * height + 2, String.valueOf( 19 ), false, 10, 0 );
        writeText( 158, konst - 9 * height + 2, String.valueOf( 5 ), false, 10, 0 );
        writeText( 158, konst - 41 * height + 2, String.valueOf( 21 ), false, 10, 0 );
        writeText( 158, konst - 13 * height + 2, String.valueOf( 7 ), false, 10, 0 );
        writeText( 158, konst - 45 * height + 2, String.valueOf( 23 ), false, 10, 0 );
        writeText( 158, konst - 17 * height + 2, String.valueOf( 9 ), false, 10, 0 );
        writeText( 158, konst - 49 * height + 2, String.valueOf( 25 ), false, 10, 0 );
        writeText( 158, konst - 21 * height + 2, String.valueOf( 11 ), false, 10, 0 );
        writeText( 158, konst - 53 * height + 2, String.valueOf( 27 ), false, 10, 0 );
        writeText( 158, konst - 25 * height + 2, String.valueOf( 13 ), false, 10, 0 );
        writeText( 158, konst - 57 * height + 2, String.valueOf( 29 ), false, 10, 0 );
        writeText( 158, konst - 29 * height + 2, String.valueOf( 15 ), false, 10, 0 );
        writeText( 158, konst - 61 * height + 2, String.valueOf( 31 ), false, 10, 0 );

        writeText( 238, konst - 3 * height + 2, String.valueOf( 33 ), false, 10, 0 );
        writeText( 238, konst - 35 * height + 2, String.valueOf( 41 ), false, 10, 0 );
        writeText( 238, konst - 11 * height + 2, String.valueOf( 35 ), false, 10, 0 );
        writeText( 238, konst - 43 * height + 2, String.valueOf( 43 ), false, 10, 0 );
        writeText( 238, konst - 19 * height + 2, String.valueOf( 37 ), false, 10, 0 );
        writeText( 238, konst - 51 * height + 2, String.valueOf( 45 ), false, 10, 0 );
        writeText( 238, konst - 27 * height + 2, String.valueOf( 39 ), false, 10, 0 );
        writeText( 238, konst - 59 * height + 2, String.valueOf( 47 ), false, 10, 0 );

        writeText( 318, konst - 7 * height + 2, String.valueOf( 49 ), false, 10, 0 );
        writeText( 318, konst - 39 * height + 2, String.valueOf( 53 ), false, 10, 0 );
        writeText( 318, konst - 23 * height + 2, String.valueOf( 51 ), false, 10, 0 );
        writeText( 318, konst - 55 * height + 2, String.valueOf( 55 ), false, 10, 0 );

        writeText( 398, konst - 15 * height + 2, String.valueOf( 57 ), false, 10, 0 );
        writeText( 398, konst - 47 * height + 2, String.valueOf( 59 ), false, 10, 0 );
        writeText( 478, konst - 31 * height + 2, String.valueOf( 61 ), false, 10, 0 );
        writeText( 420, konst - 31 * height + 2, "Pool A", true, 16, 0 );
    }

    private void createMainRoundBStandardText( int konst )
        throws Exception
    {
        int height = 12;
        writeText( 28, konst + 2, String.valueOf( 2 ), false, 10, 0 );
        writeText( 28, konst - 32 * height + 2, String.valueOf( 4 ), false, 10, 0 );
        writeText( 28, konst - 2 * height + 2, String.valueOf( 34 ), false, 10, 0 );
        writeText( 28, konst - 34 * height + 2, String.valueOf( 36 ), false, 10, 0 );
        writeText( 28, konst - 4 * height + 2, String.valueOf( 18 ), false, 10, 0 );
        writeText( 28, konst - 36 * height + 2, String.valueOf( 20 ), false, 10, 0 );
        writeText( 28, konst - 6 * height + 2, String.valueOf( 50 ), false, 10, 0 );
        writeText( 28, konst - 38 * height + 2, String.valueOf( 52 ), false, 10, 0 );
        writeText( 28, konst - 8 * height + 2, String.valueOf( 10 ), false, 10, 0 );
        writeText( 28, konst - 40 * height + 2, String.valueOf( 12 ), false, 10, 0 );
        writeText( 28, konst - 10 * height + 2, String.valueOf( 41 ), false, 10, 0 );
        writeText( 28, konst - 42 * height + 2, String.valueOf( 44 ), false, 10, 0 );
        writeText( 28, konst - 12 * height + 2, String.valueOf( 26 ), false, 10, 0 );
        writeText( 28, konst - 44 * height + 2, String.valueOf( 28 ), false, 10, 0 );
        writeText( 28, konst - 14 * height + 2, String.valueOf( 58 ), false, 10, 0 );
        writeText( 28, konst - 46 * height + 2, String.valueOf( 60 ), false, 10, 0 );
        writeText( 28, konst - 16 * height + 2, String.valueOf( 6 ), false, 10, 0 );
        writeText( 28, konst - 48 * height + 2, String.valueOf( 8 ), false, 10, 0 );
        writeText( 28, konst - 18 * height + 2, String.valueOf( 38 ), false, 10, 0 );
        writeText( 28, konst - 50 * height + 2, String.valueOf( 40 ), false, 10, 0 );
        writeText( 28, konst - 20 * height + 2, String.valueOf( 22 ), false, 10, 0 );
        writeText( 28, konst - 52 * height + 2, String.valueOf( 24 ), false, 10, 0 );
        writeText( 28, konst - 22 * height + 2, String.valueOf( 54 ), false, 10, 0 );
        writeText( 28, konst - 54 * height + 2, String.valueOf( 56 ), false, 10, 0 );
        writeText( 28, konst - 24 * height + 2, String.valueOf( 14 ), false, 10, 0 );
        writeText( 28, konst - 56 * height + 2, String.valueOf( 16 ), false, 10, 0 );
        writeText( 28, konst - 26 * height + 2, String.valueOf( 46 ), false, 10, 0 );
        writeText( 28, konst - 58 * height + 2, String.valueOf( 48 ), false, 10, 0 );
        writeText( 28, konst - 28 * height + 2, String.valueOf( 30 ), false, 10, 0 );
        writeText( 28, konst - 60 * height + 2, String.valueOf( 32 ), false, 10, 0 );
        writeText( 28, konst - 30 * height + 2, String.valueOf( 62 ), false, 10, 0 );
        writeText( 28, konst - 62 * height + 2, String.valueOf( 64 ), false, 10, 0 );

        writeText( 158, konst - height + 2, String.valueOf( 2 ), false, 10, 0 );
        writeText( 158, konst - 33 * height + 2, String.valueOf( 18 ), false, 10, 0 );
        writeText( 158, konst - 5 * height + 2, String.valueOf( 4 ), false, 10, 0 );
        writeText( 158, konst - 37 * height + 2, String.valueOf( 20 ), false, 10, 0 );
        writeText( 158, konst - 9 * height + 2, String.valueOf( 6 ), false, 10, 0 );
        writeText( 158, konst - 41 * height + 2, String.valueOf( 22 ), false, 10, 0 );
        writeText( 158, konst - 13 * height + 2, String.valueOf( 8 ), false, 10, 0 );
        writeText( 158, konst - 45 * height + 2, String.valueOf( 24 ), false, 10, 0 );
        writeText( 158, konst - 17 * height + 2, String.valueOf( 10 ), false, 10, 0 );
        writeText( 158, konst - 49 * height + 2, String.valueOf( 26 ), false, 10, 0 );
        writeText( 158, konst - 21 * height + 2, String.valueOf( 12 ), false, 10, 0 );
        writeText( 158, konst - 53 * height + 2, String.valueOf( 28 ), false, 10, 0 );
        writeText( 158, konst - 25 * height + 2, String.valueOf( 14 ), false, 10, 0 );
        writeText( 158, konst - 57 * height + 2, String.valueOf( 30 ), false, 10, 0 );
        writeText( 158, konst - 29 * height + 2, String.valueOf( 16 ), false, 10, 0 );
        writeText( 158, konst - 61 * height + 2, String.valueOf( 32 ), false, 10, 0 );

        writeText( 238, konst - 3 * height + 2, String.valueOf( 34 ), false, 10, 0 );
        writeText( 238, konst - 35 * height + 2, String.valueOf( 42 ), false, 10, 0 );
        writeText( 238, konst - 11 * height + 2, String.valueOf( 36 ), false, 10, 0 );
        writeText( 238, konst - 43 * height + 2, String.valueOf( 44 ), false, 10, 0 );
        writeText( 238, konst - 19 * height + 2, String.valueOf( 38 ), false, 10, 0 );
        writeText( 238, konst - 51 * height + 2, String.valueOf( 46 ), false, 10, 0 );
        writeText( 238, konst - 27 * height + 2, String.valueOf( 40 ), false, 10, 0 );
        writeText( 238, konst - 59 * height + 2, String.valueOf( 48 ), false, 10, 0 );

        writeText( 318, konst - 7 * height + 2, String.valueOf( 50 ), false, 10, 0 );
        writeText( 318, konst - 39 * height + 2, String.valueOf( 54 ), false, 10, 0 );
        writeText( 318, konst - 23 * height + 2, String.valueOf( 52 ), false, 10, 0 );
        writeText( 318, konst - 55 * height + 2, String.valueOf( 56 ), false, 10, 0 );

        writeText( 398, konst - 15 * height + 2, String.valueOf( 58 ), false, 10, 0 );
        writeText( 398, konst - 47 * height + 2, String.valueOf( 60 ), false, 10, 0 );
        writeText( 478, konst - 31 * height + 2, String.valueOf( 62 ), false, 10, 0 );
        writeText( 420, konst - 31 * height + 2, "Pool B", true, 16, 0 );
    }

    private void createLoserRoundAStandardText( int konst )
        throws Exception
    {
        int height = 12;

        writeText( 108, konst - 1 * height + 2, String.valueOf( 63 ), false, 10, 0 );
        writeText( 108, konst - 5 * height + 2, String.valueOf( 64 ), false, 10, 0 );
        writeText( 108, konst - 9 * height + 2, String.valueOf( 65 ), false, 10, 0 );
        writeText( 108, konst - 13 * height + 2, String.valueOf( 66 ), false, 10, 0 );
        writeText( 108, konst - 17 * height + 2, String.valueOf( 67 ), false, 10, 0 );
        writeText( 108, konst - 21 * height + 2, String.valueOf( 68 ), false, 10, 0 );
        writeText( 108, konst - 25 * height + 2, String.valueOf( 69 ), false, 10, 0 );
        writeText( 108, konst - 29 * height + 2, String.valueOf( 70 ), false, 10, 0 );
        writeText( 134, konst + height + 2, String.valueOf( 37 ), false, 10, 0 );
        writeText( 134, konst - 3 * height + 2, String.valueOf( 39 ), false, 10, 0 );
        writeText( 134, konst - 7 * height + 2, String.valueOf( 33 ), false, 10, 0 );
        writeText( 134, konst - 11 * height + 2, String.valueOf( 35 ), false, 10, 0 );
        writeText( 134, konst - 15 * height + 2, String.valueOf( 45 ), false, 10, 0 );
        writeText( 134, konst - 19 * height + 2, String.valueOf( 47 ), false, 10, 0 );
        writeText( 134, konst - 23 * height + 2, String.valueOf( 41 ), false, 10, 0 );
        writeText( 134, konst - 27 * height + 2, String.valueOf( 43 ), false, 10, 0 );

        writeText( 214, konst - 0 * height + 2, String.valueOf( 79 ), false, 10, 0 );
        writeText( 214, konst - 4 * height + 2, String.valueOf( 80 ), false, 10, 0 );
        writeText( 214, konst - 8 * height + 2, String.valueOf( 81 ), false, 10, 0 );
        writeText( 214, konst - 12 * height + 2, String.valueOf( 82 ), false, 10, 0 );
        writeText( 214, konst - 16 * height + 2, String.valueOf( 83 ), false, 10, 0 );
        writeText( 214, konst - 20 * height + 2, String.valueOf( 84 ), false, 10, 0 );
        writeText( 214, konst - 24 * height + 2, String.valueOf( 85 ), false, 10, 0 );
        writeText( 214, konst - 28 * height + 2, String.valueOf( 86 ), false, 10, 0 );

        writeText( 294, konst - 2 * height + 2, String.valueOf( 95 ), false, 10, 0 );
        writeText( 294, konst - 10 * height + 2, String.valueOf( 96 ), false, 10, 0 );
        writeText( 294, konst - 18 * height + 2, String.valueOf( 97 ), false, 10, 0 );
        writeText( 294, konst - 26 * height + 2, String.valueOf( 98 ), false, 10, 0 );
        writeText( 320, konst + 2, String.valueOf( 49 ), false, 10, 0 );
        writeText( 320, konst - 8 * height + 2, String.valueOf( 51 ), false, 10, 0 );
        writeText( 320, konst - 16 * height + 2, String.valueOf( 53 ), false, 10, 0 );
        writeText( 320, konst - 24 * height + 2, String.valueOf( 55 ), false, 10, 0 );

        writeText( 400, konst - 1 * height + 2, String.valueOf( 103 ), false, 10, 0 );
        writeText( 400, konst - 9 * height + 2, String.valueOf( 104 ), false, 10, 0 );
        writeText( 400, konst - 17 * height + 2, String.valueOf( 105 ), false, 10, 0 );
        writeText( 400, konst - 25 * height + 2, String.valueOf( 106 ), false, 10, 0 );

        writeText( 480, konst - 5 * height + 2, String.valueOf( 111 ), false, 10, 0 );
        writeText( 480, konst - 21 * height + 2, String.valueOf( 112 ), false, 10, 0 );
        writeText( 506, konst - 3 * height + 2, String.valueOf( 57 ), false, 10, 0 );
        writeText( 506, konst - 19 * height + 2, String.valueOf( 59 ), false, 10, 0 );

        writeText( 586, konst - 4 * height + 2, String.valueOf( 115 ), false, 10, 0 );
        writeText( 586, konst - 20 * height + 2, String.valueOf( 116 ), false, 10, 0 );

        writeText( 666, konst - 12 * height + 2, String.valueOf( 119 ), false, 10, 0 );
        writeText( 708, konst - 30 * height + 2, String.valueOf( 121 ), false, 10, 0 );
        writeText( 628, konst - 29 * height + 2, String.valueOf( 61 ), false, 10, 0 );
        writeText( 628, konst - 31 * height + 2, String.valueOf( 119 ), false, 10, 0 );

        writeText( 756, konst - 34 * height + 2, rb.getString( "pdf.3rdPlace" ), true, 10, 0 );
        writeText( 36, konst + 5 * height, rb.getString( "pdf.oneLoserRoundA" ), true, 24, 1 );
    }

    private void createLoserRoundBStandardText( int konst )
        throws Exception
    {
        int height = 12;

        writeText( 108, konst - 1 * height + 2, String.valueOf( 71 ), false, 10, 0 );
        writeText( 108, konst - 5 * height + 2, String.valueOf( 72 ), false, 10, 0 );
        writeText( 108, konst - 9 * height + 2, String.valueOf( 73 ), false, 10, 0 );
        writeText( 108, konst - 13 * height + 2, String.valueOf( 74 ), false, 10, 0 );
        writeText( 108, konst - 17 * height + 2, String.valueOf( 75 ), false, 10, 0 );
        writeText( 108, konst - 21 * height + 2, String.valueOf( 76 ), false, 10, 0 );
        writeText( 108, konst - 25 * height + 2, String.valueOf( 77 ), false, 10, 0 );
        writeText( 108, konst - 29 * height + 2, String.valueOf( 78 ), false, 10, 0 );
        writeText( 134, konst + height + 2, String.valueOf( 38 ), false, 10, 0 );
        writeText( 134, konst - 3 * height + 2, String.valueOf( 40 ), false, 10, 0 );
        writeText( 134, konst - 7 * height + 2, String.valueOf( 42 ), false, 10, 0 );
        writeText( 134, konst - 11 * height + 2, String.valueOf( 44 ), false, 10, 0 );
        writeText( 134, konst - 15 * height + 2, String.valueOf( 46 ), false, 10, 0 );
        writeText( 134, konst - 19 * height + 2, String.valueOf( 48 ), false, 10, 0 );
        writeText( 134, konst - 23 * height + 2, String.valueOf( 50 ), false, 10, 0 );
        writeText( 134, konst - 27 * height + 2, String.valueOf( 52 ), false, 10, 0 );

        writeText( 214, konst - 0 * height + 2, String.valueOf( 87 ), false, 10, 0 );
        writeText( 214, konst - 4 * height + 2, String.valueOf( 88 ), false, 10, 0 );
        writeText( 214, konst - 8 * height + 2, String.valueOf( 89 ), false, 10, 0 );
        writeText( 214, konst - 12 * height + 2, String.valueOf( 90 ), false, 10, 0 );
        writeText( 214, konst - 16 * height + 2, String.valueOf( 91 ), false, 10, 0 );
        writeText( 214, konst - 20 * height + 2, String.valueOf( 92 ), false, 10, 0 );
        writeText( 214, konst - 24 * height + 2, String.valueOf( 93 ), false, 10, 0 );
        writeText( 214, konst - 28 * height + 2, String.valueOf( 94 ), false, 10, 0 );

        writeText( 294, konst - 2 * height + 2, String.valueOf( 99 ), false, 10, 0 );
        writeText( 294, konst - 10 * height + 2, String.valueOf( 100 ), false, 10, 0 );
        writeText( 294, konst - 18 * height + 2, String.valueOf( 101 ), false, 10, 0 );
        writeText( 294, konst - 26 * height + 2, String.valueOf( 102 ), false, 10, 0 );
        writeText( 320, konst + 2, String.valueOf( 50 ), false, 10, 0 );
        writeText( 320, konst - 8 * height + 2, String.valueOf( 52 ), false, 10, 0 );
        writeText( 320, konst - 16 * height + 2, String.valueOf( 54 ), false, 10, 0 );
        writeText( 320, konst - 24 * height + 2, String.valueOf( 56 ), false, 10, 0 );

        writeText( 400, konst - 1 * height + 2, String.valueOf( 107 ), false, 10, 0 );
        writeText( 400, konst - 9 * height + 2, String.valueOf( 108 ), false, 10, 0 );
        writeText( 400, konst - 17 * height + 2, String.valueOf( 109 ), false, 10, 0 );
        writeText( 400, konst - 25 * height + 2, String.valueOf( 110 ), false, 10, 0 );

        writeText( 480, konst - 5 * height + 2, String.valueOf( 113 ), false, 10, 0 );
        writeText( 480, konst - 21 * height + 2, String.valueOf( 114 ), false, 10, 0 );
        writeText( 506, konst - 3 * height + 2, String.valueOf( 58 ), false, 10, 0 );
        writeText( 506, konst - 19 * height + 2, String.valueOf( 60 ), false, 10, 0 );

        writeText( 586, konst - 4 * height + 2, String.valueOf( 117 ), false, 10, 0 );
        writeText( 586, konst - 20 * height + 2, String.valueOf( 118 ), false, 10, 0 );

        writeText( 666, konst - 12 * height + 2, String.valueOf( 120 ), false, 10, 0 );
        writeText( 708, konst - 30 * height + 2, String.valueOf( 122 ), false, 10, 0 );
        writeText( 628, konst - 29 * height + 2, String.valueOf( 62 ), false, 10, 0 );
        writeText( 628, konst - 31 * height + 2, String.valueOf( 120 ), false, 10, 0 );

        writeText( 756, konst - 34 * height + 2, rb.getString( "pdf.3rdPlace" ), true, 10, 0 );
        writeText( 36, konst + 5 * height, rb.getString( "pdf.oneLoserRoundB" ), true, 24, 1 );
    }

    private int createLoserRoundTable( int konst, String[] numbersFirstRound )
        throws Exception
    {
        cb.setColorStroke( new BaseColor( 0x0, 0x0, 0x0 ) );
        cb.setLineWidth( 1f );
        int height = 12, LoserNr = 0; // konst

        for ( int i = 0; i < 32; i++ )
        {
            if ( i % 2 == 0 )
            {
                LoserNr++;
                cb.rectangle( 20, konst - i * height, 16, height );
                cb.rectangle( 36, konst - i * height, 80, height );
                writeText( 28, konst - i * height + 2, numbersFirstRound[i], false, 10, 0 );
            }
            if ( i % 4 == 1 )
            {
                cb.rectangle( 100, konst - i * height, 16, height );
                cb.rectangle( 116, konst - i * height, 106, height );
                cb.rectangle( 126, konst - i * height + 2 * height, 16, height );
                cb.rectangle( 142, konst - i * height + 2 * height, 80, height );
            }
            if ( i % 4 == 0 )
            {
                cb.rectangle( 206, konst - i * height, 16, height );
                cb.rectangle( 222, konst - i * height, 80, height );
            }
            if ( i % 8 == 2 )
            {
                cb.rectangle( 286, konst - i * height - height, 16, 3 * height );
                cb.rectangle( 302, konst - i * height, 106, height );
                cb.rectangle( 328, konst - i * height + 2 * height, 80, height );
                cb.rectangle( 312, konst - i * height + 2 * height, 16, height );

                cb.rectangle( 392, konst - i * height + height, 16, height );
                cb.rectangle( 408, konst - i * height + height, 80, height );
            }
            if ( i % 16 == 5 )
            {
                cb.rectangle( 472, konst - i * height - 3 * height, 16, 7 * height );
                cb.rectangle( 488, konst - i * height, 106, height );
                cb.rectangle( 514, konst - i * height + 2 * height, 80, height );
                cb.rectangle( 498, konst - i * height + 2 * height, 16, height );

                cb.rectangle( 578, konst - i * height + height, 16, height );
                cb.rectangle( 594, konst - i * height + height, 80, height );
            }

            if ( i % 32 == 12 )
            {
                cb.rectangle( 658, konst - i * height - 7 * height, 16, 15 * height );
                cb.rectangle( 674, konst - i * height, 80, height );

                cb.rectangle( 620, konst - 3 * i * height + 5 * height, 16, height );
                cb.rectangle( 620, konst - 3 * i * height + 7 * height, 16, height );
                cb.rectangle( 636, konst - 3 * i * height + 5 * height, 80, height );
                cb.rectangle( 636, konst - 3 * i * height + 7 * height, 80, height );

                cb.rectangle( 700, konst - 3 * i * height + height + 5 * height, 16, height );
                cb.rectangle( 716, konst - 3 * i * height + height + 5 * height, 80, height );
            }
            cb.stroke();
        }
        return konst;
    }

    private void fillMainRoundTable( Map<Integer, NewaFight> fightListMapPool )
        throws Exception
    {
        int height = 12;
        int konst = constMainRound;
        writeTextFirstRound( 101, konst + 2, fightListMapPool.get( _15 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 2 * height + 2, fightListMapPool.get( _15 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 4 * height + 2, fightListMapPool.get( _16 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 6 * height + 2, fightListMapPool.get( _16 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 8 * height + 2, fightListMapPool.get( _17 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 10 * height + 2, fightListMapPool.get( _17 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 12 * height + 2, fightListMapPool.get( _18 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 14 * height + 2, fightListMapPool.get( _18 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 16 * height + 2, fightListMapPool.get( _19 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 18 * height + 2, fightListMapPool.get( _19 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 20 * height + 2, fightListMapPool.get( _20 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 22 * height + 2, fightListMapPool.get( _20 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 24 * height + 2, fightListMapPool.get( _21 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 26 * height + 2, fightListMapPool.get( _21 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 28 * height + 2, fightListMapPool.get( _22 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 30 * height + 2, fightListMapPool.get( _22 ), _8, _0, FIGHTER_BLUE );

        writeTextFirstRound( 101, konst - 32 * height + 2, fightListMapPool.get( _23 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 34 * height + 2, fightListMapPool.get( _23 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 36 * height + 2, fightListMapPool.get( _24 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 38 * height + 2, fightListMapPool.get( _24 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 40 * height + 2, fightListMapPool.get( _25 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 42 * height + 2, fightListMapPool.get( _25 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 44 * height + 2, fightListMapPool.get( _26 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 46 * height + 2, fightListMapPool.get( _26 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 48 * height + 2, fightListMapPool.get( _27 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 50 * height + 2, fightListMapPool.get( _27 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 52 * height + 2, fightListMapPool.get( _28 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 54 * height + 2, fightListMapPool.get( _28 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 56 * height + 2, fightListMapPool.get( _29 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 58 * height + 2, fightListMapPool.get( _29 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 60 * height + 2, fightListMapPool.get( _30 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 62 * height + 2, fightListMapPool.get( _30 ), _8, _0, FIGHTER_BLUE );
        // Round 5
        writeFighterName( 206, konst - height + 2, fightListMapPool.get( _7 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 5 * height + 2, fightListMapPool.get( _7 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 9 * height + 2, fightListMapPool.get( _8 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 13 * height + 2, fightListMapPool.get( _8 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 17 * height + 2, fightListMapPool.get( _9 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 21 * height + 2, fightListMapPool.get( _9 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 25 * height + 2, fightListMapPool.get( _10 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 29 * height + 2, fightListMapPool.get( _10 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 33 * height + 2, fightListMapPool.get( _11 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 37 * height + 2, fightListMapPool.get( _11 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 41 * height + 2, fightListMapPool.get( _12 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 45 * height + 2, fightListMapPool.get( _12 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 49 * height + 2, fightListMapPool.get( _13 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 53 * height + 2, fightListMapPool.get( _13 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 57 * height + 2, fightListMapPool.get( _14 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 61 * height + 2, fightListMapPool.get( _14 ), _8, _0, FIGHTER_BLUE );
        // Round 4
        writeFighterName( 286, konst - 3 * height + 2, fightListMapPool.get( _3 ), _8, _0, FIGHTER_RED );
        writeFighterName( 286, konst - 11 * height + 2, fightListMapPool.get( _3 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 286, konst - 19 * height + 2, fightListMapPool.get( _4 ), _8, _0, FIGHTER_RED );
        writeFighterName( 286, konst - 27 * height + 2, fightListMapPool.get( _4 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 286, konst - 35 * height + 2, fightListMapPool.get( _5 ), _8, _0, FIGHTER_RED );
        writeFighterName( 286, konst - 43 * height + 2, fightListMapPool.get( _5 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 286, konst - 51 * height + 2, fightListMapPool.get( _6 ), _8, _0, FIGHTER_RED );
        writeFighterName( 286, konst - 59 * height + 2, fightListMapPool.get( _6 ), _8, _0, FIGHTER_BLUE );
        // Round3
        writeFighterName( 366, konst - 7 * height + 2, fightListMapPool.get( _1 ), _8, _0, FIGHTER_RED );
        writeFighterName( 366, konst - 23 * height + 2, fightListMapPool.get( _1 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 366, konst - 39 * height + 2, fightListMapPool.get( _2 ), _8, _0, FIGHTER_RED );
        writeFighterName( 366, konst - 55 * height + 2, fightListMapPool.get( _2 ), _8, _0, FIGHTER_BLUE );
        // Round2
        writeFighterName( 446, konst - 15 * height + 2, fightListMapPool.get( _0 ), _8, _0, FIGHTER_RED );
        writeFighterName( 446, konst - 47 * height + 2, fightListMapPool.get( _0 ), _8, _0, FIGHTER_BLUE );

        writeFighterName( 526, konst - 31 * height + 2, fightListMapPool.get( _0 ), _8, _0, FIGHTER_RED );
    }

    private void fillFinal( NewaKoClass fc )
        throws Exception
    {
        int height = 12;
        int konstFinal = 4;

        writeFighterName( 446, konstFinal + 6 * height + 2, fc.getFinalFight(), _8, _0, FIGHTER_RED );
        writeFighterName( 446, konstFinal + 4 * height + 2, fc.getFinalFight(), _8, _0, FIGHTER_BLUE );

        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdRed()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )

            writeText( 526, konstFinal + 5 * height + 2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_TRUE,
                       _10, _0 );

        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )

            writeText( 526, konstFinal + 5 * height + 2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_TRUE,
                       _10, _0 );
        cb.stroke();
    }

    private void fillLoserRoundTable( Map<Integer, NewaFight> fightListMapPool )
        throws Exception
    {
        int height = 12;
        int konst = constLoserRound;

        writeFighterName( 76, konst + 2, fightListMapPool.get( _22 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 2 * height + 2, fightListMapPool.get( _22 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 4 * height + 2, fightListMapPool.get( _23 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 6 * height + 2, fightListMapPool.get( _23 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 8 * height + 2, fightListMapPool.get( _24 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 10 * height + 2, fightListMapPool.get( _24 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 12 * height + 2, fightListMapPool.get( _25 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 14 * height + 2, fightListMapPool.get( _25 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 16 * height + 2, fightListMapPool.get( _26 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 18 * height + 2, fightListMapPool.get( _26 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 20 * height + 2, fightListMapPool.get( _27 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 22 * height + 2, fightListMapPool.get( _27 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 24 * height + 2, fightListMapPool.get( _28 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 26 * height + 2, fightListMapPool.get( _28 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 28 * height + 2, fightListMapPool.get( _29 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 30 * height + 2, fightListMapPool.get( _29 ), _8, _0, FIGHTER_BLUE );

        writeFighterName( 182, konst + 1 * height + 2, fightListMapPool.get( _14 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 1 * height + 2, fightListMapPool.get( _14 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 3 * height + 2, fightListMapPool.get( _15 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 5 * height + 2, fightListMapPool.get( _15 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 7 * height + 2, fightListMapPool.get( _16 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 9 * height + 2, fightListMapPool.get( _16 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 11 * height + 2, fightListMapPool.get( _17 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 13 * height + 2, fightListMapPool.get( _17 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 15 * height + 2, fightListMapPool.get( _18 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 17 * height + 2, fightListMapPool.get( _18 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 19 * height + 2, fightListMapPool.get( _19 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 21 * height + 2, fightListMapPool.get( _19 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 23 * height + 2, fightListMapPool.get( _20 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 25 * height + 2, fightListMapPool.get( _20 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 27 * height + 2, fightListMapPool.get( _21 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 29 * height + 2, fightListMapPool.get( _21 ), _8, _0, FIGHTER_BLUE );

        writeFighterName( 262, konst + 0 * height + 2, fightListMapPool.get( _10 ), _8, _0, FIGHTER_RED );
        writeFighterName( 262, konst - 4 * height + 2, fightListMapPool.get( _10 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 262, konst - 8 * height + 2, fightListMapPool.get( _11 ), _8, _0, FIGHTER_RED );
        writeFighterName( 262, konst - 12 * height + 2, fightListMapPool.get( _11 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 262, konst - 16 * height + 2, fightListMapPool.get( _12 ), _8, _0, FIGHTER_RED );
        writeFighterName( 262, konst - 20 * height + 2, fightListMapPool.get( _12 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 262, konst - 24 * height + 2, fightListMapPool.get( _13 ), _8, _0, FIGHTER_RED );
        writeFighterName( 262, konst - 28 * height + 2, fightListMapPool.get( _13 ), _8, _0, FIGHTER_BLUE );

        //
        writeFighterName( 368, konst + 0 * height + 2, fightListMapPool.get( _6 ), _8, _0, FIGHTER_RED );
        writeFighterName( 368, konst - 2 * height + 2, fightListMapPool.get( _6 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 368, konst - 8 * height + 2, fightListMapPool.get( _7 ), _8, _0, FIGHTER_RED );
        writeFighterName( 368, konst - 10 * height + 2, fightListMapPool.get( _7 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 368, konst - 16 * height + 2, fightListMapPool.get( _8 ), _8, _0, FIGHTER_RED );
        writeFighterName( 368, konst - 18 * height + 2, fightListMapPool.get( _8 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 368, konst - 24 * height + 2, fightListMapPool.get( _9 ), _8, _0, FIGHTER_RED );
        writeFighterName( 368, konst - 26 * height + 2, fightListMapPool.get( _9 ), _8, _0, FIGHTER_BLUE );
        //
        writeFighterName( 448, konst - 1 * height + 2, fightListMapPool.get( _4 ), _8, _0, FIGHTER_RED );
        writeFighterName( 448, konst - 9 * height + 2, fightListMapPool.get( _4 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 448, konst - 17 * height + 2, fightListMapPool.get( _5 ), _8, _0, FIGHTER_RED );
        writeFighterName( 448, konst - 25 * height + 2, fightListMapPool.get( _5 ), _8, _0, FIGHTER_BLUE );
        //
        writeFighterName( 554, konst - 3 * height + 2, fightListMapPool.get( _2 ), _8, _0, FIGHTER_RED );
        writeFighterName( 549, konst - 5 * height + 2, fightListMapPool.get( _2 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 554, konst - 19 * height + 2, fightListMapPool.get( _3 ), _8, _0, FIGHTER_RED );
        writeFighterName( 549, konst - 21 * height + 2, fightListMapPool.get( _3 ), _8, _0, FIGHTER_BLUE );
        //
        writeFighterName( 634, konst - 4 * height + 2, fightListMapPool.get( _1 ), _8, _0, FIGHTER_RED );
        writeFighterName( 634, konst - 20 * height + 2, fightListMapPool.get( _1 ), _8, _0, FIGHTER_BLUE );

        // writeText( 714, konst - 12 * height + 2, Ko.LoserRoundA[1].getNameWhite(), true, 8, 0 );

        writeFighterName( 666, konst - 29 * height + 2, fightListMapPool.get( _0 ), _10, _0, FIGHTER_RED );
        writeFighterName( 666, konst - 31 * height + 2, fightListMapPool.get( _0 ), _10, _0, FIGHTER_BLUE );
        // 3rd

        if ( fightListMapPool.get( _0 ).getWinnerId().longValue() == fightListMapPool.get( _0 ).getFighterIdRed()
            && fightListMapPool.get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 756, konst - 30 * height + 2, fightListMapPool.get( _0 ).getFighterRed().getName(),
                       BOOLEAN_TRUE, _8, _0 );
        }
        if ( fightListMapPool.get( _0 ).getWinnerId().longValue() == fightListMapPool.get( _0 ).getFighterIdBlue()
            && fightListMapPool.get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 756, konst - 30 * height + 2, fightListMapPool.get( _0 ).getFighterBlue().getName(),
                       BOOLEAN_TRUE, _8, _0 );
        }
    }

    private void manageFightListForKo64( NewaKoClass fc, PdfWriter writer )
    {
        try
        {
            int count = _1;

            // 15 to 30
            for ( int i = _15; i <= _30; i++ )
            {
                if ( !( fc.getFightListMapPoolA().get( i ).getFighterIdBlue() == TypeUtil.LONG_MIN ) )
                {
                    createFightList( fc.getFightListMapPoolA().get( i ), count, writer );
                }
                count++;
                if ( !( fc.getFightListMapPoolB().get( i ).getFighterIdBlue() == TypeUtil.LONG_MIN ) )
                {
                    createFightList( fc.getFightListMapPoolB().get( i ), count, writer );
                }
                count++;
            }
            // 7 to 14
            for ( int i = _7; i <= _14; i++ )
            {
                if ( ( fc.getFightListMapPoolA().get( i ).getId() != null) )
                {
                    createFightList( fc.getFightListMapPoolA().get( i ), count, writer );
                }
                count++;
                if ( ( fc.getFightListMapPoolB().get( i ).getId() != null) )
                {
                    createFightList( fc.getFightListMapPoolB().get( i ), count, writer );
                }
                count++;
            }
            // 3 to 6
            for ( int i = _3; i <= _6; i++ )
            {
                if ( ( fc.getFightListMapPoolA().get( i ).getId() != null) )
                {
                    createFightList( fc.getFightListMapPoolA().get( i ), count, writer );
                }
                count++;
                if ( ( fc.getFightListMapPoolB().get( i ).getId() != null) )
                {
                    createFightList( fc.getFightListMapPoolB().get( i ), count, writer );
                }
                count++;
            }
            // 1 to 2
            for ( int i = _1; i <= _2; i++ )
            {
                if ( ( fc.getFightListMapPoolA().get( i ).getId() != null) )
                {
                    createFightList( fc.getFightListMapPoolA().get( i ), count , writer);
                }
                count++;
                if ( ( fc.getFightListMapPoolB().get( i ).getId() != null) )
                {
                    createFightList( fc.getFightListMapPoolB().get( i ), count , writer);
                }
                
                count++;
            }

            count = manageFightList( fc.getFightListMapPoolA(), _0, _0, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListMapPoolB(), _0, _0, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _22, _29, count, BOOLEAN_TRUE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _22, _29, count, BOOLEAN_TRUE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolA(), _14, _21, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _14, _21, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _10, _13, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _10, _13, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolA(), _6, _9, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _6, _9, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _4, _5, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _4, _5, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolA(), _2, _3, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _2, _3, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _1, _1, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _1, _1, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolA(), _0, _0, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _0, _0, count, BOOLEAN_FALSE, writer );

            createFightList( fc.getFinalFight(), count, writer );
        }
        catch ( Exception e )
        {
            log.error( "NewaKo64ComplexListPDF.manageFightListForKo64: Exception: \ncan not create pdf document \n",
                       e );
        }
    }
}