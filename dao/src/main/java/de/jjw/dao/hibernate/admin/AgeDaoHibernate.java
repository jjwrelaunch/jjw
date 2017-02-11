/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AgeDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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

package de.jjw.dao.hibernate.admin;

import de.jjw.dao.admin.AgeDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.model.admin.Age;
import de.jjw.util.TypeUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;

import java.util.List;


public class AgeDaoHibernate
    extends BaseDaoHibernate
    implements AgeDao, IAgeDatabaseConstants, IFighterDatabaseConstants
{

    private static String AGE_ALL = "from " + TABLE_AGE + " a order by a." + ORDER_NUMBER;

    private static String AGE_IN_FIGHTER_USE =
        "select f." + ID + " from " + TABLE_FIGHTER + " f where f." + AGE + " = ?";

    private static String AGE_IN_DUO_USE = "select d." + ID + " from DuoTeam d where d.age = ?";

    public Age getAge( Long ageId )
    {
        try
        {
            return (Age) getHibernateTemplate().get( Age.class, ageId );
        }
        catch ( Exception e )
        {

        }
        return null;
    }

    public List getAllAges()
    {
        try
        {
            return getHibernateTemplate().find( AGE_ALL );
        }
        catch ( Exception e )
        {

        }
        return null;
    }

    public void saveAge( Age age )
    {
        getHibernateTemplate().saveOrUpdate( age );

    }

    public void removeAge( Age age )
    {
        getHibernateTemplate().delete( getAge( age.getId() ) );

    }

    public void removeAge( Long ageId )
    {
        getHibernateTemplate().delete( getAge( ageId ) );

    }

    public boolean isAgeInUse( Age age )
    {
        return isAgeInUse( age.getId() );
    }


    public boolean isAgeInUse( Long ageId )
    {
        Query q = getSession().createQuery( AGE_IN_FIGHTER_USE );
        q.setLong( 0, ageId );
        if ( q.list().size() == 0 )
        {
            q = getSession().createQuery( AGE_IN_DUO_USE );
            q.setLong( 0, ageId );
            return q.list().size() != 0;
        }
        else
        {
            return true;
        }
    }

    public int getMaxOrderNumber()
    {
        Criteria crit = getSession().createCriteria( Age.class );
        crit.setProjection( Projections.max( ORDER_NUMBER ) );
        Integer orderNumber = (Integer) crit.uniqueResult();
        if ( orderNumber == null )
        {
            return TypeUtil.INTEGER_0;
        }
        else
        {
            return orderNumber;
        }
    }

    /*
      * get the age of previous orderNumber
      */
    public Age getPreviousAge( long ageId )
        throws JJWDataLayerException
    {
        try
        {
            Age age = getAge( ageId );
            Query q = getSession().createQuery(
                "FROM Age a where a." + ORDER_NUMBER + " < ? order by " + ORDER_NUMBER + " desc " );
            q.setInteger( 0, age.getOrderNumber() );
            List<Age> list = q.list();
            if ( list != null && list.size() > TypeUtil.INT_DEFAULT )
            {
                return getAge( list.get( TypeUtil.INT_DEFAULT ).getId() );
            }
        }
        catch ( Exception e )
        {
            log.error( "AgeDaoHibernate.getPreviousAge", e );
            throw new JJWDataLayerException( e );
        }
        return null;
    }
}
