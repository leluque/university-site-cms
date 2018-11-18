package br.com.fatecmogidascruzes.user.service.web.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = UserPasswordMustNotBeBlankOnInclusionValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPasswordMustNotBeBlankOnInclusion {

	String message() default "A senha do usuário é obrigatória";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}