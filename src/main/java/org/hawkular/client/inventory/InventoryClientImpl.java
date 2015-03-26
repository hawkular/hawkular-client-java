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
import java.util.Set;

import javax.ws.rs.core.Response;

import org.hawkular.client.BaseClient;
import org.hawkular.client.RestFactory;
import org.hawkular.client.inventory.model.IdJSON;
import org.hawkular.client.inventory.model.MetricJSON;
import org.hawkular.client.inventory.model.MetricTypeJSON;
import org.hawkular.client.inventory.model.MetricTypeUpdateJSON;
import org.hawkular.client.inventory.model.MetricUpdateJSON;
import org.hawkular.client.inventory.model.ResourceJSON;
import org.hawkular.client.inventory.model.ResourceTypeJSON;
import org.hawkular.client.inventory.model.StringValue;
import org.hawkular.client.inventory.model.StringWrapper;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class InventoryClientImpl extends BaseClient<InventoryRestApi>
implements InventoryClient, InventoryJSONConverter {
    private Logger _logger = LoggerFactory.getLogger(InventoryClientImpl.class);

    public InventoryClientImpl(URI endpointUri, String username,
                               String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<InventoryRestApi>(InventoryRestApi.class));
    }

    @Override
    public String pinger() {
        StringWrapper obj = restApi().pinger();
        return (obj != null && obj.getValue() != null) ? obj.getValue() : "";
    }

    @Override
    public String ping() {
        StringValue obj = restApi().ping();
        return (obj != null && obj.getValue() != null) ? obj.getValue() : "";
    }

    @Override
    public List<Tenant> getTenants() {
        return restApi().getTenants();
    }

    @Override
    public boolean createTenant(IdJSON tenantId) {
        Response response = restApi().createTenant(tenantId);
        if(response.getStatus() == 201){
            _logger.debug("Tenant[{}] created successfully, Location URI: {}",
                          tenantId.getId(),response.getLocation().toString());
            return true;
        }else{
            _logger.debug("Tenant[{}] creation failed, Response Status code: {}",
                          tenantId.getId(),response.getStatus());
            return false;
        }
    }

    @Override
    public boolean createTenant(String tenantId) {
        return createTenant(new IdJSON(tenantId));
    }

    @Override
    public boolean updateTenant(String tenantId, Map<String,Object> properties) {
        Response response = restApi().updateTenant(tenantId, properties);
        if(response.getStatus() == 201){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteTenant(String tenantId) {
        Response response = restApi().deleteTenant(tenantId);
        if(response.getStatus() == 204){
            _logger.debug("Tenant[{}] deleted successfully",tenantId);
            return true;
        }else{
            _logger.debug("Tenant[{}] deletion failed, Response Status code: {}",
                          tenantId,response.getStatus());
            return false;
        }
    }

    @Override
    public Set<Environment> getEnvironments(String tenantId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Environment getEnvironment(String tenantId, String environmentId) {
        return restApi().getEnvironment(tenantId, environmentId);
    }

    @Override
    public boolean createEnvironment(String tenantId, IdJSON environmentId) {
        Response response = restApi().createEnvironment(tenantId, environmentId);
        if(response.getStatus() == 201){
            _logger.debug("Environment[{}] created successfully under the tenant[{}], Location URI:{}",
                          environmentId.getId(),tenantId,response.getLocation().toString());
            return true;
        }else{
            _logger.debug("Environment[{}] creation failed under the tenant[{}], Response Status code: {}",
                          environmentId.getId(),tenantId,response.getStatus());
            return false;
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
    public boolean updateEnvironment(String tenantId, String environmentId, Map<String,Object> properties) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteEnvironment(String tenantId, String environmentId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean createMetric(String tenantId, String environmentId, MetricJSON metric) {
        Response response = restApi().createMetric(tenantId, environmentId, metric);
        if(response.getStatus() == 201){
            _logger.debug("Metric[id:{},typeId:{}] created successfully under the environment[{}],"
                            + " tenant[{}], Location URI:{}",metric.getId(),metric.getMetricTypeId(),environmentId,
                            tenantId,response.getLocation().toString());
            return true;
        }else{
            _logger.debug("Metric[id:{},typeId:{}] creation failed under the environment[{}], tenant[{}],"
                            + " Response Status Code:{}",metric.getId(),metric.getMetricTypeId(),environmentId,tenantId,
                            response.getStatus());
            return false;
        }
    }

    @Override
    public boolean createMetric(String tenantId, String environmentId, String metricId, String metricTypeId) {
        return createMetric(tenantId, environmentId, getMetricJSON(metricId, metricTypeId));
    }

    @Override
    public boolean createMetric(Metric metric) {
        return createMetric(metric.getTenantId(), metric.getEnvironmentId(),
                            metric.getId(), metric.getType().getId());
    }

    @Override
    public Metric getMetric(String tenantId, String environmentId, String metricId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Metric> getMetrics(String tenantId, String environmentId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean updateMetric(String tenantId, String environmentId, String metricId, MetricUpdateJSON updates) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteMetric(String tenantId, String environmentId, String metricId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<MetricType> getMetricTypes(String tenantId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MetricType getMetricType(String tenantId, String metricTypeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean createMetricType(String tenantId, MetricTypeJSON metricType) {
        Response response = restApi().createMetricType(tenantId, metricType);
        if(response.getStatus() == 201){
            _logger.debug("MetricType[id:{},unit:{}] created successfully under the tenant[{}], "
                            + "Location URI:{}",metricType.getId(),metricType.getUnit(),
                            tenantId,response.getLocation().toString());
            return true;
        }else{
            _logger.debug("MetricType[id:{},unit:{}] creation failed under the tenant[{}], "
                            + " Response Status code: {}",metricType.getId(),metricType.getUnit(),
                            tenantId,response.getStatus());
            return false;
        }
    }

    @Override
    public boolean createMetricType(String tenantId, String metricTypeId, String metricTypeUnit) {
        return createMetricType(tenantId, getMetricTypeJSON(metricTypeId, metricTypeUnit));
    }

    @Override
    public boolean createMetricType(MetricType metricType) {
        return createMetricType(metricType.getTenantId(), metricType.getId(),
                                metricType.getUnit().getDisplayName());
    }

    @Override
    public boolean updateMetricType(String tenantId, String metricTypeId, MetricTypeUpdateJSON update) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteMetricType(String tenantId, String metricTypeId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<ResourceType> getResourceTypes(String tenantId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResourceType getResourceType(String tenantId, String resourceTypeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<MetricType> getMetricTypes(String tenantId, String resourceTypeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<Resource> getResources(String tenantId, String resourceTypeId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean createResourceType(String tenantId, ResourceTypeJSON resourceType) {
        Response response = restApi().createResourceType(tenantId, resourceType);
        if(response.getStatus() == 201){
            _logger.debug("ResourceType[id:{},version:{}] created successfully under the tenant[{}], "
                            + "Location URI:{}",resourceType.getId(),resourceType.getVersion(),
                            tenantId,response.getLocation().toString());
            return true;
        }else{
            _logger.debug("ResourceType[id:{},version:{}] creation failed under the tenant[{}], "
                            + " Response Status code: {}",resourceType.getId(),resourceType.getVersion(),
                            tenantId,response.getStatus());
            return false;
        }
    }

    @Override
    public boolean createResourceType(String tenantId, String resourceId, String resourceVersion) {
        return createResourceType(tenantId, getResourceTypeJSON(resourceId, resourceVersion));
    }

    @Override
    public boolean createResourceType(ResourceType resourceType) {
        return createResourceType(resourceType.getTenantId(),
                                  resourceType.getId(),
                                  resourceType.getVersion().toString());
    }

    @Override
    public boolean deleteResourceType(String tenantId, String resourceTypeId) {
        // TODO Auto-generated method stub
        return false;
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
    public boolean addResource(String tenantId, String environmentId, ResourceJSON resource) {
        Response response = restApi().addResource(tenantId, environmentId, resource);
        if(response.getStatus() == 201){
            _logger.debug("Resource[id:{},typeId:{}] added successfully under the tenant[{}], environment[{}], "
                            + "Location URI:{}",resource.getId(),resource.getType().getId(),
                            tenantId,environmentId,response.getLocation().toString());
            return true;
        }else{
            _logger.debug("Unable to add Resource[id:{},typeId:{}] under the tenant[{}], environment[{}], "
                            + "Response Status Code:{}",resource.getId(),resource.getType().getId(),
                            tenantId,environmentId,response.getStatus());
            return false;
        }
    }

    @Override
    public boolean addResource(String tenantId, String environmentId, String resourceId,
                               String resourceTypeId, String resourceTypeVersion) {
        return addResource(tenantId,
                           environmentId,
                           getResourceJSON(resourceId,
                                           getResourceTypeJSON(resourceTypeId, resourceTypeVersion)));
    }

    @Override
    public boolean addResource(Resource resource) {
        return addResource(resource.getTenantId(),
                           resource.getEnvironmentId(),
                           getResourceJSON(resource.getId(),
                                           getResourceTypeJSON(resource.getType().getId(),
                                                               resource.getType().getVersion().toString())));
    }

    @Override
    public Set<Resource> getResourcesByType(String tenantId, String environmentId, String typeId, String typeVersion) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Resource getResource(String tenantId, String environmentId, String uid) {
        return restApi().getResource(tenantId, environmentId, uid);
    }

    @Override
    public boolean deleteResource(String tenantId, String environmentId, String resourceId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean addMetricToResource(String tenantId, String environmentId, String resourceId,
                                       Collection<String> metricIds) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<Metric> listMetricsOfResource(String tenantId, String environmentID, String resourceId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Metric getMetricOfResource(String tenantId, String environmentId, String resourceId, String metricId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MetricJSON getMetricJSON(String metricId, String metricTypeId) {
        MetricJSON metricJSON = new MetricJSON();
        metricJSON.setId(metricId);
        metricJSON.setMetricTypeId(metricTypeId);
        return metricJSON;
    }

    @Override
    public MetricTypeJSON getMetricTypeJSON(String metricTypeId, String metricTypeUnit) {
        MetricTypeJSON metricTypeJSON = new MetricTypeJSON();
        metricTypeJSON.setId(metricTypeId);
        metricTypeJSON.setUnit(metricTypeUnit);
        return metricTypeJSON;
    }

    @Override
    public ResourceTypeJSON getResourceTypeJSON(String resourceTypeId, String resourceTypeVersion) {
        ResourceTypeJSON resourceTypeJSON = new ResourceTypeJSON();
        resourceTypeJSON.setId(resourceTypeId);
        resourceTypeJSON.setVersion(resourceTypeVersion);
        return resourceTypeJSON;
    }

    @Override
    public ResourceJSON getResourceJSON(String resourceId, ResourceTypeJSON resourceTypeJSON) {
        ResourceJSON resourceJSON = new ResourceJSON();
        resourceJSON.setId(resourceId);
        resourceJSON.setType(resourceTypeJSON);
        return resourceJSON;
    }

}
