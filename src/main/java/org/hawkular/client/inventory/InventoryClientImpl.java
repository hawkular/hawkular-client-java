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

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.hawkular.client.BaseClient;
import org.hawkular.client.ClientResponse;
import org.hawkular.client.RestFactory;
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
public class InventoryClientImpl extends BaseClient<InventoryRestApi>
        implements InventoryClient {
    private String tenantId = null;

    public InventoryClientImpl(URI endpointUri, String username,
            String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<InventoryRestApi>(InventoryRestApi.class));
    }

    public String getTenantId() {
        if (tenantId == null) {
            tenantId = getTenant().getEntity().getId();
        }
        return tenantId;
    }

    @Override
    public ClientResponse<StringValue> pingTime() {
        return new ClientResponse<StringValue>(StringValue.class,
                restApi().pingTime(),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<Endpoints> pingHello() {
        return new ClientResponse<Endpoints>(Endpoints.class,
                restApi().pingHello(),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<Tenant> getTenant() {
        return new ClientResponse<Tenant>(Tenant.class, restApi().getTenant(), RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateTenant(Map<String, Object> properties) {
        return updateTenant(new Update(properties));
    }

    @Override
    public ClientResponse<String> updateTenant(Update updateTenant) {
        return new ClientResponse<String>(String.class, restApi().getTenant(), RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteTenant() {
        return new ClientResponse<String>(String.class, restApi().deleteTenant(), RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    //Enviroment

    @Override
    public ClientResponse<List<Environment>> getEnvironments() {
        return new ClientResponse<List<Environment>>(Environment.class, restApi().getEnvironments(),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<Environment> getEnvironment(String environmentId) {
        return new ClientResponse<Environment>(Environment.class, restApi().getEnvironment(environmentId),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId());
    }

    @Override
    public ClientResponse<Environment> getEnvironment(Environment environment) {
        return getEnvironment(environment.getId());
    }

    @Override
    public ClientResponse<String> createEnvironment(Environment.Blueprint environmentBlueprint) {
        return new ClientResponse<String>(String.class, restApi().createEnvironment(environmentBlueprint),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createEnvironment(String environmentId) {
        return createEnvironment(new Environment.Blueprint(environmentId));
    }

    @Override
    public ClientResponse<String> createEnvironment(String environmentId, Map<String, Object> properties) {
        return createEnvironment(new Environment.Blueprint(environmentId, properties));
    }

    @Override
    public ClientResponse<String> createEnvironment(Environment environment) {
        return createEnvironment(new Environment.Blueprint(environment.getId(), environment.getProperties()));
    }

    @Override
    public ClientResponse<String> updateEnvironment(String environmentId, Environment.Update update) {
        return new ClientResponse<String>(String.class, restApi().updateEnvironment(environmentId, update),
                RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateEnvironment(String environmentId, Map<String, Object> properties) {
        return updateEnvironment(environmentId, new Environment.Update(properties));
    }

    @Override
    public ClientResponse<String> updateEnvironment(Environment environment) {
        return updateEnvironment(environment.getId(), new Environment.Update(environment.getProperties()));
    }

    @Override
    public ClientResponse<String> deleteEnvironment(String environmentId) {
        return new ClientResponse<String>(String.class, restApi().deleteEnvironment(environmentId),
                RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteEnvironment(Environment environment) {
        return this.deleteEnvironment(environment.getId());
    }

    //MetricType

    @Override
    public ClientResponse<List<MetricType>> getMetricTypes() {
        return new ClientResponse<List<MetricType>>(MetricType.class, restApi().getMetricTypes(),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<MetricType> getMetricType(String metricTypeId) {
        return new ClientResponse<MetricType>(MetricType.class, this.restApi().getMetricType(metricTypeId),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId());
    }

    @Override
    public ClientResponse<MetricType> getMetricType(MetricType metricType) {
        return this.getMetricType(metricType.getId());
    }

    @Override
    public ClientResponse<String> createMetricType(MetricType.Blueprint metricType) {
        return new ClientResponse<String>(String.class, restApi().createMetricType(metricType),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createMetricType(String metricTypeId, MetricUnit unit,
            MetricDataType metricDataType) {
        return createMetricType(new MetricType.Blueprint(metricTypeId, unit, metricDataType));
    }

    @Override
    public ClientResponse<String> createMetricType(MetricType metricType) {
        return createMetricType(new MetricType.Blueprint(metricType.getId(), metricType.getUnit(),
                metricType.getType(), metricType.getProperties()));
    }

    @Override
    public ClientResponse<String> updateMetricType(String metricTypeId,
            MetricType.Update metricUpdate) {
        return new ClientResponse<String>(String.class, restApi().updateMetricType(metricTypeId, metricUpdate),
                RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateMetricType(String metricTypeId, MetricType metricType) {
        return updateMetricType(metricTypeId, new MetricType.Update(metricType.getProperties(), metricType.getUnit()));
    }

    @Override
    public ClientResponse<String> deleteMetricType(String metricTypeId) {
        return new ClientResponse<String>(String.class, restApi().deleteMetricType(metricTypeId),
                RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteMetricType(MetricType metricType) {
        return this.deleteMetricType(metricType.getId());
    }

    //Metric

    @Override
    public ClientResponse<String> createMetric(String environmentId, String feedId, Metric.Blueprint metric) {
        Response response;
        if (feedId == null) {
            response = restApi().createMetric(environmentId, metric);
        } else {
            response = restApi().createMetric(environmentId, feedId, metric);
        }
        return new ClientResponse<String>(String.class, response, RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createMetric(String environmentId, Metric.Blueprint metric) {
        return createMetric(environmentId, null, metric);
    }

    @Override
    public ClientResponse<String> createMetric(Metric metric) {
        return createMetric(metric.getEnvironmentId(), metric.getFeedId(), new Metric.Blueprint(metric.getType()
                .getPath().toString(), metric.getId(), metric.getProperties()));
    }

    @Override
    public ClientResponse<Metric> getMetric(String environmentId, String metricId) {
        return getMetric(environmentId, null, metricId);
    }

    @Override
    public ClientResponse<Metric> getMetric(String environmentId, String feedId, String metricId) {
        if (feedId == null) {
            return new ClientResponse<Metric>(Metric.class, this.restApi().getMetric(environmentId, metricId),
                    RESPONSE_CODE.GET_SUCCESS.value(), getTenantId());
        } else {
            return new ClientResponse<Metric>(Metric.class, this.restApi().getMetric(environmentId, feedId, metricId),
                    RESPONSE_CODE.GET_SUCCESS.value(), getTenantId());
        }
    }

    @Override
    public ClientResponse<Metric> getMetric(Metric metric) {
        return getMetric(metric.getEnvironmentId(), metric.getFeedId(), metric.getId());
    }

    @Override
    public ClientResponse<List<Metric>> getMetrics(String environmentId) {
        return new ClientResponse<List<Metric>>(Metric.class, restApi().getMetrics(environmentId),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);

    }

    @Override
    public ClientResponse<List<Metric>> getMetrics(String environmentId, String feedId) {
        return new ClientResponse<List<Metric>>(Metric.class, restApi().getMetrics(environmentId, feedId),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<String> updateMetric(String environmentId, String feedId, String metricId,
            Metric.Update metricUpdate) {
        Response response;
        if (feedId == null) {
            response = restApi().updateMetric(environmentId, metricId, metricUpdate);
        } else {
            response = restApi().updateMetric(environmentId, feedId, metricId, metricUpdate);
        }
        return new ClientResponse<String>(String.class, response, RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateMetric(String environmentId, String metricId, Metric.Update metricUpdate) {
        return updateMetric(environmentId, null, metricId, metricUpdate);
    }

    @Override
    public ClientResponse<String> updateMetric(Metric metric) {
        return updateMetric(metric.getEnvironmentId(), metric.getFeedId(), metric.getId(),
                new Metric.Update(metric.getProperties()));
    }

    @Override
    public ClientResponse<String> deleteMetric(String environmentId, String feedId, String metricId) {
        Response response;
        if (feedId == null) {
            response = restApi().deleteMetric(environmentId, metricId);
        } else {
            response = restApi().deleteMetric(environmentId, feedId, metricId);
        }
        return new ClientResponse<String>(String.class, response, RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteMetric(String environmentId, String metricId) {
        return deleteMetric(environmentId, null, metricId);
    }

    @Override
    public ClientResponse<String> deleteMetric(Metric metric) {
        return this.deleteMetric(metric.getEnvironmentId(), metric.getFeedId(), metric.getId());
    }

    //Get Resource Types
    @Override
    public ClientResponse<List<ResourceType>> getResourceTypes() {
        return new ClientResponse<List<ResourceType>>(ResourceType.class, restApi().getResourceTypes(),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<ResourceType> getResourceType(String resourceTypeId) {
        return new ClientResponse<ResourceType>(ResourceType.class, restApi().getResourceType(resourceTypeId),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId());
    }

    @Override
    public ClientResponse<ResourceType> getResourceType(ResourceType resourceType) {
        return getResourceType(resourceType.getId());
    }

    @Override
    public ClientResponse<List<MetricType>> getMetricTypes(String resourceTypeId) {
        return new ClientResponse<List<MetricType>>(MetricType.class, restApi().getMetricTypes(),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<List<Resource>> getResources(String resourceTypeId) {
        return new ClientResponse<List<Resource>>(Resource.class, restApi().getResources(resourceTypeId),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<String> createResourceType(ResourceType.Blueprint resourceType) {
        return new ClientResponse<String>(String.class, restApi().createResourceType(resourceType),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createResourceType(String resourceId) {
        return createResourceType(new ResourceType.Blueprint(resourceId, null));
    }

    @Override
    public ClientResponse<String> createResourceType(ResourceType resourceType) {
        return createResourceType(new ResourceType.Blueprint(resourceType.getId(), resourceType.getProperties()));
    }

    @Override
    public ClientResponse<String> updateResourceType(String resourceTypeId, ResourceType.Update resourceTypeUpdate) {
        return new ClientResponse<String>(String.class,
                restApi().updateResourceType(resourceTypeId, resourceTypeUpdate),
                RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateResourceType(ResourceType resourceType) {
        return updateResourceType(resourceType.getId(), new ResourceType.Update(resourceType.getProperties()));
    }

    @Override
    public ClientResponse<String> deleteResourceType(String resourceTypeId) {
        return new ClientResponse<String>(String.class,
                restApi().deleteResourceType(resourceTypeId),
                RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteResourceType(ResourceType resourceType) {
        return deleteResourceType(resourceType.getId());
    }

    @Override
    public ClientResponse<String> addMetricType(String resourceTypeId, IdJSON metricTypeId) {
        return new ClientResponse<String>(String.class,
                restApi().addMetricType(resourceTypeId, metricTypeId),
                RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> removeMetricType(String resourceTypeId, String metricTypeId) {
        return new ClientResponse<String>(String.class,
                restApi().removeMetricType(resourceTypeId, metricTypeId),
                RESPONSE_CODE.REMOVE_SUCCESS.value());
    }

    //Resource
    @Override
    public ClientResponse<String> addResource(String environmentId, String feedId, Resource.Blueprint resource) {
        Response response;
        if (feedId == null) {
            response = restApi().addResource(environmentId, resource);
        } else {
            response = restApi().addResource(environmentId, feedId, resource);
        }
        return new ClientResponse<String>(String.class, response, RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> addResource(Resource resource) {
        return addResource(
                resource.getEnvironmentId(),
                resource.getFeedId(),
                new Resource.Blueprint(resource.getId(),
                        resource.getType().getPath().toString(),
                        resource.getProperties()));
    }

    @Override
    public ClientResponse<String> addResource(String environmentId, Resource.Blueprint resource) {
        return addResource(environmentId, null, resource);
    }

    @Override
    public ClientResponse<List<Resource>> getResourcesByType(String environmentId, String typeId, String typeVersion,
            boolean feedless) {
        return new ClientResponse<List<Resource>>(Resource.class, restApi().getResourcesByType(environmentId, typeId,
                typeVersion, feedless), RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<List<Resource>> getResourcesByType(String environmentId, String typeId, String typeVersion) {
        return getResourcesByType(environmentId, typeId, typeVersion, false);
    }

    @Override
    public ClientResponse<List<Resource>> getResourcesByType(String environmentId, String feedId, String typeId,
            String typeVersion) {
        return new ClientResponse<List<Resource>>(Resource.class, restApi().getResourcesByType(environmentId, feedId,
                typeId, typeVersion), RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<Resource> getResource(String environmentId, String feedId, String resourceId) {
        Response response;
        if (feedId == null) {
            response = restApi().getResource(environmentId, resourceId);
        } else {
            response = restApi().getResource(environmentId, feedId, resourceId);
        }
        return new ClientResponse<Resource>(Resource.class, response, RESPONSE_CODE.GET_SUCCESS.value(), getTenantId());

    }

    @Override
    public ClientResponse<Resource> getResource(String environmentId, String resourceId) {
        return getResource(environmentId, null, resourceId);
    }

    @Override
    public ClientResponse<Resource> getResource(Resource resource) {
        return getResource(resource.getEnvironmentId(), resource.getFeedId(), resource.getId());
    }

    @Override
    public ClientResponse<String> updateResource(String environmentId, String feedId, String resourceId,
            Resource.Update update) {
        Response response;
        if (feedId == null) {
            response = restApi().updateResource(environmentId, resourceId, update);
        } else {
            response = restApi().updateResource(environmentId, feedId, resourceId, update);
        }
        return new ClientResponse<String>(String.class, response, RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateResource(String environmentId, String resourceId, Resource.Update update) {
        return updateResource(environmentId, null, resourceId, update);
    }

    @Override
    public ClientResponse<String> updateResource(Resource resource) {
        return updateResource(resource.getEnvironmentId(), resource.getFeedId(), resource.getId(),
                new Resource.Update(resource.getProperties()));
    }

    @Override
    public ClientResponse<String> deleteResource(String environmentId, String feedId, String resourceId) {
        Response response;
        if (feedId == null) {
            response = restApi().deleteResource(environmentId, resourceId);
        } else {
            response = restApi().deleteResource(environmentId, feedId, resourceId);
        }
        return new ClientResponse<String>(String.class, response, RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteResource(String environmentId, String resourceId) {
        return deleteResource(environmentId, null, resourceId);
    }

    @Override
    public ClientResponse<String> deleteResource(Resource resource) {
        return deleteResource(resource.getEnvironmentId(), resource.getFeedId(), resource.getId());
    }

    @Override
    public ClientResponse<String> addMetricToResource(String environmentId, String feedId, String resourceId,
            Collection<String> metricIds) {
        Response response;
        if (feedId == null) {
            response = restApi().addMetricToResource(environmentId, resourceId, metricIds);
        } else {
            response = restApi().addMetricToResource(environmentId, feedId, resourceId, metricIds);
        }
        return new ClientResponse<String>(String.class, response, RESPONSE_CODE.ADD_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> addMetricToResource(String environmentId, String resourceId,
            Collection<String> metricIds) {
        return addMetricToResource(environmentId, null, resourceId, metricIds);
    }

    @Override
    public ClientResponse<List<Metric>> listMetricsOfResource(String environmentID, String feedId, String resourceId) {
        Response response;
        if (feedId == null) {
            response = restApi().listMetricsOfResource(environmentID, resourceId);
        } else {
            response = restApi().listMetricsOfResource(environmentID, feedId, resourceId);
        }
        return new ClientResponse<List<Metric>>(Metric.class, response, RESPONSE_CODE.GET_SUCCESS.value(),
                getTenantId(), true);
    }

    @Override
    public ClientResponse<List<Metric>> listMetricsOfResource(String environmentID, String resourceId) {
        return listMetricsOfResource(environmentID, null, resourceId);
    }

    @Override
    public ClientResponse<Metric> getMetricOfResource(String environmentId, String feedId, String resourceId,
            String metricId) {
        Response response;
        if (feedId == null) {
            response = this.restApi().getMetricOfResource(environmentId, resourceId, metricId);
        } else {
            response = this.restApi().getMetricOfResource(environmentId, feedId, resourceId, metricId);
        }
        return new ClientResponse<Metric>(Metric.class, response, RESPONSE_CODE.GET_SUCCESS.value(), getTenantId());
    }

    @Override
    public ClientResponse<Metric> getMetricOfResource(String environmentId, String resourceId, String metricId) {
        return getMetricOfResource(environmentId, null, resourceId, metricId);
    }

    //Feed
    @Override
    public ClientResponse<String> registerFeed(String environmentId, Feed.Blueprint feed) {
        return new ClientResponse<String>(String.class, restApi().registerFeed(environmentId, feed),
                RESPONSE_CODE.REGISTER_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> registerFeed(Feed feed) {
        return this.registerFeed(feed.getEnvironmentId(), new Feed.Blueprint(feed.getId(), feed.getProperties()));
    }

    @Override
    public ClientResponse<List<Feed>> getAllFeeds(String environmentId) {
        return new ClientResponse<List<Feed>>(Feed.class, restApi().getAllFeeds(environmentId),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId(), true);
    }

    @Override
    public ClientResponse<Feed> getFeed(String environmentId, String feedId) {
        return new ClientResponse<Feed>(Feed.class, restApi().getFeed(environmentId, feedId),
                RESPONSE_CODE.GET_SUCCESS.value(), getTenantId());
    }

    @Override
    public ClientResponse<Feed> getFeed(Feed feed) {
        return getFeed(feed.getEnvironmentId(), feed.getId());
    }

    @Override
    public ClientResponse<String> updateFeed(String environmentId, String feedId, Feed.Update update) {
        return new ClientResponse<String>(String.class, restApi().updateFeed(environmentId, feedId, update),
                RESPONSE_CODE.UPDATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> updateFeed(Feed feed) {
        return this.updateFeed(feed.getEnvironmentId(), feed.getId(), new Feed.Update(feed.getProperties()));
    }

    @Override
    public ClientResponse<String> deleteFeed(String environmentId, String feedId) {
        return new ClientResponse<String>(String.class, restApi().deleteFeed(environmentId, feedId),
                RESPONSE_CODE.DELETE_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> deleteFeed(Feed feed) {
        return deleteFeed(feed.getEnvironmentId(), feed.getId());
    }
}
