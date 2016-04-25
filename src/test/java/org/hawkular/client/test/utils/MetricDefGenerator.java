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

import java.util.HashMap;
import java.util.Map;

import org.hawkular.client.test.BaseTest;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricId;
import org.hawkular.metrics.model.MetricType;

/**
 * Random MetricDefinition generator
 * @author vnguyen
 *
 */
public class MetricDefGenerator {
    private static final int DATA_RETENTION = 21;
    private static final Map<String, String> TAGS = new HashMap<String, String>();

    @SuppressWarnings("unchecked")
    public static Metric<Double> genGaugeDef() {
        return (Metric<Double>) genDef(MetricType.GAUGE);
    }

    @SuppressWarnings("unchecked")
    public static Metric<Long> genCounterDef() {
        return (Metric<Long>) genDef(MetricType.COUNTER);
    }

    @SuppressWarnings("unchecked")
    public static Metric<AvailabilityType> genAvailDef() {
        return (Metric<AvailabilityType>) genDef(MetricType.AVAILABILITY);
    }

    public static <T> Metric<?> genDef(MetricType<T> metricType) {
        MetricId<?> id = new MetricId<>(BaseTest.getRandomId(), metricType, BaseTest.getRandomId());
        return new Metric<>(id, TAGS, DATA_RETENTION);
    }
}