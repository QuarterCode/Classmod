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

package com.quartercode.classmod.test.extra.def;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.quartercode.classmod.base.def.DefaultFeatureHolder;
import com.quartercode.classmod.extra.ExecutorInvocationException;
import com.quartercode.classmod.extra.FunctionExecutor;
import com.quartercode.classmod.extra.FunctionInvocation;
import com.quartercode.classmod.extra.Lockable;
import com.quartercode.classmod.extra.def.AbstractFunction;

@RunWith (Parameterized.class)
public class AbstractFunctionLockableTest {

    @Parameters
    public static Collection<Object[]> data() {

        List<Object[]> data = new ArrayList<Object[]>();

        data.add(new Object[] { new boolean[] { true, true }, false });
        data.add(new Object[] { new boolean[] { true, false }, true });

        return data;
    }

    private final boolean[] expectedInvocations;
    private final boolean   locked;

    public AbstractFunctionLockableTest(boolean[] expectedInvocations, boolean locked) {

        this.expectedInvocations = expectedInvocations;
        this.locked = locked;
    }

    @Test
    public void testInvoke() throws InstantiationException, IllegalAccessException, ExecutorInvocationException {

        final boolean[] actualInvocations = new boolean[2];
        Map<String, FunctionExecutor<Void>> executors = new HashMap<String, FunctionExecutor<Void>>();

        executors.put("1", new FunctionExecutor<Void>() {

            @Override
            public Void invoke(FunctionInvocation<Void> invocation, Object... arguments) throws ExecutorInvocationException {

                actualInvocations[0] = true;
                return invocation.next(arguments);
            }

        });

        executors.put("2", new FunctionExecutor<Void>() {

            @Override
            @Lockable
            public Void invoke(FunctionInvocation<Void> invocation, Object... arguments) throws ExecutorInvocationException {

                actualInvocations[1] = true;
                return invocation.next(arguments);
            }

        });

        AbstractFunction<Void> function = new AbstractFunction<Void>("testFunction", new DefaultFeatureHolder(), new ArrayList<Class<?>>(), executors);
        function.setLocked(locked);
        function.invoke();

        Assert.assertTrue("Invocation pattern doesn't equal", Arrays.equals(expectedInvocations, actualInvocations));
    }

}
