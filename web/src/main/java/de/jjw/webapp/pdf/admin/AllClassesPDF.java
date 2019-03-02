package de.jjw.webapp.pdf.admin;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.admin.Fightsystem;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.Newaclass;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.IValueConstants;
import de.jjw.webapp.pdf.BasePDF;
import de.jjw.webapp.pdf.BasePoolAndKoPDF;
import de.jjw.webapp.util.ConfigMain;

public class AllClassesPDF
    extends BasePoolAndKoPDF
    implements IValueConstants
{

    protected final Logger log = Logger.getRootLogger();

    private Map<Integer, Fightingclass> fc;

    private Map<Integer, Newaclass> nc;

    private Map<Integer, Duoclass> dc;

    public AllClassesPDF( String resourceBundleFighting, String resourceBundleDuo,String resourceBundleNewa, Map<Integer, Fightingclass> fc,
                          Map<Integer, Duoclass> dc, Map<Integer, Newaclass> nc, Locale local )
    {
        this.rbFighting = ResourceBundle.getBundle( resourceBundleFighting, local );
        this.rbDuo = ResourceBundle.getBundle( resourceBundleDuo, local );
        this.rbNewa = ResourceBundle.getBundle( resourceBundleNewa, local );
        this.fc = fc;
        this.dc = dc;
        this.nc = nc;

    }

    public void createAsNewDoc( HttpServletResponse response )
    {
        try
        {
            doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ;
            PdfWriter writer = PdfWriter.getInstance( doc, baos );

            doc.open();
            cb = writer.getDirectContent();

            createAsExistingDoc( null, null, response.getLocale(), cb, writer );

            doc.close();
            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();
        }
        catch ( Exception e )
        {
            log.error( "AllClassesPDF.createAsNewDoc: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
        }
    }

    public ByteArrayOutputStream createAsNewDocAsBAOS( Locale local )
    {
        try
        {

            doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 50000 );

            PdfWriter writer = PdfWriter.getInstance( doc, baos );

            doc.open();
            cb = writer.getDirectContent();

            createAsExistingDoc( null, null, local, cb, writer );

            doc.close();
            return baos;
        }
        catch ( Exception e )
        {
            log.error( "AllClassesPDF.createAsNewDoc: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
            return null;
        }
    }

    public void createAsExistingDoc( Document document, ByteArrayOutputStream byteArrayOutputStream, Locale local,
                                     PdfContentByte cb, PdfWriter writer )
        throws Exception
    {
        int counter = 0;
        try
        {
            if ( document != null )
                doc = document;
            if ( baos != null )
                baos = byteArrayOutputStream;
            if ( cb != null )
                this.cb = cb;
            // doc.newPage();
            // doc.add( new Paragraph( " " ) );

            if ( fc.isEmpty() && dc.isEmpty() && nc.isEmpty() )
            {
                doc.newPage();
                doc.add( new Paragraph( " " ) );
                writeTextCentral( 300, 700, rbFighting.getString( "pdf.noResult" ), BOOLEAN_TRUE, 16 );
                return;
            }

            Fightingclass item = null;
            for ( Integer i : fc.keySet() )
            {
                item = fc.get( i );
                counter++;
               

                if ( item.getFightsystem() == Fightsystem.SIMPLE_POOL )
                {
                    doc.setPageSize( PageSize.A4.rotate() );
                    doc.newPage();
                    // doc.add( new Paragraph( SPACE ) );
                    BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );
                    writeNormalHeadlineAndFooterForFighting( local, item );
                    createResultTable( 150 );
                    FightingSimplePoolClass fspc = (FightingSimplePoolClass) item;

                    fillResultTableFightingPool( 150, fspc.getFighterList() );
                    createFightTable( 280, _0 );
                    fillFightTableFighting( 280, fspc.getFightListMap() );
                }

                if ( item.getFightsystem() == Fightsystem.DOUBLE_POOL )
                {
                    doc.setPageSize( PageSize.A4.rotate() );
                    doc.newPage();
                    // doc.add( new Paragraph( SPACE ) );
                    BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );
                    writeNormalHeadlineAndFooterForFighting( local, item );
                    FightingDoublePoolClass fdpc = (FightingDoublePoolClass) item;
                    createResultTable( _0 );
                    fillResultTableFightingDPool( _0, fdpc.getFighterListPoolA() );
                    createFightTable( 130, _0 );
                    fillFightTableFighting( 130, fdpc.getFightListMapPoolA() );

                    createResultTable( 264 );
                    fillResultTableFightingDPool( 264, fdpc.getFighterListPoolB() );
                    createFightTable( 394, _1 );
                    fillFightTableFighting( 394, fdpc.getFightListMapPoolB() );

                    createFinals( 580, 410 );
                    fillFinalsFighting( 580, 410, fdpc, rbFighting );
                }

                if ( item.getFightsystem() == Fightsystem.DOUBLE_KO )
                {

                    FightingKoClass ko = (FightingKoClass) item;
                    if ( ko.getFighterListPoolA().size() < 9 )
                    {
                        doc.setPageSize( PageSize.A4.rotate() );
                        doc.newPage();
                        // doc.add( new Paragraph( SPACE ) );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );
                        writeNormalHeadlineAndFooterForFighting( local, item );
                        createMainRoundTableKO16( 570 );
                        createMainRoundStandardTextKO16( 570 );
                        createLoserRoundTableKO16( 180 );
                        createLoserRoundStandardTextKO16( 180 );

                        fillMainRoundTableKO16Fighting( 570, ko );

                        fillLoserRoundTableKO16Fighting( 180, ko );
                    }
                    if ( ko.getFighterListPoolA().size() < 17 && ko.getFighterListPoolA().size() > 8 )
                    {
                        doc.setPageSize( PageSize.A4.rotate() );
                        doc.newPage();
                        // doc.add( new Paragraph( SPACE ) );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );
                        writeTextCentral( 420, 540, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                          true, _16 );
                        writeTextCentral(
                                          420,
                                          510,
                                          ko.getAge().getDescription()
                                              + SPACE
                                              + CodestableMain.getInstance().getCodestableCodeValueByType(
                                                                                                           ICodestableTypeConstants.TYPE_SEX,
                                                                                                           ko.getSex(),
                                                                                                           local )
                                              + SPACE + ko.getWeightclass() + rbFighting.getString( "pdf.KG" ), true,
                                          14 );
                        writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
                        createMainRoundTableKO32( 570 );
                        createMainRoundStandardTextKO32( 570 );
                        fillMainRoundTableKO32Fighting( 570, ko );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );

                        cb.stroke();
                        doc.newPage();
                        doc.add( new Paragraph( " " ) );
                        createLoserRoundTableKO32( 500 );
                        writeNormalHeadlineAndFooterForFighting( local, item );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );
                        createLoserRoundStandardTextKO32( 500 );
                        fillLoserRoundTableKO32Fighting( 500, ko );
                    }
                    
                    //ko 64
                    if ( ko.getFighterListPoolA().size() < 33 && ko.getFighterListPoolA().size() > 16 )
                    {
                        int constMainRound = 800;
                        int constLoserRound = 450;
                        doc.setPageSize( PageSize.A4);
                        doc.newPage();
                        // doc.add( new Paragraph( SPACE ) );
                        createMainRoundTableKO64( constMainRound );
                        createMainRoundAStandardTextKO64( constMainRound);
                        createFinalKO64( 0 );
                        if ( ko != null )
                        {
                            BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, false );
                            writeText( 340, 790, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),true, _16 );
                            writeText(340, 760,
                                ko.getAge().getDescription()
                                    + SPACE
                                    + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                                 ko.getSex(),
                                                                                                 local ) + SPACE
                                    + ko.getWeightclass() + rbFighting.getString( "pdf.KG" ),true, 14);
                            writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
                            fillMainRoundTableKO64( ko.getFightListMapPoolA(),constMainRound );
                            fillFinalKO64( ko );
                            writeText( 406, 4 + 8 * 12 + 2, rbFighting.getString( "pdf.Finale" ), true, 18, 1 );
                        }
                        cb.stroke();
                        // Pool B                        
                        doc.setPageSize(PageSize.A4 );
                        doc.newPage();
                        // doc.add( new Paragraph( SPACE ) );
                        createMainRoundTableKO64(constMainRound );
                        createMainRoundAStandardTextKO64( constMainRound );
                        createFinalKO64( 0 );
                        if ( ko != null )
                        {
                            BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, false );
                            writeText( 340, 790, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),true, _16 );
                            writeText(340, 760,
                                ko.getAge().getDescription()
                                    + SPACE
                                    + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                                 ko.getSex(),
                                                                                                 local ) + SPACE
                                    + ko.getWeightclass() + rbFighting.getString( "pdf.KG" ),true, 14);
                            writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
                            fillMainRoundTableKO64( ko.getFightListMapPoolB(),constMainRound );
                            fillFinalKO64( ko );
                            writeText( 406, 4 + 8 * 12 + 2, rbFighting.getString( "pdf.Finale" ), true, 18, 1 );
                        }
                        cb.stroke();
                        // LoserRound A
                        doc.setPageSize( PageSize.A4.rotate() );
                        doc.newPage();
                        createLoserRoundTableKO64( constLoserRound, loserRoundAArrayKO64 );
                        createLoserRoundAStandardTextKO64( constLoserRound );
                        writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );
                        if ( ko != null )
                        {
                            writeTextCentral( 600, 540, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),true, _16 );
                            writeTextCentral(600, 510,
                                ko.getAge().getDescription()
                                    + SPACE
                                    + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                                 ko.getSex(),
                                                                                                 local ) + SPACE
                                    + ko.getWeightclass() + rbFighting.getString( "pdf.KG" ),true, 14);                           
                            fillLoserRoundTableKO64( ko.getFightListLooserMapPoolA(),constLoserRound );
                        }
                        cb.stroke();

                        // LoserRound B
                        doc.setPageSize( PageSize.A4.rotate() );
                        doc.newPage();
                        createLoserRoundTableKO64( constLoserRound, loserRoundBArrayKO64 );
                        createLoserRoundBStandardTextKO64( constLoserRound );
                        writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );
                        if ( ko != null )
                        {
                            writeTextCentral( 600, 540, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),true, _16 );
                            writeTextCentral(600, 510,
                                ko.getAge().getDescription()
                                    + SPACE
                                    + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                                 ko.getSex(),
                                                                                                 local ) + SPACE
                                    + ko.getWeightclass() + rbFighting.getString( "pdf.KG" ),true, 14);                           
                            fillLoserRoundTableKO64( ko.getFightListLooserMapPoolB(),constLoserRound );
                        }
                        cb.stroke();
                        
                    }
                }
            }

            Duoclass item2;
            for ( Integer i : dc.keySet() )
            {
                item2 = dc.get( i );
                counter++;
                doc.setPageSize( PageSize.A4.rotate() );
                doc.newPage();
                doc.add( new Paragraph( SPACE ) );
                BasePDF.addWatermark( writer.getDirectContentUnder(), "Duo", 140, true );

                if ( item2.getFightsystem() == Fightsystem.SIMPLE_POOL )
                {
                    writeNormalHeadlineAndFooterForDuo( item2 );
                    createResultTable( 150 );
                    DuoSimplePoolClass dspc = (DuoSimplePoolClass) item2;

                    fillResultTableDuoPool( 150, dspc.getDuoTeamList() );
                    createFightTable( 280, _0 );
                    fillFightTableDuo( 280, dspc.getDuoFightListMap() );
                }

                if ( item2.getFightsystem() == Fightsystem.DOUBLE_POOL )
                {
                    writeNormalHeadlineAndFooterForDuo( item2 );
                    DuoDoublePoolClass ddpc = (DuoDoublePoolClass) item2;
                    createResultTable( _0 );
                    fillResultTableDuoDPool( _0, ddpc.getDuoListPoolA() );
                    createFightTable( 130, _0 );
                    fillFightTableDuo( 130, ddpc.getFightListMapPoolA() );

                    createResultTable( 264 );
                    fillResultTableDuoDPool( 264, ddpc.getDuoListPoolB() );
                    createFightTable( 394, _1 );
                    fillFightTableDuo( 394, ddpc.getFightListMapPoolB() );

                    createFinals( 580, 410 );
                    fillFinalsDuo( 580, 410, ddpc, rbDuo );
                }

                if ( item2.getFightsystem() == Fightsystem.DOUBLE_KO )
                {

                    DuoKoClass ko = (DuoKoClass) item2;
                    if ( ko.getTeamListPoolA().size() < 9 )
                    {
                        writeNormalHeadlineAndFooterForDuo( item2 );
                        createMainRoundTableKO16( 570 );

                        createMainRoundStandardTextKO16( 570 );
                        createLoserRoundTableKO16( 180 );
                        createLoserRoundStandardTextKO16( 180 );

                        fillMainRoundTableKO16Duo( 570, ko );

                        fillLoserRoundTableKO16Duo( 180, ko );
                    }
                    if ( ko.getTeamListPoolA().size() < 17 && ko.getTeamListPoolA().size() > 8 )
                    {
                        writeTextCentral( 420, 540, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                          true, _16 );

                        writeText( 420, 510, item2.getDuoclassName(), BOOLEAN_TRUE, 14 );

                        writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
                        createMainRoundTableKO32( 570 );
                        createMainRoundStandardTextKO32( 570 );
                        fillMainRoundTableKO32Duo( 570, ko );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Duo", 140, true );

                        cb.stroke();
                        doc.newPage();
                        doc.add( new Paragraph( " " ) );
                        createLoserRoundTableKO32( 500 );
                        writeNormalHeadlineAndFooterForDuo( item2 );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Duo", 140, true );
                        createLoserRoundStandardTextKO32( 500 );
                        fillLoserRoundTableKO32Duo( 500, ko );
                    }
                }
            }

            Newaclass item3 = null;
            for ( Integer i : nc.keySet() )
            {
                item3 = nc.get( i );
                counter++;
                doc.setPageSize( PageSize.A4.rotate() );
                doc.newPage();
                // doc.add( new Paragraph( SPACE ) );
                BasePDF.addWatermark( writer.getDirectContentUnder(), "Newaza", 140, true );

                if ( item3.getFightsystem() == Fightsystem.SIMPLE_POOL )
                {
                    writeNormalHeadlineAndFooterForNewa( local, item3 );
                    createResultTableNewa( 150 );
                    NewaSimplePoolClass fspc = (NewaSimplePoolClass) item3;

                    fillResultTableNewaPool( 150, fspc.getFighterList() );
                    createFightTable( 280, _0 );
                    fillFightTableNewa( 280, fspc.getFightListMap() );
                }

                if ( item3.getFightsystem() == Fightsystem.DOUBLE_POOL )
                {
                    writeNormalHeadlineAndFooterForNewa( local, item3 );
                    NewaDoublePoolClass fdpc = (NewaDoublePoolClass) item3;
                    createResultTableNewa( _0 );
                    fillResultTableNewaDPool( _0, fdpc.getFighterListPoolA() );
                    createFightTable( 130, _0 );
                    fillFightTableNewa( 130, fdpc.getFightListMapPoolA() );

                    createResultTableNewa( 264 );
                    fillResultTableNewaDPool( 264, fdpc.getFighterListPoolB() );
                    createFightTable( 394, _1 );
                    fillFightTableNewa( 394, fdpc.getFightListMapPoolB() );

                    createFinals( 580, 410 );
                    fillFinalsNewa( 580, 410, fdpc, rbFighting );
                }

                if ( item3.getFightsystem() == Fightsystem.DOUBLE_KO )
                {

                    NewaKoClass ko = (NewaKoClass) item3;
                    if ( ko.getFighterListPoolA().size() < 9 )
                    {
                        writeNormalHeadlineAndFooterForNewa( local, item3 );
                        createMainRoundTableKO16( 570 );
                        createMainRoundStandardTextKO16( 570 );
                        createLoserRoundTableKO16( 180 );
                        createLoserRoundStandardTextKO16( 180 );

                        fillMainRoundTableKO16Newa( 570, ko );

                        fillLoserRoundTableKO16Newa( 180, ko );
                    }
                    if ( ko.getFighterListPoolA().size() < 17 && ko.getFighterListPoolA().size() > 8 )
                    {
                        writeTextCentral( 420, 540, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                          true, _16 );
                        writeTextCentral( 420,
                                          510,
                                          ko.getAge().getDescription()
                                              + SPACE
                                              + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                                           ko.getSex(),
                                                                                                           local )
                                              + SPACE + ko.getWeightclass() + rbFighting.getString( "pdf.KG" ), true,
                                          14 );
                        writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
                        createMainRoundTableKO32( 570 );
                        createMainRoundStandardTextKO32( 570 );
                        fillMainRoundTableKO32Newa( 570, ko );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Newaza", 140, true );

                        cb.stroke();
                        doc.newPage();
                        doc.add( new Paragraph( " " ) );
                        createLoserRoundTableKO32( 500 );
                        writeNormalHeadlineAndFooterForNewa( local, item3 );
                        BasePDF.addWatermark( writer.getDirectContentUnder(), "Newaza", 140, true );
                        createLoserRoundStandardTextKO32( 500 );
                        fillLoserRoundTableKO32Newa( 500, ko );
                    }
                }
            }

        }
        catch ( Exception e )
        {
            log.error( "AllClassesPDF.createAsExistingDoc: Exception: \ncan not create pdf document \n Counter: "
                + counter + "\n" + e.getMessage(), e );
            throw e;
        }

    }

    /**
     * @param response
     * @param item
     * @throws Exception
     */
    private void writeNormalHeadlineAndFooterForFighting( Locale local, Fightingclass item )
        throws Exception
    {
        writeText( 600, _540, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), BOOLEAN_TRUE, 16 );
        writeText( 600, 510, item.getAge().getDescription()
            + SPACE
            + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                    item.getSex(), local ) + SPACE
            + item.getWeightclass() + rbFighting.getString( "pdf.KG" ), BOOLEAN_TRUE, 14 );
        writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
    }

    /**
     * @param response
     * @param item
     * @throws Exception
     */
    private void writeNormalHeadlineAndFooterForNewa( Locale local, Newaclass item )
        throws Exception
    {
        writeText( 600, _540, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), BOOLEAN_TRUE, 16 );
        writeText( 600,
                   510,
                   item.getAge().getDescription()
                       + SPACE
                       + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                    item.getSex(), local ) + SPACE
                       + item.getWeightclass() + rbFighting.getString( "pdf.KG" ), BOOLEAN_TRUE, 14 );
        writeText( _24, _24, rbFighting.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
    }

    /**
     * @param response
     * @param item
     * @throws Exception
     */
    private void writeNormalHeadlineAndFooterForDuo( Duoclass item )
        throws Exception
    {
        writeText( 600, _540, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), BOOLEAN_TRUE, 16 );
        writeText( 600, 510, item.getDuoclassName(), BOOLEAN_TRUE, 14 );
        writeText( _24, _24, rbDuo.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
    }

}
