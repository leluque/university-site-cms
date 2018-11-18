package br.com.fatecmogidascruzes.testimonial.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.testimonial.service.web.TestimonialEditDTO;

public class TestimonialMustHaveImageValidator
		implements ConstraintValidator<TestimonialMustHaveImage, TestimonialEditDTO> {

	@Override
	public void initialize(TestimonialMustHaveImage testimonialMustHaveImage) {
	}

	@Override
	public boolean isValid(TestimonialEditDTO testimonialEditDTO, ConstraintValidatorContext context) {
		if ((testimonialEditDTO.getHashString() == null
				|| testimonialEditDTO.getHashString().trim().isEmpty())
				&& (null == testimonialEditDTO.getImage() || testimonialEditDTO.getImage().isEmpty())) {

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("image").addConstraintViolation();
			return false;

		}
		return true;
	}
}