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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@link FunctionExecutor}s which have this annotation are only invoked a given amount of times until they stop.
 * That means that a {@link FunctionExecutor} with a limit of one is only invoked one time. After that, it wont be invoked ever again.
 * This should be annotated at the actual {@link FunctionExecutor#invoke(FunctionInvocation, Object...)} method.
 * 
 * @see FunctionExecutor
 */
@Target (ElementType.METHOD)
@Retention (RetentionPolicy.RUNTIME)
public @interface Limit {

    /**
     * The amount of invocations after the {@link FunctionExecutor} blocks new invocation calls.
     * A {@link FunctionExecutor} with a limit of one is only invoked one time. After that, it wont be invoked ever again.
     */
    int value () default Integer.MAX_VALUE;

}
