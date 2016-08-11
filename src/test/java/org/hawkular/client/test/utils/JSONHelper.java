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
package org.hawkular.client.test.utils;

import java.io.File;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Helper class for loading json file into Java object
 * @author vnguyen
 *
 */
public class JSONHelper {

    public static <T> T load(Class<T> clz, File jsonFile) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(jsonFile, clz);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T load(Class<T> clz, String jsonFile) {
        InputStream is = JSONHelper.class.getResourceAsStream(jsonFile);
        if (is == null) {
            throw new RuntimeException("Can't load " + jsonFile);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(is, clz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
