package br.com.fatecmogidascruzes.employee.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.employee.service.web.EmployeeEditDTO;

public class EmployeeImageMustHaveDescriptionValidator
		implements ConstraintValidator<EmployeeImageMustHaveDescription, EmployeeEditDTO> {

	@Override
	public void initialize(EmployeeImageMustHaveDescription employeeImageMustHaveDescription) {
	}

	@Override
	public boolean isValid(EmployeeEditDTO employeeEditDTOe, ConstraintValidatorContext context) {
		if ((null == employeeEditDTOe.getImageAlternativeDescription()
				|| employeeEditDTOe.getImageAlternativeDescription().trim().isEmpty())) {

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("imageAlternativeDescription").addConstraintViolation();

			return false;
		}
		return true;
	}
}