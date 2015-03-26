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
import java.util.Set;

import org.hawkular.client.inventory.model.IdJSON;
import org.hawkular.client.inventory.model.MetricJSON;
import org.hawkular.client.inventory.model.MetricTypeJSON;
import org.hawkular.client.inventory.model.MetricTypeUpdateJSON;
import org.hawkular.client.inventory.model.MetricUpdateJSON;
import org.hawkular.client.inventory.model.ResourceJSON;
import org.hawkular.client.inventory.model.ResourceTypeJSON;
import org.hawkular.inventory.api.model.Environment;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;
/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface InventoryClient {

    //PingHandler/Ping
    String pingHello();
    String pingTime();

    //Tenant
    List<Tenant> getTenants();
    boolean createTenant(IdJSON tenantId);
    boolean createTenant(String tenantId);
    boolean updateTenant(String tenantId, Map<String, Object> properties);
    boolean deleteTenant(String tenantId);

    //Environment
    Set<Environment> getEnvironments(String tenantId);
    Environment getEnvironment(String tenantId, String environmentId);
    boolean createEnvironment(String tenantId, IdJSON environmentId);
    boolean createEnvironment(String tenantId, String environmentId);
    boolean createEnvironment(Environment environment);
    boolean updateEnvironment(String tenantId, String environmentId, Map<String, Object> properties);
    boolean deleteEnvironment(String tenantId, String environmentId);

    //MetricType
    MetricType getMetricType(String tenantId, String metricTypeId);
    boolean createMetricType(String tenantId, MetricTypeJSON metricType);
    boolean createMetricType(String tenantId, String metricTypeId, String metricTypeUnit);
    boolean createMetricType(MetricType metricType);
    boolean updateMetricType(String tenantId, String metricTypeId,
                             MetricTypeUpdateJSON update);
    boolean deleteMetricType(String tenantId,
                             String metricTypeId);

    //Metrics
    boolean createMetric(String tenantId,String environmentId,MetricJSON metric);
    boolean createMetric(String tenantId,String environmentId,String metricId,String metricTypeId);
    boolean createMetric(Metric metric);
    Metric getMetric(String tenantId,String environmentId,String metricId);
    Set<Metric> getMetrics(String tenantId,String environmentId);
    boolean updateMetric(String tenantId,
                         String environmentId,
                         String metricId,
                         MetricUpdateJSON updates);
    boolean deleteMetric(String tenantId,
                         String environmentId,
                         String metricId);
    Set<MetricType> getMetricTypes(String tenantId);

    //ResourceType
    Set<ResourceType> getResourceTypes(String tenantId);
    ResourceType getResourceType(String tenantId, String resourceTypeId);
    Set<MetricType> getMetricTypes(String tenantId, String resourceTypeId);
    Set<Resource> getResources(String tenantId, String resourceTypeId);
    boolean createResourceType(String tenantId, ResourceTypeJSON resourceType);
    boolean createResourceType(String tenantId, String resourceId, String resourceVersion);
    boolean createResourceType(ResourceType resourceType);
    boolean deleteResourceType(String tenantId, String resourceTypeId);
    boolean addMetricType(String tenantId, String resourceTypeId, IdJSON metricTypeId);
    boolean removeMetricType(String tenantId, String resourceTypeId, String metricTypeId);

    //Resource
    boolean addResource(String tenantId, String environmentId, ResourceJSON resource);
    boolean addResource( String tenantId, String environmentId, String resourceId,
                         String resourceTypeId, String resourceTypeVersion );
    boolean addResource(Resource resource);
    Set<Resource> getResourcesByType(String tenantId, String environmentId, String typeId, String typeVersion);
    Resource getResource(String tenantId, String environmentId, String uid);
    boolean deleteResource(String tenantId, String environmentId, String resourceId);
    boolean addMetricToResource(String tenantId, String environmentId, String resourceId,Collection<String> metricIds);
    Set<Metric> listMetricsOfResource(String tenantId, String environmentID, String resourceId);
    Metric getMetricOfResource(String tenantId, String environmentId, String resourceId, String metricId);
}
