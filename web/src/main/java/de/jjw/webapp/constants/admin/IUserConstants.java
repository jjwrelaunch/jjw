/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IUserConstants.java
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

package de.jjw.webapp.constants.admin;

public interface IUserConstants
{
    String FIELD_ID = "id";
    String FIELD_USERNAME = "username";
    String FIELD_PASSWORD = "password";
    String FIELD_CONFIRM_PASSWORD = "confirmPassword";
    String FIELD_FIRSTNAME = "firstName";
    String FIELD_LASTNAME = "lastName";
    String FIELD_CONTEXT = "context";
    String FIELD_ENABLE = "enable";
    int USER_MAX_LENGTH_DESCRIPTION = 30;
    int USER_MIN_PASSWORD_LENGHT = 8;
}
