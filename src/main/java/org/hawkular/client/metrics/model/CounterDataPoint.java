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
package org.hawkular.client.metrics.model;

import static org.hawkular.metrics.core.api.MetricType.COUNTER;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import java.util.Map;

import org.hawkular.metrics.core.api.DataPoint;
import org.hawkular.metrics.core.api.Metric;
import org.hawkular.metrics.core.api.MetricId;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import rx.Observable;

/**
 * @author John Sanda
 */
public class CounterDataPoint {
    private final long timestamp;
    private final long value;

    @JsonCreator(mode = Mode.PROPERTIES)
    public CounterDataPoint(
            @JsonProperty("timestamp")
            Long timestamp,
            @JsonProperty("value")
            Long value,
            @JsonProperty("tags")
            Map<String, String> tags
    ) {
        checkArgument(timestamp != null, "Data point timestamp is null");
        checkArgument(value != null, "Data point value is null");
        this.timestamp = timestamp;
        this.value = value;
    }

    public CounterDataPoint(DataPoint<Long> dataPoint) {
        timestamp = dataPoint.getTimestamp();
        value = dataPoint.getValue();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CounterDataPoint that = (CounterDataPoint) o;
        return timestamp == that.timestamp && value == that.value;
    }

    @Override
    public int hashCode() {
        int result = (int) (timestamp ^ (timestamp >>> 32));
        result = 31 * result + (int) (value ^ (value >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("timestamp", timestamp)
                .add("value", value)
                .toString();
    }

    public static List<DataPoint<Long>> asDataPoints(List<CounterDataPoint> points) {
        return Lists.transform(points, p -> new DataPoint<>(p.getTimestamp(), p.getValue()));
    }

    public static Observable<Metric<Long>> toObservable(String tenantId, String metricId, List<CounterDataPoint>
            points) {
        List<DataPoint<Long>> dataPoints = asDataPoints(points);
        Metric<Long> metric = new Metric<>(new MetricId<>(tenantId, COUNTER, metricId), dataPoints);
        return Observable.just(metric);
    }
}
