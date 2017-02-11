/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassWeb.java
 * Created : 17 Jun 2010
 * Last Modified: Thu, 17 Jun 2010 18:49:12
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

package de.jjw.service.modelWeb;

import java.sql.Date;

import de.jjw.model.newa.Newaclass;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.TypeUtil;

public class NewaclassWeb
    extends Newaclass
    implements IGlobalProjectConstants
{

    private String sexWeb;

    private Date startTimeWeb;

    private String estimateTimeWeb;

    private String certificationWeb; // only as dummy for sorting issues

    private boolean classStartet;

    private String estimateBeginTimeWeb;

    public String getSexWeb()
    {
        return sexWeb;
    }

    public void setSexWeb( String sexWeb )
    {
        this.sexWeb = sexWeb;
    }


    public Date getStartTimeWeb()
    {
        return startTimeWeb;
    }

    public void setStartTimeWeb( Date startTimeWeb )
    {
        this.startTimeWeb = startTimeWeb;
    }

    public String getCertificationWeb()
    {
        if ( !complete )
        {
            return certificationWeb = IMAGE_JJW_DIR + EMPTY_GIF;
        }
        else if ( !certificationPrint )
        {
            return certificationWeb = IMAGE_JJW_DIR + CERTIFICATE_ICON;
        }
        else
        {
            return certificationWeb = IMAGE_JJW_DIR + OK_RED_GIF;
        }
    }

    public void setCertificationWeb( String certificationWeb )
    {
        this.certificationWeb = certificationWeb;
    }

    public boolean isRender()
    {
        return complete;
    }

    public String getEstimateTimeWeb()
    {
        return estimateTimeWeb;
    }

    public void setEstimateTimeWeb( String estimateTime )
    {
        this.estimateTimeWeb = estimateTime;
    }

    public boolean isCanBeParentClass()
    {
        return !isDeleteStop() && ( null == getParentId() || TypeUtil.isEmptyOrDefault( getParentId() ) );
    }

    public boolean isClassStartet()
    {
        return classStartet = null != startTime ? true : false;
    }

    public boolean isNotClassStartet()
    {
        return !isClassStartet();
    }

    /**
     * @param classStartet the classStartet to set
     */
    public void setClassStartet( boolean classStartet )
    {
        this.classStartet = classStartet;
    }

    /**
     * @return the estimateBeginTimeWeb
     */
    public String getEstimateBeginTimeWeb()
    {
        return estimateBeginTimeWeb;
    }

    /**
     * @param estimateBeginTimeWeb the estimateBeginTimeWeb to set
     */
    public void setEstimateBeginTimeWeb( String estimateBeginTimeWeb )
    {
        this.estimateBeginTimeWeb = estimateBeginTimeWeb;
    }

}
