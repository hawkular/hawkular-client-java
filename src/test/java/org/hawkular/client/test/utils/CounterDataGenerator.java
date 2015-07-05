package org.hawkular.client.test.utils;

import java.util.List;
import java.util.Random;

import org.hawkular.client.metrics.model.Counter;
import org.hawkular.client.metrics.model.CounterDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.test.BTG;
import org.hawkular.metrics.core.api.DataPoint;
import org.hawkular.metrics.core.api.MetricType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

public class CounterDataGenerator {

    /**
     * Generate Counter metric definition
     * @return MetricDefinition
     */
    public static MetricDefinition genDef() {
        return DataGenerator.<Counter>genDef(MetricType.COUNTER);
    }

    public static List<CounterDataPoint> gen(int size) {
        BTG ts = new BTG();
        Random random = new Random();
        long value = 1000;
        Builder<CounterDataPoint> builder = new ImmutableList.Builder<>();
        for(int i=0; i< size; i++) {
            DataPoint<Long> point = new DataPoint<>(ts.nextMilli(), value);
            builder.add(new CounterDataPoint(point));
            value = value + random.nextInt(100);
        }
        return builder.build();
    }
}
