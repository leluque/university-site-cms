package br.com.fatecmogidascruzes.agenda.service.web;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AgendaEntryTableRowDTO {

	private UUID hash;
	private String hashString;
	private String name;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;

	public static AgendaEntryTableRowDTO from(AgendaEntry agendaEntry) {
		AgendaEntryTableRowDTO eventTableRowDTO = new AgendaEntryTableRowDTO();
		eventTableRowDTO.setHash(agendaEntry.getHash());
		eventTableRowDTO.setHashString(FriendlyId.toFriendlyId(agendaEntry.getHash()));
		eventTableRowDTO.setName(agendaEntry.getName());
		if (null != agendaEntry.getStartDate()) {
			eventTableRowDTO.setStartDate(agendaEntry.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		if (null != agendaEntry.getStartTime()) {
			eventTableRowDTO.setStartTime(agendaEntry.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		}
		if (null != agendaEntry.getEndDate()) {
			eventTableRowDTO.setEndDate(agendaEntry.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		if (null != agendaEntry.getEndTime()) {
			eventTableRowDTO.setEndTime(agendaEntry.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		}
		return eventTableRowDTO;
	}

	public static List<AgendaEntryTableRowDTO> listFrom(List<AgendaEntry> events) {
		List<AgendaEntryTableRowDTO> eventTableRowDTOs = new ArrayList<>();
		events.forEach(event -> eventTableRowDTOs.add(AgendaEntryTableRowDTO.from(event)));
		return eventTableRowDTOs;
	}

}
