package org.hawkular.client.metrics.mixins;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public abstract class MetricMixin {
    @JsonIgnore
    abstract String getTenantId();
}
