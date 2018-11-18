package br.com.fatecmogidascruzes.news.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.news.service.web.NewsEditDTO;

public class ImageMustHaveDescriptionValidator implements ConstraintValidator<ImageMustHaveDescription, NewsEditDTO> {

	@Override
	public void initialize(ImageMustHaveDescription imageMustHaveDescription) {
	}

	@Override
	public boolean isValid(NewsEditDTO newsEditDTO, ConstraintValidatorContext context) {
		if ((newsEditDTO.isCurrentlyHasImage() || newsEditDTO.hasNewImage()) && !newsEditDTO.hasImageDescription()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("imageAlternativeDescription").addConstraintViolation();

			return false;

		}

		return true;
	}
}