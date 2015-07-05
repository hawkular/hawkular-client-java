package org.hawkular.client.test;

import java.util.List;

import org.hawkular.client.metrics.model.CounterDataPoint;
import org.hawkular.client.metrics.model.MetricDefinition;
import org.hawkular.client.test.utils.CounterDataGenerator;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;

public class CounterMetricTest extends BaseTest {

    private final MetricDefinition expectedDefinition = CounterDataGenerator.genDef();
    private final List<CounterDataPoint> expectedData = CounterDataGenerator.gen(10);

    public CounterMetricTest() throws Exception {
        super();
    }

    @Test
    public void createDefinition() throws Exception {
        Reporter.log("Creating: " + expectedDefinition.toString(), true);
        client().metrics().createCounter(expectedDefinition.getTenantId(), expectedDefinition);
    }

    @Test (dependsOnMethods="createDefinition")
    public void getDefinition() throws Exception {
        MetricDefinition actual = client().metrics().getCounter(expectedDefinition.getTenantId(), expectedDefinition.getId());
        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(actual, expectedDefinition);
    }

    @Test(dependsOnMethods="getDefinition")
    public void addData() throws Exception {
        Reporter.log("Adding: " + expectedData, true);
        client().metrics().addCounterData(expectedDefinition.getTenantId(),expectedDefinition.getId(), expectedData);
    }

    @Test(dependsOnMethods="addData")
    public void getData() throws Exception {
        List<CounterDataPoint> actual = client().metrics().getCounterData(expectedDefinition.getTenantId(), expectedDefinition.getId());
        Reporter.log("Got: " + actual.toString(), true);
        Assert.assertEquals(Lists.reverse(actual), expectedData);
    }
}
