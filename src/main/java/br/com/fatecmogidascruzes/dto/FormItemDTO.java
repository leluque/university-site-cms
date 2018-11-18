package br.com.fatecmogidascruzes.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.domain.NamedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
/**
 * A form item Data Transfer Object stores items that must be shown in a form,
 * such as SELECT options.
 * 
 * @author Leandro Luque
 */
public class FormItemDTO {

	protected String hash;
	protected String hashString;
	protected String name;

	public static <T extends NamedEntity> FormItemDTO from(T entity) {
		if (null != entity) {
			FormItemDTO comboDTO = new FormItemDTO();
			comboDTO.setHash(FriendlyId.toFriendlyId(entity.getHash()));
			comboDTO.setHashString(FriendlyId.toFriendlyId(entity.getHash()));
			comboDTO.setName(entity.getName());
			return comboDTO;
		}
		return null;
	}

	public static <T extends NamedEntity> FormItemDTO[] arrayFrom(Collection<T> entities) {
		if (null != entities) {
			FormItemDTO[] dtos = new FormItemDTO[entities.size()];
			int i = 0;
			for (T entity : entities) {
				dtos[i++] = FormItemDTO.from(entity);
			}
			return dtos;
		}
		return null;
	}

	public static <T extends NamedEntity> List<FormItemDTO> listFrom(Collection<T> entities) {
		if (null != entities) {
			List<FormItemDTO> dtos = new ArrayList<>();
			for (T entity : entities) {
				dtos.add(FormItemDTO.from(entity));
			}
			return dtos;
		}
		return null;
	}

	public static <T extends NamedEntity> List<FormItemDTO> listFrom(List<T> entities) {
		if (null != entities) {
			List<FormItemDTO> dtos = new ArrayList<>();
			for (T entity : entities) {
				dtos.add(FormItemDTO.from(entity));
			}
			return dtos;
		}
		return null;
	}

}
