package br.com.fatecmogidascruzes.agenda.service.web;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import br.com.fatecmogidascruzes.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class AgendaEntryDetailsDTO {

	@JsonFormat(pattern = "yyyyMMdd")
	private LocalDate startDate;
	@JsonFormat(pattern = "HHmmss")
	private LocalTime startsAt;
	@JsonFormat(pattern = "yyyyMMdd")
	private LocalDate endDate;
	@JsonFormat(pattern = "HHmmss")
	private LocalTime endsAt;
	private String name;
	private String description;

	public static AgendaEntryDetailsDTO from(AgendaEntry event, User whoIsTheUser) {

		AgendaEntryDetailsDTO eventDetailsTO = new AgendaEntryDetailsDTO();
		eventDetailsTO.setName(event.getName());
		eventDetailsTO.setDescription(event.getLongDescription());
		eventDetailsTO.setStartDate(event.getStartDate());
		eventDetailsTO.setStartsAt(event.getStartTime());
		eventDetailsTO.setEndDate(event.getEndDate());
		eventDetailsTO.setEndsAt(event.getEndTime());

		return eventDetailsTO;

	}

}
