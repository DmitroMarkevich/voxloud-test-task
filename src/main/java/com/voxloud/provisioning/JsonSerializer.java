package com.voxloud.provisioning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voxloud.provisioning.errorhandling.exception.JsonSerializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonSerializer {

    private final ObjectMapper objectMapper;

    /**
     * Converts an object to its JSON representation as a String.
     *
     * @param obj the object to convert to JSON
     * @return a String representing the JSON value of the object
     * @throws JsonSerializationException if the conversion fails
     */
    public String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("Failed to convert object to JSON");
        }
    }

    /**
     * Converts a JSON String to an object of the specified type reference.
     *
     * @param json      the JSON String to convert
     * @param valueType the type reference of the object to convert to
     * @param <T>       the type of the object to be returned
     * @return an object of type T
     * @throws JsonSerializationException if the conversion fails
     */
    public <T> T fromJson(String json, TypeReference<T> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            throw new JsonSerializationException("Failed to convert JSON to object");
        }
    }
}