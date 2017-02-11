/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : OptimisticLockingDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

package de.jjw.dao.hibernate.generalhelper;

import java.sql.Timestamp;

import org.hibernate.Query;

import de.jjw.dao.generalhelper.OptimisticLocking;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.BaseDaoHibernate;




public class OptimisticLockingDaoHibernate
    extends BaseDaoHibernate
    implements OptimisticLocking
{


    private static String OPTI_SQL_1 = "SELECT modificationDate FROM ";

    private static String OPTI_SQL_2 = " WHERE id=?";

    private static String ERROR_MSG = "OptimisticLockingException";

    private static String INTRUDER_CHECK = ";";

    private static int INTRUDER_NEGATIV = -1;

    public void testLocking( String table, Long id, Timestamp timestamp )
        throws OptimisticLockingException
    {
        //sql injection verhindern
        if ( table.indexOf( INTRUDER_CHECK ) != INTRUDER_NEGATIV )
        {
            throw new OptimisticLockingException( ERROR_MSG );
        }
        Query q = getSession().createQuery( OPTI_SQL_1 + table + OPTI_SQL_2 );

        q.setLong( 0, id );

        Timestamp dbTime = (Timestamp) q.list().get( 0 );
        if ( timestamp == null || timestamp.before( dbTime ) )
        {
            throw new OptimisticLockingException( ERROR_MSG );
        }
    }

    public void testLocking( String table, String id, Timestamp timestamp )
        throws OptimisticLockingException
    {
//      sql injection verhindern
        if ( table.indexOf( INTRUDER_CHECK ) != INTRUDER_NEGATIV )
        {
            throw new OptimisticLockingException( ERROR_MSG );
        }
        Query q = getSession().createQuery( OPTI_SQL_1 + table + OPTI_SQL_2 );

        q.setString( 0, id );

        Timestamp dbTime = (Timestamp) q.list().get( 0 );
        if ( timestamp == null || timestamp.before( dbTime ) )
        {
            throw new OptimisticLockingException( ERROR_MSG );
        }

    }

}
