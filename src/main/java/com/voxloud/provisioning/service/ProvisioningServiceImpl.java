package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.errorhandling.exception.DeviceNotFoundException;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.service.contracts.ProvisioningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProvisioningServiceImpl implements ProvisioningService {

    private final DeviceRepository deviceRepository;
    private final DeviceConfigHandler deviceConfigHandler;
    private final OverrideFragmentManager overrideFragmentManager;

    @Value("${provisioning.port}")
    private String provisioningPort;

    @Value("${provisioning.domain}")
    private String provisioningDomain;

    @Value("${provisioning.codecs}")
    private String provisioningCodecs;

    /**
     * Retrieves the provisioning file for a specified device identified by its MAC address.
     *
     * @param macAddress the MAC address of the device for which the provisioning file is requested
     * @return a String representing the generated provisioning file content
     * @throws DeviceNotFoundException if no device is found with the specified MAC address
     */
    public String getProvisioningFile(String macAddress) {
        log.info("Requesting provisioning file for MAC address: {}", macAddress);

        Device device = deviceRepository.findById(macAddress)
                .orElseThrow(() -> new DeviceNotFoundException(
                        String.format("Unable to locate the device with MAC address: %s", macAddress)));

        Map<String, Object> deviceData = new HashMap<>();
        deviceData.put("username", device.getUsername());
        deviceData.put("password", device.getPassword());
        deviceData.put("port", provisioningPort);
        deviceData.put("domain", provisioningDomain);
        deviceData.put("codecs", provisioningCodecs);

        if (Objects.nonNull(device.getOverrideFragment()) && !device.getOverrideFragment().isEmpty()) {
            log.info("Processing override fragment for device model: {}", device.getModel());
            overrideFragmentManager.processOverrideFragment(deviceData, device.getOverrideFragment(), device.getModel());
        }

        return deviceConfigHandler.generateConfigFile(deviceData, device.getModel());
    }
}