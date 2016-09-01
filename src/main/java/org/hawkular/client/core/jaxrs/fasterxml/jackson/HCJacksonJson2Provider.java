/*
 * Copyright 2015-2016 Red Hat, Inc. and/or its affiliates
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
package org.hawkular.client.core.jaxrs.fasterxml.jackson;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HCJacksonJson2Provider extends ResteasyJackson2Provider {

    @Override
    public void writeTo(Object value, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException {
        ObjectMapper mapper = ClientObjectMapper.config(locateMapper(type, mediaType));

        //TODO: Solve with a custome serializer
        //FIXES: AlertsCondition tests failing
        //If its a List, try to get the GenericType back, as the 'genericType' might be the interface,
        //but we want the concrete back so Jackson can marshal correctly
        if (value instanceof List) {
            List valueItems = (List)value;
            if (valueItems.size() > 0) {
                JavaType javaTypeObj = mapper.getTypeFactory().constructType(valueItems.get(0).getClass());
                genericType = javaTypeObj.getRawClass();
            }
        }

        super.writeTo(value, type, genericType, annotations, mediaType, httpHeaders, entityStream);
    }
}
