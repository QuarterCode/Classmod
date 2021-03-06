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

package com.quartercode.classmod.base.def;

import com.quartercode.classmod.base.Feature;
import com.quartercode.classmod.base.FeatureDefinition;
import com.quartercode.classmod.base.FeatureHolder;

/**
 * An abstract feature definition is used to get a {@link Feature} from a {@link FeatureHolder}.
 * It's an implementation of the {@link FeatureDefinition} interface.
 * It contains the name of the {@link Feature} and the type it has as a generic parameter.
 * You can use an abstract feature definition to construct a new instance of the defined {@link Feature} through {@link #create(FeatureHolder)}.
 * 
 * @param <F> The type the defined {@link Feature} has.
 * @see FeatureDefinition
 * @see Feature
 */
public abstract class AbstractFeatureDefinition<F extends Feature> implements FeatureDefinition<F> {

    private final String name;

    /**
     * Creates a new abstract feature definition for defining a {@link Feature} with the given name.
     * 
     * @param name The name of the defined {@link Feature}.
     */
    public AbstractFeatureDefinition(String name) {

        this.name = name;
    }

    @Override
    public String getName() {

        return name;
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractFeatureDefinition<?> other = (AbstractFeatureDefinition<?>) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        return getClass().getName() + " [name=" + name + "]";
    }

}
