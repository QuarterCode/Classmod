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

/**
 * Executor invocation exceptions are the only exceptions which can be thrown by {@link FunctionExecutor}s if something goes wrong (apart from {@link RuntimeException}s).
 * However, such {@link RuntimeException}s are wrapped in executor invocation exceptions when thrown by a {@link FunctionExecutor}.
 * 
 * @see FunctionExecutor
 * @see Function
 */
public class ExecutorInvocationException extends Exception {

    private static final long serialVersionUID = 4517688115723795142L;

    /**
     * Creates a new empty executor invocation exception.
     */
    public ExecutorInvocationException() {

    }

    /**
     * Creates a new executor invocation exception with the given message.
     * 
     * @param message A detailed message which is assigned to the exception.
     */
    public ExecutorInvocationException(String message) {

        super(message);
    }

    /**
     * Creates a new executor invocation exception with the given causing {@link Throwable}.
     * 
     * @param cause The {@link Throwable} which caused the exception.
     */
    public ExecutorInvocationException(Throwable cause) {

        super(cause);
    }

    /**
     * Creates a new executor invocation exception with the given message and causing {@link Throwable}.
     * 
     * @param message A detailed message which is assigned to the exception.
     * @param cause The {@link Throwable} which caused the exception.
     */
    public ExecutorInvocationException(String message, Throwable cause) {

        super(message, cause);
    }

}
