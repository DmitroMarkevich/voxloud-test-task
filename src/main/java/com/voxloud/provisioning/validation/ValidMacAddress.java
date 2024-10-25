package com.voxloud.provisioning.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MacAddressValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMacAddress {

    String message() default "Invalid MAC address format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}