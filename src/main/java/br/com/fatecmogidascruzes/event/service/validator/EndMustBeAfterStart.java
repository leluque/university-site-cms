package br.com.fatecmogidascruzes.event.service.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = EndMustBeAfterStartValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndMustBeAfterStart {

	String message() default "A data de término do evento deve ser posterior à data de início";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}