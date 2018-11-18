package br.com.fatecmogidascruzes.user.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.user.service.web.PasswordEditDTO;

public class PasswordChangeMustMatchValidator implements ConstraintValidator<PasswordChangeMustMatch, PasswordEditDTO> {

	@Override
	public void initialize(PasswordChangeMustMatch someoneShouldBeInvited) {
	}

	@Override
	public boolean isValid(PasswordEditDTO userDTO, ConstraintValidatorContext constraintValidatorContext) {

		boolean isValid = (null == userDTO.getPassword() && null == userDTO.getRepeatPassword())
				|| (null != userDTO.getPassword() && null != userDTO.getRepeatPassword()
						&& userDTO.getPassword().equals(userDTO.getRepeatPassword()));

		if (!isValid) {
			constraintValidatorContext.disableDefaultConstraintViolation();
			constraintValidatorContext.buildConstraintViolationWithTemplate("As senhas informadas devem ser iguais")
					.addPropertyNode("repeatPassword").addConstraintViolation();
			return false;
		}
		return true;

	}

}