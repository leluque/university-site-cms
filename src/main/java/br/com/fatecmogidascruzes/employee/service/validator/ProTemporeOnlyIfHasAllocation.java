package br.com.fatecmogidascruzes.employee.service.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ProTemporeOnlyIfHasAllocationValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProTemporeOnlyIfHasAllocation {

	String message() default "O funcionário só deve ser marcado como pro tempore se tiver alocado em alguma função";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}