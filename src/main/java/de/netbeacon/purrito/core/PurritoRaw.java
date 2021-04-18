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

package de.netbeacon.purrito.core;

import de.netbeacon.purrito.core.request.Request;
import okhttp3.OkHttpClient;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PurritoRaw {

    private final OkHttpClient okHttpClient;
    private final Executor executor;

    /**
     * Creates a new instance of the wrapper
     */
    public PurritoRaw(){
        this.okHttpClient = new OkHttpClient();
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Creates a new instance of the wrapper using the specified okhttp client
     * @param okHttpClient okhttp client
     */
    public PurritoRaw(OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Creates a new instance of the wrapper using the specified okhttp client and executor
     * @param okHttpClient okhttp client
     * @param executor executor
     */
    public PurritoRaw(OkHttpClient okHttpClient, Executor executor){
        this.okHttpClient = okHttpClient;
        this.executor = executor;
    }

    /**
     * Returns a new api request builder which shall be configured to make a new request to the apipurrito
     * @return Request Builder
     */
    public Request.Builder newRequest(){
        return new Request.Builder(okHttpClient, executor);
    }
}
