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
package org.hawkular.client.metrics.fasterxml.jackson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.hawkular.metrics.model.AvailabilityBucketPoint;
import org.hawkular.metrics.model.AvailabilityType;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class AvailabilityBucketPointDeserializer extends JsonDeserializer<AvailabilityBucketPoint> {

    @SuppressWarnings("unchecked")
    @Override
    public AvailabilityBucketPoint deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec objectCodec = jp.getCodec();
        JsonNode node = objectCodec.readTree(jp);

        long start = 0L;
        JsonNode startJsonNode = node.get("start");
        if (startJsonNode != null) {
            start = startJsonNode.asLong();
        }

        long end = 0L;
        JsonNode endJsonNode = node.get("end");
        if (endJsonNode != null) {
            end = endJsonNode.asLong();
        }

        JsonNode durationMapJsonNode = node.get("durationMap");
        JsonNode lastNotUptimeJsonNode = node.get("lastNotUptime");
        JsonNode uptimeRatioJsonNode = node.get("uptimeRatio");
        JsonNode notUpCountJsonNode = node.get("notUpCount");
        JsonNode adminDurationJsonNode = node.get("adminDuration");
        JsonNode downDurationJsonNode = node.get("downDuration");

        return new AvailabilityBucketPoint.Builder(start, end)
            .setDurationMap(durationMapJsonNode == null ? new HashMap<AvailabilityType, Long>() : objectCodec.treeToValue(durationMapJsonNode, Map.class))
            .setAdminDuration(adminDurationJsonNode == null ? 0L : adminDurationJsonNode.asLong())
            .setLastNotUptime(lastNotUptimeJsonNode == null ? 0L : lastNotUptimeJsonNode.asLong())
            .setDownDuration(downDurationJsonNode == null ? 0L : downDurationJsonNode.asLong())
            .setUptimeRatio(uptimeRatioJsonNode == null ? 0L : uptimeRatioJsonNode.asLong())
            .setNotUptimeCount(notUpCountJsonNode == null ? 0L : notUpCountJsonNode.asLong())
            .build();
    }
}
