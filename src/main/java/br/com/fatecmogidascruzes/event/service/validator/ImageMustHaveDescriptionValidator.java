package br.com.fatecmogidascruzes.event.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.event.service.web.EventEditDTO;

public class ImageMustHaveDescriptionValidator implements ConstraintValidator<ImageMustHaveDescription, EventEditDTO> {

	@Override
	public void initialize(ImageMustHaveDescription imageMustHaveDescription) {
	}

	@Override
	public boolean isValid(EventEditDTO eventEditDTO, ConstraintValidatorContext context) {
		if ((eventEditDTO.isCurrentlyHasImage() || eventEditDTO.hasNewImage()) && !eventEditDTO.hasImageDescription()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("imageAlternativeDescription").addConstraintViolation();

			return false;

		}

		return true;
	}
}