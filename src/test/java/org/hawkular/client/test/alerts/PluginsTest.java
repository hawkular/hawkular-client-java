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

import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.test.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = {"alerts", "alerts-plugins"})
public class PluginsTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(PluginsTest.class);

    public static final String EMAIL_PLUGIN_NAME = "email";
    public static final String EMAIL_PLUGIN_PROPERTY_TO = "to";
    public static final String EMAIL_PLUGIN_PROPERTY_FROM = "from";
    private static final List<String> emailProperties =
        Arrays.asList("cc", "from", "from-name", "template.hawkular.url", "template.html", "template.plain", "to");

    @Test
    public void findActionPlugins() {
        LOG.info("Testing with Plugin == {}", EMAIL_PLUGIN_NAME);

        ClientResponse<List<String>> response = client()
            .alerts()
            .plugins()
            .findActionPlugins();

        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertTrue(response.getEntity().contains(EMAIL_PLUGIN_NAME));
    }

    @Test(dependsOnMethods = "findActionPlugins")
    public void getActionPlugin() {
        ClientResponse<List<String>> response = client()
            .alerts()
            .plugins()
            .getActionPlugin(EMAIL_PLUGIN_NAME);

        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getEntity().size() > 0);
        Assert.assertEquals(response.getEntity(), emailProperties);
    }
}
