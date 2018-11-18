package br.com.fatecmogidascruzes.contact.service.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ContactDTO {

	@NotBlank(message = "O seu nome é obrigatório")
	@Length(max = 100, message = "O seu nome pode ter no máximo 100 caracteres")
	private String name;

	@NotBlank(message = "O seu e-mail é obrigatório")
	@Length(max = 50, message = "O seu e-mail pode ter no máximo 50 caracteres")
	@Email(message = "O seu e-mail deve ser válido")
	private String email;

	@Length(max = 50, message = "O seu telefone pode ter no máximo 50 caracteres")
	private String phone;

	@NotBlank(message = "A mensagem é obrigatória")
	@Length(max = 2000, message = "A mensagem pode ter no máximo 100 caracteres")
	private String message;

}
