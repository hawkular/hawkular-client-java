package org.hawkular.client.metrics.mixins;

import java.io.IOException;

import org.hawkular.metrics.model.Tenant;
import org.hawkular.metrics.model.TenantDefinition;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
@JsonSerialize(using = TenantSerializer.class)
@JsonDeserialize(using = TenantDeserializer.class)
public abstract class TenantMixin {
}

class TenantSerializer extends JsonSerializer<Tenant> {
    @Override
    public void serialize(Tenant tenant, JsonGenerator jgen, SerializerProvider sp) throws IOException,
            JsonProcessingException {
        if (tenant != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(jgen, new TenantDefinition(tenant));
        } else {
            jgen.writeNull();
        }
    }
}

class TenantDeserializer extends JsonDeserializer<Tenant> {
    @Override
    public Tenant deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TenantDefinition tenantDefinition = objectMapper.readValue(jp, TenantDefinition.class);
        if (tenantDefinition != null) {
            return tenantDefinition.toTenant();
        }
        return null;
    }
}
