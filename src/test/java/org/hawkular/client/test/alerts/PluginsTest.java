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
package org.hawkular.client.test.alerts;

import java.util.Arrays;
import java.util.List;

import org.hawkular.client.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@Test(groups = { "alerts" })
public class PluginsTest extends BaseTest {
    public PluginsTest() throws Exception {
        super();
    }

    public static final String EMAIL_PLUGIN_NAME = "email";

    @Test(groups={"known-failure"})
    public void findPlugins() {
        ClientResponse<String[]> response = client().alerts().findActionPlugins();
        Reporter.log("Alert available Plugins Result: " + response.toString(), true);
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getEntity().length > 0);
    }

    @Test(groups={"known-failure"})
    public void findEmailPlugin() {
        ClientResponse<String[]> response = client().alerts().getActionPlugin(EMAIL_PLUGIN_NAME);
        Reporter.log("Alert Email Plugin variables Result: " + response.toString(), true);
        Assert.assertTrue(response.isSuccess());
        List<String> result = Arrays.asList(response.getEntity());
        Assert.assertTrue(result.contains("cc"));
        Assert.assertTrue(result.contains("from"));
        Assert.assertTrue(result.contains("from-name"));
        Assert.assertTrue(result.contains("template.hawkular.url"));
        Assert.assertTrue(result.contains("template.html"));
        Assert.assertTrue(result.contains("template.plain"));
        Assert.assertTrue(result.contains("to"));
    }

}
