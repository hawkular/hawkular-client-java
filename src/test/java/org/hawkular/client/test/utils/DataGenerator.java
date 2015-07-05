package org.hawkular.client.test.utils;

import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.test.BaseTest;
import org.hawkular.metrics.core.api.Metric;
import org.hawkular.metrics.core.api.MetricId;
import org.hawkular.metrics.core.api.MetricType;
import org.hawkular.metrics.core.api.Tenant;

public class DataGenerator {

    public static <T> MetricDefinition genDef(MetricType metricType) {
        Tenant tenant = BaseTest.randomTenant();
        MetricId metricId = BaseTest.randomMetricId();
        return DataGenerator.<T>genDef(metricType, tenant.getId(), metricId);
    }

    public static <T> MetricDefinition genDef(MetricType metricType, String tenantId, MetricId metricId) {
        Metric<T> metric = new Metric<>(tenantId,
                                        metricType,
                                        metricId);
        return new MetricDefinition(metric);
    }
}
