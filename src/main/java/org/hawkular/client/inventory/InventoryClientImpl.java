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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.hawkular.client.BaseClient;
import org.hawkular.client.RestFactory;
import org.hawkular.client.inventory.model.IdJSON;
import org.hawkular.client.inventory.model.StringValue;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.MetricUnit;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.api.model.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class InventoryClientImpl extends BaseClient<InventoryRestApi>
        implements InventoryClient {
    private static final Logger _logger = LoggerFactory.getLogger(InventoryClientImpl.class);

    public InventoryClientImpl(URI endpointUri, String username,
            String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<InventoryRestApi>(InventoryRestApi.class));
    }

    @Override
    public String pingTime() {
        StringValue obj = restApi().pingTime();
        return (obj != null && obj.getValue() != null) ? obj.getValue() : "";
    }

    @Override
    public String pingHello() {
        StringValue obj = restApi().pingHello();
        return (obj != null && obj.getValue() != null) ? obj.getValue() : "";
    }

    @Override
    public List<Tenant> getTenants() {
        List<Tenant> tenants = restApi().getTenants();
        return tenants == null ? new ArrayList<Tenant>() : tenants;
    }

    @Override
    public boolean createTenant(IdJSON tenantId) {
        Response response = restApi().createTenant(tenantId);
        try {
            if (response.getStatus() == 201) {
                _logger.debug("Tenant[{}] created successfully, Location URI: {}",
                        tenantId.getId(), response.getLocation().toString());
                return true;
            } else {
                _logger.warn("Tenant[{}] creation failed, HTTP Status code: {}, Error message if any:{}",
                        tenantId.getId(), response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createTenant(String tenantId) {
        return createTenant(new IdJSON(tenantId));
    }

    @Override
    public boolean createTenant(Tenant tenant) {
        return createTenant(tenant.getId());
    }

    @Override
    public boolean updateTenant(String tenantId, Map<String, Object> properties) {
        Response response = restApi().updateTenant(tenantId, properties);
        if (response.getStatus() == 201) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTenant(String tenantId) {
        Response response = restApi().deleteTenant(tenantId);
        try {
            if (response.getStatus() == 204) {
                _logger.debug("Tenant[{}] deleted successfully", tenantId);
                return true;
            } else {
                _logger.warn("Tenant[{}] deletion failed, HTTP Status code: {}, Error message if any:{}",
                        tenantId, response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteTenant(Tenant tenant) {
        return deleteTenant(tenant.getId());
    }

    //Enviroment

    @Override
    public List<Environment> getEnvironments(String tenantId) {
        List<Environment> environments = restApi().getEnvironments(tenantId);
        return environments == null ? new ArrayList<Environment>() : environments;
    }

    @Override
    public Environment getEnvironment(String tenantId, String environmentId) {
        return restApi().getEnvironment(tenantId, environmentId);
    }

    @Override
    public Environment getEnvironment(Environment environment) {
        return restApi().getEnvironment(environment.getTenantId(), environment.getId());
    }

    @Override
    public boolean createEnvironment(String tenantId, IdJSON environmentId) {
        Response response = restApi().createEnvironment(tenantId, environmentId);
        try {
            if (response.getStatus() == 201) {
                _logger.debug("Environment[{}] created successfully under the tenant[{}], Location URI:{}",
                        environmentId.getId(), tenantId, response.getLocation().toString());
                return true;
            } else {
                _logger.warn(
                        "Environment[{}] creation failed under the tenant[{}], HTTP Status code: {},"
                                + " Error message if any:{}", environmentId.getId(), tenantId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createEnvironment(String tenantId, String environmentId) {
        IdJSON environmentIdJson = new IdJSON(environmentId);
        return createEnvironment(tenantId, environmentIdJson);
    }

    @Override
    public boolean createEnvironment(Environment environment) {
        IdJSON environmentIdJson = new IdJSON(environment.getId());
        return createEnvironment(environment.getTenantId(), environmentIdJson);
    }

    @Override
    public boolean updateEnvironment(String tenantId, String environmentId, Map<String, Object> properties) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteEnvironment(String tenantId, String environmentId) {
        Response response = restApi().deleteEnvironment(tenantId, environmentId);
        try {
            if (response.getStatus() == 204) {
                _logger.debug("Environment[{}] under the tenant[{}] was deleted successfully",
                        environmentId, tenantId);
                return true;
            } else {
                _logger.warn(
                        "Environment[{}] under the tenant[{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", environmentId, tenantId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteEnvironment(Environment environment) {
        return this.deleteEnvironment(environment.getTenantId(), environment.getId());
    }

    //MetricType

    @Override
    public List<MetricType> getMetricTypes(String tenantId) {
        List<MetricType> metricTypes = this.restApi().getMetricTypes(tenantId);
        return metricTypes == null ? new ArrayList<MetricType>() : metricTypes;
    }

    @Override
    public MetricType getMetricType(String tenantId, String metricTypeId) {
        return this.restApi().getMetricType(tenantId, metricTypeId);
    }

    @Override
    public MetricType getMetricType(MetricType metricType) {
        return this.getMetricType(metricType.getTenantId(), metricType.getId());
    }

    @Override
    public boolean createMetricType(String tenantId, MetricType.Blueprint metricType) {
        Response response = restApi().createMetricType(tenantId, metricType);
        try {
            if (response.getStatus() == 201) {
                _logger.debug("MetricType[id:{},unit:{}] created successfully under the tenant[{}], "
                        + "Location URI:{}", metricType.getId(), metricType.getUnit(),
                        tenantId, response.getLocation().toString());
                return true;
            } else {
                _logger.warn("MetricType[id:{},unit:{}] creation failed under the tenant[{}], "
                        + " HTTP Status code: {}, Error message if any:{}", metricType.getId(), metricType.getUnit(),
                        tenantId, response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createMetricType(String tenantId, String metricTypeId, MetricUnit unit) {
        return createMetricType(new MetricType(tenantId, metricTypeId, unit));
    }

    @Override
    public boolean createMetricType(MetricType metricType) {
        return createMetricType(metricType.getTenantId(),
                new MetricType.Blueprint(metricType.getId(), metricType.getUnit(), metricType.getProperties()));
    }

    @Override
    public boolean updateMetricType(String tenantId, String metricTypeId, MetricType metricType) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteMetricType(String tenantId, String metricTypeId) {
        Response response = restApi().deleteMetricType(tenantId, metricTypeId);
        try {
            if (response.getStatus() == 204) {
                _logger.debug("MetricType[{}] under the tenant[{}] was deleted successfully", metricTypeId, tenantId);
                return true;
            } else {
                _logger.warn(
                        "MetricType[{}] under the tenant[{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", metricTypeId, tenantId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteMetricType(MetricType metricType) {
        return this.deleteMetricType(metricType.getTenantId(), metricType.getId());
    }

    //Metric

    @Override
    public boolean createMetric(String tenantId, String environmentId, Metric.Blueprint metric) {
        Response response = restApi().createMetric(tenantId, environmentId, metric);
        try {
            if (response.getStatus() == 201) {
                _logger.debug("Metric[id:{},typeId:{}] created successfully under the environment[{}],"
                        + " tenant[{}], Location URI:{}", metric.getId(), metric.getMetricTypeId(),
                        tenantId,
                        response.getLocation().toString());
                return true;
            } else {
                _logger.warn("Metric[id:{},typeId:{}] creation failed under the environment[{}], tenant[{}],"
                        + " HTTP Status code: {}, Error message if any:{}", metric.getId(), metric.getMetricTypeId(),
                        environmentId,
                        tenantId, response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createMetric(String tenantId, String environmentId, String metricId, String metricTypeId) {
        return createMetric(tenantId, environmentId, new Metric.Blueprint(metricTypeId, metricId));
    }

    @Override
    public boolean createMetric(String tenantId, String environmentId, String metricId, MetricType type) {
        return createMetric(tenantId, environmentId, new Metric.Blueprint(type.getId(), metricId));
    }

    @Override
    public boolean createMetric(Metric metric) {
        return createMetric(metric.getTenantId(), metric.getEnvironmentId(), new Metric.Blueprint(metric.getType()
                .getId(), metric.getId(), metric.getProperties()));
    }

    @Override
    public Metric getMetric(String tenantId, String environmentId, String metricId) {
        return this.restApi().getMetric(tenantId, environmentId, metricId);
    }

    @Override
    public Metric getMetric(Metric metric) {
        return this.restApi().getMetric(metric.getTenantId(), metric.getEnvironmentId(), metric.getId());
    }

    @Override
    public List<Metric> getMetrics(String tenantId, String environmentId) {
        List<Metric> metrics = restApi().getMetrics(tenantId, environmentId);
        return metrics == null ? new ArrayList<Metric>() : metrics;

    }

    @Override
    public boolean updateMetric(String tenantId, String environmentId, String metricId, Metric metric) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteMetric(String tenantId, String environmentId, String metricId) {
        Response response = restApi().deleteMetric(tenantId, environmentId, metricId);
        try {
            if (response.getStatus() == 204) {
                _logger.debug("Metric[{}] under [tenant:{},environment:{}] was deleted successfully", metricId,
                        tenantId, environmentId);
                return true;
            } else {
                _logger.warn(
                        "Metric[{}] under the [tenant:{},environment:{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", metricId, tenantId, environmentId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteMetric(Metric metric) {
        return this.deleteMetric(metric.getTenantId(), metric.getEnvironmentId(), metric.getId());
    }

    @Override
    public List<ResourceType> getResourceTypes(String tenantId) {
        List<ResourceType> metrics = restApi().getResourceTypes(tenantId);
        return metrics == null ? new ArrayList<ResourceType>() : metrics;
    }

    @Override
    public ResourceType getResourceType(String tenantId, String resourceTypeId) {
        return restApi().getResourceType(tenantId, resourceTypeId);
    }

    @Override
    public ResourceType getResourceType(ResourceType resourceType) {
        return restApi().getResourceType(resourceType.getTenantId(), resourceType.getId());
    }

    @Override
    public List<MetricType> getMetricTypes(String tenantId, String resourceTypeId) {
        List<MetricType> metricTypes = restApi().getMetricTypes(tenantId);
        return metricTypes == null ? new ArrayList<MetricType>() : metricTypes;
    }

    @Override
    public List<Resource> getResources(String tenantId, String resourceTypeId) {
        List<Resource> resources = restApi().getResources(tenantId, resourceTypeId);
        return resources == null ? new ArrayList<Resource>() : resources;
    }

    @Override
    public boolean createResourceType(String tenantId, ResourceType.Blueprint resourceType) {
        Response response = restApi().createResourceType(tenantId, resourceType);
        try {
            if (response.getStatus() == 201) {
                _logger.debug("ResourceType[id:{},version:{}] created successfully under the tenant[{}], "
                        + "Location URI:{}", resourceType.getId(), resourceType.getVersion(),
                        tenantId, response.getLocation().toString());
                return true;
            } else {
                _logger.debug("ResourceType[id:{},version:{}] creation failed under the tenant[{}], "
                        + " HTTP Status code: {}, Error message if any:{}", resourceType.getId(),
                        resourceType.getVersion(),
                        tenantId, response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createResourceType(String tenantId, String resourceId, String resourceVersion) {
        return createResourceType(tenantId, new ResourceType.Blueprint(resourceId, resourceVersion));
    }

    @Override
    public boolean createResourceType(String tenantId, String resourceId, Version resourceVersion) {
        return createResourceType(tenantId, new ResourceType.Blueprint(resourceId, resourceVersion.toString()));
    }

    @Override
    public boolean createResourceType(ResourceType resourceType) {
        return createResourceType(resourceType.getTenantId(), new ResourceType.Blueprint(resourceType.getId(),
                resourceType.getVersion().toString()));
    }

    @Override
    public boolean deleteResourceType(String tenantId, String resourceTypeId) {
        Response response = restApi().deleteResourceType(tenantId, resourceTypeId);
        try {
            if (response.getStatus() == 204) {
                _logger.debug("ResourceType[{}] under the tenant[{}] was deleted successfully", resourceTypeId,
                        tenantId);
                return true;
            } else {
                _logger.warn(
                        "ResourceType[{}] under the tenant[{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", resourceTypeId, tenantId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteResourceType(ResourceType resourceType) {
        return deleteResourceType(resourceType.getTenantId(), resourceType.getId());
    }

    @Override
    public boolean addMetricType(String tenantId, String resourceTypeId, IdJSON metricTypeId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeMetricType(String tenantId, String resourceTypeId, String metricTypeId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addResource(String tenantId, String environmentId, Resource.Blueprint resource) {
        Response response = restApi().addResource(tenantId, environmentId, resource);
        try {
            if (response.getStatus() == 201) {
                _logger.debug("Resource[id:{},typeId:{}] added successfully under the tenant[{}], environment[{}], "
                        + "Location URI:{}", resource.getId(), resource.getResourceTypeId(),
                        tenantId, environmentId, response.getLocation().toString());
                return true;
            } else {
                _logger.debug("Unable to add Resource[id:{},typeId:{}] under the tenant[{}], environment[{}], "
                        + "HTTP Status code: {}, Error message if any:{}", resource.getId(),
                        resource.getResourceTypeId(),
                        tenantId, environmentId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean addResource(String tenantId, String environmentId, String resourceId,
            String resourceTypeId) {
        return addResource(tenantId, environmentId,
                new Resource.Blueprint(resourceId, resourceTypeId));
    }

    @Override
    public boolean addResource(Resource resource) {
        return addResource(resource.getTenantId(), resource.getEnvironmentId(),
                new Resource.Blueprint(resource.getId(), resource.getType().getId()));
    }

    @Override
    public List<Resource> getResourcesByType(String tenantId, String environmentId, String typeId, String typeVersion) {
        List<Resource> resources = restApi().getResourcesByType(tenantId, environmentId, typeId, typeVersion);
        return resources == null ? new ArrayList<Resource>() : resources;
    }

    @Override
    public Resource getResource(String tenantId, String environmentId, String uid) {
        return restApi().getResource(tenantId, environmentId, uid);
    }

    @Override
    public Resource getResource(Resource resource) {
        return getResource(resource.getTenantId(), resource.getEnvironmentId(), resource.getId());
    }

    @Override
    public boolean deleteResource(String tenantId, String environmentId, String resourceId) {
        Response response = restApi().deleteResource(tenantId, environmentId, resourceId);
        try {
            if (response.getStatus() == 204) {
                _logger.debug("Resource[{}] under [tenant:{},environment:{}] was deleted successfully", resourceId,
                        tenantId, environmentId);
                return true;
            } else {
                _logger.warn(
                        "Resource[{}] under the [tenant:{},environment:{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", resourceId, tenantId, environmentId,
                        response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteResource(Resource resource) {
        return deleteResource(resource.getTenantId(), resource.getEnvironmentId(), resource.getId());
    }

    @Override
    public boolean addMetricToResource(String tenantId, String environmentId, String resourceId,
            Collection<String> metricIds) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Metric> listMetricsOfResource(String tenantId, String environmentID, String resourceId) {
        List<Metric> metrics = restApi().listMetricsOfResource(tenantId, environmentID, resourceId);
        return metrics == null ? new ArrayList<Metric>() : metrics;
    }

    @Override
    public Metric getMetricOfResource(String tenantId, String environmentId, String resourceId, String metricId) {
        // TODO Auto-generated method stub
        return null;
    }

    //Feed
    @Override
    public boolean registerFeed(String tenantId, String environmentId, Feed.Blueprint feed) {
        Response response = restApi().registerFeed(tenantId, environmentId, feed);
        try {
            if (response.getStatus() == 201) {
                _logger.debug("Feed[{}] under [tenant:{},environment:{}] was registered successfully", feed.getId(),
                        tenantId, environmentId);
                return true;
            } else {
                _logger.warn(
                        "Feed[{}] under the [tenant:{},environment:{}] registration failed, HTTP Status code: {},"
                                + " Error message if any:{}", feed.getId(), tenantId, environmentId,
                        response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean registerFeed(Feed feed) {
        return this.registerFeed(feed.getTenantId(), feed.getEnvironmentId(),
                new Feed.Blueprint(feed.getId(), feed.getProperties()));
    }

    @Override
    public List<Feed> getAllFeeds(String tenantId, String environmentId) {
        List<Feed> feeds = restApi().getAllFeeds(tenantId, environmentId);
        return feeds == null ? new ArrayList<Feed>() : feeds;
    }

    @Override
    public Feed getFeed(String tenantId, String environmentId, String feedId) {
        return restApi().getFeed(tenantId, environmentId, feedId);
    }

    @Override
    public Feed getFeed(Feed feed) {
        return restApi().getFeed(feed.getTenantId(), feed.getEnvironmentId(), feed.getId());
    }

    @Override
    public boolean updateFeed(String tenantId, String environmentId, String feedId, Feed feed) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean updateFeed(Feed feed) {
        return this.updateFeed(feed.getTenantId(), feed.getEnvironmentId(), feed.getId(), feed);
    }

    @Override
    public boolean deleteFeed(String tenantId, String environmentId, String feedId) {
        Response response = restApi().deleteFeed(tenantId, environmentId, feedId);
        try {
            if (response.getStatus() == 204) {
                _logger.debug("Feed[{}] under [tenant:{},environment:{}] was deleted successfully", feedId,
                        tenantId, environmentId);
                return true;
            } else {
                _logger.warn(
                        "Feed[{}] under the [tenant:{},environment:{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", feedId, tenantId, environmentId,
                        response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteFeed(Feed feed) {
        return deleteFeed(feed.getTenantId(), feed.getEnvironmentId(), feed.getId());
    }
}
