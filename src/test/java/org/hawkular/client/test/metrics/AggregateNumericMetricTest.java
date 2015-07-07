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
package org.hawkular.client.test.metrics;

import org.hawkular.client.test.BaseTest;
//import org.hawkular.metrics.core.api.NumericData;
import org.hawkular.metrics.core.api.Tenant;

public class AggregateNumericMetricTest extends BaseTest {
    public AggregateNumericMetricTest() throws Exception {
        super();
        // TODO Auto-generated constructor stub
    }

    private final Tenant tenant = randomTenant();
//    private final List<NumericData> expectedRawData = GaugeDataGenerator.gen(4);
//
//    public AggregateNumericMetricTest() throws Exception {
//        super();
//    }
//
//    @Test(priority=10)
//    public void addDataTest() throws Exception {
//        client().metrics().addNumericMetricData(tenant.getId(), "cpu", expectedRawData);
//    }
//
//    @Test (dependsOnMethods="addDataTest")
//    public void getDataBucketsTest1() throws Exception {
//        int buckets = 1;
//        List<AggregateNumericData> actual = client().metrics().getAggregateNumericDataByBuckets(
//                    tenant.getId(),
//                    "cpu",
//                    expectedRawData.get(0).getTimestamp(),
//                    System.currentTimeMillis(),
//                    buckets);
//
//
//        List<AggregateNumericData> expectedAggregate = AggregateNumericData.from(expectedRawData, buckets);
//
//        Reporter.log("Original size: " + actual.size(), true);
//        Reporter.log("Original raw: " + expectedRawData, true);
//
//        Reporter.log("Expected Agg.: " + expectedAggregate, true);
//        Reporter.log("Actual Agg.: " + actual, true);
//
//        Assert.assertEquals(actual, expectedAggregate);
//    }

}
