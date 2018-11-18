package br.com.fatecmogidascruzes.user.service.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.service.web.validator.UserPasswordMustNotBeBlankOnInclusion;
import br.com.fatecmogidascruzes.user.service.web.validator.UserPasswordsMustMatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@UserPasswordMustNotBeBlankOnInclusion
@UserPasswordsMustMatch
public class UserEditDTO {

	@Length(max = 100, message = "A identificacão do usuário não pode exceder 100 caracteres.")
	private String hashString;
	@Length(max = 50, message = "O nome do usuário não pode exceder 100 caracteres")
	@NotBlank(message = "O nome do usuário é obrigatório")
	private String fullName;
	@Length(max = 200, message = "As observações sobre o usuário não podem exceder 200 caracteres")
	private String description;
	@Email(message = "O nome do usuário deve ser um e-mail válido")
	@Length(max = 100, message = "O e-mail do usuário não pode exceder 100 caracteres")
	@NotBlank(message = "O e-mail do usuário é obrigatório")
	private String name;
	@Length(max = 30, message = "A senha do usuário não pode exceder 30 caracteres")
	private String password;
	@Length(max = 30, message = "A senha do usuário não pode exceder 30 caracteres")
	private String repeatPassword;

	public static UserEditDTO from(User user) {

		UserEditDTO userDTO = new UserEditDTO();
		userDTO.setHashString(user.getHashString());
		userDTO.setFullName(user.getFullName());
		userDTO.setName(user.getName());
		return userDTO;

	}

	public void fill(User user) {

		user.setFullName(fullName);
		user.setName(name);
		if (null != password && !password.isEmpty()) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(password));
		}

	}

}
