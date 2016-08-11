/*
 * Copyright 2015-2016 Red Hat, Inc. and/or its affiliates
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
package org.hawkular.client.alert.model;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class AlertsParams {

    Long startTime;
    Long endTime;
    String alertIds;
    String triggerIds;
    String statuses;
    String severities;
    String tags;
    Boolean thin;

    public AlertsParams() {

    }

    public AlertsParams(Long startTime, Long endTime) {
        this(startTime, endTime, null, null, null, null, null, null);
    }

    public AlertsParams(String tags) {
        this(null, null, null, null, null, null, tags, null);
    }

    public AlertsParams(Long startTime, Long endTime, String alertIds, String triggerIds, String statuses,
            String severities, String tags, Boolean thin) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.alertIds = alertIds;
        this.triggerIds = triggerIds;
        this.statuses = statuses;
        this.severities = severities;
        this.tags = tags;
        this.thin = thin;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Start Time: ").append(this.startTime);
        builder.append("End Time: ").append(this.endTime);
        builder.append("Alert Ids: ").append(this.alertIds);
        builder.append("Trigger Ids: ").append(this.triggerIds);
        builder.append("Statuses: ").append(this.statuses);
        builder.append("Severities: ").append(this.severities);
        builder.append("Tags: ").append(this.tags);
        builder.append("Thin: ").append(this.thin);
        return builder.toString();
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getAlertIds() {
        return alertIds;
    }

    public void setAlertIds(String alertIds) {
        this.alertIds = alertIds;
    }

    public String getTriggerIds() {
        return triggerIds;
    }

    public void setTriggerIds(String triggerIds) {
        this.triggerIds = triggerIds;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    public String getSeverities() {
        return severities;
    }

    public void setSeverities(String severities) {
        this.severities = severities;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Boolean getThin() {
        return thin;
    }

    public void setThin(Boolean thin) {
        this.thin = thin;
    }

}
