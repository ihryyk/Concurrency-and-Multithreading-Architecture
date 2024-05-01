package org.example.task5.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonObjectMapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String toJson(T payload) {
        try {
            return OBJECT_MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException jpe) {
            String message = "Failed to convert to JSON.";
            log.error(message, jpe);
            throw new IllegalArgumentException(message, jpe);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException jpe) {
            String message = String.format("Failed to map JSON to %s.", clazz.getSimpleName());
            log.error(message, jpe);
            throw new IllegalArgumentException(message, jpe);
        }
    }

}
