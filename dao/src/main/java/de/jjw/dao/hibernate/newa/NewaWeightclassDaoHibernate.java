/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaWeightclassDaoHibernate.java
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

package de.jjw.dao.hibernate.newa;


import java.util.List;

import org.hibernate.Query;

import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.newa.NewaWeightclassDao;
import de.jjw.model.admin.Age;
import de.jjw.model.newa.Newaclass;
import de.jjw.util.TypeUtil;

public class NewaWeightclassDaoHibernate
    extends BaseDaoHibernate
    implements NewaWeightclassDao, INewaWeightclassDatabaseConstants, INewaFighterDatabaseConstants,
    IAgeDatabaseConstants
{

    private static String WEIGHTCLASS_ALL = "SELECT new Newaclass(w, "
        + " (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id ),"
        + // number of NewaFighter in class
        " (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id and f.ready=1)"
        +
        // number of NewaFighter in class which are ready
        " ) " + " from " + TABLE_NEWACLASS + " w left join  w." + INewaWeightclassDatabaseConstants.AGE
        + " a order by a." + START_AGE + " desc, w." + INewaWeightclassDatabaseConstants.SEX + ", w." + START_WEIGHT;

    private static String WEIGHTCLASS_BY_AGE =
 "from " + TABLE_NEWACLASS + " w left join fetch w."
        + INewaWeightclassDatabaseConstants.AGE + " a where w." + INewaWeightclassDatabaseConstants.AGE
        + "=? order by a." + START_AGE + " desc, w." + INewaWeightclassDatabaseConstants.SEX + ", w." + START_WEIGHT;

    private static String WEIGHTCLASS_IN_FIGHTER_USE =
 "select f.* from " + TABLE_FIGHTER + " f where f."
        + INewaFighterDatabaseConstants.WEIGHTCLASS + "= ?";

    private static String WEIGHTCLASS_AGE_SHORT =
 "from" + TABLE_NEWACLASS
        + " w left join fetch w.age a where a.ageShort =? order by w.sex, w.startWeight";

    private static String WEIGHTCLASS_AGE_ID =
        "from Weightclass w left join fetch w.age a where a.id =? order by w.sex, w.startweight";

    private static String WEIGHTCLASS_BY_AGE_SEX_WEIGHT =
 SQL_FROM + TABLE_NEWACLASS + " w " + SQL_LEFT + SQL_JOIN
        + SQL_FETCH + " w." + INewaWeightclassDatabaseConstants.AGE + " a " + " LEFT JOIN FETCH w."
        + INewaWeightclassDatabaseConstants.PARENT + " p " + SQL_WHERE + "a." + ID + SQL_EQUAL + SQL_QUESTION_MARK
        +
 SQL_AND + " w." + INewaWeightclassDatabaseConstants.SEX + SQL_EQUAL + SQL_QUESTION_MARK + SQL_AND
        +
            SQL_QUESTION_MARK + SQL_LOWER_EQUAL + " w." + WEIGHT_LIMIT + SQL_AND + SQL_QUESTION_MARK + SQL_GREATER +
            " w." + START_WEIGHT;

    public Newaclass getWeightclass( Long id )
    {
        return (Newaclass) getHibernateTemplate().get( Newaclass.class, id );
    }

    public List<Newaclass> getAllWeightclasses()
    {
        try
        {
            return (List<Newaclass>) getHibernateTemplate().find( WEIGHTCLASS_ALL );
        }
        catch ( Exception e )
        {
            e.getMessage();
        }
        return null;
    }

    public List<Newaclass> getWeightclassByAgeNameShort( String ageShort )
    {
        Query q = getSession().createQuery( WEIGHTCLASS_AGE_SHORT );
        q.setString( 0, ageShort );
        return (List<Newaclass>) q.list();
    }

    public List<Newaclass> getWeightclassByAge( Age age )
    {
        Query q = getSession().createQuery( WEIGHTCLASS_BY_AGE );
        q.setLong( 0, age.getId() );
        return (List<Newaclass>) q.list();
    }

    public void saveWeightclass( Newaclass newaclass )
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( newaclass );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save weightclass: " + newaclass.toString() );
        }
    }

    public void removeWeightclass( Long weightclassId )
    {
        getHibernateTemplate().delete( getWeightclass( weightclassId ) );

    }

    public void removeWeightclass( Newaclass newaclass )
    {
        getHibernateTemplate().delete( getWeightclass( newaclass.getId() ) );

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
    public Newaclass getWeightclassByAgeSexWeight( Long ageId, String sex, Double weight )
        throws JJWDataLayerException
    {
        Newaclass ret = null;
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
                return (Newaclass) list.get( 0 );
            }
        }
        catch ( Exception e )
        {
            log.error( "can not get newaclass ba age,sex and weightclass", e );
            throw new JJWDataLayerException( e );
        }

        return ret;
    }

    private static String WEIGHTCLASS_IN_TEAM_USE = "from " + TABLE_FIGHTER + " f where f."
        + INewaWeightclassDatabaseConstants.NEWACLASS + " = ?";

    public boolean isWeightclassInUse( Long newaclassId )
    {
        Query q = getSession().createQuery( WEIGHTCLASS_IN_TEAM_USE );
        q.setLong( 0, newaclassId );

        return !q.list().isEmpty();

    }

    public boolean isWeightclassInUse( Newaclass newaclassId )
    {
        return isWeightclassInUse( newaclassId.getId() );
    }

    private static String WEIGHTCLASS_CHILD_OR_PARENT = "from " + TABLE_NEWACLASS + " f where f." + PARENT + " = ?";

    public boolean isWeightclassChildOrParent( Long newaclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( WEIGHTCLASS_CHILD_OR_PARENT );
            q.setLong( 0, newaclassId );

            if ( q.list().isEmpty() )
            {
                Newaclass fc = getWeightclass( newaclassId );
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
