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

import java.util.List;
import java.util.Random;

import org.hawkular.client.metrics.model.Gauge;
import org.hawkular.client.metrics.model.GaugeDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.test.BTG;
import org.hawkular.metrics.core.api.DataPoint;
import org.hawkular.metrics.core.api.MetricType;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class GaugeDataGenerator {
    private BTG ts = new BTG();
    private Random random = new Random();
    private int size;
    private final List<GaugeDataPoint> data;

    public GaugeDataGenerator(int size) {
        this.size = size;
        data = build(size);
    }

    public static List<GaugeDataPoint> gen(int size) {
        return new GaugeDataGenerator(size).data();
    }

    public static List<GaugeDataPoint> gen(int size, int interval) {
        return new GaugeDataGenerator(size).data();
    }

    public static MetricDefinition genDef() {
        return DataGenerator.<Gauge>genDef(MetricType.GAUGE);
    }

    public List<GaugeDataPoint> data() {
        return data;
    }

    private List<GaugeDataPoint> build(int size) {
        Builder<GaugeDataPoint> builder = new ImmutableList.Builder<>();
        for(int i=0; i< size; i++) {
            DataPoint<Double> point = new DataPoint<>(ts.nextMilli(), random.nextDouble());
            builder.add(new GaugeDataPoint(point));
        }
        return builder.build();
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("size", size)
                .add("data", data)
                .toString();
    }

}