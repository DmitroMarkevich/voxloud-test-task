package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.errorhandling.exception.DeviceNotFoundException;
import com.voxloud.provisioning.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProvisioningServiceTest {

    @InjectMocks
    private ProvisioningServiceImpl provisioningService;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceConfigHandler deviceConfigHandler;

    @Mock
    private OverrideFragmentManager overrideFragmentManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getProvisioningFile_ShouldThrowException_WhenDeviceNotFound() {
        String macAddress = "a1-b2-c3-d4-e5-f6";

        when(deviceRepository.findById(macAddress)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> provisioningService.getProvisioningFile(macAddress));

        verify(deviceRepository).findById(macAddress);
        verifyNoMoreInteractions(deviceRepository);
    }

    @Test
    void getProvisioningFile_ShouldReturnConfigFile_WhenDeviceFound() {
        String macAddress = "a1-b2-c3-d4-e5-f6";

        Device device = new Device();
        device.setUsername("testUser");
        device.setPassword("testPassword");
        device.setOverrideFragment("testFragment");
        device.setModel(Device.DeviceModel.DESK);

        when(deviceRepository.findById(macAddress)).thenReturn(Optional.of(device));
        when(deviceConfigHandler.generateConfigFile(any(Map.class), eq(device.getModel()))).thenReturn("expectedConfigFileContent");

        String actualConfigFile = provisioningService.getProvisioningFile(macAddress);

        assertEquals("expectedConfigFileContent", actualConfigFile);
        verify(deviceRepository).findById(macAddress);
        verify(deviceConfigHandler).generateConfigFile(any(Map.class), eq(device.getModel()));
        verify(overrideFragmentManager).processOverrideFragment(any(Map.class), eq(device.getOverrideFragment()), eq(device.getModel()));
    }

}
