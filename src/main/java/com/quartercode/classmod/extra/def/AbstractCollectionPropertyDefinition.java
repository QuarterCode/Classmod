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

package com.quartercode.classmod.extra.def;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import com.quartercode.classmod.base.FeatureHolder;
import com.quartercode.classmod.base.def.AbstractFeatureDefinition;
import com.quartercode.classmod.extra.CollectionProperty;
import com.quartercode.classmod.extra.CollectionPropertyDefinition;
import com.quartercode.classmod.extra.FunctionDefinition;
import com.quartercode.classmod.extra.FunctionExecutor;
import com.quartercode.classmod.extra.Storage;
import com.quartercode.classmod.extra.ValueFactory;
import com.quartercode.classmod.util.FunctionDefinitionFactory;

/**
 * An abstract collection property definition is used to retrieve a {@link CollectionProperty} from a {@link FeatureHolder}.
 * The class is the default implementation of the {@link CollectionPropertyDefinition} interface.<br>
 * <br>
 * Every definition contains the name of the collection property, as well as the getter, adder and remover {@link FunctionExecutor}s that are used.
 * You can use an abstract collection property definition to construct a new instance of the defined collection property through {@link #create(FeatureHolder)}.
 * 
 * @param <E> The type of object that can be stored inside the defined collection property's {@link Collection}.
 * @param <C> The type of collection that can be stored inside the defined collection property.
 * @see CollectionPropertyDefinition
 * @see CollectionProperty
 * @see FunctionExecutor
 */
public abstract class AbstractCollectionPropertyDefinition<E, C extends Collection<E>> extends AbstractFeatureDefinition<CollectionProperty<E, C>> implements CollectionPropertyDefinition<E, C> {

    private static final String[]          EXCLUDED_FIELDS = { "getter", "adder", "remover" };

    private Storage<C>                     storageTemplate;
    private ValueFactory<C>                collectionFactory;
    private boolean                        ignoreEquals;

    private final FunctionDefinition<C>    getter;
    private final FunctionDefinition<Void> adder;
    private final FunctionDefinition<Void> remover;

    /**
     * Creates a new abstract collection property definition for defining a {@link CollectionProperty} with the given name and {@link Storage} implementation.
     * Also sets a template {@link Collection} whose clones are used by collection property instances.
     * 
     * @param name The name of the defined collection property.
     * @param storageTemplate A {@link Storage} implementation that should be reproduced and used by every created collection property for storing collections.
     * @param collectionFactory A {@link ValueFactory} that returns new collections for all created collection properties.
     */
    public AbstractCollectionPropertyDefinition(String name, Storage<C> storageTemplate, ValueFactory<C> collectionFactory) {

        super(name);

        Validate.notNull(storageTemplate, "The storage template of a default collection property definition cannot be null");
        Validate.notNull(collectionFactory, "The collection factory of a default collection property definition cannot be null");

        this.storageTemplate = storageTemplate;
        this.collectionFactory = collectionFactory;

        getter = FunctionDefinitionFactory.create(name);
        adder = FunctionDefinitionFactory.create(name, Object.class);
        remover = FunctionDefinitionFactory.create(name, Object.class);
    }

    /**
     * Creates a new abstract collection property definition for defining a {@link CollectionProperty} with the given name, {@link Storage} implementation, and "ignoreEquals" flag.
     * Also sets a template {@link Collection} whose clones are used by collection property instances.
     * 
     * @param name The name of the defined collection property.
     * @param storageTemplate A {@link Storage} implementation that should be reproduced and used by every created collection property for storing collections.
     * @param collectionFactory A {@link ValueFactory} that returns new collections for all created collection properties.
     * @param ignoreEquals Whether the value of the defined collection property should be excluded from equality checks of its feature holder.
     */
    public AbstractCollectionPropertyDefinition(String name, Storage<C> storageTemplate, ValueFactory<C> collectionFactory, boolean ignoreEquals) {

        this(name, storageTemplate, collectionFactory);

        this.ignoreEquals = ignoreEquals;
    }

    @Override
    public C newCollection() {

        return collectionFactory.get();
    }

    @Override
    public boolean isIgnoreEquals() {

        return ignoreEquals;
    }

    @Override
    public Map<String, FunctionExecutor<C>> getGetterExecutorsForVariant(Class<? extends FeatureHolder> variant) {

        return getter.getExecutorsForVariant(variant);
    }

    @Override
    public void addGetterExecutor(String name, Class<? extends FeatureHolder> variant, FunctionExecutor<C> executor) {

        getter.addExecutor(name, variant, executor);
    }

    @Override
    public void removeGetterExecutor(String name, Class<? extends FeatureHolder> variant) {

        getter.removeExecutor(name, variant);
    }

    @Override
    public Map<String, FunctionExecutor<Void>> getAdderExecutorsForVariant(Class<? extends FeatureHolder> variant) {

        return adder.getExecutorsForVariant(variant);
    }

    @Override
    public void addAdderExecutor(String name, Class<? extends FeatureHolder> variant, FunctionExecutor<Void> executor) {

        adder.addExecutor(name, variant, executor);
    }

    @Override
    public void removeAdderExecutor(String name, Class<? extends FeatureHolder> variant) {

        adder.removeExecutor(name, variant);
    }

    @Override
    public Map<String, FunctionExecutor<Void>> getRemoverExecutorsForVariant(Class<? extends FeatureHolder> variant) {

        return remover.getExecutorsForVariant(variant);
    }

    @Override
    public void addRemoverExecutor(String name, Class<? extends FeatureHolder> variant, FunctionExecutor<Void> executor) {

        remover.addExecutor(name, variant, executor);
    }

    @Override
    public void removeRemoverExecutor(String name, Class<? extends FeatureHolder> variant) {

        remover.removeExecutor(name, variant);
    }

    /**
     * Creates a new {@link Storage} instance from the stored storage template.
     * This method should be only used by subclasses.
     * 
     * @return A new storage instance.
     */
    protected Storage<C> newStorage() {

        return storageTemplate.reproduce();
    }

    @Override
    public int hashCode() {

        return HashCodeBuilder.reflectionHashCode(this, EXCLUDED_FIELDS);
    }

    @Override
    public boolean equals(Object obj) {

        return EqualsBuilder.reflectionEquals(this, obj, EXCLUDED_FIELDS);
    }

    @Override
    public String toString() {

        return ReflectionToStringBuilder.toStringExclude(this, EXCLUDED_FIELDS);
    }

}
