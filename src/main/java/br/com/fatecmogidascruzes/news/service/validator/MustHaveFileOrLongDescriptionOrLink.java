package br.com.fatecmogidascruzes.news.service.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = MustHaveFileOrLongDescriptionOrLinkValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MustHaveFileOrLongDescriptionOrLink {

	String message() default "A notícia deve ter ou um arquivo PDF, um conteúdo ou um link associado";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
