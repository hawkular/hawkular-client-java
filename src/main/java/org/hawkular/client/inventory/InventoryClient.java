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
package org.hawkular.client.inventory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hawkular.client.ClientResponse;
import org.hawkular.client.inventory.json.Endpoints;
import org.hawkular.client.inventory.json.IdJSON;
import org.hawkular.client.inventory.json.StringValue;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricDataType;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.MetricUnit;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.api.model.Tenant.Update;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface InventoryClient {

    public enum RESPONSE_CODE {
        GET_SUCCESS(200),
        CREATE_SUCCESS(201),
        ADD_SUCCESS(201),
        REGISTER_SUCCESS(201),
        UPDATE_SUCCESS(204),
        DELETE_SUCCESS(204),
        REMOVE_SUCCESS(204);

        private int code;

        private RESPONSE_CODE(int code) {
            this.code = code;
        }

        public int value() {
            return this.code;
        }
    }

    //PingHandler/Ping
    ClientResponse<Endpoints> pingHello();

    ClientResponse<StringValue> pingTime();

    //Tenant
    ClientResponse<Tenant> getTenant();

    ClientResponse<String> updateTenant(Map<String, Object> properties);

    ClientResponse<String> updateTenant(Update updateTenant);

    ClientResponse<String> deleteTenant();

    //Environment
    ClientResponse<List<Environment>> getEnvironments();

    ClientResponse<Environment> getEnvironment(String environmentId);

    ClientResponse<Environment> getEnvironment(Environment environment);

    ClientResponse<String> createEnvironment(String environmentId, Map<String, Object> properties);

    ClientResponse<String> createEnvironment(String environmentId);

    ClientResponse<String> createEnvironment(Environment.Blueprint environmentBlueprint);

    ClientResponse<String> createEnvironment(Environment environment);

    ClientResponse<String> updateEnvironment(String environmentId, Map<String, Object> properties);

    ClientResponse<String> updateEnvironment(String environmentId, Environment.Update update);

    ClientResponse<String> updateEnvironment(Environment environment);

    ClientResponse<String> deleteEnvironment(String environmentId);

    ClientResponse<String> deleteEnvironment(Environment environment);

    //MetricType
    ClientResponse<List<MetricType>> getMetricTypes();

    ClientResponse<MetricType> getMetricType(String metricTypeId);

    ClientResponse<MetricType> getMetricType(MetricType metricType);

    ClientResponse<String> createMetricType(String metricTypeId, MetricUnit unit, MetricDataType metricDataType);

    ClientResponse<String> createMetricType(MetricType metricType);

    ClientResponse<String> createMetricType(MetricType.Blueprint metricType);

    ClientResponse<String> updateMetricType(String metricTypeId,
            MetricType metricType);

    ClientResponse<String> updateMetricType(String metricTypeId,
            MetricType.Update metricUpdate);

    ClientResponse<String> deleteMetricType(String metricTypeId);

    ClientResponse<String> deleteMetricType(MetricType metricType);

    //Metrics
    ClientResponse<String> createMetric(Metric metric);

    ClientResponse<String> createMetric(String environmentId, Metric.Blueprint metric);

    ClientResponse<String> createMetric(String environmentId, String feedId, Metric.Blueprint metric);

    ClientResponse<Metric> getMetric(String environmentId, String metricId);

    ClientResponse<Metric> getMetric(String environmentId, String metricId, String feedId);

    ClientResponse<Metric> getMetric(Metric metric);

    ClientResponse<List<Metric>> getMetrics(String environmentId);

    ClientResponse<List<Metric>> getMetrics(String environmentId, String feedId);

    ClientResponse<String> updateMetric(Metric metric);

    ClientResponse<String> updateMetric(String environmentId, String metricId, Metric.Update metricUpdate);

    ClientResponse<String> updateMetric(String environmentId, String feedId, String metricId,
            Metric.Update metricUpdate);

    ClientResponse<String> deleteMetric(Metric metric);

    ClientResponse<String> deleteMetric(String environmentId,
            String metricId);

    ClientResponse<String> deleteMetric(String environmentId,
            String feedId,
            String metricId);

    //ResourceType
    ClientResponse<List<ResourceType>> getResourceTypes();

    ClientResponse<ResourceType> getResourceType(String resourceTypeId);

    ClientResponse<ResourceType> getResourceType(ResourceType resourceType);

    ClientResponse<List<MetricType>> getMetricTypes(String resourceTypeId);

    ClientResponse<List<Resource>> getResources(String resourceTypeId);

    ClientResponse<String> createResourceType(ResourceType resourceType);

    ClientResponse<String> createResourceType(ResourceType.Blueprint resourceType);

    ClientResponse<String> createResourceType(String resourceId);

    ClientResponse<String> updateResourceType(String resourceTypeId, ResourceType.Update resourceTypeUpdate);

    ClientResponse<String> updateResourceType(ResourceType resourceType);

    ClientResponse<String> deleteResourceType(String resourceTypeId);

    ClientResponse<String> deleteResourceType(ResourceType resourceType);

    ClientResponse<String> addMetricType(String resourceTypeId, IdJSON metricTypeId);

    ClientResponse<String> removeMetricType(String resourceTypeId, String metricTypeId);

    //Resource
    ClientResponse<String> addResource(Resource resource);

    ClientResponse<String> addResource(String environmentId, Resource.Blueprint resource);

    ClientResponse<String> addResource(String environmentId, String feedId, Resource.Blueprint resource);

    ClientResponse<List<Resource>> getResourcesByType(String environmentId, String typeId, String typeVersion,
            boolean feedless);

    ClientResponse<List<Resource>> getResourcesByType(String environmentId, String typeId, String typeVersion);

    ClientResponse<List<Resource>> getResourcesByType(String environmentId, String feedId, String typeId,
            String typeVersion);

    ClientResponse<Resource> getResource(String environmentId, String feedId, String resourceId);

    ClientResponse<Resource> getResource(String environmentId, String resourceId);

    ClientResponse<Resource> getResource(Resource resource);

    ClientResponse<String> updateResource(String environmentId, String resourceId, Resource.Update update);

    ClientResponse<String> updateResource(String environmentId, String feedId, String resourceId,
            Resource.Update update);

    ClientResponse<String> updateResource(Resource resource);

    ClientResponse<String> deleteResource(String environmentId, String feedId, String resourceId);

    ClientResponse<String> deleteResource(String environmentId, String resourceId);

    ClientResponse<String> deleteResource(Resource resource);

    ClientResponse<String> addMetricToResource(String environmentId, String feedId, String resourceId,
            Collection<String> metricIds);

    ClientResponse<String> addMetricToResource(String environmentId, String resourceId, Collection<String> metricIds);

    ClientResponse<List<Metric>> listMetricsOfResource(String environmentID, String feedId, String resourceId);

    ClientResponse<List<Metric>> listMetricsOfResource(String environmentID, String resourceId);

    ClientResponse<Metric> getMetricOfResource(String environmentId, String feedId, String resourceId, String metricId);

    ClientResponse<Metric> getMetricOfResource(String environmentId, String resourceId, String metricId);

    //Feed
    ClientResponse<String> registerFeed(String environmentId, Feed.Blueprint feed);

    ClientResponse<String> registerFeed(Feed feed);

    ClientResponse<List<Feed>> getAllFeeds(String environmentId);

    ClientResponse<Feed> getFeed(String environmentId, String feedId);

    ClientResponse<Feed> getFeed(Feed feed);

    ClientResponse<String> updateFeed(String environmentId, String feedId, Feed.Update update);

    ClientResponse<String> updateFeed(Feed feed);

    ClientResponse<String> deleteFeed(String environmentId, String feedId);

    ClientResponse<String> deleteFeed(Feed feed);
}
