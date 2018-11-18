package br.com.fatecmogidascruzes.user.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.user.service.web.UserEditDTO;

public class UserPasswordMustNotBeBlankOnInclusionValidator
		implements ConstraintValidator<UserPasswordMustNotBeBlankOnInclusion, UserEditDTO> {

	@Override
	public void initialize(UserPasswordMustNotBeBlankOnInclusion passwordMustNotBeBlankOnInclusion) {
	}

	@Override
	public boolean isValid(UserEditDTO userEditDTO, ConstraintValidatorContext constraintValidatorContext) {

		boolean isValid = true;
		if ((null == userEditDTO.getHashString() || userEditDTO.getHashString().isEmpty())
				&& (null == userEditDTO.getPassword() || userEditDTO.getPassword().trim().length() < 8)) {
			isValid = false;
		}

		if (!isValid) {
			constraintValidatorContext.disableDefaultConstraintViolation();
			if (userEditDTO.getPassword().trim().length() < 8) {
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