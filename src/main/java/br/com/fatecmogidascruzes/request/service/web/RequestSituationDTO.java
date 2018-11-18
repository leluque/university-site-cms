package br.com.fatecmogidascruzes.request.service.web;

import javax.validation.constraints.NotNull;

import br.com.fatecmogidascruzes.dto.BaseEditDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class RequestSituationDTO extends BaseEditDTO {

	@NotNull(message = "O protocolo da solicitação é obrigatório")
	private Long protocol;

}
