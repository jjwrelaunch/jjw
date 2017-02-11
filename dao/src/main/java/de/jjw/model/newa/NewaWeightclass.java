/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaWeightclass.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

import de.jjw.model.admin.Age;

/**
 * ibernate.class table="newa_weightclass"
 */
public class NewaWeightclass
    extends Newaclass
{

    /**
     * ibernate.many-to-one  not-null="true" class="de.jjw.model.admin.Age" column="age"
     *
     * @return Returns the age.
     */
    public Age getAge()
    {
        return age;
    }

    /**
     * @param age The age to set.
     */
    public void setAge( Age age )
    {
        this.age = age;
    }


    /**
     * ibernate.property column="sex"
     *
     * @return Returns the sex.
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * @param sex The sex to set.
     */
    public void setSex( String sex )
    {
        this.sex = sex;
    }

    /**
     * ibernate.property column="startWeight"
     *
     * @return Returns the startWeight.
     */
    public double getStartWeight()
    {
        return startWeight;
    }

    /**
     * @param startWeight The startWeight to set.
     */
    public void setStartWeight( double startWeight )
    {
        this.startWeight = startWeight;
    }


    /**
     * ibernate.property column="weightclass"
     *
     * @return Returns the weightclass.
     */
    public String getWeightclass()
    {
        return weightclass;
    }

    /**
     * @param weightclass The weightclass to set.
     */
    public void setWeightclass( String weightclass )
    {
        this.weightclass = weightclass;
    }

    /**
     * ibernate.property column="weightLimit"
     *
     * @return Returns the weightLimit.
     */
    public double getWeightLimit()
    {
        return weightLimit;
    }

    /**
     * @param weightLimit The weightLimit to set.
     */
    public void setWeightLimit( double weightLimit )
    {
        this.weightLimit = weightLimit;
    }


    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals( Object o )
    {
        try
        {
            if ( o == null )
            {
                return false;
            }

            NewaWeightclass weightclass = (NewaWeightclass) o;

            if ( this.getAge().equals( weightclass.getAge() ) &&
                this.getCreateDate().equals( weightclass.getCreateDate() ) &&
                this.getCreateId().equals( weightclass.getCreateId() ) && this.getId().equals( weightclass.getId() ) &&
                this.getModificationDate().equals( weightclass.getModificationDate() ) &&
                this.getModificationId().equals( weightclass.getModificationId() ) &&
                this.getNumberOfFighter() == weightclass.getNumberOfFighter() &&
                this.getSex().equals( weightclass.getSex() ) && this.getStartWeight() == weightclass.getStartWeight() &&
                this.getUser().equals( weightclass.getUser() ) &&
                this.getWeightclass().equals( weightclass.getWeightclass() ) &&
                this.getWeightLimit() == weightclass.getWeightLimit() )
            {
                return true;
            }
        }
        catch ( Exception e )
        {
            ;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return 0;
    }


}
