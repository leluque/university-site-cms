package br.com.fatecmogidascruzes.event.service.web;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.event.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EventTableRowDTO {

	private UUID hash;
	private String hashString;
	private String name;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;

	public static EventTableRowDTO from(Event event) {
		EventTableRowDTO eventTableRowDTO = new EventTableRowDTO();
		eventTableRowDTO.setHash(event.getHash());
		eventTableRowDTO.setHashString(FriendlyId.toFriendlyId(event.getHash()));
		eventTableRowDTO.setName(event.getName());
		eventTableRowDTO.setStartDate(event.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		eventTableRowDTO.setStartTime(event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		eventTableRowDTO.setEndDate(event.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		eventTableRowDTO.setEndTime(event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
		return eventTableRowDTO;
	}

	public static List<EventTableRowDTO> listFrom(List<Event> events) {
		List<EventTableRowDTO> eventTableRowDTOs = new ArrayList<>();
		events.forEach(event -> eventTableRowDTOs.add(EventTableRowDTO.from(event)));
		return eventTableRowDTOs;
	}

}
