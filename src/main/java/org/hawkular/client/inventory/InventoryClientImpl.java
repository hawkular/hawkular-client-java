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
import org.hawkular.inventory.api.model.Tenant.Update;
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
    public Tenant getTenant() {
        return restApi().getTenant();
    }

    @Override
    public boolean updateTenant(Map<String, Object> properties) {
        return updateTenant(new Update(properties));
    }

    @Override
    public boolean updateTenant(Update updateTenant) {
        Response response = restApi().updateTenant(updateTenant);
        if (response.getStatus() == 201) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTenant() {
        Response response = restApi().deleteTenant();
        try {
            if (response.getStatus() == RESPONSE_CODE.DELETE_SUCCESS.value()) {
                _logger.debug("Tenant deleted successfully");
                return true;
            } else {
                _logger.warn("Tenant deletion failed, HTTP Status code: {}, Error message if any:{}",
                        response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    //Enviroment

    @Override
    public List<Environment> getEnvironments() {
        List<Environment> environments = restApi().getEnvironments();
        return environments == null ? new ArrayList<Environment>() : environments;
    }

    @Override
    public Environment getEnvironment(String environmentId) {
        return restApi().getEnvironment(environmentId);
    }

    @Override
    public Environment getEnvironment(Environment environment) {
        return restApi().getEnvironment(environment.getId());
    }

    @Override
    public boolean createEnvironment(Environment.Blueprint environmentBlueprint) {
        Response response = restApi().createEnvironment(environmentBlueprint);
        try {
            if (response.getStatus() == RESPONSE_CODE.CREATE_SUCCESS.value()) {
                _logger.debug("Environment[{}] created successfully, Location URI:{}",
                        environmentBlueprint.getId(), response.getLocation().toString());
                return true;
            } else {
                _logger.warn(
                        "Environment[{}] creation failed, HTTP Status code: {},"
                                + " Error message if any:{}", environmentBlueprint.getId(),
                        response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createEnvironment(String environmentId) {
        return createEnvironment(new Environment.Blueprint(environmentId));
    }

    @Override
    public boolean createEnvironment(String environmentId, Map<String, Object> properties) {
        return createEnvironment(new Environment.Blueprint(environmentId, properties));
    }

    @Override
    public boolean createEnvironment(Environment environment) {
        return createEnvironment(new Environment.Blueprint(environment.getId(), environment.getProperties()));
    }

    @Override
    public boolean updateEnvironment(String environmentId, Environment.Update update) {
        Response response = restApi().updateEnvironment(environmentId, update);
        try {
            if (response.getStatus() == RESPONSE_CODE.UPDATE_SUCCESS.value()) {
                _logger.debug("Environment[{}] was updated successfully",
                        environmentId);
                return true;
            } else {
                _logger.warn(
                        "Environment[{}] updation failed, HTTP Status code: {},"
                                + " Error message if any:{}", environmentId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean updateEnvironment(String environmentId, Map<String, Object> properties) {
        return updateEnvironment(environmentId, new Environment.Update(properties));
    }

    @Override
    public boolean updateEnvironment(Environment environment) {
        return updateEnvironment(environment.getId(), new Environment.Update(environment.getProperties()));
    }

    @Override
    public boolean deleteEnvironment(String environmentId) {
        Response response = restApi().deleteEnvironment(environmentId);
        try {
            if (response.getStatus() == RESPONSE_CODE.DELETE_SUCCESS.value()) {
                _logger.debug("Environment[{}] was deleted successfully",
                        environmentId);
                return true;
            } else {
                _logger.warn(
                        "Environment[{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", environmentId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteEnvironment(Environment environment) {
        return this.deleteEnvironment(environment.getId());
    }

    //MetricType

    @Override
    public List<MetricType> getMetricTypes() {
        List<MetricType> metricTypes = this.restApi().getMetricTypes();
        return metricTypes == null ? new ArrayList<MetricType>() : metricTypes;
    }

    @Override
    public MetricType getMetricType(String metricTypeId) {
        return this.restApi().getMetricType(metricTypeId);
    }

    @Override
    public MetricType getMetricType(MetricType metricType) {
        return this.getMetricType(metricType.getId());
    }

    @Override
    public boolean createMetricType(MetricType.Blueprint metricType) {
        Response response = restApi().createMetricType(metricType);
        try {
            if (response.getStatus() == RESPONSE_CODE.CREATE_SUCCESS.value()) {
                _logger.debug("MetricType[id:{},unit:{}] created successfully, "
                        + "Location URI:{}", metricType.getId(), metricType.getUnit(),
                        response.getLocation().toString());
                return true;
            } else {
                _logger.warn("MetricType[id:{},unit:{}] creation failed, "
                        + " HTTP Status code: {}, Error message if any:{}", metricType.getId(), metricType.getUnit(),
                        response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createMetricType(String metricTypeId, MetricUnit unit) {
        return createMetricType(new MetricType.Blueprint(metricTypeId, unit));
    }

    @Override
    public boolean createMetricType(MetricType metricType) {
        return createMetricType(new MetricType.Blueprint(metricType.getId(), metricType.getUnit(),
                metricType.getProperties()));
    }

    @Override
    public boolean updateMetricType(String metricTypeId,
            MetricType.Update metricUpdate) {
        Response response = restApi().updateMetricType(metricTypeId, metricUpdate);
        try {
            if (response.getStatus() == RESPONSE_CODE.UPDATE_SUCCESS.value()) {
                _logger.debug("MetricType[{}] was updated successfully", metricTypeId);
                return true;
            } else {
                _logger.warn(
                        "MetricType[{}] update failed, HTTP Status code: {},"
                                + " Error message if any:{}", metricTypeId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean updateMetricType(String metricTypeId, MetricType metricType) {
        return updateMetricType(metricTypeId, new MetricType.Update(metricType.getProperties(), metricType.getUnit()));
    }

    @Override
    public boolean deleteMetricType(String metricTypeId) {
        Response response = restApi().deleteMetricType(metricTypeId);
        try {
            if (response.getStatus() == RESPONSE_CODE.DELETE_SUCCESS.value()) {
                _logger.debug("MetricType[{}] was deleted successfully", metricTypeId);
                return true;
            } else {
                _logger.warn(
                        "MetricType[{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", metricTypeId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteMetricType(MetricType metricType) {
        return this.deleteMetricType(metricType.getId());
    }

    //Metric

    @Override
    public boolean createMetric(String environmentId, String feedId, Metric.Blueprint metric) {
        Response response;
        if (feedId == null) {
            response = restApi().createMetric(environmentId, metric);
        } else {
            response = restApi().createMetric(environmentId, feedId, metric);
        }
        try {
            if (response.getStatus() == RESPONSE_CODE.CREATE_SUCCESS.value()) {
                _logger.debug("Metric[id:{},typeId:{}] created successfully under environment[{}],"
                        + " Location URI:{}", metric.getId(), metric.getMetricTypeId(),
                        response.getLocation().toString());
                return true;
            } else {
                _logger.warn("Metric[id:{},typeId:{}] creation failed under environment[{}],"
                        + " HTTP Status code: {}, Error message if any:{}", metric.getId(), metric.getMetricTypeId(),
                        environmentId, response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createMetric(String environmentId, Metric.Blueprint metric) {
        return createMetric(environmentId, null, metric);
    }

    @Override
    public boolean createMetric(Metric metric) {
        return createMetric(metric.getEnvironmentId(), metric.getFeedId(), new Metric.Blueprint(metric.getType()
                .getId(), metric.getId(), metric.getProperties()));
    }

    @Override
    public Metric getMetric(String environmentId, String metricId) {
        return getMetric(environmentId, null, metricId);
    }

    @Override
    public Metric getMetric(String environmentId, String feedId, String metricId) {
        if (feedId == null) {
            return this.restApi().getMetric(environmentId, metricId);
        } else {
            return this.restApi().getMetric(environmentId, feedId, metricId);
        }
    }

    @Override
    public Metric getMetric(Metric metric) {
        return getMetric(metric.getEnvironmentId(), metric.getFeedId(), metric.getId());
    }

    @Override
    public List<Metric> getMetrics(String environmentId) {
        List<Metric> metrics = restApi().getMetrics(environmentId);
        return metrics == null ? new ArrayList<Metric>() : metrics;

    }

    @Override
    public List<Metric> getMetrics(String environmentId, String feedId) {
        List<Metric> metrics = restApi().getMetrics(environmentId, feedId);
        return metrics == null ? new ArrayList<Metric>() : metrics;
    }

    @Override
    public boolean updateMetric(String environmentId, String feedId, String metricId, Metric.Update metricUpdate) {
        Response response;
        if (feedId == null) {
            response = restApi().updateMetric(environmentId, metricId, metricUpdate);
        } else {
            response = restApi().updateMetric(environmentId, feedId, metricId, metricUpdate);
        }
        try {
            if (response.getStatus() == RESPONSE_CODE.UPDATE_SUCCESS.value()) {
                _logger.debug("Metric[id:{}] updated successfully under environment[{}],"
                        + "feed[{}], Location URI:{}", metricId, response.getLocation().toString());
                return true;
            } else {
                _logger.warn("Metric[id:{}] update failed under environment[{}],"
                        + "feed[{}], HTTP Status code: {}, Error message if any:{}", metricId, environmentId,
                        response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean updateMetric(String environmentId, String metricId, Metric.Update metricUpdate) {
        return updateMetric(environmentId, null, metricId, metricUpdate);
    }

    @Override
    public boolean updateMetric(Metric metric) {
        return updateMetric(metric.getEnvironmentId(), metric.getFeedId(), metric.getId(),
                new Metric.Update(metric.getProperties()));
    }

    @Override
    public boolean deleteMetric(String environmentId, String feedId, String metricId) {
        Response response;
        if (feedId == null) {
            response = restApi().deleteMetric(environmentId, metricId);
        } else {
            response = restApi().deleteMetric(environmentId, feedId, metricId);
        }
        try {
            if (response.getStatus() == RESPONSE_CODE.DELETE_SUCCESS.value()) {
                _logger.debug("Metric[{}] under [environment:{},feedId:{}] was deleted successfully",
                        metricId, environmentId, feedId);
                return true;
            } else {
                _logger.warn(
                        "Metric[{}] under [environment:{},feedId:{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", metricId, environmentId, feedId,
                        response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    public boolean deleteMetric(String environmentId, String metricId) {
        return deleteMetric(environmentId, null, metricId);
    }

    @Override
    public boolean deleteMetric(Metric metric) {
        return this.deleteMetric(metric.getEnvironmentId(), metric.getFeedId(), metric.getId());
    }

    //Get Resource Types
    @Override
    public List<ResourceType> getResourceTypes() {
        List<ResourceType> metrics = restApi().getResourceTypes();
        return metrics == null ? new ArrayList<ResourceType>() : metrics;
    }

    @Override
    public ResourceType getResourceType(String resourceTypeId) {
        return restApi().getResourceType(resourceTypeId);
    }

    @Override
    public ResourceType getResourceType(ResourceType resourceType) {
        return getResourceType(resourceType.getId());
    }

    @Override
    public List<MetricType> getMetricTypes(String resourceTypeId) {
        List<MetricType> metricTypes = restApi().getMetricTypes();
        return metricTypes == null ? new ArrayList<MetricType>() : metricTypes;
    }

    @Override
    public List<Resource> getResources(String resourceTypeId) {
        List<Resource> resources = restApi().getResources(resourceTypeId);
        return resources == null ? new ArrayList<Resource>() : resources;
    }

    @Override
    public boolean createResourceType(ResourceType.Blueprint resourceType) {
        Response response = restApi().createResourceType(resourceType);
        try {
            if (response.getStatus() == RESPONSE_CODE.CREATE_SUCCESS.value()) {
                _logger.debug("ResourceType[id:{},version:{}] created successfully, "
                        + "Location URI:{}", resourceType.getId(), resourceType.getVersion(),
                        response.getLocation().toString());
                return true;
            } else {
                _logger.debug("ResourceType[id:{},version:{}] creation failed, "
                        + " HTTP Status code: {}, Error message if any:{}", resourceType.getId(),
                        resourceType.getVersion(),
                        response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean createResourceType(String resourceId, String resourceVersion) {
        return createResourceType(new ResourceType.Blueprint(resourceId, resourceVersion));
    }

    @Override
    public boolean createResourceType(String resourceId, Version resourceVersion) {
        return createResourceType(new ResourceType.Blueprint(resourceId, resourceVersion.toString()));
    }

    @Override
    public boolean createResourceType(ResourceType resourceType) {
        return createResourceType(new ResourceType.Blueprint(resourceType.getId(), resourceType.getVersion()
                .toString()));
    }

    @Override
    public boolean updateResourceType(String resourceTypeId, ResourceType.Update resourceTypeUpdate) {
        Response response = restApi().updateResourceType(resourceTypeId, resourceTypeUpdate);
        try {
            if (response.getStatus() == RESPONSE_CODE.UPDATE_SUCCESS.value()) {
                _logger.debug("ResourceType[id:{},version:{}] updated successfully, "
                        + "Location URI:{}", resourceTypeId, resourceTypeUpdate.getVersion(),
                        response.getLocation().toString());
                return true;
            } else {
                _logger.debug("ResourceType[id:{},version:{}] update failed, "
                        + " HTTP Status code: {}, Error message if any:{}", resourceTypeId,
                        resourceTypeUpdate.getVersion(),
                        response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean updateResourceType(ResourceType resourceType) {
        return updateResourceType(resourceType.getId(), new ResourceType.Update(resourceType.getProperties(),
                resourceType.getVersion().toString()));
    }

    @Override
    public boolean deleteResourceType(String resourceTypeId) {
        Response response = restApi().deleteResourceType(resourceTypeId);
        try {
            if (response.getStatus() == RESPONSE_CODE.DELETE_SUCCESS.value()) {
                _logger.debug("ResourceType[{}] was deleted successfully", resourceTypeId);
                return true;
            } else {
                _logger.warn(
                        "ResourceType[{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", resourceTypeId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteResourceType(ResourceType resourceType) {
        return deleteResourceType(resourceType.getId());
    }

    @Override
    public boolean addMetricType(String resourceTypeId, IdJSON metricTypeId) {
        Response response = restApi().addMetricType(resourceTypeId, metricTypeId);
        try {
            if (response.getStatus() == RESPONSE_CODE.ADD_SUCCESS.value()) {
                _logger.debug("ResourceType[{}] was added successfully", resourceTypeId);
                return true;
            } else {
                _logger.warn(
                        "ResourceType[{}] addetion failed, HTTP Status code: {},"
                                + " Error message if any:{}", resourceTypeId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean removeMetricType(String resourceTypeId, String metricTypeId) {
        Response response = restApi().removeMetricType(resourceTypeId, metricTypeId);
        try {
            if (response.getStatus() == RESPONSE_CODE.REMOVE_SUCCESS.value()) {
                _logger.debug("ResourceType[{}] was removed successfully", resourceTypeId);
                return true;
            } else {
                _logger.warn(
                        "ResourceType[{}] failed to remove, HTTP Status code: {},"
                                + " Error message if any:{}", resourceTypeId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    //Resource
    @Override
    public boolean addResource(String environmentId, String feedId, Resource.Blueprint resource) {
        Response response;
        if (feedId == null) {
            response = restApi().addResource(environmentId, resource);
        } else {
            response = restApi().addResource(environmentId, feedId, resource);
        }
        try {
            if (response.getStatus() == RESPONSE_CODE.ADD_SUCCESS.value()) {
                _logger.debug("Resource[id:{},typeId:{}] added successfully under environment[{}], "
                        + "Location URI:{}", resource.getId(), resource.getResourceTypeId(),
                        environmentId, response.getLocation().toString());
                return true;
            } else {
                _logger.debug("Unable to add Resource[id:{},typeId:{}] under environment[{}], "
                        + "HTTP Status code: {}, Error message if any:{}", resource.getId(),
                        resource.getResourceTypeId(),
                        environmentId, response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean addResource(Resource resource) {
        return addResource(resource.getEnvironmentId(), resource.getFeedId(),
                new Resource.Blueprint(resource.getId(), resource.getType().getId(), resource.getProperties()));
    }

    @Override
    public boolean addResource(String environmentId, Resource.Blueprint resource) {
        return addResource(environmentId, null, resource);
    }

    @Override
    public List<Resource> getResourcesByType(String environmentId, String typeId, String typeVersion,
            boolean feedless) {
        List<Resource> resources = restApi().getResourcesByType(environmentId, typeId, typeVersion, feedless);
        return resources == null ? new ArrayList<Resource>() : resources;
    }

    @Override
    public List<Resource> getResourcesByType(String environmentId, String typeId, String typeVersion) {
        return getResourcesByType(environmentId, typeId, typeVersion, false);
    }

    @Override
    public List<Resource> getResourcesByType(String environmentId, String feedId, String typeId, String typeVersion) {
        List<Resource> resources = restApi().getResourcesByType(environmentId, feedId, typeId, typeVersion);
        return resources == null ? new ArrayList<Resource>() : resources;
    }

    @Override
    public Resource getResource(String environmentId, String feedId, String resourceId) {
        if (feedId == null) {
            return restApi().getResource(environmentId, resourceId);
        } else {
            return restApi().getResource(environmentId, feedId, resourceId);
        }
    }

    @Override
    public Resource getResource(String environmentId, String resourceId) {
        return getResource(environmentId, null, resourceId);
    }

    @Override
    public Resource getResource(Resource resource) {
        return getResource(resource.getEnvironmentId(), resource.getFeedId(), resource.getId());
    }

    @Override
    public boolean updateResource(String environmentId, String feedId, String resourceId, Resource.Update update) {
        Response response;
        if (feedId == null) {
            response = restApi().updateResource(environmentId, resourceId, update);
        } else {
            response = restApi().updateResource(environmentId, feedId, resourceId, update);
        }
        try {
            if (response.getStatus() == RESPONSE_CODE.UPDATE_SUCCESS.value()) {
                _logger.debug("Resource[{}] under [environment:{}] was updated successfully", resourceId,
                        environmentId);
                return true;
            } else {
                _logger.warn(
                        "Resource[{}] under [environment:{}] update failed, HTTP Status code: {},"
                                + " Error message if any:{}", resourceId, environmentId,
                        response.getStatus(), response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean updateResource(String environmentId, String resourceId, Resource.Update update) {
        return updateResource(environmentId, null, resourceId, update);
    }

    @Override
    public boolean updateResource(Resource resource) {
        return updateResource(resource.getEnvironmentId(), resource.getFeedId(), resource.getId(),
                new Resource.Update(resource.getProperties()));
    }

    @Override
    public boolean deleteResource(String environmentId, String feedId, String resourceId) {
        Response response;
        if (feedId == null) {
            response = restApi().deleteResource(environmentId, resourceId);
        } else {
            response = restApi().deleteResource(environmentId, feedId, resourceId);
        }
        try {
            if (response.getStatus() == RESPONSE_CODE.DELETE_SUCCESS.value()) {
                _logger.debug("Resource[{}] under [environment:{}] was deleted successfully", resourceId,
                        environmentId);
                return true;
            } else {
                _logger.warn(
                        "Resource[{}] under [environment:{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", resourceId, environmentId,
                        response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean deleteResource(String environmentId, String resourceId) {
        return deleteResource(environmentId, null, resourceId);
    }

    @Override
    public boolean deleteResource(Resource resource) {
        return deleteResource(resource.getEnvironmentId(), resource.getFeedId(), resource.getId());
    }

    @Override
    public boolean addMetricToResource(String environmentId, String feedId, String resourceId,
            Collection<String> metricIds) {
        Response response;
        if (feedId == null) {
            response = restApi().addMetricToResource(environmentId, resourceId, metricIds);
        } else {
            response = restApi().addMetricToResource(environmentId, feedId, resourceId, metricIds);
        }
        try {
            if (response.getStatus() == RESPONSE_CODE.ADD_SUCCESS.value()) {
                _logger.debug("Resource[{}] under [environment:{}] was added successfully", resourceId,
                        environmentId);
                return true;
            } else {
                _logger.warn(
                        "Resource[{}] under [environment:{}] addition failed, HTTP Status code: {},"
                                + " Error message if any:{}", resourceId, environmentId,
                        response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }
    }

    @Override
    public boolean addMetricToResource(String environmentId, String resourceId, Collection<String> metricIds) {
        return addMetricToResource(environmentId, null, resourceId, metricIds);
    }

    @Override
    public List<Metric> listMetricsOfResource(String environmentID, String feedId, String resourceId) {
        List<Metric> metrics;
        if (feedId == null) {
            metrics = restApi().listMetricsOfResource(environmentID, resourceId);
        } else {
            metrics = restApi().listMetricsOfResource(environmentID, feedId, resourceId);
        }
        return metrics == null ? new ArrayList<Metric>() : metrics;
    }

    @Override
    public List<Metric> listMetricsOfResource(String environmentID, String resourceId) {
        return listMetricsOfResource(environmentID, null, resourceId);
    }

    @Override
    public Metric getMetricOfResource(String environmentId, String feedId, String resourceId, String metricId) {
        if (feedId == null) {
            return this.restApi().getMetricOfResource(environmentId, resourceId, metricId);
        } else {
            return this.restApi().getMetricOfResource(environmentId, feedId, resourceId, metricId);
        }
    }

    @Override
    public Metric getMetricOfResource(String environmentId, String resourceId, String metricId) {
        return getMetricOfResource(environmentId, null, resourceId, metricId);
    }

    //Feed
    @Override
    public boolean registerFeed(String environmentId, Feed.Blueprint feed) {
        Response response = restApi().registerFeed(environmentId, feed);
        try {
            if (response.getStatus() == RESPONSE_CODE.REGISTER_SUCCESS.value()) {
                _logger.debug("Feed[{}] under [environment:{}] was registered successfully", feed.getId(),
                        environmentId);
                return true;
            } else {
                _logger.warn(
                        "Feed[{}] under [environment:{}] registration failed, HTTP Status code: {},"
                                + " Error message if any:{}", feed.getId(), environmentId,
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
        return this.registerFeed(feed.getEnvironmentId(), new Feed.Blueprint(feed.getId(), feed.getProperties()));
    }

    @Override
    public List<Feed> getAllFeeds(String environmentId) {
        List<Feed> feeds = restApi().getAllFeeds(environmentId);
        return feeds == null ? new ArrayList<Feed>() : feeds;
    }

    @Override
    public Feed getFeed(String environmentId, String feedId) {
        return restApi().getFeed(environmentId, feedId);
    }

    @Override
    public Feed getFeed(Feed feed) {
        return restApi().getFeed(feed.getEnvironmentId(), feed.getId());
    }

    @Override
    public boolean updateFeed(String environmentId, String feedId, Feed.Update update) {
        Response response = restApi().updateFeed(environmentId, feedId, update);
        try {
            if (response.getStatus() == RESPONSE_CODE.UPDATE_SUCCESS.value()) {
                _logger.debug("Feed[{}] under [environment:{}] was updated successfully", feedId,
                        environmentId);
                return true;
            } else {
                _logger.warn(
                        "Feed[{}] under [environment:{}] update failed, HTTP Status code: {},"
                                + " Error message if any:{}", feedId, environmentId,
                        response.getStatus(),
                        response.readEntity(String.class));
                return false;
            }
        } finally {
            response.close();
        }

    }

    @Override
    public boolean updateFeed(Feed feed) {
        return this.updateFeed(feed.getEnvironmentId(), feed.getId(), new Feed.Update(feed.getProperties()));
    }

    @Override
    public boolean deleteFeed(String environmentId, String feedId) {
        Response response = restApi().deleteFeed(environmentId, feedId);
        try {
            if (response.getStatus() == RESPONSE_CODE.DELETE_SUCCESS.value()) {
                _logger.debug("Feed[{}] under [environment:{}] was deleted successfully", feedId,
                        environmentId);
                return true;
            } else {
                _logger.warn(
                        "Feed[{}] under [environment:{}] deletion failed, HTTP Status code: {},"
                                + " Error message if any:{}", feedId, environmentId,
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
        return deleteFeed(feed.getEnvironmentId(), feed.getId());
    }
}
