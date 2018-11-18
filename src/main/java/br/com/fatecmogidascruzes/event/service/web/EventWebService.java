package br.com.fatecmogidascruzes.event.service.web;

import java.util.List;
import java.util.UUID;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.event.Event;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;

public interface EventWebService {

	EventEditDTO getEventEditDTOByHash(UUID eventHash) throws InexistentOrDisabledEntity;

	void saveEvent(EventEditDTO eventEditDTO);

	TableDTO<EventTableRowDTO> getTable(SearchCriteria searchCriteria, int draw);

	List<EventHomeDTO> getHomeEvents();

	List<EventHomeDTO> getEnabledEvents();

	EventDetailDTO getEventDetail(UUID hash);

	Event abrirLink(UUID hash);
}
