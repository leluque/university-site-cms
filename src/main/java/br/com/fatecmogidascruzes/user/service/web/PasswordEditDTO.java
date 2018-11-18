package br.com.fatecmogidascruzes.user.service.web;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.service.web.validator.PasswordChangeMustMatch;
import br.com.fatecmogidascruzes.user.service.web.validator.PasswordChangeMustNotBeBlankOnInclusion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@PasswordChangeMustNotBeBlankOnInclusion
@PasswordChangeMustMatch
public class PasswordEditDTO {

	@Length(max = 100, message = "A identificacão do usuário não pode exceder 100 caracteres.")
	private String hashString;
	@Length(max = 30, message = "A senha do usuário não pode exceder 30 caracteres")
	private String password;
	@Length(max = 30, message = "A senha do usuário não pode exceder 30 caracteres")
	private String repeatPassword;

	public static PasswordEditDTO from(User user) {

		PasswordEditDTO passwordEditDTO = new PasswordEditDTO();
		passwordEditDTO.setHashString(user.getHashString());
		return passwordEditDTO;

	}

	public void fill(User user) {

		if (null != password && !password.isEmpty()) {
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(password));
		}

	}

}
