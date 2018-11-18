package br.com.fatecmogidascruzes.agenda.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.agenda.service.web.AgendaEntryEditDTO;

public class EndMustBeAfterStartValidator implements ConstraintValidator<EndMustBeAfterStart, AgendaEntryEditDTO> {

	@Override
	public void initialize(EndMustBeAfterStart endMustBeAfterStart) {
	}

	@Override
	public boolean isValid(AgendaEntryEditDTO eventEditDTO, ConstraintValidatorContext constraintValidatorContext) {

		// se temInformacaoAcessibilidade
		 // se acessibilidadeVisual é nulo, return false.
		 // ou se acessibilidadeAuditiva é nulo, return false.
		
		boolean isValid = null != eventEditDTO.getStartDateTime() && null != eventEditDTO.getEndDateTime()
				&& eventEditDTO.getEndDateTime().isAfter(eventEditDTO.getStartDateTime());

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