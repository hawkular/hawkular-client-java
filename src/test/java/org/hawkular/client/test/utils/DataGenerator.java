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
package org.hawkular.client.test.utils;

import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.test.BaseTest;
import org.hawkular.metrics.core.api.Metric;
import org.hawkular.metrics.core.api.MetricId;
import org.hawkular.metrics.core.api.MetricType;
import org.hawkular.metrics.core.api.Tenant;

public class DataGenerator {

    public static <T> MetricDefinition genDef(MetricType metricType) {
        Tenant tenant = BaseTest.randomTenant();
        MetricId metricId = BaseTest.randomMetricId();
        return DataGenerator.<T>genDef(metricType, tenant.getId(), metricId);
    }

    public static <T> MetricDefinition genDef(MetricType metricType, String tenantId, MetricId metricId) {
        Metric<T> metric = new Metric<>(tenantId,
                                        metricType,
                                        metricId);
        return new MetricDefinition(metric);
    }
}
