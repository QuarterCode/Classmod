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

package com.quartercode.classmod.extra;

import com.quartercode.classmod.base.Feature;

/**
 * A property is a simple {@link Feature} which stores an object.
 * 
 * @param <T> The type of object which can be stored inside the property.
 */
public interface Property<T> extends Feature, Iterable<T> {

    /**
     * Returns the object which is stored inside the property.
     * 
     * @return The stored object.
     */
    public T get();

    /**
     * Changes the object which is stored inside the property.
     * 
     * @param value The new stored object.
     */
    public void set(T value);

}
