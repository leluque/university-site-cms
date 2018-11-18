package br.com.fatecmogidascruzes.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.domain.BaseCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BaseCategoryTableRowDTO {

	private UUID hash;
	private String hashString;
	private String name;

	public static BaseCategoryTableRowDTO from(BaseCategory baseCategory) {
		BaseCategoryTableRowDTO baseCategoryTableRowDTO = new BaseCategoryTableRowDTO();
		baseCategoryTableRowDTO.setHash(baseCategory.getHash());
		baseCategoryTableRowDTO.setHashString(FriendlyId.toFriendlyId(baseCategory.getHash()));
		baseCategoryTableRowDTO.setName(baseCategory.getName());
		return baseCategoryTableRowDTO;
	}

	public static <T extends BaseCategory> List<BaseCategoryTableRowDTO> listFrom(List<T> baseCategories) {
		List<BaseCategoryTableRowDTO> categoryTableRowDTOs = new ArrayList<>();
		baseCategories.forEach(baseCategory -> categoryTableRowDTOs.add(BaseCategoryTableRowDTO.from(baseCategory)));
		return categoryTableRowDTOs;
	}

}
