package com.voxloud.provisioning.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.voxloud.provisioning.JsonSerializer;
import com.voxloud.provisioning.entity.Device;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OverrideFragmentManager {

    private final JsonSerializer jsonSerializer;

    /**
     * Processes the override fragment for the specified device model.
     *
     * @param data             a map containing the existing configuration data to be modified.
     * @param overrideFragment a string representation of the override configuration.
     * @param model            the device model for which the override is being processed.
     */
    public void processOverrideFragment(Map<String, Object> data, String overrideFragment, Device.DeviceModel model) {
        if (model == Device.DeviceModel.DESK) {
            processDeskProperties(data, overrideFragment);
        } else {
            Map<String, Object> fragmentData = jsonSerializer.fromJson(overrideFragment, new TypeReference<Map<String, Object>>() {
            });
            data.putAll(fragmentData);
        }
    }

    /**
     * Processes desk-specific properties from the override fragment.
     *
     * @param data             a map containing the existing configuration data to be modified.
     * @param overrideFragment a string representation of the override configuration, formatted as key=value pairs.
     */
    private void processDeskProperties(Map<String, Object> data, String overrideFragment) {
        String[] lines = overrideFragment.split("\n");
        for (String line : lines) {
            String[] keyValue = line.split("=", 2);

            if (keyValue.length != 2) {
                return;
            }

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "domain":
                case "port":
                case "timeout":
                    data.put(key, value.isEmpty() ? null : value);
                    break;
                default:
                    break;
            }
        }
    }
}