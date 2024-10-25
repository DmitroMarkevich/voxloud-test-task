package com.voxloud.provisioning.service;

import com.voxloud.provisioning.service.contracts.ConfigFileGenerator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PropertyFileGenerator implements ConfigFileGenerator {

    /**
     * Generates a property configuration based on the provided data.
     *
     * @param data a map containing configuration data. Each key-value pair will be
     *             converted to the format "key=value" in the properties file.
     * @return the generated properties configuration as a String.
     */
    @Override
    public String generateConfig(Map<String, Object> data) {
        StringBuilder propertyBuilder = new StringBuilder();
        data.forEach((key, value) ->
                propertyBuilder.append(key).append('=')
                        .append(value)
                        .append(System.lineSeparator()));

        return propertyBuilder.toString();
    }
}