/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dhbw.ka.mwi.businesshorizon2.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author DHBW WWI KA
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsValidOrderPostRequestValidator.class})
public @interface IsValidOrderPostRequest {

    String message() default "The order & seasonal order must be null or must consist of 3 integers >= 0, seasonal order of 4 integers >= 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
