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
public class PluginsTest extends BaseTest {
    public PluginsTest() throws Exception {
        super();
    }

    public static final String EMAIL_PLUGIN_NAME = "email";

    @Test
    public void findPlugins() {
        ClientResponse<String[]> response = client().alerts().findActionPlugins();
        Reporter.log("Alert available Plugins Result: " + response.toString(), true);
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getEntity().length > 0);
    }

    @Test
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
