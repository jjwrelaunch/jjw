 package de.jjw.webapp.pdf.fighting;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.service.modelWeb.FightWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.webapp.util.ConfigMain;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FightingVisualizelProtokollPDF
{
private static final String S = "S";
private static final String C = "C";
private static final String _3 = "3";
private static final String _2 = "2";
private static final String _1 = "1";
private static final String W = "W";
private static final String CSHR = "CSHR";
private static final String CSHL = "CSHL";
private static final String PCDR = "PCdR";
private static final String PCDL = "PCdL";
private static final String PCIR = "PCiR";
private static final String PCIL = "PCiL";
private static final String PSDR = "PSdR";
private static final String PSDL = "PSdL";
private static final String PSIR = "PSiR";
private static final String PSIL = "PSiL";
private static final String PD1R = "Pd1R";
private static final String PD1L = "Pd1L";
private static final String PI1R = "Pi1R";
private static final String PI1L = "Pi1L";
private static final String ID3R = "Id3R";
private static final String ID2R = "Id2R";
private static final String ID1R = "Id1R";
private static final String ID3L = "Id3L";
private static final String ID2L = "Id2L";
private static final String ID1L = "Id1L";
private static final String II3R = "Ii3R";
private static final String II2R = "Ii2R";
private static final String II1R = "Ii1R";
private static final String II3L = "Ii3L";
private static final String II2L = "Ii2L";
private static final String II1L = "Ii1L";
private static final String CSM_ = "CSM_";
private static final String SPLIT = ":";
private static final String ND2_ = "ND2_";
private static final String DP2_ = "DP2_";
private static final String DP0_ = "DP0_";
private static final String ND1_ = "ND1_";
private static final String DP1_ = "DP1_";
private static final String GO__ = "GO__";
private ResourceBundle rb;
private Document doc;
PdfContentByte cb;
ByteArrayOutputStream baos;
private List<Ippon> ipponRed;
private List<Ippon> ipponBlue;
private List<Integer> wazaariRedList;
private List<Integer> wazaariBlueList;
private List<Penalty> penaltyRedList;
private List<Penalty> penaltyBlueList;
List<HoldingTime> _holdingTimeRed = new ArrayList();
List<HoldingTime> _holdingTimeBlue = new ArrayList();
private static int FIGHT_START = 36;
private int height = 600;
private int endTime;

private HttpServletResponse response;

private Locale locale;
    
public FightingVisualizelProtokollPDF(String ressourceBundle, HttpServletResponse response,  Locale locale)
{
  this.rb = ResourceBundle.getBundle( ressourceBundle, locale );
  this.response = response;
  this.locale = locale;                    
}

private static final SimpleDateFormat FORMAT_Date = new SimpleDateFormat("HH:mm  dd.MM.yyyy");
   
public ByteArrayOutputStream visualizeAllProtokoll(Map<FighterWeb, List<FightWeb>> protokollMap)
{
try
{
    
    doc = new Document( PageSize.A4, 36, 36, 36, 36 );
    ByteArrayOutputStream baos = new ByteArrayOutputStream( 8192 );
    PdfWriter writer = PdfWriter.getInstance( doc, baos );
    doc.open();
    cb = writer.getDirectContent();
     
    String headLine1=ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1();
    String headLine2=ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine2();

     
    List<FightWeb> protokoll = null;
    
    for ( Fighter fighter: protokollMap.keySet())
    {
        doc.newPage();        
 
        protokoll = protokollMap.get(fighter);
        int cursorY = 800;
        this.height = 600;
 
        writeText(300, cursorY, headLine1, true, 16, 0);
        cursorY -= 30;
        writeText(300, cursorY, headLine2, true, 16, 0);
        cursorY -= 50;
        //      writeText(300, cursorY, rb.getString("pdf.visualizeFight.headline"), true, 14, 0);
        cursorY -= 50;

        writeText(300, cursorY, fighter.getFirstname() + " " + fighter.getName(), true, 14, 0);
        cb.setColorStroke(new BaseColor(0, 0, 0));
        cb.setLineWidth(1.0F);
        for (int i = 0; i < protokoll.size(); i++)
        {
            analyzeProtokoll(((Fight)protokoll.get(i)).getProtokoll());
 
            writeText(FIGHT_START + 10, this.height + 40, ((Fight)protokoll.get(i)).getFighterRed().getName(), ((Fight)protokoll.get(i)).getWinnerId().equals( ((Fight)protokoll.get(i)).getFighterIdRed() ) , 10, 1);
            writeText(FIGHT_START - 10 + 360, this.height + 40, ((Fight)protokoll.get(i)).getFighterBlue().getName(), !((Fight)protokoll.get(i)).getWinnerId().equals( ((Fight)protokoll.get(i)).getFighterIdRed()) , 10, 2);
            writeText(FIGHT_START + 10 + 360, this.height + 40, "Kampfzeit: " + FORMAT_Date.format(new Date(((Fight)protokoll.get(i)).getSaveTime().getTime())), false, 10, 1);
            writeText(FIGHT_START + 10 + 360, this.height + 20, "Kampfdauer: " + ((Fight)protokoll.get(i)).getFightTime() + "s", false, 10, 1);
            writeText(FIGHT_START + 10 + 360, this.height, "Endstand: " + ((Fight)protokoll.get(i)).getPointsRed() + ":" + ((Fight)protokoll.get(i)).getPointsBlue(), false, 10, 1);
  
            cb.rectangle(FIGHT_START, this.height, 360.0F, -20.0F);
            cb.stroke();
            if (this.endTime > 0)
            {
                if (this.endTime > 180) {
                    this.endTime = 180;
                }
                Rectangle re = new Rectangle(FIGHT_START, this.height, FIGHT_START + this.endTime * 2, this.height - 20);
                re.setBackgroundColor(new BaseColor(0, 255, 0));
                cb.rectangle(re);
                cb.stroke();
            }
            processLists();
           
            cb.moveTo(FIGHT_START + 120, this.height);
            cb.lineTo(FIGHT_START + 120, this.height - 20);
 
            cb.moveTo(FIGHT_START + 240, this.height);
            cb.lineTo(FIGHT_START + 240, this.height - 20);
 
            cb.stroke();
            height -= 120;
        }
        writeText(24, 24, rb.getString("pdf.copyright") + "", false, 8, 1);
    }
    doc.close();
    
    response.setContentLength( baos.size() );

    ServletOutputStream out = response.getOutputStream();
    baos.writeTo( out );
    baos.flush();
    return baos;
    }
    catch (Exception e)
    {
        System.err.println("FightingVisualizelProtokollPDF : Exception: \ncan not create pdf document \n" + e.getMessage() );
        e.printStackTrace();
        return null;
    }
    }
    
/* 177:    */   private void processLists()
/* 178:    */     throws Exception
/* 179:    */   {
/* 180:237 */     writeText(FIGHT_START - 1, this.height + 2, "W", false, 5, 2);
/* 181:238 */     writeText(FIGHT_START - 1, this.height - 27, "W", false, 5, 2);
/* 182:    */     
/* 183:240 */     writeText(FIGHT_START - 1, this.height + 7, "1", false, 5, 2);
/* 184:241 */     writeText(FIGHT_START - 1, this.height - 32, "1", false, 5, 2);
/* 185:242 */     writeText(FIGHT_START - 1, this.height + 12, "2", false, 5, 2);
/* 186:243 */     writeText(FIGHT_START - 1, this.height - 37, "2", false, 5, 2);
/* 187:244 */     writeText(FIGHT_START - 1, this.height + 17, "3", false, 5, 2);
/* 188:245 */     writeText(FIGHT_START - 1, this.height - 42, "3", false, 5, 2);
/* 189:    */     
/* 190:247 */     writeText(FIGHT_START - 1, this.height + 22, "S", false, 5, 2);
/* 191:248 */     writeText(FIGHT_START - 1, this.height - 47, "S", false, 5, 2);
/* 192:249 */     writeText(FIGHT_START - 1, this.height + 27, "C", false, 5, 2);
/* 193:250 */     writeText(FIGHT_START - 1, this.height - 52, "C", false, 5, 2);
/* 194:252 */     for (int i = 0; i < this.wazaariRedList.size(); i++)
/* 195:    */     {
/* 196:254 */       Rectangle re = new Rectangle(((Integer)this.wazaariRedList.get(i)).intValue() * 2 + FIGHT_START, this.height + 6, ((Integer)this.wazaariRedList.get(i)).intValue() * 2 + 1 + FIGHT_START, this.height + 1);
/* 197:    */       
/* 198:    */ 
/* 199:257 */       re.setBackgroundColor(new BaseColor(0, 0, 0));
/* 200:258 */       this.cb.rectangle(re);
/* 201:    */     }
/* 202:261 */     for (int i = 0; i < this.wazaariBlueList.size(); i++)
/* 203:    */     {
/* 204:263 */       Rectangle re = new Rectangle(((Integer)this.wazaariBlueList.get(i)).intValue() * 2 + FIGHT_START, this.height - 27, ((Integer)this.wazaariBlueList.get(i)).intValue() * 2 + 1 + FIGHT_START, this.height - 22);
/* 205:    */       
/* 206:    */ 
/* 207:266 */       re.setBackgroundColor(new BaseColor(0, 0, 0));
/* 208:267 */       this.cb.rectangle(re);
/* 209:    */     }
/* 210:271 */     for (int i = 0; i < this.ipponRed.size(); i++)
/* 211:    */     {
/* 212:273 */       Ippon ipp = (Ippon)this.ipponRed.get(i);
/* 213:274 */       if ("1".equals(ipp.part))
/* 214:    */       {
/* 215:276 */         Rectangle re = new Rectangle(ipp.time * 2 + FIGHT_START, this.height + 7, ipp.time * 2 + 1 + FIGHT_START, this.height + 12);
/* 216:    */         
/* 217:278 */         re.setBackgroundColor(new BaseColor(255, 0, 0));
/* 218:279 */         this.cb.rectangle(re);
/* 219:    */       }
/* 220:281 */       if ("2".equals(ipp.part))
/* 221:    */       {
/* 222:283 */         Rectangle re = new Rectangle(ipp.time * 2 + FIGHT_START, this.height + 12, ipp.time * 2 + 1 + FIGHT_START, this.height + 17);
/* 223:    */         
/* 224:285 */         re.setBackgroundColor(new BaseColor(0, 255, 0));
/* 225:286 */         this.cb.rectangle(re);
/* 226:    */       }
/* 227:288 */       if ("3".equals(ipp.part))
/* 228:    */       {
/* 229:290 */         Rectangle re = new Rectangle(ipp.time * 2 + FIGHT_START, this.height + 17, ipp.time * 2 + 1 + FIGHT_START, this.height + 22);
/* 230:    */         
/* 231:292 */         re.setBackgroundColor(new BaseColor(0, 0, 255));
/* 232:293 */         this.cb.rectangle(re);
/* 233:    */       }
/* 234:    */     }
/* 235:297 */     for (int i = 0; i < this.ipponBlue.size(); i++)
/* 236:    */     {
/* 237:299 */       Ippon ipp = (Ippon)this.ipponBlue.get(i);
/* 238:300 */       if ("1".equals(ipp.part))
/* 239:    */       {
/* 240:302 */         Rectangle re = new Rectangle(ipp.time * 2 + FIGHT_START, this.height - 27, ipp.time * 2 + 1 + FIGHT_START, this.height - 32);
/* 241:    */         
/* 242:304 */         re.setBackgroundColor(new BaseColor(255, 0, 0));
/* 243:305 */         this.cb.rectangle(re);
/* 244:    */       }
/* 245:307 */       if ("2".equals(ipp.part))
/* 246:    */       {
/* 247:309 */         Rectangle re = new Rectangle(ipp.time * 2 + FIGHT_START, this.height - 32, ipp.time * 2 + 1 + FIGHT_START, this.height - 37);
/* 248:    */         
/* 249:311 */         re.setBackgroundColor(new BaseColor(0, 255, 0));
/* 250:312 */         this.cb.rectangle(re);
/* 251:    */       }
/* 252:314 */       if ("3".equals(ipp.part))
/* 253:    */       {
/* 254:316 */         Rectangle re = new Rectangle(ipp.time * 2 + FIGHT_START, this.height - 37, ipp.time * 2 + 1 + FIGHT_START, this.height - 42);
/* 255:    */         
/* 256:318 */         re.setBackgroundColor(new BaseColor(0, 0, 255));
/* 257:319 */         this.cb.rectangle(re);
/* 258:    */       }
/* 259:    */     }
/* 260:325 */     for (int i = 0; i < this.penaltyRedList.size(); i++)
/* 261:    */     {
/* 262:327 */       Penalty pen = (Penalty)this.penaltyRedList.get(i);
/* 263:328 */       if ("S".equals(pen.penalty))
/* 264:    */       {
/* 265:330 */         Rectangle re = new Rectangle(pen.time * 2 + FIGHT_START, this.height + 22, pen.time * 2 + 1 + FIGHT_START, this.height + 27);
/* 266:    */         
/* 267:332 */         re.setBackgroundColor(new BaseColor(0, 0, 0));
/* 268:333 */         this.cb.rectangle(re);
/* 269:    */       }
/* 270:335 */       if ("C".equals(pen.penalty))
/* 271:    */       {
/* 272:337 */         Rectangle re = new Rectangle(pen.time * 2 + FIGHT_START, this.height + 27, pen.time * 2 + 1 + FIGHT_START, this.height + 32);
/* 273:    */         
/* 274:339 */         re.setBackgroundColor(new BaseColor(255, 0, 0));
/* 275:340 */         this.cb.rectangle(re);
/* 276:    */       }
/* 277:    */     }
/* 278:345 */     for (int i = 0; i < this.penaltyBlueList.size(); i++)
/* 279:    */     {
/* 280:347 */       Penalty pen = (Penalty)this.penaltyBlueList.get(i);
/* 281:348 */       if ("S".equals(pen.penalty))
/* 282:    */       {
/* 283:350 */         Rectangle re = new Rectangle(pen.time * 2 + FIGHT_START, this.height - 42, pen.time * 2 + 1 + FIGHT_START, this.height - 47);
/* 284:    */         
/* 285:352 */         re.setBackgroundColor(new BaseColor(0, 0, 0));
/* 286:353 */         this.cb.rectangle(re);
/* 287:    */       }
/* 288:355 */       if ("C".equals(pen.penalty))
/* 289:    */       {
/* 290:357 */         Rectangle re = new Rectangle(pen.time * 2 + FIGHT_START, this.height - 47, pen.time * 2 + 1 + FIGHT_START, this.height - 52);
/* 291:    */         
/* 292:359 */         re.setBackgroundColor(new BaseColor(255, 0, 0));
/* 293:360 */         this.cb.rectangle(re);
/* 294:    */       }
/* 295:    */     }
/* 296:366 */     for (int i = 0; i < this._holdingTimeRed.size(); i++)
/* 297:    */     {
/* 298:368 */       Rectangle re = new Rectangle(((HoldingTime)this._holdingTimeRed.get(i)).startTime * 2 + FIGHT_START, this.height, ((HoldingTime)this._holdingTimeRed.get(i)).endTime * 2 + FIGHT_START, this.height - 5);
/* 299:    */       
/* 300:    */ 
/* 301:371 */       re.setBackgroundColor(new BaseColor(0, 0, 255));
/* 302:372 */       this.cb.rectangle(re);
/* 303:    */     }
/* 304:374 */     for (int i = 0; i < this._holdingTimeBlue.size(); i++)
/* 305:    */     {
/* 306:376 */       Rectangle re = new Rectangle(((HoldingTime)this._holdingTimeBlue.get(i)).startTime * 2 + FIGHT_START, this.height - 15, ((HoldingTime)this._holdingTimeBlue.get(i)).endTime * 2 + FIGHT_START, this.height - 20);
/* 307:    */       
/* 308:    */ 
/* 309:379 */       re.setBackgroundColor(new BaseColor(0, 0, 255));
/* 310:380 */       this.cb.rectangle(re);
/* 311:    */     }
/* 312:382 */     this.cb.stroke();
/* 313:    */   }
/* 314:    */   
/* 315:    */   private void writeText(int x, int y, String text, boolean bold, int size, int loc)
/* 316:    */     throws Exception
/* 317:    */   {
/* 318:    */     try
/* 319:    */     {
/* 320:    */       BaseFont bf;
/* 322:402 */       if (bold) {
/* 323:403 */         bf = BaseFont.createFont("Helvetica-Bold", "Cp1252", false);
/* 324:    */       } else {
/* 325:405 */         bf = BaseFont.createFont("Helvetica", "Cp1252", false);
/* 326:    */       }
/* 327:406 */       if ((text.equalsIgnoreCase("&nbsp;")) || (text.equalsIgnoreCase("-1"))) {
/* 328:407 */         text = "";
/* 329:    */       }
/* 330:408 */       this.cb.stroke();
/* 331:409 */       this.cb.beginText();
/* 332:410 */       this.cb.setFontAndSize(bf, size);
/* 333:411 */       if (loc == 0) {
/* 334:412 */         this.cb.showTextAligned(1, text, x, y, 0.0F);
/* 335:    */       }
/* 336:413 */       if (loc == 1) {
/* 337:414 */         this.cb.showTextAligned(0, text, x, y, 0.0F);
/* 338:    */       }
/* 339:415 */       if (loc == 2) {
/* 340:416 */         this.cb.showTextAligned(2, text, x, y, 0.0F);
/* 341:    */       }
/* 342:417 */       this.cb.endText();
/* 343:    */     }
/* 344:    */     catch (Exception e)
/* 345:    */     {
/* 346:421 */       System.err.println("VisualizeProtokollPDF.writeText: Exception: \n can not create pdf document \n" + e.getMessage());
/* 347:    */     }
/* 348:    */   }
/* 349:    */   
/* 350:    */   private void analyzeProtokoll(String protokoll)
/* 351:    */   {
/* 352:429 */     this.endTime = 0;
/* 353:430 */     String[] protokollArray = protokoll.split(";");
/* 354:431 */     Stack<Integer> ipponRedI = new Stack<Integer>();
/* 355:432 */     Stack<Integer> ipponRedII = new Stack<Integer>();
/* 356:433 */     Stack<Integer> ipponRedIII = new Stack<Integer>();
/* 357:    */     
/* 358:435 */     Stack<Integer> ipponBlueI = new Stack<Integer>();
/* 359:436 */     Stack<Integer> ipponBlueII = new Stack<Integer>();
/* 360:437 */     Stack<Integer> ipponBlueIII = new Stack<Integer>();
/* 361:    */     
/* 362:439 */     Stack<Integer> shidoRed = new Stack<Integer>();
/* 363:440 */     Stack<Integer> shidoBlue = new Stack<Integer>();
/* 364:    */     
/* 365:442 */     Stack<Integer> wazaariRed = new Stack<Integer>();
/* 366:443 */     Stack<Integer> wazaariBlue = new Stack<Integer>();
/* 367:    */     
/* 368:445 */     Stack<Integer> chuiRed = new Stack<Integer>();
/* 369:446 */     Stack<Integer> chuiBlue = new Stack<Integer>();
/* 370:    */     
/* 371:448 */     List<HoldingTime> holdingTimeRed = new ArrayList<HoldingTime>();
/* 372:449 */     List<HoldingTime> holdingTimeBlue = new ArrayList<HoldingTime>();
/* 373:451 */     for (int i = 0; i < protokollArray.length; i++) {
/* 374:453 */       if ((!protokollArray[i].startsWith("GO__")) && (!protokollArray[i].startsWith("DP0_")) && (!protokollArray[i].startsWith("DP1_")) && (!protokollArray[i].startsWith("DP2_")) && (!protokollArray[i].startsWith("ND1_")) && (!protokollArray[i].startsWith("ND2_")))
/* 375:    */       {
/* 376:459 */         if ("CSM_".equals(protokollArray[i].split(":")[0])) {
/* 377:460 */           this.endTime = Integer.valueOf(protokollArray[i].split(":")[1]).intValue();
/* 378:    */         }
/* 379:462 */         if ("Ii1R".equals(protokollArray[i].split(":")[0])) {
/* 380:463 */           ipponRedI.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 381:    */         }
/* 382:464 */         if ("Ii2R".equals(protokollArray[i].split(":")[0])) {
/* 383:465 */           ipponRedII.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 384:    */         }
/* 385:466 */         if ("Ii3R".equals(protokollArray[i].split(":")[0])) {
/* 386:467 */           ipponRedIII.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 387:    */         }
/* 388:469 */         if ("Ii1L".equals(protokollArray[i].split(":")[0])) {
/* 389:470 */           ipponBlueI.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 390:    */         }
/* 391:471 */         if ("Ii2L".equals(protokollArray[i].split(":")[0])) {
/* 392:472 */           ipponBlueII.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 393:    */         }
/* 394:473 */         if ("Ii3L".equals(protokollArray[i].split(":")[0])) {
/* 395:474 */           ipponBlueIII.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 396:    */         }
/* 397:476 */         if ("Id1R".equals(protokollArray[i].split(":")[0])) {
/* 398:477 */           ipponRedI.pop();
/* 399:    */         }
/* 400:478 */         if ("Id2R".equals(protokollArray[i].split(":")[0])) {
/* 401:479 */           ipponRedII.pop();
/* 402:    */         }
/* 403:480 */         if ("Id3R".equals(protokollArray[i].split(":")[0])) {
/* 404:481 */           ipponRedIII.pop();
/* 405:    */         }
/* 406:483 */         if ("Id1L".equals(protokollArray[i].split(":")[0])) {
/* 407:484 */           ipponBlueI.pop();
/* 408:    */         }
/* 409:485 */         if ("Id2L".equals(protokollArray[i].split(":")[0])) {
/* 410:486 */           ipponBlueII.pop();
/* 411:    */         }
/* 412:487 */         if ("Id3L".equals(protokollArray[i].split(":")[0])) {
/* 413:488 */           ipponBlueIII.pop();
/* 414:    */         }
/* 415:491 */         if ("Pi1R".equals(protokollArray[i].split(":")[0])) {
/* 416:492 */           wazaariRed.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 417:    */         }
/* 418:493 */         if ("Pi1L".equals(protokollArray[i].split(":")[0])) {
/* 419:494 */           wazaariBlue.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 420:    */         }
/* 421:495 */         if ("Pd1R".equals(protokollArray[i].split(":")[0])) {
/* 422:496 */           wazaariRed.pop();
/* 423:    */         }
/* 424:497 */         if ("Pd1L".equals(protokollArray[i].split(":")[0])) {
/* 425:498 */           wazaariBlue.pop();
/* 426:    */         }
/* 427:501 */         if ("PSiR".equals(protokollArray[i].split(":")[0])) {
/* 428:502 */           shidoRed.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 429:    */         }
/* 430:503 */         if ("PSiL".equals(protokollArray[i].split(":")[0])) {
/* 431:504 */           shidoBlue.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 432:    */         }
/* 433:505 */         if ("PSdR".equals(protokollArray[i].split(":")[0])) {
/* 434:506 */           shidoRed.pop();
/* 435:    */         }
/* 436:507 */         if ("PSdL".equals(protokollArray[i].split(":")[0])) {
/* 437:508 */           shidoBlue.pop();
/* 438:    */         }
/* 439:510 */         if ("PCiR".equals(protokollArray[i].split(":")[0])) {
/* 440:511 */           chuiRed.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 441:    */         }
/* 442:512 */         if ("PCiL".equals(protokollArray[i].split(":")[0])) {
/* 443:513 */           chuiBlue.push(Integer.valueOf(protokollArray[i].split(":")[1]));
/* 444:    */         }
/* 445:514 */         if ("PCdR".equals(protokollArray[i].split(":")[0])) {
/* 446:515 */           chuiRed.pop();
/* 447:    */         }
/* 448:516 */         if ("PCdL".equals(protokollArray[i].split(":")[0])) {
/* 449:517 */           chuiBlue.pop();
/* 450:    */         }
/* 451:520 */         if ("CSHL".equals(protokollArray[i].split(":")[0]))
/* 452:    */         {
/* 453:522 */           if (holdingTimeBlue.isEmpty()) {
/* 454:524 */             holdingTimeBlue.add(new HoldingTime());
/* 455:    */           }
/* 456:526 */           if (((HoldingTime)holdingTimeBlue.get(holdingTimeBlue.size() - 1)).startTime == -2147483648)
/* 457:    */           {
/* 458:528 */             ((HoldingTime)holdingTimeBlue.get(holdingTimeBlue.size() - 1)).startTime = Integer.valueOf(protokollArray[i].split(":")[1]).intValue();
/* 459:    */           }
/* 460:533 */           else if (((HoldingTime)holdingTimeBlue.get(holdingTimeBlue.size() - 1)).endTime == -2147483648)
/* 461:    */           {
/* 462:535 */             ((HoldingTime)holdingTimeBlue.get(holdingTimeBlue.size() - 1)).endTime = Integer.valueOf(protokollArray[i].split(":")[1]).intValue();
/* 463:    */           }
/* 464:    */           else
/* 465:    */           {
/* 466:540 */             holdingTimeBlue.add(new HoldingTime());
/* 467:541 */             ((HoldingTime)holdingTimeBlue.get(holdingTimeBlue.size() - 1)).startTime = Integer.valueOf(protokollArray[i].split(":")[1]).intValue();
/* 468:    */           }
/* 469:    */         }
/* 470:547 */         if ("CSHR".equals(protokollArray[i].split(":")[0]))
/* 471:    */         {
/* 472:549 */           if (holdingTimeRed.isEmpty()) {
/* 473:551 */             holdingTimeRed.add(new HoldingTime());
/* 474:    */           }
/* 475:553 */           if (((HoldingTime)holdingTimeRed.get(holdingTimeRed.size() - 1)).startTime == -2147483648)
/* 476:    */           {
/* 477:555 */             ((HoldingTime)holdingTimeRed.get(holdingTimeRed.size() - 1)).startTime = Integer.valueOf(protokollArray[i].split(":")[1]).intValue();
/* 478:    */           }
/* 479:560 */           else if (((HoldingTime)holdingTimeRed.get(holdingTimeRed.size() - 1)).endTime == -2147483648)
/* 480:    */           {
/* 481:562 */             ((HoldingTime)holdingTimeRed.get(holdingTimeRed.size() - 1)).endTime = Integer.valueOf(protokollArray[i].split(":")[1]).intValue();
/* 482:    */           }
/* 483:    */           else
/* 484:    */           {
/* 485:567 */             holdingTimeRed.add(new HoldingTime());
/* 486:568 */             ((HoldingTime)holdingTimeRed.get(holdingTimeRed.size() - 1)).startTime = Integer.valueOf(protokollArray[i].split(":")[1]).intValue();
/* 487:    */           }
/* 488:    */         }
/* 489:    */       }
/* 490:    */     }
/* 491:575 */     this.ipponRed = doIppons(ipponRedI, ipponRedII, ipponRedIII);
/* 492:576 */     this.ipponBlue = doIppons(ipponBlueI, ipponBlueII, ipponBlueIII);
/* 493:    */     
/* 494:578 */     this.wazaariRedList = doWazaari(wazaariRed);
/* 495:579 */     this.wazaariBlueList = doWazaari(wazaariBlue);
/* 496:    */     
/* 497:581 */     this.penaltyRedList = doPenalty(shidoRed, chuiRed);
/* 498:582 */     this.penaltyBlueList = doPenalty(shidoBlue, chuiBlue);
/* 499:    */     
/* 500:584 */     this._holdingTimeRed = holdingTimeRed;
/* 501:585 */     this._holdingTimeBlue = holdingTimeBlue;
/* 502:    */   }
/* 503:    */   
/* 504:    */   private List<Integer> doWazaari(Stack<Integer> wazaari)
/* 505:    */   {
/* 506:594 */     List<Integer> retList = new ArrayList();
/* 507:595 */     while (!wazaari.isEmpty())
/* 508:    */     {
/* 509:597 */       retList.add(wazaari.lastElement());
/* 510:598 */       wazaari.pop();
/* 511:    */     }
/* 512:600 */     return retList;
/* 513:    */   }
/* 514:    */   
/* 515:    */   private List<Penalty> doPenalty(Stack<Integer> shidoRed, Stack<Integer> chuiRed)
/* 516:    */   {
/* 517:609 */     List<Penalty> penaltyRedList = new ArrayList(5);
/* 518:610 */     while ((!shidoRed.isEmpty()) || (!chuiRed.isEmpty())) {
/* 519:612 */       if (shidoRed.isEmpty())
/* 520:    */       {
/* 521:614 */         penaltyRedList.add(new Penalty("C", ((Integer)chuiRed.lastElement()).intValue()));
/* 522:615 */         chuiRed.pop();
/* 523:    */       }
/* 524:618 */       else if (chuiRed.isEmpty())
/* 525:    */       {
/* 526:620 */         penaltyRedList.add(new Penalty("S", ((Integer)shidoRed.lastElement()).intValue()));
/* 527:621 */         shidoRed.pop();
/* 528:    */       }
/* 529:625 */       else if (((Integer)shidoRed.lastElement()).intValue() <= ((Integer)chuiRed.lastElement()).intValue())
/* 530:    */       {
/* 531:627 */         penaltyRedList.add(new Penalty("S", ((Integer)shidoRed.lastElement()).intValue()));
/* 532:628 */         shidoRed.pop();
/* 533:    */       }
/* 534:    */       else
/* 535:    */       {
/* 536:633 */         penaltyRedList.add(new Penalty("C", ((Integer)chuiRed.lastElement()).intValue()));
/* 537:634 */         chuiRed.pop();
/* 538:    */       }
/* 539:    */     }
/* 540:638 */     return penaltyRedList;
/* 541:    */   }
/* 542:    */   
/* 543:    */   private List<Ippon> doIppons(Stack<Integer> partI, Stack<Integer> partII, Stack<Integer> partIII)
/* 544:    */   {
/* 545:643 */     List<Ippon> ipponList = new ArrayList(10);
/* 546:644 */     while ((!partI.isEmpty()) || (!partII.isEmpty()) || (!partIII.isEmpty())) {
/* 547:647 */       if (partIII.isEmpty())
/* 548:    */       {
/* 549:649 */         if (partII.isEmpty())
/* 550:    */         {
/* 551:651 */           ipponList.add(new Ippon("1", ((Integer)partI.lastElement()).intValue()));
/* 552:652 */           partI.pop();
/* 553:    */         }
/* 554:655 */         else if (partI.isEmpty())
/* 555:    */         {
/* 556:657 */           ipponList.add(new Ippon("2", ((Integer)partII.lastElement()).intValue()));
/* 557:658 */           partII.pop();
/* 558:    */         }
/* 559:661 */         else if (((Integer)partI.lastElement()).intValue() <= ((Integer)partII.lastElement()).intValue())
/* 560:    */         {
/* 561:663 */           ipponList.add(new Ippon("1", ((Integer)partI.lastElement()).intValue()));
/* 562:664 */           partI.pop();
/* 563:    */         }
/* 564:    */         else
/* 565:    */         {
/* 566:669 */           ipponList.add(new Ippon("2", ((Integer)partII.lastElement()).intValue()));
/* 567:670 */           partII.pop();
/* 568:    */         }
/* 569:    */       }
/* 570:676 */       else if (partII.isEmpty())
/* 571:    */       {
/* 572:678 */         if (partIII.isEmpty())
/* 573:    */         {
/* 574:680 */           ipponList.add(new Ippon("1", ((Integer)partI.lastElement()).intValue()));
/* 575:681 */           partI.pop();
/* 576:    */         }
/* 577:684 */         else if (partI.isEmpty())
/* 578:    */         {
/* 579:686 */           ipponList.add(new Ippon("3", ((Integer)partIII.lastElement()).intValue()));
/* 580:687 */           partIII.pop();
/* 581:    */         }
/* 582:690 */         else if (((Integer)partI.lastElement()).intValue() <= ((Integer)partIII.lastElement()).intValue())
/* 583:    */         {
/* 584:692 */           ipponList.add(new Ippon("1", ((Integer)partI.lastElement()).intValue()));
/* 585:693 */           partI.pop();
/* 586:    */         }
/* 587:    */         else
/* 588:    */         {
/* 589:698 */           ipponList.add(new Ippon("3", ((Integer)partIII.lastElement()).intValue()));
/* 590:699 */           partIII.pop();
/* 591:    */         }
/* 592:    */       }
/* 593:705 */       else if (partI.isEmpty())
/* 594:    */       {
/* 595:707 */         if (partIII.isEmpty())
/* 596:    */         {
/* 597:709 */           ipponList.add(new Ippon("2", ((Integer)partII.lastElement()).intValue()));
/* 598:710 */           partII.pop();
/* 599:    */         }
/* 600:713 */         else if (partII.isEmpty())
/* 601:    */         {
/* 602:715 */           ipponList.add(new Ippon("3", ((Integer)partIII.lastElement()).intValue()));
/* 603:716 */           partIII.pop();
/* 604:    */         }
/* 605:719 */         else if (((Integer)partII.lastElement()).intValue() <= ((Integer)partIII.lastElement()).intValue())
/* 606:    */         {
/* 607:721 */           ipponList.add(new Ippon("2", ((Integer)partII.lastElement()).intValue()));
/* 608:722 */           partII.pop();
/* 609:    */         }
/* 610:    */         else
/* 611:    */         {
/* 612:727 */           ipponList.add(new Ippon("3", ((Integer)partIII.lastElement()).intValue()));
/* 613:728 */           partIII.pop();
/* 614:    */         }
/* 615:    */       }
/* 616:734 */       else if ((((Integer)partI.lastElement()).intValue() <= ((Integer)partII.lastElement()).intValue()) && (((Integer)partI.lastElement()).intValue() <= ((Integer)partIII.lastElement()).intValue()))
/* 617:    */       {
/* 618:736 */         ipponList.add(new Ippon("1", ((Integer)partI.lastElement()).intValue()));
/* 619:737 */         partI.pop();
/* 620:    */       }
/* 621:740 */       else if ((((Integer)partII.lastElement()).intValue() <= ((Integer)partI.lastElement()).intValue()) && (((Integer)partII.lastElement()).intValue() <= ((Integer)partIII.lastElement()).intValue()))
/* 622:    */       {
/* 623:742 */         ipponList.add(new Ippon("2", ((Integer)partII.lastElement()).intValue()));
/* 624:743 */         partII.pop();
/* 625:    */       }
/* 626:    */       else
/* 627:    */       {
/* 628:748 */         ipponList.add(new Ippon("3", ((Integer)partIII.lastElement()).intValue()));
/* 629:749 */         partIII.pop();
/* 630:    */       }
/* 631:    */     }
/* 632:753 */     return ipponList;
/* 633:    */   }
/* 634:    */   
/* 635:    */   private class Penalty
/* 636:    */   {
/* 637:    */     public String penalty;
/* 638:    */     public int time;
/* 639:    */     
/* 640:    */     public Penalty(String penalty, int time)
/* 641:    */     {
/* 642:765 */       this.penalty = penalty;
/* 643:766 */       this.time = time;
/* 644:    */     }
/* 645:    */   }
/* 646:    */   
/* 647:    */   private class Ippon
/* 648:    */   {
/* 649:    */     public String part;
/* 650:    */     public int time;
/* 651:    */     
/* 652:    */     public Ippon(String part, int time)
/* 653:    */     {
/* 654:779 */       this.part = part;
/* 655:780 */       this.time = time;
/* 656:    */     }
/* 657:    */   }
/* 658:    */   
/* 659:    */   private class HoldingTime
/* 660:    */   {
/* 661:786 */     public int startTime = -2147483648;
/* 662:788 */     public int endTime = -2147483648;
/* 663:    */     
/* 664:    */     public HoldingTime() {}
/* 665:    */   }
/* 666:    */ }

