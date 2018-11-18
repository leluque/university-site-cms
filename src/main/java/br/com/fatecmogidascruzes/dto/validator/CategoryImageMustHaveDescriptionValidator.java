package br.com.fatecmogidascruzes.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.dto.BaseCategoryWithImageEditDTO;

public class CategoryImageMustHaveDescriptionValidator
		implements ConstraintValidator<CategoryImageMustHaveDescription, BaseCategoryWithImageEditDTO> {

	@Override
	public void initialize(CategoryImageMustHaveDescription categoryImageMustHaveDescription) {
	}

	@Override
	public boolean isValid(BaseCategoryWithImageEditDTO baseCategoryWithImageDTO, ConstraintValidatorContext context) {
		if ((null == baseCategoryWithImageDTO.getImageAlternativeDescription()
				|| baseCategoryWithImageDTO.getImageAlternativeDescription().trim().isEmpty())) {

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("imageAlternativeDescription").addConstraintViolation();

			return false;
		}
		return true;
	}
}