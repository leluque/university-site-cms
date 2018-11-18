package br.com.fatecmogidascruzes.event.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.data.SearchCriteria.Order;
import br.com.fatecmogidascruzes.event.Event;
import br.com.fatecmogidascruzes.event.data.EventDAO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class EventServiceImpl extends BaseServiceImpl<Event, Long> implements EventService {

	private final EventDAO eventDAO;

	public EventServiceImpl(EventDAO eventDAO) {
		super(eventDAO);
		this.eventDAO = eventDAO;
	}

	@Override
	public List<Event> getEnabledAndUnallocated() {
		return this.eventDAO.getEnabledAndNotAllocated();
	}

	@Override
	public List<Event> getEnabledAndAllocatedByPeriod(LocalDate start, LocalDate end) {
		return this.eventDAO.getEnabledAndAllocated(start, end);
	}

	@Override
	public List<Event> getEnabledByYear(LocalDate date) {
		return this.eventDAO.getEnabledByYear(date);
	}

	@Override
	public List<Event> getEnabledByYear(int year) {
		return this.eventDAO.getEnabledByYear(LocalDate.of(year, 1, 1));
	}

	@Override
	public List<Event> getEnabledByMonth(LocalDate date) {
		return this.eventDAO.getEnabledByMonth(date);
	}

	@Override
	public List<Event> getEnabledByMonth(int year, int month) {
		return this.eventDAO.getEnabledByMonth(LocalDate.of(year, month, 1));
	}

	@Override
	public List<Event> getEnabledByDay(LocalDate date) {
		return this.eventDAO.getEnabledByDay(date);
	}

	@Override
	public void changeEventStartDate(UUID eventHash, LocalDate start) throws InexistentOrDisabledEntity {

		Optional<Event> eventOptional = this.getByHash(eventHash);
		if (eventOptional.isPresent() && eventOptional.get().isEnabled()) {
			Event event = eventOptional.get();
			long differenceInDaysBetweenStartAndEnd = 1L;
			if (null != event.getEndDate() && null != event.getStartDate()) {
				differenceInDaysBetweenStartAndEnd = event.getStartDate().until(event.getEndDate(), ChronoUnit.DAYS);
			}

			event.setStartDate(start);
			event.setEndDate(start.plusDays(differenceInDaysBetweenStartAndEnd));
			this.save(event);
		} else {
			throw new InexistentOrDisabledEntity("The informed event does not exist or is disabled");
		}

	}

	@Override
	public void moveEvent(UUID eventHash, long changeInDays) throws InexistentOrDisabledEntity {

		Optional<Event> eventOptional = this.getByHash(eventHash);
		if (eventOptional.isPresent() && eventOptional.get().isEnabled()) {
			Event event = eventOptional.get();
			event.setStartDate(event.getStartDate().plus(changeInDays, ChronoUnit.DAYS));
			event.setEndDate(event.getEndDate().plus(changeInDays, ChronoUnit.DAYS));
			this.save(event);
		} else {
			throw new InexistentOrDisabledEntity("The informed event does not exist or is disabled");
		}

	}

	@Override
	public void changeEventDuration(UUID eventHash, long changeInDays) throws InexistentOrDisabledEntity {

		Optional<Event> eventOptional = this.getByHash(eventHash);
		if (eventOptional.isPresent() && eventOptional.get().isEnabled()) {
			Event event = eventOptional.get();
			event.setEndDate(event.getEndDate().plus(changeInDays, ChronoUnit.DAYS));
			this.save(event);
		} else {
			throw new InexistentOrDisabledEntity("The informed event does not exist or is disabled");
		}

	}

	@Override
	public void unallocateEvent(UUID eventHash) throws InexistentOrDisabledEntity {

		Optional<Event> eventOptional = this.getByHash(eventHash);
		if (eventOptional.isPresent() && eventOptional.get().isEnabled()) {
			Event event = eventOptional.get();
			event.setStartDate(null);
			event.setStartTime(null);
			event.setEndDate(null);
			event.setEndTime(null);

			this.save(event);
		} else {
			throw new InexistentOrDisabledEntity("The informed event does not exist or is disabled");
		}

	}

	@Override
	public Page<Event> getEnabledByFilter(SearchCriteria searchCriteria) {
		return eventDAO.getEnabledByFilter(searchCriteria.getFilter(), prepareCriteria(searchCriteria));
	}

	/**
	 * This method returns 0 to 3 events if there are less or exactly 3 news
	 * available. It can also return 3 to 6 events if there are at least 6 news
	 * available.
	 */
	@Override
	public List<Event> getHomeEvents() {
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setInitialRegister(0);
		searchCriteria.setNumberOfRegisters(6);
		searchCriteria.setOrder(Order.DESCENDING);
		searchCriteria.addSortBy("creationDate");
		List<Event> unmodifiableEvents = eventDAO.getByEnabledTrue(prepareCriteria(searchCriteria)).getContent();
		List<Event> events = new ArrayList<>();
		for (Event eventItem : unmodifiableEvents) {
			events.add(eventItem);
		}
		if (events.size() >= 3 && events.size() < 6) {
			while (3 != events.size()) {
				events.remove(events.size() - 1);
			}
		}
		return events;
	}

}
