/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PreviewWebTatamiClasses.java
 * Created : 08 Feb 2011
 * Last Modified: Tue, 08 Feb 2011 20:55:48
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

import java.util.ArrayList;
import java.util.List;

import de.jjw.model.Preview;


public class PreviewWebTatamiClasses

{

    protected Long tatamiUserId;

    protected String className;

    protected String fights;

    protected String estStart;

    protected String restOfNeededTime;

    /**
     * @return the tatamiUserId
     */
    public Long getTatamiUserId()
    {
        return tatamiUserId;
    }

    /**
     * @param tatamiUserId the tatamiUserId to set
     */
    public void setTatamiUserId( Long tatamiUserId )
    {
        this.tatamiUserId = tatamiUserId;
    }

    /**
     * @return the className
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName( String className )
    {
        this.className = className;
    }

    /**
     * @return the fights
     */
    public String getFights()
    {
        return fights;
    }

    /**
     * @param fights the fights to set
     */
    public void setFights( String fights )
    {
        this.fights = fights;
    }

    /**
     * @return the estStart
     */
    public String getEstStart()
    {
        return estStart;
    }

    /**
     * @param estStart the estStart to set
     */
    public void setEstStart( String estStart )
    {
        this.estStart = estStart;
    }

    /**
     * @return the restOfNeededTime
     */
    public String getRestOfNeededTime()
    {
        return restOfNeededTime;
    }

    /**
     * @param restOfNeededTime the restOfNeededTime to set
     */
    public void setRestOfNeededTime( String restOfNeededTime )
    {
        this.restOfNeededTime = restOfNeededTime;
    }

}
