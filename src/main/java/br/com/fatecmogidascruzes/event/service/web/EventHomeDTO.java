package br.com.fatecmogidascruzes.event.service.web;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EventHomeDTO {

	protected String hashString;
	private String name;
	private String shortDescription;
	private String internalPath;
	private String externalLink;
	private String imageAlternativeDescription;
	private String date;
	private int day;
	private String month;
	private int year;
	private int views;

	public static EventHomeDTO from(Event event) {
		EventHomeDTO eventHomeDTO = new EventHomeDTO();
		eventHomeDTO.setHashString(FriendlyId.toFriendlyId(event.getHash()));
		eventHomeDTO.setName(event.getName());
		eventHomeDTO.setShortDescription(event.getShortDescription());
		if (null == event.getLink() || event.getLink().trim().isEmpty()) {
			eventHomeDTO.setExternalLink(null);
		} else {
			eventHomeDTO.setExternalLink(event.getLink());
		}

		eventHomeDTO.setInternalPath("detalheEvento?hash=" + eventHomeDTO.getHashString());
		eventHomeDTO.setImageAlternativeDescription(event.getImage().getAlternativeDescription());
		eventHomeDTO.setViews(event.getViews());

		eventHomeDTO.setDate(event.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		eventHomeDTO.setDay(event.getCreationDate().getDayOfMonth());
		eventHomeDTO.setMonth(event.getCreationDate().getMonth().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR")));
		eventHomeDTO.setYear(event.getCreationDate().getYear());

		return eventHomeDTO;
	}

	public static <T extends Event> List<EventHomeDTO> listFrom(List<T> eventList) {
		List<EventHomeDTO> eventHomeDTOs = new ArrayList<>();
		eventList.forEach(event -> eventHomeDTOs.add(EventHomeDTO.from(event)));
		return eventHomeDTOs;
	}

}
