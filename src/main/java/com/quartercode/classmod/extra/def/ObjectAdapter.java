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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The object adapter is used for mapping an {@link Object} field.
 * There are some bugs in JAXB the adapter works around.
 * 
 * Note: Of course, this way of doing things is really hacky, but there's currently no other solution.
 * Also note that you must add the {@link ClassWrapper} class to the "classpath" of your {@link JAXBContext}.
 */
public class ObjectAdapter extends XmlAdapter<Object, Object> {

    /**
     * Creates a new object adapter.
     */
    public ObjectAdapter() {

    }

    @Override
    public Object unmarshal(Object v) {

        if (v instanceof Wrapper) {
            return ((Wrapper<?>) v).getObject();
        } else {
            return v;
        }
    }

    @Override
    public Object marshal(Object v) {

        if (v instanceof Class) {
            return new ClassWrapper((Class<?>) v);
        } else if (v instanceof Collection) {
            return new CollectionWrapper((Collection<?>) v);
        } else {
            return v;
        }
    }

    public static interface Wrapper<T> {

        public T getObject();

    }

    @XmlType (name = "class")
    public static class ClassWrapper implements Wrapper<Class<?>> {

        @XmlValue
        private Class<?> object;

        protected ClassWrapper() {

        }

        public ClassWrapper(Class<?> object) {

            this.object = object;
        }

        @Override
        public Class<?> getObject() {

            return object;
        }

    }

    @XmlType (name = "collection")
    public static class CollectionWrapper implements Wrapper<Collection<?>> {

        @XmlElement (name = "item")
        private Collection<?> object;

        protected CollectionWrapper() {

        }

        public CollectionWrapper(Collection<?> object) {

            this.object = object;
        }

        @Override
        public Collection<?> getObject() {

            return object;
        }

    }

}