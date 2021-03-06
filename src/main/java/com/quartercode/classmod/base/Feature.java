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

package com.quartercode.classmod.base;

/**
 * A feature is a part of a {@link FeatureHolder} and is used for creating modifiable classes.
 * The content of a feature is not limited, but it has to provide a name and store its holder.
 * 
 * @see FeatureDefinition
 */
public interface Feature extends Named {

    /**
     * Returns the name of the feature.
     * The name is used for storing and accessing features in a {@link FeatureHolder}.
     * 
     * @return The name of the feature.
     */
    @Override
    public String getName();

    /**
     * Returns the {@link FeatureHolder} which has and uses the feature.
     * The feature is accessible though the {@link FeatureHolder#get(FeatureDefinition)} method of the returned holder.
     * 
     * @return The {@link FeatureHolder} which holds the feature.
     */
    public FeatureHolder getHolder();

}
