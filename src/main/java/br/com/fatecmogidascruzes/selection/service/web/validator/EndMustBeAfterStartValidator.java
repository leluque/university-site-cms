package br.com.fatecmogidascruzes.selection.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.selection.service.web.SelectionEditDTO;

public class EndMustBeAfterStartValidator implements ConstraintValidator<EndMustBeAfterStart, SelectionEditDTO> {

	@Override
	public void initialize(EndMustBeAfterStart endMustBeAfterStart) {
	}

	@Override
	public boolean isValid(SelectionEditDTO selectionEditDTO, ConstraintValidatorContext constraintValidatorContext) {

		boolean isValid = null != selectionEditDTO.getStartDateTime() && null != selectionEditDTO.getEndDateTime()
				&& selectionEditDTO.getEndDateTime().isAfter(selectionEditDTO.getStartDateTime());

		if (!isValid) {
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext
					.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
					.addPropertyNode("endDateTime").addConstraintViolation();
			return false;
		}
		return true;

	}

}