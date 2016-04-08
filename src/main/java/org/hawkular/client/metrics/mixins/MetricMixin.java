package org.hawkular.client.metrics.mixins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricId;
import org.hawkular.metrics.model.MetricType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@JsonDeserialize(using = MetricDeserializer.class)
public abstract class MetricMixin {
    @JsonIgnore
    abstract String getTenantId();
}

//TenantId missing with default deserializer
//URL: https://github.com/hawkular/hawkular-metrics/blob/master/core/metrics-model/src/main/java/
//org/hawkular/metrics/model/Metric.java#L74
@SuppressWarnings({ "unchecked", "rawtypes" })
class MetricDeserializer extends JsonDeserializer<Metric<?>> {
    @Override
    public Metric<?> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        //Sample input: 
        //{"id":"sjmja23q","dataRetention":7,"type":"counter","tenantId":"28026b36-8fe4-4332-84c8-524e173a68bf"}
        ObjectCodec objectCodec = jp.getCodec();
        JsonNode node = objectCodec.readTree(jp);
        String id = node.get("id").asText();
        if (id == null) {
            return null;
        }
        final JsonNode tagsNode = node.get("tags");
        Map<String, String> tags = tagsNode == null ? emptyMap() : unmodifiableMap((Map<String, String>) objectCodec
                .treeToValue(tagsNode, Map.class));
        Integer dataRetention = node.get("dataRetention").asInt();
        String tenantId = node.get("tenantId").asText();
        MetricType<?> type = MetricType.fromTextCode(node.get("type").asText());
        final JsonNode dataNodeArray = node.get("data");
        List<DataPoint<?>> dataPoints = new ArrayList<DataPoint<?>>();
        if (dataNodeArray != null && dataNodeArray.isArray()) {
            for (final JsonNode dataNode : dataNodeArray) {
                dataPoints.add(objectCodec.treeToValue(dataNode, DataPoint.class));
            }
        }
        MetricId<?> metricId = new MetricId<>(tenantId, type, id);
        return new Metric(metricId, tags, dataRetention, dataPoints);
    }
}