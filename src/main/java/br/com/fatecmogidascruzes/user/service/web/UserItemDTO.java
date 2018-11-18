package br.com.fatecmogidascruzes.user.service.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UserItemDTO {

	protected String hash;
	protected String hashString;
	protected String name;

	public static UserItemDTO from(User user) {
		if (null != user) {
			UserItemDTO comboDTO = new UserItemDTO();
			comboDTO.setHash(FriendlyId.toFriendlyId(user.getHash()));
			comboDTO.setHashString(FriendlyId.toFriendlyId(user.getHash()));
			comboDTO.setName(String.format("%s", user.getFullName()));
			return comboDTO;
		}
		return null;
	}

	public static UserItemDTO[] arrayFrom(Collection<User> users) {
		if (null != users) {
			UserItemDTO[] dtos = new UserItemDTO[users.size()];
			int i = 0;
			for (User user : users) {
				dtos[i++] = UserItemDTO.from(user);
			}
			return dtos;
		}
		return null;
	}

	public static List<UserItemDTO> listFrom(Collection<User> users) {
		if (null != users) {
			List<UserItemDTO> dtos = new ArrayList<>();
			for (User user : users) {
				dtos.add(UserItemDTO.from(user));
			}
			return dtos;
		}
		return null;
	}

	public static List<UserItemDTO> listFrom(List<User> users) {
		if (null != users) {
			List<UserItemDTO> dtos = new ArrayList<>();
			for (User user : users) {
				dtos.add(UserItemDTO.from(user));
			}
			return dtos;
		}
		return null;
	}

}
