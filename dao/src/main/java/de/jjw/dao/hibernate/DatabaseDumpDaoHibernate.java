/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DatabaseDumpDaoHibernate.java
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
package de.jjw.dao.hibernate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.hibernate.Query;
import org.hibernate.jdbc.Work;

import de.jjw.dao.DatabaseDumpDao;

public class DatabaseDumpDaoHibernate
    extends BaseDaoHibernate
    implements DatabaseDumpDao
{
    private static final String STRING_SPACE = " ";

    private static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";

    private static final String UNSIGNED = "UNSIGNED";

    private static final String TIMESTAMP = "TIMESTAMP";

    private static final String BLOB = "BLOB";

    private static final String DOUBLE = "DOUBLE";

    private static final String STRING_HIGH_KOMMA = "'";

    private static final String TABLE = "TABLE";

    private static final String NOT_NULL = "NOT NULL";

    private static final String NULL = "NULL";

    private static final String PK_NAME = "PK_NAME";

    private static final String IS_NULLABLE = "IS_NULLABLE";

    private static final String COLUMN_SIZE = "COLUMN_SIZE";

    private static final String TYPE_NAME = "TYPE_NAME";

    private static final String COLUMN_NAME = "COLUMN_NAME";

    private static final String TABLE_TYPE = "TABLE_TYPE";

    private static final String TABLE_NAME = "TABLE_NAME";

    Connection dbConn = null;

    // credit to ... for inspiration
    public String getDatabaseDump()
        throws JJWDataLayerException
    {

        // Default to not having a quote character
        String columnNameQuote = "";
        DatabaseMetaData dbMetaData = null;

        try
        {
            getSession().doWork( new Work()
            {
                @Override
                public void execute( Connection arg0 )
                    throws SQLException
                {
                    setDbConn( arg0 );
                }
            } );

            dbMetaData = dbConn.getMetaData();

            StringBuffer result = new StringBuffer();
            ResultSet rs = dbMetaData.getTables( "", "", "", null );

            if ( !rs.next() )
            {
                log.error( "Unable to dump DB" );
                rs.close();
                throw new JJWDataLayerException();
            }
            else
            {
                do
                {
                    String tableName = rs.getString( TABLE_NAME );
                    String tableType = rs.getString( TABLE_TYPE );
                    if ( TABLE.equalsIgnoreCase( tableType ) )
                    {
                        result.append( "\n\n-- " + tableName );
                        result.append( "\nCREATE TABLE " + tableName + " (\n" );
                        ResultSet tableMetaData = dbMetaData.getColumns( null, null, tableName, "%" );
                        boolean firstLine = true;
                        while ( tableMetaData.next() )
                        {
                            if ( firstLine )
                                firstLine = false;
                            else
                                // If we're not the first line, then finish the previous line with a comma
                                result.append( ",\n" );

                            String columnName = tableMetaData.getString( COLUMN_NAME );
                            String columnType = tableMetaData.getString( TYPE_NAME ); // unsigned loeschen
                            // WARNING: this may give daft answers for some types on some databases (eg JDBC-ODBC link)
                            int columnSize = tableMetaData.getInt( COLUMN_SIZE );
                            String nullable = tableMetaData.getString( IS_NULLABLE );
                            String nullString = NULL;
                            if ( "NO".equalsIgnoreCase( nullable ) )
                            {
                                nullString = NOT_NULL;
                            }

                            if ( columnType.indexOf( BLOB ) > 0 )
                            {
                                columnType = BLOB;
                            }

                            if ( columnType.indexOf( "UNSIGNED" ) > 0 )
                            {
                                columnType = columnType.substring( 0, columnType.indexOf( UNSIGNED ) );
                            }

                            if ( TIMESTAMP.equals( columnType ) || BLOB.equals( columnType ) )
                                result.append( "    " + columnNameQuote + columnName + columnNameQuote + STRING_SPACE
                                    + columnType + STRING_SPACE + nullString );
                            else
                            {
                                if ( DOUBLE.equals( columnType ) )
                                {
                                    int columnSizeDigit = tableMetaData.getInt( DECIMAL_DIGITS );
                                    result.append( "    " + columnNameQuote + columnName + columnNameQuote
                                        + STRING_SPACE + columnType + " (" + columnSize + "," + columnSizeDigit + ")"
                                        + STRING_SPACE + nullString );
                                }
                                else
                                    result.append( "    " + columnNameQuote + columnName + columnNameQuote
                                        + STRING_SPACE + columnType + " (" + columnSize + ")" + STRING_SPACE
                                        + nullString );
                            }
                        }
                        tableMetaData.close();

                        // Now we need to put the primary key constraint
                        try
                        {
                            ResultSet primaryKeys = dbMetaData.getPrimaryKeys( "", "", tableName );

                            String primaryKeyName = null;
                            StringBuffer primaryKeyColumns = new StringBuffer();
                            while ( primaryKeys.next() )
                            {
                                String thisKeyName = primaryKeys.getString( PK_NAME );
                                if ( ( thisKeyName != null && primaryKeyName == null )
                                    || ( thisKeyName == null && primaryKeyName != null )
                                    || ( thisKeyName != null && !thisKeyName.equals( primaryKeyName ) )
                                    || ( primaryKeyName != null && !primaryKeyName.equals( thisKeyName ) ) )
                                {
                                    // the keynames aren't the same, so output all that we have so far (if anything)
                                    // and start a new primary key entry
                                    if ( primaryKeyColumns.length() > 0 )
                                    {
                                        // There's something to output
                                        result.append( ",\n    PRIMARY KEY " );
                                        // if ( primaryKeyName != null )
                                        // {
                                        // result.append( primaryKeyName );
                                        // }
                                        result.append( "(" + columnNameQuote + primaryKeyColumns.toString()
                                            + columnNameQuote + ")" );
                                    }
                                    // Start again with the new name
                                    primaryKeyColumns = new StringBuffer();
                                    primaryKeyName = thisKeyName;
                                }
                                // Now append the column
                                if ( primaryKeyColumns.length() > 0 )
                                {
                                    primaryKeyColumns.append( ", " );
                                }
                                primaryKeyColumns.append( primaryKeys.getString( COLUMN_NAME ) );
                            }
                            if ( primaryKeyColumns.length() > 0 )
                            {
                                // There's something to output
                                result.append( ",\n    PRIMARY KEY " );
                                result.append( " (" + primaryKeyColumns.toString() + ")" );
                            }
                        }
                        catch ( SQLException e )
                        {
                            // NB you will get this exception with the JDBC-ODBC link because it says
                            // [Microsoft][ODBC Driver Manager] Driver does not support this function
                            log.error( "Unable to get primary keys for table " + tableName + " because " + e );
                        }

                        result.append( "\n);\n\n" );

                        // Right, we have a table, so we can go and dump it
                        dumpTable( dbConn, result, tableName );
                    }
                }
                while ( rs.next() );
                rs.close();
            }
            // dbConn.close();
            return result.toString();
        }
        catch ( SQLException e )
        {
            log.error( "Problem with DatabaseDump", e );
            throw new JJWDataLayerException();
        }
    }

    private static void dumpTable( Connection dbConn, StringBuffer result, String dataTable )
        throws JJWDataLayerException
    {
        try
        {
            // First we output the create table stuff
            PreparedStatement stmt = dbConn.prepareStatement( "SELECT * FROM " + dataTable );
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnCount = rsMetaData.getColumnCount();

            // Now we can output the actual data
            result.append( "\n\n-- Data for " + dataTable + "\n" );
            while ( rs.next() )
            {
                result.append( "INSERT INTO " + dataTable + " VALUES (" );
                for ( int i = 0; i < columnCount; i++ )
                {
                    if ( i > 0 )
                    {
                        result.append( ", " );
                    }
                    Object value = rs.getObject( i + 1 );

                    if ( value == null )
                    {
                        result.append( NULL );
                    }
                    else
                    {
                        String outputValue = value.toString();
                        if ( "team".equals( dataTable ) && i == 4 )
                        {
                            // outputValue = rs.getString( i + 1 );
                            result.append( NULL ); // no dump of logo, because of datasize
                        }
                        else
                        {
                            outputValue = outputValue.replaceAll( STRING_HIGH_KOMMA, "\\'" );
                            result.append( STRING_HIGH_KOMMA + outputValue + STRING_HIGH_KOMMA );
                        }
                    }
                }
                result.append( ");\n" );
            }
            rs.close();
            stmt.close();
        }
        catch ( SQLException e )
        {
            throw new JJWDataLayerException();
        }
    }

    /**
     * @return the dbConn
     */
    public Connection getDbConn()
    {
        return dbConn;
    }

    /**
     * @param dbConn the dbConn to set
     */
    public void setDbConn( Connection dbConn )
    {
        this.dbConn = dbConn;
    }

    private static String SQL_FIGHT_LIST = "Select max(" + IBaseDatabaseConstants.MODIFICATION_DATE
        + ") FROM  fight_list";

    private static String SQL_DUO_FIGHT_LIST = "Select max(" + IBaseDatabaseConstants.MODIFICATION_DATE
        + ") FROM  duo_fight_list";

    private static String SQL_FIGHTER = "Select max(" + IBaseDatabaseConstants.MODIFICATION_DATE + ") FROM  fighter";

    private static String SQL_DUOTEAM = "Select max(" + IBaseDatabaseConstants.MODIFICATION_DATE + ") FROM  duoteam";

    private static String SQL_WEIGHTCLASS = "Select max(" + IBaseDatabaseConstants.MODIFICATION_DATE
        + ") FROM  weightclass";

    private static String SQL_DUOCLASS = "Select max(" + IBaseDatabaseConstants.MODIFICATION_DATE + ") FROM  duoclass";

    public Timestamp getLatestsChangeTimestamp()
        throws JJWDataLayerException
    {
        try
        {
            Timestamp ret = null;

            ret = getLatestTableChange( ret, SQL_FIGHT_LIST );
            ret = getLatestTableChange( ret, SQL_DUO_FIGHT_LIST );
            ret = getLatestTableChange( ret, SQL_FIGHTER );
            ret = getLatestTableChange( ret, SQL_DUOTEAM );
            ret = getLatestTableChange( ret, SQL_WEIGHTCLASS );
            ret = getLatestTableChange( ret, SQL_DUOCLASS );

            return ret;
        }
        catch ( Exception e )
        {
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * @param ret
     * @return
     */
    private Timestamp getLatestTableChange( final Timestamp timestamp, String query )
    {
        Timestamp ret = timestamp;
        Query q2 = getSession().createSQLQuery( query );
        Object o2 = q2.uniqueResult();

        if ( o2 != null && ( ret == null || ret.before( (Timestamp) o2 ) ) )
            ret = (Timestamp) o2;
        return ret;
    }

    private final static String SQL_FIGHTINGCLASSES_OVER = "select count(f.id),sum(if(complete='Y',1,0)) "
        + " from fighter f left join weightclass fc on f.fightingclass=fc.id "
        + "where fc.inuse='Y' group by fc.id having count(f.id)>1";

    private final static String SQL_DUOCLASSES_OVER =
        "select count(f.id),sum(if(complete='Y',1,0)) "
            + " from duoteam f left join duoclass fc on f.duoclass=fc.id where fc.inuse='Y' group by fc.id having count(f.id)>1";

    public boolean isEventOver()
        throws JJWDataLayerException
    {
        try
        {
            Query q2 = getSession().createSQLQuery( SQL_FIGHTINGCLASSES_OVER );

            boolean isclosed = true;

            for ( Object o2 : q2.list() )
            {
                if ( ( (BigInteger) ( (Object[]) o2 )[0] ).longValue() != ( (BigDecimal) ( (Object[]) o2 )[1] ).longValue() )
                {
                    isclosed = false;
                    break;
                }
            }

            Query q = getSession().createSQLQuery( SQL_DUOCLASSES_OVER );
            for ( Object o2 : q.list() )
            {
                if ( ( (BigInteger) ( (Object[]) o2 )[0] ).longValue() != ( (BigDecimal) ( (Object[]) o2 )[1] ).longValue() )
                {
                    isclosed = false;
                    break;
                }
            }

            if ( q.list().isEmpty() && q2.list().isEmpty() )
                return false;
            return isclosed;
        }
        catch ( Exception e )
        {
            throw new JJWDataLayerException( e );
        }
    }
}
