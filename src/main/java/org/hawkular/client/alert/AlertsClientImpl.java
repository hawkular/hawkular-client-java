package org.hawkular.client.alert;

import java.net.URI;
import java.util.List;

import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.BaseClient;
import org.hawkular.client.ClientResponse;
import org.hawkular.client.RestFactory;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class AlertsClientImpl extends BaseClient<AlertsRestApi> implements AlertsClient {

    public AlertsClientImpl(URI endpointUri, String username, String password) throws Exception {
        super(endpointUri, username, password, new RestFactory<AlertsRestApi>(AlertsRestApi.class));
    }

    //Triggers
    @Override
    public ClientResponse<List<Trigger>> findTriggers() {
        return new ClientResponse<List<Trigger>>(Trigger.class, restApi().findTriggers(),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }

    @Override
    public ClientResponse<Trigger> getTrigger(String triggerId) {
        return new ClientResponse<Trigger>(Trigger.class, restApi().getTrigger(triggerId),
                RESPONSE_CODE.GET_SUCCESS.value());
    }

    @Override
    public ClientResponse<String> createTrigger(Trigger trigger) {
        return new ClientResponse<String>(String.class, restApi().createTrigger(trigger),
                RESPONSE_CODE.CREATE_SUCCESS.value());
    }

    @Override
    public ClientResponse<List<Condition>> getTriggerConditions(Trigger trigger) {
        return getTriggerConditions(trigger.getId());
    }

    @Override
    public ClientResponse<List<Condition>> getTriggerConditions(String triggerId) {
        return new ClientResponse<List<Condition>>(Condition.class, restApi().getTriggerConditions(triggerId),
                RESPONSE_CODE.GET_SUCCESS.value(), true);
    }
}
