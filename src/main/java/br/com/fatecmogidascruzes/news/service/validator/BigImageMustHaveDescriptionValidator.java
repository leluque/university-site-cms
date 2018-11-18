package br.com.fatecmogidascruzes.news.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.news.service.web.NewsEditDTO;

public class BigImageMustHaveDescriptionValidator implements ConstraintValidator<BigImageMustHaveDescription, NewsEditDTO> {

	@Override
	public void initialize(BigImageMustHaveDescription bigImageMustHaveDescription) {
	}

	@Override
	public boolean isValid(NewsEditDTO newsEditDTO, ConstraintValidatorContext context) {
		if ((newsEditDTO.isCurrentlyHasBigImage() || newsEditDTO.hasNewBigImage()) && !newsEditDTO.hasBigImageDescription()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("bigImageAlternativeDescription").addConstraintViolation();

			return false;

		}

		return true;
	}
}