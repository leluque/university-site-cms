package br.com.fatecmogidascruzes.user.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.user.service.web.PasswordEditDTO;

public class PasswordChangeMustNotBeBlankOnInclusionValidator
		implements ConstraintValidator<PasswordChangeMustNotBeBlankOnInclusion, PasswordEditDTO> {

	@Override
	public void initialize(PasswordChangeMustNotBeBlankOnInclusion passwordMustNotBeBlankOnInclusion) {
	}

	@Override
	public boolean isValid(PasswordEditDTO PasswordEditDTO, ConstraintValidatorContext constraintValidatorContext) {

		boolean isValid = true;
		if ((null == PasswordEditDTO.getHashString() || PasswordEditDTO.getHashString().isEmpty())
				&& (null == PasswordEditDTO.getPassword() || PasswordEditDTO.getPassword().trim().length() < 8)) {
			isValid = false;
		}

		if (!isValid) {
			constraintValidatorContext.disableDefaultConstraintViolation();
			if (PasswordEditDTO.getPassword().trim().length() < 8) {
				constraintValidatorContext
						.buildConstraintViolationWithTemplate("A senha do usuário deve ter no mínimo 8 caracteres")
						.addPropertyNode("password").addConstraintViolation();
			} else {
				constraintValidatorContext.buildConstraintViolationWithTemplate("A senha do usuário é obrigatória")
						.addPropertyNode("password").addConstraintViolation();
			}
			return false;
		}
		return true;

	}

}