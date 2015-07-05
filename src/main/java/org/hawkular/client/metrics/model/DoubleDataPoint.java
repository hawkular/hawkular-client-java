package org.hawkular.client.metrics.model;

import org.hawkular.metrics.core.api.DataPoint;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DoubleDataPoint extends DataPoint<Double> {

    @JsonCreator
    public DoubleDataPoint(long timestamp, Double value) {
        super(timestamp, value);
    }

}
