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
