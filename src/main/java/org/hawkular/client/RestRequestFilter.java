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

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class RestRequestFilter implements ClientRequestFilter {

    private static final Logger _logger = LoggerFactory.getLogger(RestRequestFilter.class);
    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static HashMap<String, Object> additionalHeaders = new HashMap<String, Object>();

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        for (String key : additionalHeaders.keySet()) {
            requestContext.getHeaders().add(key, additionalHeaders.get(key));
        }
        logRequests(requestContext);
    }

    private void logRequests(ClientRequestContext requestContext) throws JsonProcessingException {
        if (_logger.isDebugEnabled()) {
            _logger.debug(">> HTTP: {}", requestContext.getMethod());
            _logger.debug("   URI: {}", requestContext.getUri());
            _logger.debug("   Headers: {}", requestContext.getHeaders());
            _logger.debug("   Data: {}", OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(requestContext.getEntity()));
        }
    }

    public static void addHeader(String key, Object value) {
        additionalHeaders.put(key, value);
    }

    public static void removeHeader(String key) {
        additionalHeaders.remove(key);
    }

}