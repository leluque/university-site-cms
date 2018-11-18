package br.com.fatecmogidascruzes.gallery.service.web.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AlbumMustHaveCoverValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AlbumMustHaveCover {

	String message() default "A capa do álbum é obrigatória";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
