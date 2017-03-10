/*
 * Copyright 2015-2017 Red Hat, Inc. and/or its affiliates
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
package org.hawkular.client.core.jaxrs.fasterxml.jackson;

import org.hawkular.client.metrics.fasterxml.jackson.AvailabilityBucketPointMixin;
import org.hawkular.client.metrics.fasterxml.jackson.AvailabilityTypeMixin;
import org.hawkular.client.metrics.fasterxml.jackson.MetricTypeMixin;
import org.hawkular.client.metrics.fasterxml.jackson.NumericBucketPointMixin;
import org.hawkular.client.metrics.fasterxml.jackson.TaggedBucketPointMixin;
import org.hawkular.client.metrics.fasterxml.jackson.TenantMixin;
import org.hawkular.inventory.json.InventoryJacksonConfig;
import org.hawkular.metrics.model.AvailabilityBucketPoint;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.NumericBucketPoint;
import org.hawkular.metrics.model.TaggedBucketPoint;
import org.hawkular.metrics.model.Tenant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ClientObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    public ClientObjectMapper() {
        config(this);
    }

    public static ObjectMapper config(ObjectMapper mapper) {
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        addMetricMixins(mapper);
        addInventoryMixins(mapper);

        return mapper;
    }

    private static void addMetricMixins(ObjectMapper mapper) {
        mapper.addMixIn(Tenant.class, TenantMixin.class)
            .addMixIn(MetricType.class, MetricTypeMixin.class)
            .addMixIn(AvailabilityType.class, AvailabilityTypeMixin.class)
            .addMixIn(TaggedBucketPoint.class, TaggedBucketPointMixin.class)
            .addMixIn(NumericBucketPoint.class, NumericBucketPointMixin.class)
            .addMixIn(AvailabilityBucketPoint.class, AvailabilityBucketPointMixin.class);
    }

    private static void addInventoryMixins(ObjectMapper mapper) {
        InventoryJacksonConfig.configure(mapper);
    }
}
