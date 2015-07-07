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
package org.hawkular.client;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.hawkular.client.inventory.model.ApiError;

public class ClientResponse<T> {
    private int statusCode;
    private ApiError apiError;
    private T entity;
    private boolean success = false;
    GenericType<T> type;


    public ClientResponse(Class<T> type, Response response, int statusCode) {
        try {
            this.setStatusCode(response.getStatus());
            if (response.getStatus() == statusCode) {
                this.setSuccess(true);
                this.setEntity(response.readEntity(type));
            } else {
                this.setApiError(response.readEntity(ApiError.class));
            }
        } finally {
            response.close();
        }
    }

    @SuppressWarnings("unchecked")
    public ClientResponse(GenericType<?> type, Response response, int statusCode) {
        try {
            this.setStatusCode(response.getStatus());
            if (response.getStatus() == statusCode) {
                this.setSuccess(true);
                this.setEntity((T) response.readEntity(type));
            } else {
                this.setApiError(response.readEntity(ApiError.class));
            }
        } finally {
            response.close();
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public void setApiError(ApiError apiError) {
        this.apiError = apiError;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
