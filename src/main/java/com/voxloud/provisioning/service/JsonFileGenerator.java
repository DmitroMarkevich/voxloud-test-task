package com.voxloud.provisioning.service;

import com.voxloud.provisioning.JsonSerializer;
import com.voxloud.provisioning.service.contracts.ConfigFileGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonFileGenerator implements ConfigFileGenerator {

    private final JsonSerializer jsonSerializer;

    /**
     * Generates a JSON configuration based on the provided data.
     *
     * @param data a map containing configuration data. The map can include a key "codecs"
     *             which will be processed to convert a comma-separated string into a list.
     * @return the generated JSON configuration as a String.
     */
    @Override
    public String generateConfig(Map<String, Object> data) {
        if (data.containsKey("codecs")) {
            String codecsString = String.valueOf(data.get("codecs"));
            data.put("codecs", Arrays.asList(codecsString.split(",")));
        }

        return jsonSerializer.toJson(data);
    }
}