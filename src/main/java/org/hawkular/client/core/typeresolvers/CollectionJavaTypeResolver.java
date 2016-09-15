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
package org.hawkular.client.core.typeresolvers;

import java.util.List;

import org.hawkular.client.core.jaxrs.fasterxml.jackson.ClientObjectMapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CollectionJavaTypeResolver {

    private final ObjectMapper objectMapper;

    public CollectionJavaTypeResolver() {
        this.objectMapper = new ClientObjectMapper();
    }

    /**
     * List with Generic, i.e.: List<Double>
     */
    public JavaType get(Class<? extends List> collectionClazz, Class<?> clazz) {
        JavaType clazzType = objectMapper.getTypeFactory().constructType(clazz);
        return objectMapper.getTypeFactory().constructCollectionType(collectionClazz, clazzType);
    }

    /**
     * List with Generic, Generic, i.e.: List<Metric<Double>>
     */
    public JavaType get(Class<? extends List> collectionClazz, Class<?> clazz, Class<?> parametrizedClazz) {
        JavaType parametrizedClazzType = objectMapper.getTypeFactory().constructType(parametrizedClazz);
        JavaType clazzType = objectMapper.getTypeFactory().constructParametrizedType(clazz, clazz, parametrizedClazzType);

        return objectMapper.getTypeFactory().constructCollectionType(collectionClazz, clazzType);
    }
}
