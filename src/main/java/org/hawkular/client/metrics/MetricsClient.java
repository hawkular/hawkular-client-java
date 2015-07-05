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
package org.hawkular.client.metrics;

import java.util.List;

import org.hawkular.client.metrics.model.AvailabilityDataPoint;
import org.hawkular.client.metrics.model.CounterDataPoint;
import org.hawkular.client.metrics.model.GaugeDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.metrics.model.TenantParam;
import org.hawkular.metrics.core.api.Tenant;

public interface MetricsClient {

    /**
     * Get all tenants
     * @return List of tenants
     */
    List<TenantParam> getTenants();

    /**
     * Create a new tenant
     * @param tenant
     */
    boolean createTenant(Tenant tenant);

    /**
     * Create a Gauge metric definition
     */
    void createGaugeMetric(String tenantId, MetricDefinition metricDefinition);

    /**
     * Get Gauge metric definition
     */
    MetricDefinition getGaugeMetric(String tenantId, String metricId);

    /**
     * Add data to Gauge metric
     */
    void addGaugeData(String tenantId, String metricId, List<GaugeDataPoint> data);

    /**
     * Retrieve Gauge metric data
     */
    List<GaugeDataPoint> getGaugeData(String tenantId, String metricId);

//    /**
//     * Retrieve most recent numeric metric data. See implementation for default time range.
//     */
//    List<NumericData> getNumericMetricData(String tenantId, String metricId);
//
//    List<AggregateNumericData> getAggregateNumericDataByBuckets(String tenantId,
//                                                      String metricId,
//                                                      long startTime,
//                                                      long endTime,
//                                                      int buckets);
//
    /**
     * Create a Availability metric definition
     */
    void createAvailabilityMetric(String tenantId, MetricDefinition metricDefinition);

    /**
     * Get Availability metric definition
     */
    MetricDefinition getAvailabilityMetric(String tenantId, String metricId);

    /**
     * Add data to Availability metric
     */
    void addAvailabilityData(String tenantId,
                             String metricId,
                             List<AvailabilityDataPoint> data);

    /**
     * Get data from Availability metric
     */
    List<AvailabilityDataPoint> getAvailabilityData(String tenantId,
                                                    String metricId);
    /**
     * Create a Counter metric definition
     */
    void createCounter(String tenantId,
                       MetricDefinition metricDefinition);

    /**
     * Get Counter metric definition
     */
    MetricDefinition getCounter(String tenantId,
                               String metricId);

    /**
     * Add data to Counter metric
     */
    void addCounterData(String tenantId,
                        String metricId,
                        List<CounterDataPoint> data);

    /**
     * Get data from Counter metric
     */
    List<CounterDataPoint> getCounterData(String tenantId,
                                          String metricId);
}
