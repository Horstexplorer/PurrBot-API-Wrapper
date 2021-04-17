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

package de.netbeacon.purrito.core.responses;

/**
 * Used wherever things go wrong
 */
public class ResponseError extends RuntimeException implements IResponse.Error {

    private final Throwable throwable;

    /**
     * Creates a new instance of this class
     * @param message error message
     */
    public ResponseError(String message){
        super(message);
        this.throwable = this;
    }

    /**
     * Creates a new instance of this class
     * @param throwable throwable
     */
    public ResponseError(Throwable throwable){
        super(throwable);
        this.throwable = throwable;
    }

    /**
     * Creates a new instance of this class
     * @param message error message
     * @param throwable throwable
     */
    public ResponseError(String message, Throwable throwable){
        super(message, throwable);
        this.throwable = throwable;
    }

    @Override
    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public Type getResponseType() {
        return Type.ERROR;
    }
}
