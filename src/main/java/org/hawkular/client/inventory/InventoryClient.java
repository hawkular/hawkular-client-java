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

import org.hawkular.client.inventory.model.IdJSON;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.MetricUnit;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.api.model.Version;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface InventoryClient {

    //PingHandler/Ping
    String pingHello();
    String pingTime();

    //TenantJson
    List<Tenant> getTenants();
    boolean createTenant(IdJSON tenantId);
    boolean createTenant(String tenantId);
    boolean updateTenant(String tenantId, Map<String, Object> properties);
    boolean deleteTenant(String tenantId);
    boolean deleteTenant(Tenant tenant);
    boolean createTenant(Tenant tenant);

    //Environment
    List<Environment> getEnvironments(String tenantId);
    Environment getEnvironment(String tenantId, String environmentId);
    Environment getEnvironment(Environment environment);
    boolean createEnvironment(String tenantId, IdJSON environmentId);
    boolean createEnvironment(String tenantId, String environmentId);
    boolean createEnvironment(Environment environment);
    boolean updateEnvironment(String tenantId, String environmentId, Map<String, Object> properties);
    boolean deleteEnvironment(String tenantId, String environmentId);
    boolean deleteEnvironment(Environment environment);

    //MetricType
    List<MetricType> getMetricTypes(String tenantId);
    MetricType getMetricType(String tenantId, String metricTypeId);
    MetricType getMetricType(MetricType metricType);
    boolean createMetricType(String tenantId, String metricTypeId, MetricUnit unit);
    boolean createMetricType(MetricType metricType);
    boolean createMetricType(String tenantId, MetricType.Blueprint metricType);
    boolean updateMetricType(String tenantId, String metricTypeId,
                             MetricType metricType);
    boolean deleteMetricType(String tenantId,
                             String metricTypeId);
    boolean deleteMetricType(MetricType metricType);

    //Metrics
    boolean createMetric(Metric metric);
    boolean createMetric(String tenantId,String environmentId,Metric.Blueprint metric);
    boolean createMetric(String tenantId,String environmentId,String metricId,String metricTypeId);
    boolean createMetric(String tenantId,String environmentId,String metricId,MetricType type);
    Metric getMetric(String tenantId,String environmentId,String metricId);
    Metric getMetric(Metric metric);
    List<Metric> getMetrics(String tenantId,String environmentId);
    boolean updateMetric(String tenantId,
                         String environmentId,
                         String metricId,
                         Metric metric);
    boolean deleteMetric(Metric metric);
    boolean deleteMetric(String tenantId,
                         String environmentId,
                         String metricId);

    //ResourceType
    List<ResourceType> getResourceTypes(String tenantId);
    ResourceType getResourceType(String tenantId, String resourceTypeId);
    ResourceType getResourceType(ResourceType resourceType);
    List<MetricType> getMetricTypes(String tenantId, String resourceTypeId);
    List<Resource> getResources(String tenantId, String resourceTypeId);
    boolean createResourceType(ResourceType resourceType);
    boolean createResourceType(String tenantId, ResourceType.Blueprint resourceType);
    boolean createResourceType(String tenantId, String resourceId, String resourceVersion);
    boolean createResourceType(String tenantId, String resourceId, Version resourceVersion);
    boolean deleteResourceType(String tenantId, String resourceTypeId);
    boolean deleteResourceType(ResourceType resourceType);
    boolean addMetricType(String tenantId, String resourceTypeId, IdJSON metricTypeId);
    boolean removeMetricType(String tenantId, String resourceTypeId, String metricTypeId);

    //Resource
    boolean addResource(Resource resource);
    boolean addResource(String tenantId, String environmentId, Resource.Blueprint resource);
    boolean addResource( String tenantId, String environmentId, String resourceId,
                         String resourceTypeId);
    List<Resource> getResourcesByType(String tenantId, String environmentId, String typeId, String typeVersion);
    Resource getResource(String tenantId, String environmentId, String uid);
    Resource getResource(Resource resource);
    boolean deleteResource(String tenantId, String environmentId, String resourceId);
    boolean deleteResource(Resource resource);
    boolean addMetricToResource(String tenantId, String environmentId, String resourceId,Collection<String> metricIds);
    List<Metric> listMetricsOfResource(String tenantId, String environmentID, String resourceId);
    Metric getMetricOfResource(String tenantId, String environmentId, String resourceId, String metricId);
}
