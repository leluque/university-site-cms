package br.com.fatecmogidascruzes.agenda.service.web;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.user.User;

public interface AgendaEntryWebService {

	List<CalendarEntryDTO> getUnallocatedAndEnabledAgendaEntries();

	List<CalendarEntryDTO> getAllocatedAndEnabledAgendaEntriesByPeriod(LocalDate start, LocalDate end);

	AgendaEntryEditDTO getAgendaEntryEditDTOByHash(UUID agendaEntryHash) throws InexistentOrDisabledEntity;

	AgendaEntryRepetitionEditDTO getAgendaEntryRepetitionDTOByHash(UUID agendaEntryHash) throws InexistentOrDisabledEntity;

	void saveAgendaEntry(AgendaEntryEditDTO agendaEntryEditDTO, User loggedUser);

	void generateRepetitions(AgendaEntryRepetitionEditDTO agendaEntryRepetitionDTO, User loggedUser) throws BadRequestException;

	TableDTO<AgendaEntryTableRowDTO> getTable(SearchCriteria searchCriteria, int draw);

}
