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
import org.hawkular.client.core.jaxrs.ResponseCodes;
import org.hawkular.client.core.jaxrs.param.TagsConverter;
import org.hawkular.client.metrics.model.Order;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.DataPointGenerator;
import org.hawkular.client.test.utils.MetricGenerator;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.hawkular.client.test.utils.TagGenerator;
import org.hawkular.metrics.model.DataPoint;
import org.hawkular.metrics.model.Metric;
import org.hawkular.metrics.model.MetricType;
import org.hawkular.metrics.model.NumericBucketPoint;
import org.hawkular.metrics.model.Percentile;
import org.hawkular.metrics.model.TaggedBucketPoint;
import org.hawkular.metrics.model.param.Duration;
import org.hawkular.metrics.model.param.Percentiles;
import org.hawkular.metrics.model.param.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GaugeTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(GaugeTest.class);

    private final DataPointGenerator<Double> dataPointGenerator = new DataPointGenerator<Double>() {
        @Override
        protected Double getValue(Random random) {
            return random.nextDouble();
        }
    };

    private Integer originalGaugeCount = 0;
    private final String metricName = RandomStringGenerator.getRandomId();
    private final String podNamespace = RandomStringGenerator.getRandomId();
    private final String podName = RandomStringGenerator.getRandomId();
    private final Tags tags = TagGenerator.generate(podNamespace, podName);
    private final List<DataPoint<Double>> expectedDataPoints = dataPointGenerator.generator(10, tags.getTags());
    private final Metric<Double> expectedMetric = MetricGenerator.generate(MetricType.GAUGE, tags.getTags(), metricName, expectedDataPoints);

    @Test
    public void findGaugeMetricsCount() {
        ClientResponse<List<Metric<Double>>> response = client()
            .metrics()
            .gauge()
            .findGaugeMetrics(tags);

        if (response.getStatusCode() == ResponseCodes.NO_CONTENT_204.value()) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(response.isSuccess());
            Assert.assertNotNull(response.getEntity());
            Assert.assertTrue(response.getEntity().size() > 0);

            originalGaugeCount = response.getEntity().size();
        }
    }

    @Test(dependsOnMethods = "findGaugeMetricsCount")
    public void createGaugeMetric() {
        LOG.info("Testing with MetricName == {}", metricName);

        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .createGaugeMetric(true, expectedMetric);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createGaugeMetric")
    public void findGaugeMetrics() {
        ClientResponse<List<Metric<Double>>> response = client()
            .metrics()
            .gauge()
            .findGaugeMetrics(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertTrue(originalGaugeCount == (response.getEntity().size() - 1));

        Optional<Metric<Double>> value = response.getEntity().stream()
            .filter(a -> a.equals(expectedMetric))
            .findFirst();

        Assert.assertTrue(value.isPresent());
    }

    @Test(dependsOnMethods = "createGaugeMetric")
    public void addGaugeData() {
        Metric<Double> metric = MetricGenerator.generate(MetricType.GAUGE, tags.getTags(), metricName + "1", dataPointGenerator.generator(10, tags.getTags()));

        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .addGaugeData(Arrays.asList(metric));

        Assert.assertTrue(response.isSuccess());
    }

    /**
     * TODO: Buckets are always empty
     */
    @Test(dependsOnMethods = "addGaugeData")
    public void findGaugeRateStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .gauge()
            .findGaugeRateStats(
                null, null, 1, duration, new Percentiles(Arrays.asList(percentile)), null, Arrays.asList(metricName), true);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);

        NumericBucketPoint bucket = response.getEntity().get(0);
        //Assert.assertFalse(bucket.isEmpty());
    }

    /**
     * TODO: Buckets are always empty
     */
    @Test(dependsOnMethods = "addGaugeData")
    public void findGaugeStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .gauge()
            .findGaugeStats(
                null, null, null, duration, new Percentiles(Arrays.asList(percentile)), null, Arrays.asList(metricName), true);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);

        NumericBucketPoint bucket = response.getEntity().get(0);
        //Assert.assertFalse(bucket.isEmpty());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeMetricTagValues() {
        ClientResponse<Map<String, List<String>>> response = client()
            .metrics()
            .gauge()
            .getGaugeMetricTagValues(tags);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertEquals(TagGenerator.convert(tags.getTags()), response.getEntity());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeMetric() {
        ClientResponse<Metric<Double>> response = client()
            .metrics()
            .gauge()
            .getGaugeMetric(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(expectedMetric, response.getEntity());
    }

    /**
     * TODO: Not sure what populates this... as always get back 204 - no content
     */
    @Test(dependsOnMethods = "addGaugeData", enabled = false)
    public void findGaugeDataPeriods() {
        ClientResponse<List<Long[]>> response = client()
            .metrics()
            .gauge()
            .findGaugeDataPeriods(metricName, null, null, 1.0, "eq");

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    /**
     * TODO: Not sure what populates this... as always get back 204 - no content
     */
    @Test(dependsOnMethods = "addGaugeData", enabled = false)
    public void getGaugeRate() {
        ClientResponse<List<DataPoint<Double>>> response = client()
            .metrics()
            .gauge()
            .getGaugeRate(metricName, null, null, 1, Order.ASC);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
    }

    /**
     * TODO: Buckets are always empty
     */
    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeRateStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<NumericBucketPoint>> response = client()
            .metrics()
            .gauge()
            .getGaugeRateStats(metricName, null, null, null, duration, new Percentiles(Arrays.asList(percentile)));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);

        NumericBucketPoint bucket = response.getEntity().get(0);
        //Assert.assertFalse(bucket.isEmpty());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void addGaugeDataForMetric() {
        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .addGaugeDataForMetric(metricName, expectedDataPoints);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "addGaugeDataForMetric")
    public void findGaugeDataWithId() {
        ClientResponse<List<DataPoint<Double>>> response = client()
            .metrics()
            .gauge()
            .findGaugeDataWithId(metricName, null, null, true, Integer.MAX_VALUE, Order.ASC);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertEquals(expectedDataPoints.size(), response.getEntity().size());
        Assert.assertEquals(expectedDataPoints, response.getEntity());
    }

    /**
     * TODO: Not sure what populates this... as always get back 204 - no content
     */
    @Test(dependsOnMethods = "addGaugeData", enabled = false)
    public void getGaugeStats() {
        Percentile percentile = new Percentile("90.0");
        Duration duration = new Duration(1, TimeUnit.DAYS);

        ClientResponse<List<DataPoint<Double>>> response = client()
            .metrics()
            .gauge()
            .getGaugeStats(
                metricName, null, null, true, null, duration, new Percentiles(Arrays.asList(percentile)));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }
    
    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeStatsTags() {
        Percentile percentile = new Percentile("90.0");

        ClientResponse<Map<String, TaggedBucketPoint>> response = client()
            .metrics()
            .gauge()
            .getGaugeStatsTags(metricName, tags, null, null, new Percentiles(Arrays.asList(percentile)));

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);

        String tagsKey = new TagsConverter().toString(tags);
        Assert.assertTrue(response.getEntity().containsKey(tagsKey));

        TaggedBucketPoint bucket = response.getEntity().get(tagsKey);
        Assert.assertNotNull(bucket);
        Assert.assertNotNull(bucket.getTags());
        Assert.assertEquals(tags.getTags(), bucket.getTags());
    }

    @Test(dependsOnMethods = "addGaugeData")
    public void getGaugeMetricTags() {
        ClientResponse<Map<String, String>> response = client()
            .metrics()
            .gauge()
            .getGaugeMetricTags(metricName);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertEquals(tags.getTags(), response.getEntity());
    }

    @Test(dependsOnMethods = "getGaugeMetricTags")
    public void updateGaugeMetricTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .updateGaugeMetricTags(metricName, TagGenerator.generateMap(RandomStringGenerator.getRandomId(), RandomStringGenerator.getRandomId()));

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "updateGaugeMetricTags")
    public void deleteGaugeMetricTags() {
        ClientResponse<Empty> response = client()
            .metrics()
            .gauge()
            .deleteGaugeMetricTags(metricName, tags);

        Assert.assertTrue(response.isSuccess());
    }
}
