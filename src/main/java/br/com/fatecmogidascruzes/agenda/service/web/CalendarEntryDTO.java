package br.com.fatecmogidascruzes.agenda.service.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.devskiller.friendly_id.FriendlyId;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class CalendarEntryDTO {

	private String hashString;
	private String title;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate start;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate end;
	private boolean allDay = true;

	public static CalendarEntryDTO from(AgendaEntry agendaEntry) {

		CalendarEntryDTO calendarAgendaEntryDTO = new CalendarEntryDTO();
		calendarAgendaEntryDTO.setHashString(FriendlyId.toFriendlyId(agendaEntry.getHash()));
		calendarAgendaEntryDTO.setTitle(agendaEntry.getName());
		calendarAgendaEntryDTO.setStart(agendaEntry.getStartDate());
		// AgendaEntry end date is a exclusive data in the calendar Javascript library used in
		// client-side (fullCalendar).
		if (null != agendaEntry.getEndDate()) {
			calendarAgendaEntryDTO.setEnd(agendaEntry.getEndDate().plusDays(1));
		}
		return calendarAgendaEntryDTO;

	}

	public static List<CalendarEntryDTO> listFrom(List<AgendaEntry> agendaEntrys) {

		List<CalendarEntryDTO> calendarAgendaEntryDTOs = new ArrayList<>();
		for (AgendaEntry agendaEntry : agendaEntrys) {
			calendarAgendaEntryDTOs.add(from(agendaEntry));
		}
		return calendarAgendaEntryDTOs;

	}

}
