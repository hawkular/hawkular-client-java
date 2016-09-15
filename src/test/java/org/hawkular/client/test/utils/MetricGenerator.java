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

import java.util.List;
import java.util.Map;

import org.hawkular.client.test.BaseTest;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricId;
import org.hawkular.metrics.model.MetricType;

public class MetricGenerator {

    private static final int DATA_RETENTION = 21;

    public static <T> Metric<T> generate(MetricType<T> metricType, Map<String, String> tags, String name, List<DataPoint<T>> dataPoints) {
        MetricId<T> id = new MetricId<T>(BaseTest.HEADER_TENANT, metricType, name);
        return new Metric<T>(id, tags, DATA_RETENTION, dataPoints);
    }
}
