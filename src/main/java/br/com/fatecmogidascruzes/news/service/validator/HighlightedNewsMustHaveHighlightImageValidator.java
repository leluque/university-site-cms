package br.com.fatecmogidascruzes.news.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.news.service.web.NewsEditDTO;

public class HighlightedNewsMustHaveHighlightImageValidator
		implements ConstraintValidator<HighlightedNewsMustHaveHighlightImage, NewsEditDTO> {

	@Override
	public void initialize(HighlightedNewsMustHaveHighlightImage highlightedNewsMustHaveHighlightImage) {
	}

	@Override
	public boolean isValid(NewsEditDTO newsEditDTO, ConstraintValidatorContext context) {
		if (newsEditDTO.isHighlight() && !newsEditDTO.hasNewHighlightImage()
				&& !newsEditDTO.isCurrentlyHasHighlightImage()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("highlightImage").addConstraintViolation();
			return false;
		}
		return true;
	}

}