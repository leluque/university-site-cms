package br.com.fatecmogidascruzes.gallery.service.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.fatecmogidascruzes.gallery.service.web.AlbumEditDTO;

public class AlbumMustHaveCoverValidator implements ConstraintValidator<AlbumMustHaveCover, AlbumEditDTO> {

	@Override
	public void initialize(AlbumMustHaveCover categoryMustHaveImage) {
	}

	@Override
	public boolean isValid(AlbumEditDTO albumEditDTO, ConstraintValidatorContext context) {
		if ((albumEditDTO.getHashString() == null || albumEditDTO.getHashString().trim().isEmpty())
				&& (null == albumEditDTO.getCover() || albumEditDTO.getCover().isEmpty())) {

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("cover").addConstraintViolation();
			return false;

		}
		return true;
	}
}