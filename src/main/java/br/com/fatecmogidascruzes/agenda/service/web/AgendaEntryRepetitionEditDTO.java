package br.com.fatecmogidascruzes.agenda.service.web;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.devskiller.friendly_id.FriendlyId;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import br.com.fatecmogidascruzes.agenda.RepetitionPolicy;
import br.com.fatecmogidascruzes.agenda.service.validator.DateLimitMustBeInFuture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@DateLimitMustBeInFuture
public class AgendaEntryRepetitionEditDTO {

	private String hashString;

	@NotNull(message = "A data/hora de término das repetições é obrigatória")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate dateLimit;

	@NotNull(message = "A frequência de repetição é obrigatória")
	private RepetitionPolicy repetition;

	public static AgendaEntryRepetitionEditDTO from(AgendaEntry event) {
		AgendaEntryRepetitionEditDTO eventEditDTO = new AgendaEntryRepetitionEditDTO();

		eventEditDTO.setHashString(FriendlyId.toFriendlyId(event.getHash()));
		return eventEditDTO;
	}

}
