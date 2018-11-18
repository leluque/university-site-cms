package br.com.fatecmogidascruzes.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.domain.BaseCategoryWithImage;
import br.com.fatecmogidascruzes.dto.validator.CategoryImageMustHaveDescription;
import br.com.fatecmogidascruzes.dto.validator.CategoryMustHaveImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@CategoryMustHaveImage
@CategoryImageMustHaveDescription
public class BaseCategoryWithImageEditDTO {

	private String hashString;
	@NotBlank(message = "O nome da categoria é obrigatório")
	@Length(max = 100, message = "O nome da categoria não pode exceder 100 caracteres")
	private String name;
	@Length(max = 200, message = "A descrição da categoria não pode exceder 100 caracteres")
	private String description;
	private MultipartFile image;
	private String imageAlternativeDescription;

	public static BaseCategoryWithImageEditDTO from(BaseCategoryWithImage baseCategoryWithImage) {
		BaseCategoryWithImageEditDTO expenseCategoryEditDTO = new BaseCategoryWithImageEditDTO();
		expenseCategoryEditDTO.setHashString(FriendlyId.toFriendlyId(baseCategoryWithImage.getHash()));
		expenseCategoryEditDTO.setName(baseCategoryWithImage.getName());
		expenseCategoryEditDTO.setDescription(baseCategoryWithImage.getDescription());
		expenseCategoryEditDTO
				.setImageAlternativeDescription(baseCategoryWithImage.getImage().getAlternativeDescription());

		return expenseCategoryEditDTO;
	}

	public void fill(BaseCategoryWithImage baseCategoryWithImage) {
		baseCategoryWithImage.setName(name);
		baseCategoryWithImage.setDescription(description);
		baseCategoryWithImage.setImageAlternativeDescription(baseCategoryWithImage.getImageAlternativeDescription());
	}

}
