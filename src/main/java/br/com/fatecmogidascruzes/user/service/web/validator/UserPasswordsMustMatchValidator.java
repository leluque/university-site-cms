package br.com.fatecmogidascruzes.user.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.user.service.web.UserEditDTO;

public class UserPasswordsMustMatchValidator implements ConstraintValidator<UserPasswordsMustMatch, UserEditDTO> {

	@Override
	public void initialize(UserPasswordsMustMatch someoneShouldBeInvited) {
	}

	@Override
	public boolean isValid(UserEditDTO userDTO, ConstraintValidatorContext constraintValidatorContext) {

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