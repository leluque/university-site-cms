package br.com.fatecmogidascruzes.agenda.service.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.agenda.service.web.AgendaEntryRepetitionEditDTO;

public class DateLimitMustBeInFutureValidator
		implements ConstraintValidator<DateLimitMustBeInFuture, AgendaEntryRepetitionEditDTO> {

	@Override
	public void initialize(DateLimitMustBeInFuture dateLimitMustBeInFuture) {
	}

	@Override
	public boolean isValid(AgendaEntryRepetitionEditDTO eventRepetitionEditDTO,
			ConstraintValidatorContext constraintValidatorContext) {
		boolean isValid = null != eventRepetitionEditDTO.getDateLimit()
				&& eventRepetitionEditDTO.getDateLimit().isAfter(LocalDate.now());

		if (!isValid) {
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext
					.buildConstraintViolationWithTemplate("A data de replicação do evento deve ser futura")
					.addPropertyNode("dateLimit").addConstraintViolation();
			return false;
		}
		return true;

	}

}