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
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.core.jaxrs.Order;
import org.hawkular.client.test.BTG;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.DataPointGenerator;
import org.hawkular.client.test.utils.MetricGenerator;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.client.test.utils.TagGenerator;
import org.hawkular.metrics.model.AvailabilityBucketPoint;
import org.hawkular.metrics.model.AvailabilityType;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Tags;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AvailabilityTest extends BaseTest {

    private final DataPointGenerator<AvailabilityType> dataPointGenerator = new DataPointGenerator<AvailabilityType>() {
        private List<AvailabilityType> values = Arrays.asList(AvailabilityType.DOWN, AvailabilityType.UP, AvailabilityType.UNKNOWN);
        private AtomicInteger counter = new AtomicInteger(0);

        @Override
        protected AvailabilityType getValue(Random random) {
            if (counter.get() >= values.size()) {
                counter.set(0);
            }

            int index = counter.getAndIncrement();
            return values.get(index);
        }
    };

    private Integer originalCounterCount = 0;
    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);
    private final Metric<AvailabilityType> expectedMetric = MetricGenerator.generate(MetricType.AVAILABILITY, tags.getTags(), metricName, dataPointGenerator.generator(3));


    public void findAvailabilityMetricsCount() {

    }

    @Test
    public void createAvailabilityMetric() {
        ClientResponse<Empty> response = client()
            .metrics()
            .availability()
            .createAvailabilityMetric(true, expectedMetric);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createAvailabilityMetric")
    public void findAvailabilityMetrics() {
        ClientResponse<List<Metric<AvailabilityType>>> response = client()
            .metrics()
            .availability()
            .findAvailabilityMetrics(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "findAvailabilityMetrics")
    public void addAvailabilityData() {
        Metric<AvailabilityType> metric = MetricGenerator.generate(MetricType.AVAILABILITY, tags.getTags(), metricName + "1", dataPointGenerator.generator(3));

        ClientResponse<Empty> response = client()
            .metrics()
            .availability()
            .addAvailabilityData(Arrays.asList(metric));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "addAvailabilityData")
    public void getGaugeTags() {
        ClientResponse<Map<String, List<String>>> response = client()
            .metrics()
            .availability()
            .getGaugeTags(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addAvailabilityData")
    public void getAvailabilityMetric() {
        ClientResponse<Metric<AvailabilityType>> response = client()
            .metrics()
            .availability()
            .getAvailabilityMetric(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addAvailabilityData", enabled = false)
    public void findAvailabilityData() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        ClientResponse<List<DataPoint<AvailabilityType>>> response = client()
            .metrics()
            .availability()
            .findAvailabilityData(metricName, start, end, true, 1, Order.ASC);

        //TODO: Not sure what populates this... as always get back 204 - no content
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getAvailabilityMetric")
    public void addAvailabilityDataForMetric() {
        ClientResponse<Empty> response = client()
            .metrics()
            .availability()
            .addAvailabilityDataForMetric(metricName, dataPointGenerator.generator(3));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "addAvailabilityDataForMetric")
    public void findAvailabilityStats() {
        BTG ts = new BTG();
        Long start = ts.nextMilli() - TimeUnit.SECONDS.toMillis(10L);
        Long end = ts.nextMilli() + TimeUnit.SECONDS.toMillis(10L);

        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<AvailabilityBucketPoint>> response = client()
            .metrics()
            .availability()
            .findAvailabilityStats(metricName, start, end, null, duration);

        //todo: wrong type
        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "addAvailabilityDataForMetric")
    public void getAvailabilityMetricTags() {
        ClientResponse<Map<String, String>> response = client()
            .metrics()
            .availability()
            .getAvailabilityMetricTags(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getAvailabilityMetricTags")
    public void updateAvailabilityMetricTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .availability()
            .updateAvailabilityMetricTags(metricName, TagGenerator.generateMap("updatedNamespace", "updatedPod"));

        //todo: race condition
        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateAvailabilityMetricTags")
    public void deleteAvailabilityMetricTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .availability()
            .deleteAvailabilityMetricTags(metricName, TagGenerator.generate("updatedNamespace", "updatedPod"));

        Assert.assertTrue(response.isSuccess());
    }
}
