package br.com.fatecmogidascruzes.event.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.event.service.web.EventEditDTO;

public class BigImageMustHaveDescriptionValidator implements ConstraintValidator<BigImageMustHaveDescription, EventEditDTO> {

	@Override
	public void initialize(BigImageMustHaveDescription bigImageMustHaveDescription) {
	}

	@Override
	public boolean isValid(EventEditDTO eventEditDTO, ConstraintValidatorContext context) {
		if ((eventEditDTO.isCurrentlyHasBigImage() || eventEditDTO.hasNewBigImage()) && !eventEditDTO.hasBigImageDescription()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("bigImageAlternativeDescription").addConstraintViolation();

			return false;

		}

		return true;
	}
}