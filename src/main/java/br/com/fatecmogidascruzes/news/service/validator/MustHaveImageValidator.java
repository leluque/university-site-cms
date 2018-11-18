package br.com.fatecmogidascruzes.news.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.news.service.web.NewsEditDTO;

public class MustHaveImageValidator implements ConstraintValidator<MustHaveImage, NewsEditDTO> {

	@Override
	public void initialize(MustHaveImage mustHaveImage) {
	}

	@Override
	public boolean isValid(NewsEditDTO newsEditDTO, ConstraintValidatorContext context) {
		if (!newsEditDTO.hasNewImage() && !newsEditDTO.isCurrentlyHasImage()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("image").addConstraintViolation();
			return false;
		}
		return true;
	}

}