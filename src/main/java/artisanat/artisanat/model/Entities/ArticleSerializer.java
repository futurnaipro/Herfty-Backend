package artisanat.artisanat.model.Entities;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ArticleSerializer extends JsonSerializer<Article> {
    
    @Override
    public void serialize(Article article, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", article.getId());
        gen.writeStringField("nom", article.getNom());
        // ... serialize other fields
        gen.writeEndObject();
    }

}
