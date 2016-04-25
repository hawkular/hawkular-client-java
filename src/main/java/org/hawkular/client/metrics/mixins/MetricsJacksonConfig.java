package org.hawkular.client.metrics.mixins;

import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.Tenant;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class MetricsJacksonConfig {
    private MetricsJacksonConfig() {

    }

    public static void configure(ObjectMapper objectMapper) {
        objectMapper.addMixIn(Tenant.class, TenantMixin.class);
        objectMapper.addMixIn(MetricType.class, MetricTypeMixin.class);
        objectMapper.addMixIn(Metric.class, MetricMixin.class);

    }
}
