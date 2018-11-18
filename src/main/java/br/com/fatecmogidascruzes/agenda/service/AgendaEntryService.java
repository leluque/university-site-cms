package br.com.fatecmogidascruzes.agenda.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.service.BaseService;

public interface AgendaEntryService extends BaseService<AgendaEntry, Long> {

	List<AgendaEntry> getEnabledAndUnallocated();

	List<AgendaEntry> getEnabledAndAllocatedByPeriod(LocalDate start, LocalDate end);

	List<AgendaEntry> getEnabledByYear(LocalDate date);

	List<AgendaEntry> getEnabledByYear(int year);

	List<AgendaEntry> getEnabledByMonth(LocalDate date);

	List<AgendaEntry> getEnabledByMonth(int year, int month);

	List<AgendaEntry> getEnabledByDay(LocalDate date);

	void removeCopies(UUID agendaEntryHash) throws InexistentOrDisabledEntity;

	void changeAgendaEntryStartDate(UUID agendaEntryHash, LocalDate start) throws InexistentOrDisabledEntity;

	void changeAgendaEntryDuration(UUID agendaEntryHash, long changeInDays) throws InexistentOrDisabledEntity;

	void moveAgendaEntry(UUID agendaEntryHash, long changeInDays) throws InexistentOrDisabledEntity;

	void unallocateAgendaEntry(UUID agendaEntryHash) throws InexistentOrDisabledEntity;

	Page<AgendaEntry> getEnabledByFilter(SearchCriteria searchCriteria);

}