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

import org.apache.commons.lang3.Validate;
import com.quartercode.classmod.base.FeatureHolder;
import com.quartercode.classmod.extra.Property;
import com.quartercode.classmod.extra.PropertyDefinition;
import com.quartercode.classmod.extra.Storage;
import com.quartercode.classmod.extra.ValueFactory;
import com.quartercode.classmod.extra.def.AbstractPropertyDefinition;
import com.quartercode.classmod.extra.def.DefaultProperty;
import com.quartercode.classmod.factory.Factory;

/**
 * A factory for {@link AbstractPropertyDefinition}s that can create {@link DefaultProperty} objects.
 */
public class DefaultPropertyDefinitionFactory {

    @Factory (parameters = { "name", "storage", "initialValue", "ignoreEquals" })
    public <T> PropertyDefinition<T> create(String name, Storage<T> storageTemplate, ValueFactory<T> initialValueFactory, boolean ignoreEquals) {

        Validate.notNull(name, "Name of new property definition cannot be null");
        Validate.notNull(storageTemplate, "Storage template of new property definition cannot be null");

        return new AbstractPropertyDefinition<T>(name, storageTemplate, initialValueFactory, ignoreEquals) {

            @Override
            public Property<T> create(FeatureHolder holder) {

                return new DefaultProperty<>(getName(), holder, newStorage(), newInitialValue());
            }

        };
    }

}