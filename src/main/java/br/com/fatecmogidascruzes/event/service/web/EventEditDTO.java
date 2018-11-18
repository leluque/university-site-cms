package br.com.fatecmogidascruzes.event.service.web;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatecmogidascruzes.event.Event;
import br.com.fatecmogidascruzes.event.service.validator.BigImageMustHaveDescription;
import br.com.fatecmogidascruzes.event.service.validator.EndMustBeAfterStart;
import br.com.fatecmogidascruzes.event.service.validator.ImageMustHaveDescription;
import br.com.fatecmogidascruzes.event.service.validator.MustHaveImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@EndMustBeAfterStart(message = "A data de término do evento deve ser posterior à data de início")
@MustHaveImage
@ImageMustHaveDescription
@BigImageMustHaveDescription
public class EventEditDTO {

	private String hashString;

	@NotBlank(message = "O título do evento é obrigatório")
	@Length(max = 100, message = "O título do evento pode ter no máximo 100 caracteres")
	private String name;

	@NotNull(message = "A data/hora de início do evento é obrigatória")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime startDateTime;

	@NotNull(message = "A data/hora de término do evento é obrigatória")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime endDateTime;

	@NotBlank(message = "A descrição breve do evento é obrigatória")
	@Length(max = 150, message = "A descrição breve do evento não pode exceder 100 caracteres")
	private String shortDescription;

	@Length(max = 30000, message = "A descrição longa do evento não pode exceder 8192 caracteres")
	private String longDescription;

	@Length(max = 100, message = "A identificação do álbum do evento não pode exceder 100 caracteres")
	private String album;

	@Length(max = 100, message = "O local do evento não pode exceder 100 caracteres")
	private String place;

	private Double latitude;

	private Double longitude;

	private boolean currentlyHasImage;
	private MultipartFile image;
	private String imageAlternativeDescription;

	private boolean currentlyHasBigImage;
	private MultipartFile bigImage;
	private String bigImageAlternativeDescription;

	public boolean hasNewImage() {
		return null != image && !image.isEmpty();
	}

	public boolean hasImageDescription() {
		return null != imageAlternativeDescription && !imageAlternativeDescription.trim().isEmpty();
	}

	public boolean hasNewBigImage() {
		return null != bigImage && !bigImage.isEmpty();
	}

	public boolean hasBigImageDescription() {
		return null != bigImageAlternativeDescription && !bigImageAlternativeDescription.trim().isEmpty();
	}

	public static EventEditDTO from(Event event) {
		EventEditDTO eventEditDTO = new EventEditDTO();

		eventEditDTO.setHashString(FriendlyId.toFriendlyId(event.getHash()));
		eventEditDTO.setName(event.getName());
		eventEditDTO.setAlbum(null != event.getAlbum() ? FriendlyId.toFriendlyId(event.getAlbum().getHash()) : "");
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

		if (null != event.getImage()) {
			eventEditDTO.setCurrentlyHasImage(true);
			eventEditDTO.setImageAlternativeDescription(event.getImage().getAlternativeDescription());
		}
		if (null != event.getBigImage()) {
			eventEditDTO.setCurrentlyHasBigImage(true);
			eventEditDTO.setBigImageAlternativeDescription(event.getBigImage().getAlternativeDescription());
		}

		eventEditDTO.setPlace(event.getPlace());

		return eventEditDTO;
	}

	public void fill(Event event) {

		event.setName(name);
		event.setShortDescription(shortDescription);
		event.setLongDescription(longDescription);

		event.setStartDate(startDateTime.toLocalDate());
		event.setStartTime(startDateTime.toLocalTime());
		event.setEndDate(endDateTime.toLocalDate());
		event.setEndTime(endDateTime.toLocalTime());

		event.setPlace(place);

		event.setLastUpdate(LocalDateTime.now());

	}

}
