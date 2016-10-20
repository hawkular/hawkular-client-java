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

import java.util.Map;

import org.hawkular.client.core.jaxrs.fasterxml.jackson.ClientObjectMapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapJavaTypeResolver {

    private final ObjectMapper objectMapper;

    public MapJavaTypeResolver() {
        this.objectMapper = new ClientObjectMapper();
    }

    /**
     * Map with Generic Key and Value, i.e.: Map<String, TaggedBucketPoint>
     */
    public JavaType get(Class<? extends Map> mapClazz, Class<?> mapClazzKey, Class<?> mapClazzValue) {
        JavaType mapClazzKeyType = objectMapper.getTypeFactory().constructType(mapClazzKey);
        JavaType mapClazzValueType = objectMapper.getTypeFactory().constructType(mapClazzValue);

        return objectMapper.getTypeFactory().constructMapType(mapClazz, mapClazzKeyType, mapClazzValueType);
    }

    /**
     * Map with Generic Key and Genric Value, i.e.: Map<String, List<TaggedBucketPoint>>
     */
    public JavaType get(
        Class<? extends Map> mapClazz, Class<?> mapClazzKey, Class<?> mapClazzValue, Class<?> mapClazzParametrizedValue) {
        JavaType mapClazzKeyType = objectMapper.getTypeFactory().constructType(mapClazzKey);
        JavaType parametrizedClazzType = objectMapper.getTypeFactory().constructParametrizedType(mapClazzValue, mapClazzValue, mapClazzParametrizedValue);

        return objectMapper.getTypeFactory().constructMapType(mapClazz, mapClazzKeyType, parametrizedClazzType);
    }

    /**
     * Map of a Map with Generic Key and Value, i.e.: Map<ElementType, Map<CanonicalPath, Integer>>
     */
    public JavaType get(
        Class<? extends Map> mapClazz, Class<?> mapClazzKey, Class<? extends Map> mapClazzValue, Class<?> mapClazzParametrizedKey, Class<?> mapClazzParametrizedValue) {
        JavaType mapClazzKeyType = objectMapper.getTypeFactory().constructType(mapClazzKey);

        JavaType mapClazzParametrizedKeyType = objectMapper.getTypeFactory().constructType(mapClazzParametrizedKey);
        JavaType mapClazzParametrizedValueType = objectMapper.getTypeFactory().constructType(mapClazzParametrizedValue);
        JavaType innerMap = objectMapper.getTypeFactory().constructMapType(mapClazzValue, mapClazzParametrizedKeyType, mapClazzParametrizedValueType);

        return objectMapper.getTypeFactory().constructMapType(mapClazz, mapClazzKeyType, innerMap);
    }
}
