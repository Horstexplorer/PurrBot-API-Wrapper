/*
 *     Copyright 2021 Horstexplorer @ https://www.netbeacon.de
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.netbeacon.purrito.qol.execution;

import de.netbeacon.purrito.core.PurritoRaw;

import java.util.function.Consumer;

public class ExecutionStage<O> {

    private final PurritoRaw purritoRaw;
    private final ExecutionTask<O> executionTask;

    /**
     * Creates a new instance of this class
     *
     * @param purritoRaw    instance
     * @param executionTask task to execute
     */
    public ExecutionStage(PurritoRaw purritoRaw, ExecutionTask<O> executionTask) {
        this.purritoRaw = purritoRaw;
        this.executionTask = executionTask;
    }

    /**
     * Execute the task sync and return the result
     *
     * @return result object or null if anything went wrong
     */
    public O sync() {
        return executionTask.sync(purritoRaw);
    }

    /**
     * Execute the task async with a success and error consumer
     *
     * @param onSuccess called on success with the result
     */
    public void async(Consumer<O> onSuccess) {
        async(onSuccess, null);
    }

    /**
     * Execute the task async with a success and error consumer
     *
     * @param onSuccess called on success with the result
     * @param onError   called on error with the exception
     */
    public void async(Consumer<O> onSuccess, Consumer<Exception> onError) {
        executionTask.async(purritoRaw, onSuccess, onError);
    }

}
