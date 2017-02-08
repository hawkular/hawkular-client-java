/*
 * Copyright 2015-2017 Red Hat, Inc. and/or its affiliates
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

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.hawkular.alerts.api.model.event.Event;
import org.hawkular.client.core.ClientResponse;
import org.hawkular.client.core.jaxrs.Empty;
import org.hawkular.client.test.BaseTest;
import org.hawkular.client.test.utils.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = { "alerts" })
public class AlertsEventsTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(AlertsEventsTest.class);

    private final String eventId = "New-Event-" + RandomStringGenerator.getRandomId();
    private final String tags = String.format("%s|%s", RandomStringGenerator.getRandomId(), RandomStringGenerator.getRandomId());

    @Test
    public void createEvent() {
        LOG.info("Testing with Event == {}", eventId);

        Event event = new Event(BaseTest.HEADER_TENANT, eventId, "testing", "test event");

        ClientResponse<Event> response = client()
            .alerts()
            .events()
            .createEvent(event);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
        Assert.assertEquals(response.getEntity(), event);
    }

    @Test(dependsOnMethods = "createEvent")
    public void findEvents() {
        long now = Instant.now().toEpochMilli();
        long start = now - Duration.ofHours(1).toMillis();
        long end = now;

        ClientResponse<List<Event>> response = client()
            .alerts()
            .events()
            .findEvents(start, end, PluginsTest.EMAIL_PLUGIN_NAME, null, null, null, false);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "findEvents")
    public void getEvent() {
        ClientResponse<Event> response = client()
            .alerts()
            .events()
            .getEvent(eventId, false);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getEntity());
    }

    @Test(dependsOnMethods = "getEvent")
    public void createTags() {
        ClientResponse<Empty> response = client()
            .alerts()
            .events()
            .createTags(eventId, tags);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "createTags")
    public void deleteTags() {
        ClientResponse<Empty> response = client()
            .alerts()
            .events()
            .deleteTags(eventId, tags);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "deleteTags")
    public void deleteEvents() {
        long now = Instant.now().toEpochMilli();
        long start = now - Duration.ofHours(1).toMillis();
        long end = now;

        ClientResponse<Integer> response = client()
            .alerts()
            .events()
            .deleteEvents(start, end, PluginsTest.EMAIL_PLUGIN_NAME, null, null, null);

        Assert.assertTrue(response.isSuccess());
    }

    @Test(dependsOnMethods = "deleteTags")
    public void deleteEvent() {
        ClientResponse<Empty> response = client()
            .alerts()
            .events()
            .deleteEvent(eventId);

        Assert.assertTrue(response.isSuccess());
    }
}
