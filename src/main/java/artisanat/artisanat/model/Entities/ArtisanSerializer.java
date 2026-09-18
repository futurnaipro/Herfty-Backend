package artisanat.artisanat.model.Entities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ArtisanSerializer extends JsonSerializer<Artisan> {
@Override
    public void serialize(Artisan artisan, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", artisan.getId());
        gen.writeEndObject();
    }
}
