package org.hawkular.client.alert;

import java.util.List;

import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.ClientResponse;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public interface AlertsClient {
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

    public ClientResponse<List<Trigger>> findTriggers();

    public ClientResponse<Trigger> getTrigger(String triggerId);

    public ClientResponse<String> createTrigger(Trigger trigger);

    public ClientResponse<List<Condition>> getTriggerConditions(Trigger trigger);

    public ClientResponse<List<Condition>> getTriggerConditions(String triggerId);
}
