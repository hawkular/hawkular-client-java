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
import org.hawkular.inventory.api.model.Feed;
import org.hawkular.inventory.api.model.Metric;
import org.hawkular.inventory.api.model.MetricType;
import org.hawkular.inventory.api.model.MetricUnit;
import org.hawkular.inventory.api.model.Resource;
import org.hawkular.inventory.api.model.ResourceType;
import org.hawkular.inventory.api.model.Tenant;
import org.hawkular.inventory.api.model.Tenant.Update;
import org.hawkular.inventory.api.model.Version;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface InventoryClient {

    public enum RESPONSE_CODE {
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
    String pingHello();

    String pingTime();

    //Tenant
    Tenant getTenant();

    boolean updateTenant(Map<String, Object> properties);

    boolean updateTenant(Update updateTenant);

    boolean deleteTenant();

    //Environment
    List<Environment> getEnvironments();

    Environment getEnvironment(String environmentId);

    Environment getEnvironment(Environment environment);

    boolean createEnvironment(String environmentId, Map<String, Object> properties);

    boolean createEnvironment(String environmentId);

    boolean createEnvironment(Environment.Blueprint environmentBlueprint);

    boolean createEnvironment(Environment environment);

    boolean updateEnvironment(String environmentId, Map<String, Object> properties);

    boolean updateEnvironment(String environmentId, Environment.Update update);

    boolean updateEnvironment(Environment environment);

    boolean deleteEnvironment(String environmentId);

    boolean deleteEnvironment(Environment environment);

    //MetricType
    List<MetricType> getMetricTypes();

    MetricType getMetricType(String metricTypeId);

    MetricType getMetricType(MetricType metricType);

    boolean createMetricType(String metricTypeId, MetricUnit unit);

    boolean createMetricType(MetricType metricType);

    boolean createMetricType(MetricType.Blueprint metricType);

    boolean updateMetricType(String metricTypeId,
            MetricType metricType);

    boolean updateMetricType(String metricTypeId,
            MetricType.Update metricUpdate);

    boolean deleteMetricType(String metricTypeId);

    boolean deleteMetricType(MetricType metricType);

    //Metrics
    boolean createMetric(Metric metric);

    boolean createMetric(String environmentId, Metric.Blueprint metric);

    boolean createMetric(String environmentId, String feedId, Metric.Blueprint metric);

    Metric getMetric(String environmentId, String metricId);

    Metric getMetric(String environmentId, String metricId, String feedId);

    Metric getMetric(Metric metric);

    List<Metric> getMetrics(String environmentId);

    List<Metric> getMetrics(String environmentId, String feedId);

    boolean updateMetric(Metric metric);

    boolean updateMetric(String environmentId, String metricId, Metric.Update metricUpdate);

    boolean updateMetric(String environmentId, String feedId, String metricId, Metric.Update metricUpdate);

    boolean deleteMetric(Metric metric);

    boolean deleteMetric(String environmentId,
            String metricId);

    boolean deleteMetric(String environmentId,
            String feedId,
            String metricId);

    //ResourceType
    List<ResourceType> getResourceTypes();

    ResourceType getResourceType(String resourceTypeId);

    ResourceType getResourceType(ResourceType resourceType);

    List<MetricType> getMetricTypes(String resourceTypeId);

    List<Resource> getResources(String resourceTypeId);

    boolean createResourceType(ResourceType resourceType);

    boolean createResourceType(ResourceType.Blueprint resourceType);

    boolean createResourceType(String resourceId, String resourceVersion);

    boolean createResourceType(String resourceId, Version resourceVersion);

    boolean updateResourceType(String resourceTypeId, ResourceType.Update resourceTypeUpdate);

    boolean updateResourceType(ResourceType resourceType);

    boolean deleteResourceType(String resourceTypeId);

    boolean deleteResourceType(ResourceType resourceType);

    boolean addMetricType(String resourceTypeId, IdJSON metricTypeId);

    boolean removeMetricType(String resourceTypeId, String metricTypeId);

    //Resource
    boolean addResource(Resource resource);

    boolean addResource(String environmentId, Resource.Blueprint resource);

    boolean addResource(String environmentId, String feedId, Resource.Blueprint resource);

    List<Resource> getResourcesByType(String environmentId, String typeId, String typeVersion, boolean feedless);

    List<Resource> getResourcesByType(String environmentId, String typeId, String typeVersion);

    List<Resource> getResourcesByType(String environmentId, String feedId, String typeId, String typeVersion);

    Resource getResource(String environmentId, String feedId, String resourceId);

    Resource getResource(String environmentId, String resourceId);

    Resource getResource(Resource resource);

    boolean updateResource(String environmentId, String resourceId, Resource.Update update);

    boolean updateResource(String environmentId, String feedId, String resourceId, Resource.Update update);

    boolean updateResource(Resource resource);

    boolean deleteResource(String environmentId, String feedId, String resourceId);

    boolean deleteResource(String environmentId, String resourceId);

    boolean deleteResource(Resource resource);

    boolean addMetricToResource(String environmentId, String feedId, String resourceId, Collection<String> metricIds);

    boolean addMetricToResource(String environmentId, String resourceId, Collection<String> metricIds);

    List<Metric> listMetricsOfResource(String environmentID, String feedId, String resourceId);

    List<Metric> listMetricsOfResource(String environmentID, String resourceId);

    Metric getMetricOfResource(String environmentId, String feedId, String resourceId, String metricId);

    Metric getMetricOfResource(String environmentId, String resourceId, String metricId);

    //Feed
    boolean registerFeed(String environmentId, Feed.Blueprint feed);

    boolean registerFeed(Feed feed);

    List<Feed> getAllFeeds(String environmentId);

    Feed getFeed(String environmentId, String feedId);

    Feed getFeed(Feed feed);

    boolean updateFeed(String environmentId, String feedId, Feed.Update update);

    boolean updateFeed(Feed feed);

    boolean deleteFeed(String environmentId, String feedId);

    boolean deleteFeed(Feed feed);
}
