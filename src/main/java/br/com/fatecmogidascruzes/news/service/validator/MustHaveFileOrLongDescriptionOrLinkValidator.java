package br.com.fatecmogidascruzes.news.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.news.service.web.NewsEditDTO;

public class MustHaveFileOrLongDescriptionOrLinkValidator
		implements ConstraintValidator<MustHaveFileOrLongDescriptionOrLink, NewsEditDTO> {

	@Override
	public void initialize(MustHaveFileOrLongDescriptionOrLink mustHaveFileOrLongDescription) {
	}

	@Override
	public boolean isValid(NewsEditDTO newsEditDTO, ConstraintValidatorContext context) {
		if (!newsEditDTO.isCurrentlyHasFile() && !newsEditDTO.hasNewFile() && !newsEditDTO.hasContent() && !newsEditDTO.hasLink()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("longDescription").addConstraintViolation();

			return false;

		}

		return true;
	}
}