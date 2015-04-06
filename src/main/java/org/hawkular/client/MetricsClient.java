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
package org.hawkular.client;

import java.util.List;

import org.hawkular.metrics.core.api.Availability;
import org.hawkular.metrics.core.api.AvailabilityMetric;
import org.hawkular.metrics.core.api.NumericData;
import org.hawkular.metrics.core.api.NumericMetric;
import org.hawkular.metrics.core.api.Tenant;

public interface MetricsClient {

    List<Tenant> findTenants();

    void createTenant(Tenant tenant);

    /**
     * Create a NumericMetric definition
     */
    void createNumericMetric(NumericMetric metric);

    /**
     * Find a NumericMetric definition
     */
    NumericMetric findNumericMetric(String tenantId, String metricId);

    /**
     * Add data to a numeric metric
     */
    void addNumericMetricData(String tenantId, String metricId, List<NumericData> data);

    /**
     * Retrieve numeric metric data within a specific time stamp range
     */
    List<NumericData> getNumericMetricData(String tenantId, String metricId, long startTime, long endTime);

    /**
     * Retrieve most recent numeric metric data. See implementation for default time range.
     */
    List<NumericData> getNumericMetricData(String tenantId, String metricId);

    List<NumericData> getNumericMetricDataWithBuckets(String tenantId,
                                                      String metricId,
                                                      long startTime,
                                                      long endTime,
                                                      int buckets);

    /**
     * Create a AvailabilityMetric definition
     */
    void createAvailability(String tenantId, AvailabilityMetric metric);

    /**
     * Not sure what this api returns
     */
    String findAvailabilityByTags(String tenantId, String csvTags);

    /**
     * Add availability data
     */
    void addAvailabilityData(String tenantId,
                                   String availId,
                                   List<Availability> data);

    /**
     * Get availability data
     */
    List<Availability> getAvailabilityData(String tenantId,
                                           String metricId);
}
