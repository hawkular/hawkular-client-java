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

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * I can't find anything similar to this in hawkular-metrics project
 * @author vnguyen
 *
 */
public class AggregateNumericData {

    @JsonCreator
    public AggregateNumericData(
            @JsonProperty("start") long start,
            @JsonProperty("end") long end,
            @JsonProperty("min") double min,
            @JsonProperty("max") double max,
            @JsonProperty("avg") double avg,
            @JsonProperty("median") double median,
            @JsonProperty("percentile95th") double percentile95th,
            @JsonProperty("empty") boolean empty) {
        this.start = start;
        this.end = end;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.median = median;
        this.percentile95th = percentile95th;
        this.empty = empty;
    }

    public final long start;
    public final long end;
    public final double min;
    public final double max;
    public final double avg;
    public final double median;
    public final double percentile95th;
    public final boolean empty;

    public static List<AggregateNumericData> from(Gauge rawData, int buckets) {
//        AggregateNumericData data = new AggregateNumericData(
//                0,
//                0,
//                StatisticHelper.MIN.apply(rawData).getAsDouble(),
//                StatisticHelper.MAX.apply(rawData).getAsDouble(),
//                StatisticHelper.AVG.apply(rawData).getAsDouble(),
//                StatisticHelper.MEDIAN.apply(rawData).getAsDouble(),
//                StatisticHelper.PERCENTILE95th.apply(rawData).getAsDouble(),
//                false);
//        return ImmutableList.of(data);
        return null;
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs == null)  {
           return false;
        }
        if (getClass() != rhs.getClass()) {
           return false;
        }
        final AggregateNumericData other = (AggregateNumericData) rhs;
        return    Objects.equals(this.avg, other.avg)
               && Objects.equals(this.max, other.max)
               && Objects.equals(this.min, other.min)
               && Objects.equals(this.median, other.median)
               && Objects.equals(this.percentile95th, other.percentile95th);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, min, max, avg, median, percentile95th);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("start,end", String.join(",", Long.toString(start), Long.toString(end)))
                .add("min", min)
                .add("max", max)
                .add("avg", avg)
                .add("median", median)
                .add("percentile95th", percentile95th)
                .toString();
    }
}
