package br.com.fatecmogidascruzes.news.service.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = HighlightedNewsMustHaveHighlightImageValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HighlightedNewsMustHaveHighlightImage {

	String message() default "A imagem de destaque é obrigatória para uma notícia destaque";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
