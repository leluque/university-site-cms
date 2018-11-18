package br.com.fatecmogidascruzes.event.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.event.Event;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.service.BaseService;

public interface EventService extends BaseService<Event, Long> {

	List<Event> getEnabledAndUnallocated();

	List<Event> getEnabledAndAllocatedByPeriod(LocalDate start, LocalDate end);

	List<Event> getEnabledByYear(LocalDate date);

	List<Event> getEnabledByYear(int year);

	List<Event> getEnabledByMonth(LocalDate date);

	List<Event> getEnabledByMonth(int year, int month);

	List<Event> getEnabledByDay(LocalDate date);

	void changeEventStartDate(UUID eventHash, LocalDate start) throws InexistentOrDisabledEntity;

	void changeEventDuration(UUID eventHash, long changeInDays) throws InexistentOrDisabledEntity;

	void moveEvent(UUID eventHash, long changeInDays) throws InexistentOrDisabledEntity;

	void unallocateEvent(UUID eventHash) throws InexistentOrDisabledEntity;

	Page<Event> getEnabledByFilter(SearchCriteria searchCriteria);

	List<Event> getHomeEvents();

}