package com.voxloud.provisioning.service.contracts;

import java.util.Map;

public interface ConfigFileGenerator {

    String generateConfig(Map<String, Object> configurationData);
}
