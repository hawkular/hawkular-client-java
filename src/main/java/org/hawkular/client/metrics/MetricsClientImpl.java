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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.hawkular.client.BaseClient;
import org.hawkular.client.RestFactory;
import org.hawkular.client.metrics.model.AvailabilityDataPoint;
import org.hawkular.client.metrics.model.CounterDataPoint;
import org.hawkular.client.metrics.model.GaugeDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.metrics.model.TenantParam;
import org.hawkular.metrics.core.api.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hawkular-Metrics client implementation
 * @author vnguyen
 *
 */
public class MetricsClientImpl extends BaseClient<MetricsRestApi> implements MetricsClient {
    //private static final Duration EIGHT_HOURS = Duration.ofHours(8);
    private static final Logger logger = LoggerFactory.getLogger(MetricsClientImpl.class);

    public MetricsClientImpl(URI endpointUri, String username, String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<MetricsRestApi>(MetricsRestApi.class));
    }

    @Override
    public List<TenantParam> getTenants() {
        List<TenantParam> list = restApi().getTenants();
        return list == null ? new ArrayList<TenantParam>(0) : list;
    }

    @Override
    public boolean createTenant(Tenant tenant) {
        TenantParam param = new TenantParam(tenant);
        Response response = restApi().createTenant(param);
        try {
            if (response.getStatus() == 201) {
                logger.debug("Tenant[{}] created successfully, Location URI: {}",
                        tenant.getId(), response.getLocation().toString());
                return true;
            } else {
                logger.warn("Tenant[{}] creation failed, HTTP Status code: {}, Error message if any:{}",
                        tenant.getId(), response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public void createGaugeMetric(String tenantId, MetricDefinition definition) {
        restApi().createGaugeMetric(tenantId, definition);
    }

    @Override
    public MetricDefinition getGaugeMetric(String tenantId, String metricId) {
        return restApi().getGaugeMetric(tenantId, metricId);
    }

    @Override
    public void addGaugeData(String tenantId, String metricId,
            List<GaugeDataPoint> data) {
        restApi().addGaugeData(tenantId, metricId, data);
    }


    @Override
    public List<GaugeDataPoint> getGaugeData(String tenantId, String metricId) {
        return restApi().getGaugeData(tenantId, metricId);
    }

    @Override
    public void createAvailabilityMetric(String tenantId,
            MetricDefinition metricDefinition) {
        restApi().createAvailability(tenantId, metricDefinition);
    }

    @Override
    public MetricDefinition getAvailabilityMetric(String tenantId,
            String metricId) {
        return restApi().getAvailabilityMetric(tenantId, metricId);
    }

    @Override
    public void addAvailabilityData(String tenantId,
                                    String metricId,
                                    List<AvailabilityDataPoint> data) {
      logger.debug("addAvailabilityData(): tenant={}, data={}",tenantId, metricId, data);
      restApi().addAvailabilityData(tenantId, metricId, data);
    }

    @Override
    public List<AvailabilityDataPoint> getAvailabilityData(String tenantId, String metricId) {
      logger.debug("getAvailabilityData: tenantId={}, metricId={}, data={}", tenantId, metricId);
      return restApi().getAvailabilityData(tenantId, metricId);
    }

    @Override
    public void createCounter(String tenantId, MetricDefinition metricDefinition) {
        logger.debug("createCounter: tenantId={}, metricDef={}", tenantId, metricDefinition);
        restApi().createCounter(tenantId, metricDefinition);
    }

    @Override
    public MetricDefinition getCounter(String tenantId, String metricId) {
        logger.debug("getCounter: tenantId={}, metricId={}", tenantId, metricId);
        return restApi().getCounter(tenantId, metricId);
    }

    @Override
    public void addCounterData(String tenantId, String metricId,
            List<CounterDataPoint> data) {
        logger.debug("addCounterData: tenantId={}, metricId={}, data={}", tenantId, metricId, data);
        restApi().addCounterData(tenantId, metricId, data);
    }

    @Override
    public List<CounterDataPoint> getCounterData(String tenantId, String metricId) {
        logger.debug("getCounterData: tenantId={}, metricId={}", tenantId, metricId);
        return restApi().getCounterData(tenantId, metricId);
    }


//    @Override
//    public List<NumericData> getNumericMetricData(String tenantId,
//            String metricId, long startTime, long endTime) {
//        logger.debug("getNumericMetricData(): tenant={}, metric={}, start={}, end={}",
//                        tenantId, metricId, startTime, endTime);
//        return restApi().getNumericMetricData(tenantId, metricId, startTime, endTime);
//    }
//
//    @Override
//    public List<NumericData> getNumericMetricData(String tenantId,
//            String metricId) {
//        logger.debug("getNumericMetricData(): tenant={}, metric={}",tenantId, metricId);
//        long now = System.currentTimeMillis();
//        long sometime = now - EIGHT_HOURS.toMillis();
//        return this.getNumericMetricData(tenantId, metricId, sometime, now);
//    }
//
//    @Override
//    public List<AggregateNumericData> getAggregateNumericDataByBuckets(String tenantId,
//                                                             String metricId,
//                                                             long startTime,
//                                                             long endTime,
//                                                             int buckets) {
//        logger.debug("getNumericMetricDataWithBuckets(): tenantId={}, metric={}, start={}, end={}, buckets={}",
//                        tenantId, metricId, startTime, endTime, buckets);
//        return restApi().getAggregateNumericDataByBuckets(tenantId, metricId, startTime, endTime, buckets);
//    }
//
}
