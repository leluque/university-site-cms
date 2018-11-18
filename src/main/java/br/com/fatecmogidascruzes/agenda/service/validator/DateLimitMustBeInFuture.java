package br.com.fatecmogidascruzes.agenda.service.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = DateLimitMustBeInFutureValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateLimitMustBeInFuture {

	String message() default "A data de replicação do evento deve ser futura";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}