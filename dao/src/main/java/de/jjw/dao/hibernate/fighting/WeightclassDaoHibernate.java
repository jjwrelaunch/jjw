/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : WeightclassDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.dao.hibernate.fighting;


import java.util.List;

import org.hibernate.Query;

import de.jjw.dao.fighting.WeightclassDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.model.admin.Age;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.util.TypeUtil;

public class WeightclassDaoHibernate
    extends BaseDaoHibernate
    implements WeightclassDao, IWeightclassDatabaseConstants, IFighterDatabaseConstants, IAgeDatabaseConstants
{

    private static String WEIGHTCLASS_ALL = "SELECT new Fightingclass(w, " +
        " (select count(distinct f.id) from Fighter f where f.fightingclass = w.id )," + //number of fighter in class
        " (select count(distinct f.id) from Fighter f where f.fightingclass = w.id and f.ready=1)" +
        //number of fighter in class which are ready
        " ) " + " from " + TABLE_WEIGHTCLASS + " w left join  w." + IWeightclassDatabaseConstants.AGE +
        " a order by a." + START_AGE + " desc, w." + IWeightclassDatabaseConstants.SEX + ", w." + START_WEIGHT;

    private static String WEIGHTCLASS_BY_AGE =
        "from " + TABLE_WEIGHTCLASS + " w left join fetch w." + IWeightclassDatabaseConstants.AGE + " a where w." +
            IWeightclassDatabaseConstants.AGE + "=? order by a." + START_AGE + " desc, w." +
            IWeightclassDatabaseConstants.SEX + ", w." + START_WEIGHT;

    private static String WEIGHTCLASS_IN_FIGHTER_USE =
        "select f.* from " + TABLE_FIGHTER + " f where f." + IFighterDatabaseConstants.WEIGHTCLASS + "= ?";

    private static String WEIGHTCLASS_AGE_SHORT =
        "from" + TABLE_WEIGHTCLASS + " w left join fetch w.age a where a.ageShort =? order by w.sex, w.startWeight";

    private static String WEIGHTCLASS_AGE_ID =
        "from Weightclass w left join fetch w.age a where a.id =? order by w.sex, w.startweight";

    private static String WEIGHTCLASS_BY_AGE_SEX_WEIGHT =
        SQL_FROM + TABLE_WEIGHTCLASS + " w " + SQL_LEFT + SQL_JOIN + SQL_FETCH + " w." +
 IWeightclassDatabaseConstants.AGE + " a " + " LEFT JOIN FETCH w."
        + IWeightclassDatabaseConstants.PARENT + " p " + SQL_WHERE + "a." + ID + SQL_EQUAL + SQL_QUESTION_MARK
        +
            SQL_AND + " w." + IWeightclassDatabaseConstants.SEX + SQL_EQUAL + SQL_QUESTION_MARK + SQL_AND +
            SQL_QUESTION_MARK + SQL_LOWER_EQUAL + " w." + WEIGHT_LIMIT + SQL_AND + SQL_QUESTION_MARK + SQL_GREATER +
            " w." + START_WEIGHT;

    public Fightingclass getWeightclass( Long id )
    {
        return (Fightingclass) getHibernateTemplate().get( Fightingclass.class, id );
    }

    public List<Fightingclass> getAllWeightclasses()
    {
        try
        {
            return (List<Fightingclass>) getHibernateTemplate().find( WEIGHTCLASS_ALL );
        }
        catch ( Exception e )
        {
            e.getMessage();
        }
        return null;
    }

    public List<Fightingclass> getWeightclassByAgeNameShort( String ageShort )
    {
        Query q = getSession().createQuery( WEIGHTCLASS_AGE_SHORT );
        q.setString( 0, ageShort );
        return (List<Fightingclass>) q.list();
    }

    public List<Fightingclass> getWeightclassByAge( Age age )
    {
        Query q = getSession().createQuery( WEIGHTCLASS_BY_AGE );
        q.setLong( 0, age.getId() );
        return (List<Fightingclass>) q.list();
    }

    public void saveWeightclass( Fightingclass fightingclass )
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( fightingclass );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save weightclass: " + fightingclass.toString() );
        }
    }

    public void removeWeightclass( Long weightclassId )
    {
        getHibernateTemplate().delete( getWeightclass( weightclassId ) );

    }

    public void removeWeightclass( Fightingclass fightingclass )
    {
        getHibernateTemplate().delete( getWeightclass( fightingclass.getId() ) );

    }

    /**
     * get the right weigt(fighting)class for a fighter who is
     * specified by age, sex and weight
     *
     * @param ageId
     * @param sex
     * @param weight
     * @return
     * @throws JJWDataLayerException
     */
    public Fightingclass getWeightclassByAgeSexWeight( Long ageId, String sex, Double weight )
        throws JJWDataLayerException
    {
        Fightingclass ret = null;
        try
        {
            Query q = getSession().createQuery( WEIGHTCLASS_BY_AGE_SEX_WEIGHT );
            q.setLong( 0, ageId );
            q.setString( 1, sex );
            q.setDouble( 2, weight );
            q.setDouble( 3, weight );
            List list = q.list();

            if ( list != null && list.size() > 0 )
            {
                return (Fightingclass) list.get( 0 );
            }
        }
        catch ( Exception e )
        {
            log.error( "can not get fightingclass ba age,sex and weightclass", e );
            throw new JJWDataLayerException( e );
        }

        return ret;
    }

    private static String WEIGHTCLASS_IN_TEAM_USE = "from " + TABLE_FIGHTER + " f where f." + FIGHTINGCLASS + " = ?";

    public boolean isWeightclassInUse( Long fightingclassId )
    {
        Query q = getSession().createQuery( WEIGHTCLASS_IN_TEAM_USE );
        q.setLong( 0, fightingclassId );

        return !q.list().isEmpty();

    }

    public boolean isWeightclassInUse( Fightingclass fightingclassId )
    {
        return isWeightclassInUse( fightingclassId.getId() );
    }

    private static String WEIGHTCLASS_CHILD_OR_PARENT = "from " + TABLE_FIGHTINGCLASS + " f where f." + PARENT + " = ?";

    public boolean isWeightclassChildOrParent( Long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( WEIGHTCLASS_CHILD_OR_PARENT );
            q.setLong( 0, fightingclassId );

            if ( q.list().isEmpty() )
            {
                Fightingclass fc = getWeightclass( fightingclassId );
                if ( null == fc.getParentId() )
                    return false;
                return !TypeUtil.isEmptyOrDefault( fc.getParentId() );
            }
            else
            {
                return true;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not isWeightclassChildOrParent", e );
            throw new JJWDataLayerException( e );
        }
    }
}
