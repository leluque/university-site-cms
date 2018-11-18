package br.com.fatecmogidascruzes.testimonial.service.web.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = TestimonialMustHaveImageValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestimonialMustHaveImage {

	String message() default "A imagem da categoria é obrigatória";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
