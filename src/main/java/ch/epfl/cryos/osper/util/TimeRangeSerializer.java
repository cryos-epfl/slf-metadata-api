package ch.epfl.cryos.osper.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Range;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by kryvych on 27/10/16.
 */
public class TimeRangeSerializer extends JsonSerializer<Range> {


    @Override
    public void serialize(Range value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeObjectField("from", value.lowerEndpoint());
        if (value.hasUpperBound()) {
            jgen.writeObjectField("until", value.upperEndpoint());
        }
        jgen.writeEndObject();
    }

}