package com.voxloud.provisioning.validation;

import com.voxloud.provisioning.errorhandling.exception.InvalidMacAddressException;
import com.voxloud.provisioning.validation.MacAddressValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

class MacAddressValidatorTest {

    private MacAddressValidator macAddressValidator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        macAddressValidator = new MacAddressValidator();
        context = org.mockito.Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    void testValidMacAddress() {
        String validMacAddress = "00:1A:2B:3C:4D:5E";
        assertTrue(macAddressValidator.isValid(validMacAddress, context),
                "The MAC address should be valid."
        );
    }

    @Test
    void testInvalidMacAddressFormat() {
        String invalidMacAddress = "00:1A:2B:3C:4D:G6";

        InvalidMacAddressException thrown = assertThrows(
                InvalidMacAddressException.class,
                () -> macAddressValidator.isValid(invalidMacAddress, context),
                "Expected isValid() to throw, but it didn't"
        );
    }

    @Test
    void testNullMacAddress() {
        String nullMacAddress = null;

        InvalidMacAddressException thrown = assertThrows(
                InvalidMacAddressException.class,
                () -> macAddressValidator.isValid(nullMacAddress, context),
                "Expected isValid() to throw, but it didn't"
        );

        assertEquals("MAC address cannot be null", thrown.getMessage());
    }

    @Test
    void testEmptyMacAddress() {
        String emptyMacAddress = "";

        InvalidMacAddressException thrown = assertThrows(
                InvalidMacAddressException.class,
                () -> macAddressValidator.isValid(emptyMacAddress, context),
                "Expected isValid() to throw, but it didn't"
        );

        assertEquals("MAC address cannot be blank", thrown.getMessage());
    }
}
