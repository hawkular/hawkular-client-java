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
package org.hawkular.client.test;

import java.util.List;
import java.util.Map;

import org.hawkular.metrics.core.api.MetricId;
import org.hawkular.metrics.core.api.NumericData;
import org.hawkular.metrics.core.api.NumericMetric;
import org.hawkular.metrics.core.api.Tenant;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class NumericMetricTest extends BaseTest {
    private final Tenant testTenant = randomTenant();
    private final MetricId metricId = new MetricId("metric22");
    private final NumericMetric expectedMetric = new NumericMetric(testTenant.getId(), metricId);
    private final Map<String, String> expectedTags = ImmutableMap.of("tag1", "one_value", "unit", "KB/second");

    private final List<NumericData> expectedData1 = ImmutableList.of(
                                                        new NumericData(10L, 2.80d));

    public NumericMetricTest() throws Exception {
        super();
    }

    @Test
    public void createNumericMetric() throws Exception {
        expectedMetric.setTags(expectedTags);
        Reporter.log(expectedMetric.toString());
        client().metrics().createNumericMetric(expectedMetric);
    }

    @Test (dependsOnMethods="createNumericMetric")
    public void readNumericMetricTags() throws Exception {
        NumericMetric actualMetric = client().metrics().findNumericMetric(testTenant.getId(), metricId.getName());
        Reporter.log(actualMetric.toString());

        Assert.assertEquals(actualMetric, expectedMetric);

        Assert.assertEquals(actualMetric.getTags(), expectedMetric.getTags());

    }

    @Test (dependsOnMethods="createNumericMetric")
    public void addData() {
        client().metrics().addNumericMetricData(testTenant.getId(), metricId.getName(), expectedData1);
    }

    @Test (dependsOnMethods="addData")
    public void getNumericDataTest() throws Exception {
        List<NumericData> actual = client().metrics().getNumericMetricData(testTenant.getId(), metricId.getName());
        //Assert.assertEquals(actual, expectedMetric.getData());
    }

}
