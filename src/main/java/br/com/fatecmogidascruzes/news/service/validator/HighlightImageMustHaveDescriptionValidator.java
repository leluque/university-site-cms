package br.com.fatecmogidascruzes.news.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.news.service.web.NewsEditDTO;

public class HighlightImageMustHaveDescriptionValidator
		implements ConstraintValidator<HighlightImageMustHaveDescription, NewsEditDTO> {

	@Override
	public void initialize(HighlightImageMustHaveDescription highlightImageMustHaveDescription) {
	}

	@Override
	public boolean isValid(NewsEditDTO newsEditDTO, ConstraintValidatorContext context) {
		if (newsEditDTO.isHighlight() && !newsEditDTO.hasHighlightImageDescription()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("highlightImageAlternativeDescription").addConstraintViolation();

			return false;

		}

		return true;
	}
}