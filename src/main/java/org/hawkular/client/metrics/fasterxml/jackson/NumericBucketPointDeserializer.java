/*
 * Copyright 2015-2016 Red Hat, Inc. and/or its affiliates
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
import java.util.ArrayList;
import java.util.List;

import org.hawkular.metrics.model.NumericBucketPoint;
import org.hawkular.metrics.model.Percentile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Response causes Constructor Miss-match, due to "empty" field
 * TODO: get metrics to exclude "empty" from model
 */
public class NumericBucketPointDeserializer extends JsonDeserializer<NumericBucketPoint> {

    @SuppressWarnings("unchecked")
    @Override
    public NumericBucketPoint deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
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

        JsonNode minJsonNode = node.get("min");
        JsonNode avgJsonNode = node.get("avg");
        JsonNode medianJsonNode = node.get("median");
        JsonNode maxJsonNode = node.get("max");
        JsonNode sumJsonNode = node.get("sum");
        JsonNode samplesJsonNode = node.get("samples");

        JsonNode percentilesJsonNode = node.get("percentiles");
        List<Percentile> percentiles = new ArrayList<Percentile>();
        if (percentilesJsonNode != null) {
            percentiles = objectCodec.treeToValue(percentilesJsonNode, List.class);
        }

        return new NumericBucketPoint.Builder(start, end)
            .setMin(minJsonNode == null ? 0 : minJsonNode.asDouble())
            .setAvg(avgJsonNode == null ? 0 : avgJsonNode.asDouble())
            .setMedian(medianJsonNode == null ? 0 : medianJsonNode.asDouble())
            .setMax(maxJsonNode == null ? 0 : maxJsonNode.asDouble())
            .setSum(sumJsonNode == null ? 0 : sumJsonNode.asDouble())
            .setSamples(samplesJsonNode == null ? 0 : samplesJsonNode.asInt())
            .setPercentiles(percentiles)
            .build();
    }
}
