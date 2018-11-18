package br.com.fatecmogidascruzes.testimonial.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.testimonial.service.web.TestimonialEditDTO;

public class TestimonialImageMustHaveDescriptionValidator
		implements ConstraintValidator<TestimonialImageMustHaveDescription, TestimonialEditDTO> {

	@Override
	public void initialize(TestimonialImageMustHaveDescription testimonialImageMustHaveDescription) {
	}

	@Override
	public boolean isValid(TestimonialEditDTO testimonialEditDTO, ConstraintValidatorContext context) {
		if ((null == testimonialEditDTO.getImageAlternativeDescription()
				|| testimonialEditDTO.getImageAlternativeDescription().trim().isEmpty())) {

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("imageAlternativeDescription").addConstraintViolation();

			return false;
		}
		return true;
	}
}