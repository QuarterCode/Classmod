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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import com.quartercode.classmod.base.Feature;
import com.quartercode.classmod.base.FeatureDefinition;
import com.quartercode.classmod.base.FeatureHolder;
import com.quartercode.classmod.base.Persistent;
import com.quartercode.classmod.extra.LockableClass;

/**
 * A default feature holder is a class which is modifiable through {@link Feature}s.
 * It is just an implementation of {@link FeatureHolder}.
 * A user can get {@link Feature}s through the central access method {@link #get(FeatureDefinition)}.
 * Such {@link Feature}s are defined by {@link FeatureDefinition} which describe how a feature looks like.
 * 
 * @see FeatureHolder
 * @see Feature
 * @see FeatureDefinition
 * @see LockableClass
 */
public class DefaultFeatureHolder implements FeatureHolder, LockableClass {

    private final Set<Feature> features = new HashSet<Feature>();
    private boolean            locked;

    /**
     * Creates a new default feature holder.
     */
    public DefaultFeatureHolder() {

        locked = true;
    }

    @Override
    public boolean isLocked() {

        return locked;
    }

    @Override
    public void setLocked(boolean locked) {

        this.locked = locked;

        for (Feature feature : this) {
            if (feature instanceof LockableClass) {
                ((LockableClass) feature).setLocked(locked);
            }
        }
    }

    // If this doesn't succeed we have a really serious programming problem
    @SuppressWarnings ("unchecked")
    @Override
    public <F extends Feature> F get(FeatureDefinition<F> definition) {

        for (Feature feature : features) {
            if (feature.getName().equals(definition.getName())) {
                return (F) feature;
            }
        }

        F feature = definition.create(this);
        if (feature instanceof LockableClass) {
            ((LockableClass) feature).setLocked(locked);
        }
        features.add(feature);
        return feature;
    }

    /**
     * Returns a set of all {@link Persistent} {@link Feature}s of the default feature holder.
     * This uses an object set since JAXB can't handle interfaces.
     * 
     * @return All {@link Persistent} {@link Feature}s of the default feature holder.
     */
    @XmlElement (name = "features")
    public Set<Object> getPersistentFeatures() {

        Set<Object> persistentFeatures = new HashSet<Object>();
        for (Feature feature : features) {
            if (feature.getClass().isAnnotationPresent(Persistent.class)) {
                persistentFeatures.add(feature);
            }
        }

        return persistentFeatures;
    }

    /**
     * Adds the given set of {@link Persistent} {@link Feature}s to the default feature holder.
     * This uses an object set since JAXB can't handle interfaces.
     * 
     * @param persistentFeatures The {@link Persistent} {@link Feature}s to add.
     */
    public void setPersistentFeatures(Set<Object> persistentFeatures) {

        for (Object persistentFeature : persistentFeatures) {
            if (persistentFeature instanceof Feature) {
                features.add((Feature) persistentFeature);
            }
        }
    }

    @Override
    public Iterator<Feature> iterator() {

        return features.iterator();
    }

    /**
     * Returns the unique serialization id for the default feature holder.
     * The id is just the identy hash code ({@link System#identityHashCode(Object)}) of the object as a hexadecimal string.
     * 
     * @return The unique serialization id for the default feature holder.
     */
    @XmlAttribute
    @XmlID
    public String getId() {

        return Integer.toHexString(System.identityHashCode(this));
    }

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + (features == null ? 0 : features.hashCode());
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
        DefaultFeatureHolder other = (DefaultFeatureHolder) obj;
        if (features == null) {
            if (other.features != null) {
                return false;
            }
        } else if (!features.equals(other.features)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        StringBuffer featureString = new StringBuffer();
        for (Feature feature : features) {
            featureString.append(", ").append(feature.getName());
        }
        featureString.append("{").append(featureString.length() == 0 ? "" : featureString.substring(2)).append("}");

        return getClass().getName() + " [features=" + featureString + "]";
    }

}
