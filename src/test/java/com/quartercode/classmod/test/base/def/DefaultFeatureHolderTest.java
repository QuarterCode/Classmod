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

package com.quartercode.classmod.test.base.def;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.quartercode.classmod.base.Feature;
import com.quartercode.classmod.base.FeatureDefinition;
import com.quartercode.classmod.base.FeatureHolder;
import com.quartercode.classmod.base.Initializable;
import com.quartercode.classmod.base.def.AbstractFeature;
import com.quartercode.classmod.base.def.AbstractFeatureDefinition;
import com.quartercode.classmod.base.def.DefaultFeatureHolder;

public class DefaultFeatureHolderTest {

    private static FeatureDefinition<TestFeature1> TEST_FEATURE_1;
    private static FeatureDefinition<TestFeature2> TEST_FEATURE_1_WRONG_TYPE;
    private static FeatureDefinition<TestFeature2> TEST_FEATURE_2;
    private static FeatureDefinition<TestFeature3> TEST_FEATURE_3;
    private static FeatureDefinition<TestFeature3> TEST_FEATURE_3_WRONG_DEFINITION_TYPE;

    @BeforeClass
    public static void setUpBeforeClass() {

        TEST_FEATURE_1 = new AbstractFeatureDefinition<TestFeature1>("testFeature1") {

            @Override
            public TestFeature1 create(FeatureHolder holder) {

                return new TestFeature1(getName(), holder);
            }

        };

        TEST_FEATURE_1_WRONG_TYPE = new AbstractFeatureDefinition<TestFeature2>("testFeature1") {

            @Override
            public TestFeature2 create(FeatureHolder holder) {

                // Something went wrong
                Assert.fail("Should not be called");
                return null;
            }

        };

        TEST_FEATURE_2 = new AbstractFeatureDefinition<TestFeature2>("testFeature2") {

            @Override
            public TestFeature2 create(FeatureHolder holder) {

                return new TestFeature2(getName(), holder);
            }

        };

        TEST_FEATURE_3 = new TestFeature3Definition();

        TEST_FEATURE_3_WRONG_DEFINITION_TYPE = new AbstractFeatureDefinition<TestFeature3>("testFeature3") {

            @Override
            public TestFeature3 create(FeatureHolder holder) {

                return new TestFeature3(getName(), holder);
            }

        };
    }

    private DefaultFeatureHolder featureHolder;

    @Before
    public void setUp() {

        featureHolder = new DefaultFeatureHolder();
    }

    @Test
    public void testGet() {

        Feature feature1 = featureHolder.get(TEST_FEATURE_1);
        Feature feature2 = featureHolder.get(TEST_FEATURE_2);

        Assert.assertEquals("Type of feature from definition TEST_FEATURE_1", TestFeature1.class, feature1.getClass());
        Assert.assertEquals("Type of feature from definition TEST_FEATURE_2", TestFeature2.class, feature2.getClass());

        Assert.assertEquals("Name of feature from definition TEST_FEATURE_1", "testFeature1", feature1.getName());
        Assert.assertEquals("Name of feature from definition TEST_FEATURE_2", "testFeature2", feature2.getName());

        Assert.assertSame("Result of second call of get(TEST_FEATURE_1) (should be same as first call)", feature1, featureHolder.get(TEST_FEATURE_1));
        Assert.assertSame("Result of second call of get(TEST_FEATURE_2) (should be same as first call)", feature2, featureHolder.get(TEST_FEATURE_2));
    }

    @Test (expected = ClassCastException.class)
    public void testGetWrongType() {

        featureHolder.get(TEST_FEATURE_1);

        @SuppressWarnings ("unused")
        // Because of type erasure, the error occures here
        TestFeature2 feature = featureHolder.get(TEST_FEATURE_1_WRONG_TYPE);
    }

    @Test
    public void testGetInitialize() {

        TestFeature3 feature = featureHolder.get(TEST_FEATURE_3);
        Assert.assertTrue("Feature wasn't initialized properly", feature.initializeCalls == 1);

        feature = featureHolder.get(TEST_FEATURE_3);
        Assert.assertTrue("Feature was initialized more than once", feature.initializeCalls == 1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetInitializeWrongDefinitionType() {

        featureHolder.get(TEST_FEATURE_3_WRONG_DEFINITION_TYPE);
    }

    private static class TestFeature1 extends AbstractFeature {

        private TestFeature1(String name, FeatureHolder holder) {

            super(name, holder);
        }

    }

    private static class TestFeature2 extends AbstractFeature {

        private TestFeature2(String name, FeatureHolder holder) {

            super(name, holder);
        }

    }

    private static class TestFeature3 extends AbstractFeature implements Initializable<TestFeature3Definition> {

        private boolean initialized;
        public int      initializeCalls;

        private TestFeature3(String name, FeatureHolder holder) {

            super(name, holder);
        }

        @Override
        public void initialize(TestFeature3Definition definition) {

            initialized = true;
            initializeCalls++;
        }

        @Override
        public boolean isInitialized() {

            return initialized;
        }

    }

    private static class TestFeature3Definition extends AbstractFeatureDefinition<TestFeature3> {

        private TestFeature3Definition() {

            super("testFeature3");
        }

        @Override
        public TestFeature3 create(FeatureHolder holder) {

            return new TestFeature3(getName(), holder);
        }

    }

}
