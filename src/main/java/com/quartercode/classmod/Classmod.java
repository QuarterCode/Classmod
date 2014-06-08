/*
 * This file is part of Classmod.
 * Copyright (c) 2014 QuarterCode <http://www.quartercode.com/>
 *
 * Classmod is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Classmod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Classmod. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.classmod;

import javax.xml.bind.JAXBContext;

/**
 * This class contains some miscellaneous stuff related to the classmod framework.
 * 
 * @see #CONTEXT_PATH
 */
public class Classmod {

    private static final String PACKAGE      = "com.quartercode.classmod";

    /**
     * The JAXB context path for classmod you can use in {@link JAXBContext#newInstance(String)}.
     * The constant doesn't has a ":" seperator at the start or the end.
     * The actual value is {@value #CONTEXT_PATH}.
     */
    public static final String  CONTEXT_PATH = PACKAGE + ".base.def:" + PACKAGE + ".extra.def:" + PACKAGE + ".extra.storage";

    private Classmod() {

    }

}