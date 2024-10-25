package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.errorhandling.exception.UnsupportedDeviceModelException;
import com.voxloud.provisioning.service.contracts.ConfigFileGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Responsible for generating configuration files for various device models.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceConfigHandler {

    private final ConfigFileGenerator jsonFileGenerator;
    private final ConfigFileGenerator propertyFileGenerator;

    /**
     * Generates a configuration file based on the provided data and device model.
     *
     * @param configurationData the data to be included in the configuration
     * @param deviceModel       the model of the device for which the configuration is generated
     * @return the generated configuration as a String
     * @throws UnsupportedDeviceModelException if the device model is unsupported
     */
    public String generateConfigFile(Map<String, Object> configurationData, Device.DeviceModel deviceModel) {
        log.info("Generating configuration file for device model: {}", deviceModel);

        switch (deviceModel) {
            case DESK:
                log.info("Using property file generator for device model: {}", deviceModel);
                return propertyFileGenerator.generateConfig(configurationData);
            case CONFERENCE:
                log.info("Using JSON file generator for device model: {}", deviceModel);
                return jsonFileGenerator.generateConfig(configurationData);
            default:
                log.error("Unsupported device model: {}", deviceModel);
                throw new UnsupportedDeviceModelException("Unsupported device model: " + deviceModel);
        }
    }
}