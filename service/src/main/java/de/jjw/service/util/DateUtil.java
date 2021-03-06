/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DateUtil.java
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

package de.jjw.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;

import de.jjw.util.Constants;


/**
 * Date Utility Class This is used to convert Strings to Dates and Timestamps
 * <p/>
 * <p>
 * <a href="DateUtil.java.html"><i>View Source</i></a>
 * </p>
 */
public class DateUtil
{
    // ~ Static fields/initializers =============================================

    protected final static Logger log = Logger.getRootLogger();

    private static String defaultDatePattern = null;

    private static String timePattern = "HH:mm";

    // ~ Methods ================================================================

    /**
     * Return default datePattern (MM/dd/yyyy)
     * 
     * @return a string representing the date pattern on the UI
     */
    public static synchronized String getDatePattern()
    {
        Locale locale = LocaleContextHolder.getLocale();
        try
        {
            defaultDatePattern = ResourceBundle.getBundle( Constants.BUNDLE_KEY, locale ).getString( "date.format" );
        }
        catch ( MissingResourceException mse )
        {
            defaultDatePattern = "MM/dd/yyyy";
        }

        return defaultDatePattern;
    }

    /**
     * This method attempts to convert an Oracle-formatted date in the form dd-MMM-yyyy to mm/dd/yyyy.
     * 
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static final String getDate( Date aDate )
    {
        SimpleDateFormat df = null;
        String returnValue = "";

        if ( aDate != null )
        {
            df = new SimpleDateFormat( getDatePattern() );
            returnValue = df.format( aDate );
        }

        return ( returnValue );
    }

    /**
     * This method generates a string representation of a date/time in the format you specify on input
     * 
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @throws ParseException
     * @see java.text.SimpleDateFormat
     */
    public static final Date convertStringToDate( String aMask, String strDate )
        throws ParseException
    {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat( aMask );

        if ( log.isDebugEnabled() )
        {
            log.debug( "converting '" + strDate + "' to date with mask '" + aMask + "'" );
        }

        try
        {
            date = df.parse( strDate );
        }
        catch ( ParseException pe )
        {
            // log.error("ParseException: " + pe);
            throw new ParseException( pe.getMessage(), pe.getErrorOffset() );
        }

        return ( date );
    }

    /**
     * This method returns the current date time in the format: MM/dd/yyyy HH:MM a
     * 
     * @param theTime the current time
     * @return the current date/time
     */
    public static String getTimeNow( Date theTime )
    {
        return getDateTime( timePattern, theTime );
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     * 
     * @return the current date
     * @throws ParseException
     */
    public static Calendar getToday()
        throws ParseException
    {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat( getDatePattern() );

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format( today );
        Calendar cal = new GregorianCalendar();
        cal.setTime( convertStringToDate( todayAsString ) );

        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in the format you specify on input
     * 
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see java.text.SimpleDateFormat
     */
    public static final String getDateTime( String aMask, Date aDate )
    {
        SimpleDateFormat df = null;
        String returnValue = "";

        if ( aDate == null )
        {
            log.error( "aDate is null!" );
        }
        else
        {
            df = new SimpleDateFormat( aMask );
            returnValue = df.format( aDate );
        }

        return ( returnValue );
    }

    /**
     * This method generates a string representation of a date based on the System Property 'dateFormat' in the format
     * you specify on input
     * 
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString( Date aDate )
    {
        return getDateTime( getDatePattern(), aDate );
    }

    /**
     * This method converts a String to a date using the datePattern
     * 
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException
     */
    public static Date convertStringToDate( String strDate )
        throws ParseException
    {
        Date aDate = null;

        try
        {
            if ( log.isDebugEnabled() )
            {
                log.debug( "converting date with pattern: " + getDatePattern() );
            }

            aDate = convertStringToDate( getDatePattern(), strDate );
        }
        catch ( ParseException pe )
        {
            log.error( "Could not convert '" + strDate + "' to a date, throwing exception" );
            pe.printStackTrace();
            throw new ParseException( pe.getMessage(), pe.getErrorOffset() );

        }

        return aDate;
    }
}
