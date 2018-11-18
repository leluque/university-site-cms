package br.com.fatecmogidascruzes.agenda.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import br.com.fatecmogidascruzes.agenda.data.AgendaEntryDAO;
import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class AgendaEntryServiceImpl extends BaseServiceImpl<AgendaEntry, Long> implements AgendaEntryService {

	private final AgendaEntryDAO agendaEntryDAO;

	public AgendaEntryServiceImpl(AgendaEntryDAO agendaEntryDAO) {
		super(agendaEntryDAO);
		this.agendaEntryDAO = agendaEntryDAO;
	}

	@Override
	public List<AgendaEntry> getEnabledAndUnallocated() {
		return this.agendaEntryDAO.getEnabledAndNotAllocated();
	}

	@Override
	public List<AgendaEntry> getEnabledAndAllocatedByPeriod(LocalDate start, LocalDate end) {
		return this.agendaEntryDAO.getEnabledAndAllocated(start, end);
	}

	@Override
	public List<AgendaEntry> getEnabledByYear(LocalDate date) {
		return this.agendaEntryDAO.getEnabledByYear(date);
	}

	@Override
	public List<AgendaEntry> getEnabledByYear(int year) {
		return this.agendaEntryDAO.getEnabledByYear(LocalDate.of(year, 1, 1));
	}

	@Override
	public List<AgendaEntry> getEnabledByMonth(LocalDate date) {
		return this.agendaEntryDAO.getEnabledByMonth(date);
	}

	@Override
	public List<AgendaEntry> getEnabledByMonth(int year, int month) {
		return this.agendaEntryDAO.getEnabledByMonth(LocalDate.of(year, month, 1));
	}

	@Override
	public List<AgendaEntry> getEnabledByDay(LocalDate date) {
		return this.agendaEntryDAO.getEnabledByDay(date);
	}

	@Override
	public void changeAgendaEntryStartDate(UUID agendaEntryHash, LocalDate start) throws InexistentOrDisabledEntity {

		Optional<AgendaEntry> agendaEntryOptional = this.getByHash(agendaEntryHash);
		if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
			AgendaEntry agendaEntry = agendaEntryOptional.get();
			long differenceInDaysBetweenStartAndEnd = 1L;
			if (null != agendaEntry.getEndDate() && null != agendaEntry.getStartDate()) {
				differenceInDaysBetweenStartAndEnd = agendaEntry.getStartDate().until(agendaEntry.getEndDate(),
						ChronoUnit.DAYS);
			}

			agendaEntry.setStartDate(start);
			agendaEntry.setEndDate(start.plusDays(differenceInDaysBetweenStartAndEnd));
			this.save(agendaEntry);
		} else {
			throw new InexistentOrDisabledEntity("The informed agendaEntry does not exist or is disabled");
		}

	}

	@Override
	public void moveAgendaEntry(UUID agendaEntryHash, long changeInDays) throws InexistentOrDisabledEntity {

		Optional<AgendaEntry> agendaEntryOptional = this.getByHash(agendaEntryHash);
		if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
			AgendaEntry agendaEntry = agendaEntryOptional.get();
			agendaEntry.setStartDate(agendaEntry.getStartDate().plus(changeInDays, ChronoUnit.DAYS));
			agendaEntry.setEndDate(agendaEntry.getEndDate().plus(changeInDays, ChronoUnit.DAYS));
			this.save(agendaEntry);
		} else {
			throw new InexistentOrDisabledEntity("The informed agendaEntry does not exist or is disabled");
		}

	}

	@Override
	public void changeAgendaEntryDuration(UUID agendaEntryHash, long changeInDays) throws InexistentOrDisabledEntity {

		Optional<AgendaEntry> agendaEntryOptional = this.getByHash(agendaEntryHash);
		if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
			AgendaEntry agendaEntry = agendaEntryOptional.get();
			agendaEntry.setEndDate(agendaEntry.getEndDate().plus(changeInDays, ChronoUnit.DAYS));
			this.save(agendaEntry);
		} else {
			throw new InexistentOrDisabledEntity("The informed agendaEntry does not exist or is disabled");
		}

	}

	@Override
	public void unallocateAgendaEntry(UUID agendaEntryHash) throws InexistentOrDisabledEntity {

		Optional<AgendaEntry> agendaEntryOptional = this.getByHash(agendaEntryHash);
		if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
			AgendaEntry agendaEntry = agendaEntryOptional.get();
			agendaEntry.setStartDate(null);
			agendaEntry.setStartTime(null);
			agendaEntry.setEndDate(null);
			agendaEntry.setEndTime(null);

			this.save(agendaEntry);
		} else {
			throw new InexistentOrDisabledEntity("The informed agendaEntry does not exist or is disabled");
		}

	}

	public void removeCopies(UUID agendaEntryHash) throws InexistentOrDisabledEntity {

		Optional<AgendaEntry> agendaEntryOptional = this.getByHash(agendaEntryHash);
		if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
			AgendaEntry agendaEntry = agendaEntryOptional.get();
			LocalDate from = agendaEntry.getStartDate();

			if (null != agendaEntry.getBaseAgendaEntry()) {
				agendaEntry = agendaEntry.getBaseAgendaEntry();
			}

			this.agendaEntryDAO.deleteCopiesLogicallyAfter(agendaEntry, from);

		} else {
			throw new InexistentOrDisabledEntity("The informed agendaEntry does not exist or is disabled");
		}

	}

	@Override
	public Page<AgendaEntry> getEnabledByFilter(SearchCriteria searchCriteria) {
		return agendaEntryDAO.getEnabledByFilter(searchCriteria.getFilter(), prepareCriteria(searchCriteria));
	}
}
