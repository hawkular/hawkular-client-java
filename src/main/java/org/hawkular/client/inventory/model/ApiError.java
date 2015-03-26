/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.client.inventory.model;
/**
 * Return information what failed in the REST-call.
 * @author Michael Burman
 */
public class ApiError {
    private final String errorMsg;
    private final Object details;

    public ApiError(String errorMsg) {
        this(errorMsg, null);
    }

    public ApiError(String errorMsg, Object details) {
        this.errorMsg = errorMsg;
        this.details = details;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Object getDetails() {
        return details;
    }
}