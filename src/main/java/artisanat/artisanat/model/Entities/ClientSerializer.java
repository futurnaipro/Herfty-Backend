package artisanat.artisanat.model.Entities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ClientSerializer extends JsonSerializer<Client> {
@Override
    public void serialize(Client client, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", client.getId());
        gen.writeStringField("nom", client.getNom());
        gen.writeStringField("prenom", client.getPrenom());
        gen.writeStringField("email", client.getEmail());
        gen.writeEndObject();
    }
}
