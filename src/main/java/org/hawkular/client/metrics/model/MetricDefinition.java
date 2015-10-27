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

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map;

import org.hawkular.metrics.core.api.Metric;
import org.hawkular.metrics.core.api.MetricType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

/**
 * @author John Sanda
 */
public class MetricDefinition {
    private final String tenantId;
    private final String id;
    private final Map<String, String> tags;
    private final Integer dataRetention;
    private final MetricType<?> type;

    @JsonCreator(mode = Mode.PROPERTIES)
    public MetricDefinition(
            @JsonProperty("id")
            String id,
            @JsonProperty(value = "tags")
            Map<String, String> tags,
            @JsonProperty("dataRetention")
            Integer dataRetention,
            @JsonProperty("type")
            @JsonDeserialize(using = MetricTypeDeserializer.class)
            MetricType<?> type
    ) {
        checkArgument(id != null, "Metric id is null");
        this.tenantId = null;
        this.id = id;
        this.tags = tags == null ? emptyMap() : unmodifiableMap(tags);
        this.dataRetention = dataRetention;
        this.type = type;
    }

    public MetricDefinition(Metric<?> metric) {
        this.tenantId = metric.getId().getTenantId();
        this.id = metric.getId().getName();
        this.type = metric.getId().getType();
        this.tags = metric.getTags();
        this.dataRetention = metric.getDataRetention();
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getId() {
        return id;
    }

    @JsonSerialize(include = Inclusion.NON_EMPTY)
    public Map<String, String> getTags() {
        return tags;
    }

    public Integer getDataRetention() {
        return dataRetention;
    }

    @JsonSerialize(using = MetricTypeSerializer.class)
    public MetricType<?> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetricDefinition that = (MetricDefinition) o;
        return id.equals(that.id) && type == that.type;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return com.google.common.base.Objects.toStringHelper(this)
                .add("tenantId", tenantId)
                .add("id", id)
                .add("tags", tags)
                .add("dataRetention", dataRetention)
                .add("type", type)
                .omitNullValues()
                .toString();
    }
}
