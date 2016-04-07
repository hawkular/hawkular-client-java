package org.hawkular.client.metrics.mixins;

import org.hawkular.metrics.model.fasterxml.jackson.MetricTypeDeserializer;
import org.hawkular.metrics.model.fasterxml.jackson.MetricTypeSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@JsonDeserialize(using = MetricTypeDeserializer.class)
@JsonSerialize(using = MetricTypeSerializer.class)
public abstract class MetricTypeMixin {
}
