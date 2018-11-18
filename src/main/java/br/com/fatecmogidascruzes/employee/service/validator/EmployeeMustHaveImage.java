package br.com.fatecmogidascruzes.employee.service.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmployeeMustHaveImageValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeMustHaveImage {

	String message() default "A foto do funcionário é obrigatória";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
