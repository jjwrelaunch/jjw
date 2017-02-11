/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConfigDaoHibernate.java
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

import de.jjw.dao.admin.ConfigDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.util.TypeUtil;

public class ConfigDaoHibernate
    extends BaseDaoHibernate
    implements ConfigDao
{


    public ConfigJJW getConfig()
        throws JJWDataLayerException
    {
        try
        {
            return (ConfigJJW) getHibernateTemplate().get( ConfigJJW.class, TypeUtil.LONG_1 );
        }
        catch ( Exception e )
        {
            log.error( "can get  configuration", e );
            throw new JJWDataLayerException( e );
        }


    }


    public void saveConfig( ConfigJJW config )
        throws JJWDataLayerException
    {
        if ( config != null )
        {

            try
            {
                getHibernateTemplate().saveOrUpdate( config );
            }
            catch ( Exception e )
            {
                log.error( "can not update the configuration", e );
                throw new JJWDataLayerException( e );
            }
        }
    }

}
