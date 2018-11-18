package br.com.fatecmogidascruzes.gallery.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.gallery.service.web.AlbumEditDTO;

public class AlbumCoverMustHaveDescriptionValidator
		implements ConstraintValidator<AlbumCoverMustHaveDescription, AlbumEditDTO> {

	@Override
	public void initialize(AlbumCoverMustHaveDescription categoryImageMustHaveDescription) {
	}

	@Override
	public boolean isValid(AlbumEditDTO albumEditDTO, ConstraintValidatorContext context) {
		if ((null == albumEditDTO.getCoverAlternativeDescription()
				|| albumEditDTO.getCoverAlternativeDescription().trim().isEmpty())) {

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("coverAlternativeDescription").addConstraintViolation();

			return false;
		}
		return true;
	}
}