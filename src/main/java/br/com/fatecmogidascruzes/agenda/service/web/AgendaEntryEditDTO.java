package br.com.fatecmogidascruzes.agenda.service.web;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.devskiller.friendly_id.FriendlyId;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import br.com.fatecmogidascruzes.agenda.service.validator.EndMustBeAfterStart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@EndMustBeAfterStart(message = "A data de término da entrada da agenda deve ser posterior à data de início")
public class AgendaEntryEditDTO {

	private String hashString;

	@NotBlank(message = "O título da entrada da agenda é obrigatório")
	@Length(max = 100, message = "O título da entrada da agenda pode ter no máximo 100 caracteres")
	private String name;

	@NotNull(message = "A data/hora de início da entrada da agenda é obrigatória")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime startDateTime;

	@NotNull(message = "A data/hora de término da entrada da agenda é obrigatória")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime endDateTime;

	@NotBlank(message = "A descrição breve da entrada da agenda é obrigatória")
	@Length(max = 100, message = "A descrição breve da entrada da agenda não pode exceder 100 caracteres")
	private String shortDescription;

	@Length(max = 8192, message = "A descrição longa da entrada da agenda não pode exceder 8192 caracteres")
	private String longDescription;

	@Length(max = 100, message = "A identificação do álbum da entrada da agenda não pode exceder 100 caracteres")
	private String album;

	@Length(max = 100, message = "O local da entrada da agenda não pode exceder 100 caracteres")
	private String place;

	private Double latitude;

	private Double longitude;

	public static AgendaEntryEditDTO from(AgendaEntry event) {
		AgendaEntryEditDTO eventEditDTO = new AgendaEntryEditDTO();

		eventEditDTO.setHashString(FriendlyId.toFriendlyId(event.getHash()));
		eventEditDTO.setName(event.getName());
		eventEditDTO.setShortDescription(event.getShortDescription());
		eventEditDTO.setLongDescription(event.getLongDescription());

		if (null != event.getStartDate()) {
			if (null != event.getStartTime()) {
				eventEditDTO.setStartDateTime(LocalDateTime.of(event.getStartDate(), event.getStartTime()));
			} else {
				eventEditDTO.setStartDateTime(LocalDateTime.of(event.getStartDate(), LocalTime.of(0, 0)));
			}
		}
		if (null != event.getEndDate()) {
			if (null != event.getEndTime()) {
				eventEditDTO.setEndDateTime(LocalDateTime.of(event.getEndDate(), event.getEndTime()));
			} else {
				eventEditDTO.setEndDateTime(LocalDateTime.of(event.getEndDate(), LocalTime.of(23, 59)));
			}
		}

		return eventEditDTO;
	}

	public void fill(AgendaEntry event) {

		event.setName(name);
		event.setShortDescription(shortDescription);
		event.setLongDescription(longDescription);

		event.setStartDate(startDateTime.toLocalDate());
		event.setStartTime(startDateTime.toLocalTime());
		event.setEndDate(endDateTime.toLocalDate());
		event.setEndTime(endDateTime.toLocalTime());

		event.setLastUpdate(LocalDateTime.now());

	}

}
