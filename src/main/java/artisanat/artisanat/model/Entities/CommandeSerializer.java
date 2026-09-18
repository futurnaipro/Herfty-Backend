package artisanat.artisanat.model.Entities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CommandeSerializer extends JsonSerializer<Commande> {
@Override
    public void serialize(Commande commande, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id",commande.getId());
        gen.writeEndObject();
    }
}
