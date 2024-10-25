package com.voxloud.provisioning.validation;

import com.voxloud.provisioning.errorhandling.exception.InvalidMacAddressException;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Objects;

@Slf4j
public class MacAddressValidator implements ConstraintValidator<ValidMacAddress, String> {

    private static final String MAC_ADDRESS_REGEX = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$";

    @Override
    public void initialize(ValidMacAddress constraintAnnotation) {
    }

    @Override
    public boolean isValid(String macAddress, ConstraintValidatorContext context) {
        if (Objects.isNull(macAddress)) {
            log.error("Validation failed: MAC address is null");
            throw new InvalidMacAddressException("MAC address cannot be null");
        }

        if (macAddress.isEmpty()) {
            log.error("Validation failed: MAC address is blank");
            throw new InvalidMacAddressException("MAC address cannot be blank");
        }

        if (!macAddress.matches(MAC_ADDRESS_REGEX)) {
            log.error("Validation failed: MAC address does not match the expected format: {}", macAddress);
            throw new InvalidMacAddressException(context.getDefaultConstraintMessageTemplate());
        }

        log.info("MAC address validation successful for: {}", macAddress);
        return true;
    }
}