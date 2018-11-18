package br.com.fatecmogidascruzes.event.service.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.event.Event;
import br.com.fatecmogidascruzes.event.service.EventService;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.service.FileService;
import br.com.fatecmogidascruzes.gallery.AlbumService;

@Service
public class EventWebServiceImpl implements EventWebService {

	private final EventService eventService;
	private final AlbumService albumService;
	private final FileService fileService;

	@Autowired
	public EventWebServiceImpl(EventService eventService, AlbumService albumService, FileService fileService) {
		super();
		this.eventService = eventService;
		this.albumService = albumService;
		this.fileService = fileService;
	}

	@Override
	public EventEditDTO getEventEditDTOByHash(UUID eventHash) throws InexistentOrDisabledEntity {
		Optional<Event> eventOptional = this.eventService.getByHash(eventHash);
		if (eventOptional.isPresent() && eventOptional.get().isEnabled()) {
			Event event = eventOptional.get();

			return EventEditDTO.from(event);
		} else {
			throw new InexistentOrDisabledEntity("The informed event does not exist or is disabled");
		}
	}

	@Override
	public void saveEvent(EventEditDTO eventEditDTO) {
		Event event = new Event();
		if (null != eventEditDTO.getHashString() && !eventEditDTO.getHashString().trim().isEmpty()) {
			Optional<Event> eventOptional = this.eventService
					.getByHash(FriendlyId.toUuid(eventEditDTO.getHashString()));
			if (eventOptional.isPresent() && eventOptional.get().isEnabled()) {
				event = eventOptional.get();
			}
		}

		eventEditDTO.fill(event);

		if (null != eventEditDTO.getAlbum() && !eventEditDTO.getAlbum().isEmpty()) {
			event.setAlbum(this.albumService.getByHash(FriendlyId.toUuid(eventEditDTO.getAlbum())).get());
		} else {
			event.setAlbum(null);
		}

		if (null != eventEditDTO.getImage()) {
			if (!eventEditDTO.getImage().isEmpty()) {
				// If the user has specified an image but another one exists, delete the old
				// one.
				if (null != event.getImage()) {
					fileService.removeByKey(event.getImage().getId());
				}

				try {
					File newImage = fileService.saveImage(eventEditDTO.getImage(),
							eventEditDTO.getImageAlternativeDescription());
					event.setImage(newImage);
				} catch (IOException error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the event image");
				}
			}

			event.getImage().setAlternativeDescription(eventEditDTO.getImageAlternativeDescription());
		}

		if (null != eventEditDTO.getBigImage()) {
			if (!eventEditDTO.getBigImage().isEmpty()) {
				// If the user has specified an image but another one exists, delete the old
				// one.
				if (null != event.getBigImage()) {
					fileService.removeByKey(event.getBigImage().getId());
				}

				try {
					File newImage = fileService.saveImage(eventEditDTO.getBigImage(),
							eventEditDTO.getBigImageAlternativeDescription());
					event.setBigImage(newImage);
				} catch (IOException error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the event big image");
				}
			}

			if (null != event.getBigImage()) {
				event.getBigImage().setAlternativeDescription(eventEditDTO.getBigImageAlternativeDescription());
			}
		}

		this.eventService.save(event);
	}

	@Override
	public TableDTO<EventTableRowDTO> getTable(SearchCriteria searchCriteria, int draw) {

		Page<Event> eventPage = this.eventService.getEnabledByFilter(searchCriteria);
		TableDTO<EventTableRowDTO> table = new TableDTO<>();
		table.setDraw(draw);
		table.setRecordsTotal((int) eventService.countEnabled().longValue());
		table.setRecordsFiltered((int) eventPage.getTotalElements());

		table.setData(EventTableRowDTO.listFrom(eventPage.getContent()));

		return table;
	}

	@Override
	public List<EventHomeDTO> getHomeEvents() {
		List<Event> eventList = this.eventService.getHomeEvents();
		return EventHomeDTO.listFrom(eventList);
	}

	@Override
	public List<EventHomeDTO> getEnabledEvents() {
		List<Event> eventList = this.eventService.getEnabled();
		return EventHomeDTO.listFrom(eventList);
	}

	@Override
	public EventDetailDTO getEventDetail(UUID hash) {
		Optional<Event> eventOptional = eventService.getByHash(hash);
		if (eventOptional.isPresent()) {
			Event event = eventOptional.get();
			event.setViews(event.getViews() + 1);
			eventService.update(event);

			return EventDetailDTO.from(event);
		} else {
			return new EventDetailDTO();
		}
	}

	@Override
	public Event abrirLink(UUID hash) {
		Optional<Event> eventOptional = eventService.getByHash(hash);
		if (eventOptional.isPresent() && null != eventOptional.get().getLink()) {
			Event event = eventOptional.get();
			event.setViews(event.getViews() + 1);
			eventService.update(event);

			return event;
		} else {
			return null;
		}
	}

}
