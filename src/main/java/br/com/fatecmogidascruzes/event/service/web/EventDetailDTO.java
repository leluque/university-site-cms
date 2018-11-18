package br.com.fatecmogidascruzes.event.service.web;

import java.util.ArrayList;
import java.util.List;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.event.Event;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.service.web.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EventDetailDTO {

	protected String hashString;
	private String name;
	private String longDescription;
	private boolean hasBigImage;
	private String imageAlternativeDescription;
	private List<ImageDTO> images = new ArrayList<>();

	private void addImage(File image) {
		this.images.add(ImageDTO.from(image));
	}

	public static EventDetailDTO from(Event event) {
		EventDetailDTO eventDetailDTO = new EventDetailDTO();
		eventDetailDTO.setHashString(FriendlyId.toFriendlyId(event.getHash()));
		eventDetailDTO.setName(event.getName());
		eventDetailDTO.setLongDescription(event.getLongDescription());
		if (null != event.getBigImage()) {
			eventDetailDTO.setHasBigImage(true);
			eventDetailDTO.setImageAlternativeDescription(event.getBigImage().getAlternativeDescription());
		}

		if (null != event.getAlbum()) {
			event.getAlbum().getImages().forEach(image -> eventDetailDTO.addImage(image));
		}

		return eventDetailDTO;
	}

	public static <T extends Event> List<EventDetailDTO> listFrom(List<T> eventList) {
		List<EventDetailDTO> eventDetailDTOs = new ArrayList<>();
		eventList.forEach(event -> eventDetailDTOs.add(EventDetailDTO.from(event)));
		return eventDetailDTOs;
	}

}
