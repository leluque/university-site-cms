package br.com.fatecmogidascruzes.employee.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.employee.service.web.EmployeeEditDTO;

public class ProTemporeOnlyIfHasAllocationValidator
		implements ConstraintValidator<ProTemporeOnlyIfHasAllocation, EmployeeEditDTO> {

	@Override
	public void initialize(ProTemporeOnlyIfHasAllocation proTemporeOnlyIfHasAllocation) {
	}

	@Override
	public boolean isValid(EmployeeEditDTO employeeEditDTO, ConstraintValidatorContext context) {
		if (null == employeeEditDTO.getAllocation() && employeeEditDTO.isProTempore()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("proTempore").addConstraintViolation();
			return false;
		}
		return true;

	}

}