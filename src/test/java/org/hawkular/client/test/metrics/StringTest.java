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
import java.util.concurrent.TimeUnit;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.client.test.BTG;
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

    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);

    @Test
    public void createStringMetric() {
        LOG.info("Testing with MetricName == {}", metricName);

        Metric<String> metric = MetricGenerator.generate(MetricType.STRING, tags.getTags(), metricName, dataPointGenerator.generator(10));

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

        Optional<Metric> value = response.getEntity().stream()
            .filter(a -> a.getTags().get(TagGenerator.POD_NAMESPACE).equals(podNamespace))
            .findFirst();

        Assert.assertTrue(value.isPresent());
    }

    @Test(dependsOnMethods = "findMetricsDefinitions")
    public void createMultipleStringMetric() {
        Metric<String> metric = MetricGenerator.generate(MetricType.STRING, tags.getTags(), metricName, dataPointGenerator.generator(10));

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

    @Test(dependsOnMethods = "createMultipleStringMetric")
    public void createMetricDefinitionsData() {
        BTG ts = new BTG();
        DataPoint<String> point = new DataPoint<String>(ts.nextMilli(), RandomStringGenerator.getRandomId(), tags.getTags());

        ClientResponse<Empty> response = client()
            .metrics()
            .string()
            .createMetricDefinitionsData(metricName, Arrays.asList(point));

        //TODO: On hawkular server, throws a cast exception on first send, sent email to dev-list
        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateMetricDefinitionsTags", enabled = false)
    public void getMetricDefinitionsData() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        ClientResponse<List<DataPoint>> response = client()
            .metrics()
            .string()
            .getMetricDefinitionsData(metricName, start, end, false, 1, Order.ASC);

        //TODO: Not sure what populates this... as always get back 204 - no content
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
        Assert.assertEquals(tags.getTags(), response.getEntity());
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
