package br.com.fatecmogidascruzes.event.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.event.service.web.EventEditDTO;

public class MustHaveImageValidator implements ConstraintValidator<MustHaveImage, EventEditDTO> {

	@Override
	public void initialize(MustHaveImage mustHaveImage) {
	}

	@Override
	public boolean isValid(EventEditDTO eventEditDTO, ConstraintValidatorContext context) {
		if (!eventEditDTO.hasNewImage() && !eventEditDTO.isCurrentlyHasImage()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("image").addConstraintViolation();
			return false;
		}
		return true;
	}

}