package br.com.fatecmogidascruzes.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.domain.BaseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class BaseCategoryEditDTO {

	private String hashString;
	@NotBlank(message = "O nome da categoria é obrigatório")
	@Length(max = 100, message = "O nome da categoria não pode exceder 100 caracteres")
	private String name;
	@Length(max = 200, message = "A descrição da categoria não pode exceder 200 caracteres")
	private String description;

	public static BaseCategoryEditDTO from(BaseCategory baseCategory) {
		BaseCategoryEditDTO expenseCategoryEditDTO = new BaseCategoryEditDTO();
		expenseCategoryEditDTO.setHashString(FriendlyId.toFriendlyId(baseCategory.getHash()));
		expenseCategoryEditDTO.setName(baseCategory.getName());
		expenseCategoryEditDTO.setDescription(baseCategory.getDescription());

		return expenseCategoryEditDTO;
	}

	public void fill(BaseCategory baseCategory) {
		baseCategory.setName(name);
		baseCategory.setDescription(description);
	}

}
