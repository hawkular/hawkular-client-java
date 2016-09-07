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
package org.hawkular.client.test.metrics;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.DataPointGenerator;
import org.hawkular.client.test.utils.MetricGenerator;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.client.test.utils.TagGenerator;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.param.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StringTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(StringTest.class);

    private final DataPointGenerator<String> dataPointGenerator = new DataPointGenerator<String>() {
        @Override
        protected String getValue(Random random) {
            return RandomStringGenerator.getRandomId();
        }
    };

    private Integer originalStringCount = 0;
    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);

    @Test
    public void findMetricsDefinitionsCount() {
        ClientResponse<List<Metric>> response = client()
            .metrics()
            .string()
            .findMetricsDefinitions(tags);

        if (response.getStatusCode() == ResponseCodes.NO_CONTENT_204.value()) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(response.isSuccess());
            Assert.assertNotNull(response.getEntity());
            Assert.assertTrue(response.getEntity().size() > 0);

            originalStringCount = response.getEntity().size();
        }
    }

    @Test(dependsOnMethods = "findMetricsDefinitionsCount")
    public void createStringMetric() {
        LOG.info("Testing with MetricName == {}", metricName);

        Metric<String> metric = MetricGenerator.generate(MetricType.STRING, tags.getTags(), metricName, dataPointGenerator.generator(10, tags.getTags()));

        ClientResponse<Empty> response = client()
            .metrics()
            .string()
            .createStringMetric(true, metric);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createStringMetric")
    public void findMetricsDefinitions() {
        ClientResponse<List<Metric>> response = client()
            .metrics()
            .string()
            .findMetricsDefinitions(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertTrue(originalStringCount == (response.getEntity().size() - 1));

        Optional<Metric> value = response.getEntity().stream()
            .filter(a -> a.getTags().get(TagGenerator.POD_NAMESPACE).equals(podNamespace))
            .findFirst();

        Assert.assertTrue(value.isPresent());
    }

    @Test(dependsOnMethods = "findMetricsDefinitions")
    public void createMultipleStringMetric() {
        Metric<String> metric = MetricGenerator.generate(MetricType.STRING, tags.getTags(), metricName, dataPointGenerator.generator(10, tags.getTags()));

        ClientResponse<Empty> response = client()
            .metrics()
            .string()
            .createStringMetric(Arrays.asList(metric));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findMetricsDefinitions")
    public void findMetricTags() {
        ClientResponse<Map<String, List<String>>> response = client()
            .metrics()
            .string()
            .findMetricTags(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertNotNull(response.getEntity().get(TagGenerator.POD_NAMESPACE));
        Assert.assertNotNull(response.getEntity().get(TagGenerator.POD_NAME));
    }

    @Test(dependsOnMethods = "createMultipleStringMetric")
    public void getMetricDefinitions() {
        ClientResponse<Metric> response = client()
            .metrics()
            .string()
            .getMetricDefinitions(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertNotNull(response.getEntity().getId());
        Assert.assertNotNull(response.getEntity().getTags());
    }

    /**
     * TODO: On hawkular server, throws a cast exception on first send, sent email to dev-list
     */
    @Test(dependsOnMethods = "createMultipleStringMetric")
    public void createMetricDefinitionsData() {
        ClientResponse<Empty> response = client()
            .metrics()
            .string()
            .createMetricDefinitionsData(metricName, dataPointGenerator.generator(1, tags.getTags()));

        Assert.assertTrue(response.isSuccess());
    }

    /**
     * TODO: Think this fails due to "createMetricDefinitionsData" failing on the CastException
     * ...as always get back 204 - no content
     */
    @Test(dependsOnMethods = "createMetricDefinitionsData", enabled = false)
    public void getMetricDefinitionsData() {
        ClientResponse<List<DataPoint>> response = client()
            .metrics()
            .string()
            .getMetricDefinitionsData(metricName, null, null, false, 1, Order.ASC);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "createMetricDefinitionsData")
    public void findMetricDefinitionsTags() {
        ClientResponse<Map<String, String>> response = client()
            .metrics()
            .string()
            .findMetricDefinitionsTags(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), tags.getTags());
    }

    @Test(dependsOnMethods = "findMetricDefinitionsTags")
    public void updateMetricDefinitionsTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .string()
            .updateMetricDefinitionsTags(metricName, TagGenerator.generateMap("updatedNamespace", "updatedPod"));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateMetricDefinitionsTags")
    public void deleteMetricDefinitionsTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .string()
            .deleteMetricDefinitionsTags(metricName, TagGenerator.generate("updatedNamespace", "updatedPod"));

        Assert.assertTrue(response.isSuccess());
    }
}
