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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawkular.metrics.model.param.Tags;

public class TagGenerator {

    public static final String POD_NAMESPACE = "pod_namespace";
    public static final String POD_NAME = "container_name";

    public static Tags generate(String namespace, String podName) {
        return new Tags(generateMap(namespace, podName));
    }

    public static Map<String, String> generateMap(String namespace, String podName) {
        Map<String, String> tagsMap = new HashMap<String, String>();
        tagsMap.put(POD_NAMESPACE, namespace);
        tagsMap.put(POD_NAME, podName);

        return tagsMap;
    }

    public static Map<String, List<String>> convert(Map<String, String> tags) {
        Map<String, List<String>> answer = new HashMap<String, List<String>>();
        for (Map.Entry<String, String> current : tags.entrySet()) {
            answer.put(current.getKey(), Arrays.asList(current.getValue()));
        }

        return answer;
    }
}
