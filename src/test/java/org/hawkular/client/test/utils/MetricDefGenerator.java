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

/**
 * Random MetricDefinition generator
 * @author vnguyen
 *
 */
public class MetricDefGenerator {

    public static MetricDefinition genGaugeDef() {
        return genDef(MetricType.GAUGE);
    }

    public static MetricDefinition genCounterDef() {
        return genDef(MetricType.COUNTER);
    }

    public static MetricDefinition genAvailDef() {
        return genDef(MetricType.AVAILABILITY);
    }

    public static <T> MetricDefinition genDef(MetricType<T> metricType) {
        MetricId<?> id = new MetricId<>(BaseTest.getRandomId(), metricType, BaseTest.getRandomId());
        Metric<?> metric = new Metric<>(id);
        return new MetricDefinition(metric);
    }
}