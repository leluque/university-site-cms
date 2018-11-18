package br.com.fatecmogidascruzes.user.service.web;

import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserTableRowDTO {

	private UUID hash;
	private String hashString;
	private String fullName;
	private String name;

	public static UserTableRowDTO from(User user) {

		UserTableRowDTO userTableRowDTO = new UserTableRowDTO();
		userTableRowDTO.setHash(user.getHash());
		userTableRowDTO.setHashString(FriendlyId.toFriendlyId(user.getHash()));
		userTableRowDTO.setFullName(String.format("%s", user.getFullName().trim()));
		userTableRowDTO.setName(user.getName());
		return userTableRowDTO;

	}

}
