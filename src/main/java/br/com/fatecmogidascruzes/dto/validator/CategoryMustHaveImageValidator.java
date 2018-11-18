package br.com.fatecmogidascruzes.dto.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.dto.BaseCategoryWithImageEditDTO;

public class CategoryMustHaveImageValidator
		implements ConstraintValidator<CategoryMustHaveImage, BaseCategoryWithImageEditDTO> {

	@Override
	public void initialize(CategoryMustHaveImage categoryMustHaveImage) {
	}

	@Override
	public boolean isValid(BaseCategoryWithImageEditDTO baseCategoryWithImageDTO, ConstraintValidatorContext context) {
		if ((baseCategoryWithImageDTO.getHashString() == null
				|| baseCategoryWithImageDTO.getHashString().trim().isEmpty())
				&& (null == baseCategoryWithImageDTO.getImage() || baseCategoryWithImageDTO.getImage().isEmpty())) {

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("image").addConstraintViolation();
			return false;

		}
		return true;
	}
}