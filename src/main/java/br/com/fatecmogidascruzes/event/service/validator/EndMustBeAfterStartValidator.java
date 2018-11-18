package br.com.fatecmogidascruzes.event.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.event.service.web.EventEditDTO;

public class EndMustBeAfterStartValidator implements ConstraintValidator<EndMustBeAfterStart, EventEditDTO> {

	@Override
	public void initialize(EndMustBeAfterStart endMustBeAfterStart) {
	}

	@Override
	public boolean isValid(EventEditDTO eventEditDTO, ConstraintValidatorContext constraintValidatorContext) {
		boolean isValid = null != eventEditDTO.getStartDateTime() && null != eventEditDTO.getEndDateTime()
				&& eventEditDTO.getEndDateTime().isAfter(eventEditDTO.getStartDateTime());

		if (!isValid) {
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext
					.buildConstraintViolationWithTemplate(
							"A data de término do evento deve ser posterior à data de início")
					.addPropertyNode("endDateTime").addConstraintViolation();
			return false;
		}
		return true;

	}

}