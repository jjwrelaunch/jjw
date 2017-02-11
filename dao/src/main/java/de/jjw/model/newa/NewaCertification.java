/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaCertification.java
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

package de.jjw.model.newa;

import java.util.List;

import de.jjw.util.TypeUtil;

public class NewaCertification
{

    private List<NewaFighter> fighterList = null;

    private String sex;

    private String newaclassCaption = TypeUtil.STRING_DEFAULT;

    private int certificationStyle = TypeUtil.INT_DEFAULT;

    private String age;


    public List<NewaFighter> getFighterList()
    {
        return fighterList;
    }

    public void setFighterList( List<NewaFighter> fighterList )
    {
        this.fighterList = fighterList;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex( String sex )
    {
        this.sex = sex;
    }

    public String getNewaclassCaption()
    {
        return newaclassCaption;
    }

    public void setNewaclassCaption( String newaclassCaption )
    {
        this.newaclassCaption = newaclassCaption;
    }

    public int getCertificationStyle()
    {
        return certificationStyle;
    }

    public void setCertificationStyle( int certificationStyle )
    {
        this.certificationStyle = certificationStyle;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge( String age )
    {
        this.age = age;
    }

}
