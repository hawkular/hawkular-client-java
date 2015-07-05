package org.hawkular.client.test.utils;

import java.util.List;

import org.hawkular.client.metrics.model.AvailabilityDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.test.BTG;
import org.hawkular.client.test.BaseTest;
import org.hawkular.metrics.core.api.AvailabilityType;
import org.hawkular.metrics.core.api.DataPoint;
import org.hawkular.metrics.core.api.Metric;
import org.hawkular.metrics.core.api.MetricType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class AvailabilityDataGenerator {

    /**
     * Generate a Availability definition
     * @return MetricDefinition
     */
    public static MetricDefinition genDef() {
        return genDef(BaseTest.randomTenant().getId());
    }

    /**
     * Generate a Availability definition
     * @return MetricDefinition
     */
    public static MetricDefinition genDef(String tenantId) {
        Metric<AvailabilityType> metric = new Metric<>(tenantId,
                                            MetricType.AVAILABILITY,
                                            BaseTest.randomMetricId());
        return new MetricDefinition(metric);
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
