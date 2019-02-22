/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaSimplePoolItemResult.java
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

package de.jjw.model.newa;

import java.io.Serializable;

import de.jjw.util.TypeUtil;

public class NewaSimplePoolItemResult
    implements Serializable
{

    private int resultFight1 = TypeUtil.INT_MIN;

    private int resultFight2 = TypeUtil.INT_MIN;

    private int resultFight3 = TypeUtil.INT_MIN;

    private int resultFight4 = TypeUtil.INT_MIN;

    private int ubFight1 = TypeUtil.INT_MIN;

    private int ubFight2 = TypeUtil.INT_MIN;

    private int ubFight3 = TypeUtil.INT_MIN;

    private int ubFight4 = TypeUtil.INT_MIN;
    
    private int ubAdvantageFight1 = TypeUtil.INT_MIN;

    private int ubAdvantageFight2 = TypeUtil.INT_MIN;

    private int ubAdvantageFight3 = TypeUtil.INT_MIN;

    private int ubAdvantageFight4 = TypeUtil.INT_MIN;

    public int getWinCount()
    {
        if ( resultFight1 == TypeUtil.INT_MIN && resultFight2 == TypeUtil.INT_MIN && resultFight3 == TypeUtil.INT_MIN &&
            resultFight4 == TypeUtil.INT_MIN )
        {
            return TypeUtil.INT_MIN;
        }

        int ret = 0;
        if ( resultFight1 != TypeUtil.INT_MIN )
        {
            ret += resultFight1;
        }
        if ( resultFight2 != TypeUtil.INT_MIN )
        {
            ret += resultFight2;
        }
        if ( resultFight3 != TypeUtil.INT_MIN )
        {
            ret += resultFight3;
        }
        if ( resultFight4 != TypeUtil.INT_MIN )
        {
            ret += resultFight4;
        }
        return ret;
    }

    public int getUBCount()
    {
        if ( ubFight1 == TypeUtil.INT_MIN && ubFight2 == TypeUtil.INT_MIN && ubFight3 == TypeUtil.INT_MIN &&
            ubFight4 == TypeUtil.INT_MIN )
        {
            return TypeUtil.INT_MIN;
        }

        int ret = 0;
        if ( ubFight1 != TypeUtil.INT_MIN )
        {
            ret += ubFight1;
        }
        if ( ubFight2 != TypeUtil.INT_MIN )
        {
            ret += ubFight2;
        }
        if ( ubFight3 != TypeUtil.INT_MIN )
        {
            ret += ubFight3;
        }
        if ( ubFight4 != TypeUtil.INT_MIN )
        {
            ret += ubFight4;
        }
        return ret;
    }
    
    public int getUBAdvantageCount()
    {
        if ( ubAdvantageFight1== TypeUtil.INT_MIN && ubAdvantageFight2== TypeUtil.INT_MIN && ubAdvantageFight3== TypeUtil.INT_MIN &&
            ubAdvantageFight4== TypeUtil.INT_MIN )
        {
            return TypeUtil.INT_MIN;
        }

        int ret = 0;
        if ( ubAdvantageFight1 != TypeUtil.INT_MIN )
        {
            ret += ubAdvantageFight1;
        }
        if ( ubAdvantageFight2 != TypeUtil.INT_MIN )
        {
            ret += ubAdvantageFight2;
        }
        if ( ubAdvantageFight3 != TypeUtil.INT_MIN )
        {
            ret += ubAdvantageFight3;
        }
        if ( ubAdvantageFight4 != TypeUtil.INT_MIN )
        {
            ret += ubAdvantageFight4;
        }
        return ret;
    }


    public int getResultFight1()
    {
        return resultFight1;
    }

    public void setResultFight1( int resultFight1 )
    {
        this.resultFight1 = resultFight1;
    }

    public int getResultFight2()
    {
        return resultFight2;
    }

    public void setResultFight2( int resultFight2 )
    {
        this.resultFight2 = resultFight2;
    }

    public int getResultFight3()
    {
        return resultFight3;
    }

    public void setResultFight3( int resultFight3 )
    {
        this.resultFight3 = resultFight3;
    }

    public int getResultFight4()
    {
        return resultFight4;
    }

    public void setResultFight4( int resultFight4 )
    {
        this.resultFight4 = resultFight4;
    }

    public int getUbFight1()
    {
        return ubFight1;
    }

    public void setUbFight1( int ubFight1 )
    {
        this.ubFight1 = ubFight1;
    }

    public int getUbFight2()
    {
        return ubFight2;
    }

    public void setUbFight2( int ubFight2 )
    {
        this.ubFight2 = ubFight2;
    }

    public int getUbFight3()
    {
        return ubFight3;
    }

    public void setUbFight3( int ubFight3 )
    {
        this.ubFight3 = ubFight3;
    }

    public int getUbFight4()
    {
        return ubFight4;
    }

    public void setUbFight4( int ubFight4 )
    {
        this.ubFight4 = ubFight4;
    }

    public int getUbAdvantageFight1()
    {
        return ubAdvantageFight1;
    }

    public void setUbAdvantageFight1( int ubAdvantageFight1 )
    {
        this.ubAdvantageFight1 = ubAdvantageFight1;
    }

    public int getUbAdvantageFight2()
    {
        return ubAdvantageFight2;
    }

    public void setUbAdvantageFight2( int ubAdvantageFight2 )
    {
        this.ubAdvantageFight2 = ubAdvantageFight2;
    }

    public int getUbAdvantageFight3()
    {
        return ubAdvantageFight3;
    }

    public void setUbAdvantageFight3( int ubAdvantageFight3 )
    {
        this.ubAdvantageFight3 = ubAdvantageFight3;
    }

    public int getUbAdvantageFight4()
    {
        return ubAdvantageFight4;
    }

    public void setUbAdvantageFight4( int ubAdvantageFight4 )
    {
        this.ubAdvantageFight4 = ubAdvantageFight4;
    }


    
}
