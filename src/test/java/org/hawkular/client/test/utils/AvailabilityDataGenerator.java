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

import org.hawkular.client.metrics.model.AvailabilityDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.test.BTG;
import org.hawkular.metrics.core.api.AvailabilityType;
import org.hawkular.metrics.core.api.DataPoint;
import org.hawkular.metrics.core.api.MetricType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class AvailabilityDataGenerator {

    /**
     * Generate a Availability definition
     * @return MetricDefinition
     */
    public static MetricDefinition genDef() {
        return DataGenerator.<AvailabilityType>genDef(MetricType.AVAILABILITY);

    }

    /**
     * Generate Availability data from a list of known states
     * @param states
     * @return List of data points
     */
    public static List<AvailabilityDataPoint> gen(AvailabilityType ... states) {
        BTG ts = new BTG();
        Builder<AvailabilityDataPoint> builder = new ImmutableList.Builder<>();
        for(int i=0; i< states.length; i++) {
            DataPoint<AvailabilityType> point = new DataPoint<>(ts.nextMilli(), states[i]);
            builder.add(new AvailabilityDataPoint(point));
        }
        return builder.build();
    }
}
