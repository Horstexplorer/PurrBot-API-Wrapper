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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public abstract class ExecutionTask<O>{

	protected final Logger logger = LoggerFactory.getLogger(PurritoRaw.class);

	/**
	 * Execute the task and wait for the result
	 *
	 * @param purritoRaw purritoraw instance
	 *
	 * @return the result object
	 */
	protected abstract O sync(PurritoRaw purritoRaw);

	/**
	 * Execute the task and call the consumers accordingly when something happened
	 *
	 * @param purritoRaw purritoraw instance
	 * @param onSuccess  returns the result object on success
	 * @param onError    returns the exception if any occurs
	 */
	protected abstract void async(PurritoRaw purritoRaw, Consumer<O> onSuccess, Consumer<Exception> onError);

}
