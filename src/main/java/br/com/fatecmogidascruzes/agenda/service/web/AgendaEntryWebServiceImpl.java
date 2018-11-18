package br.com.fatecmogidascruzes.agenda.service.web;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import br.com.fatecmogidascruzes.agenda.RepetitionPolicy;
import br.com.fatecmogidascruzes.agenda.service.AgendaEntryService;
import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.user.User;

@Service
public class AgendaEntryWebServiceImpl implements AgendaEntryWebService {

	private final AgendaEntryService agendaEntryService;

	@Autowired
	public AgendaEntryWebServiceImpl(AgendaEntryService agendaEntryService) {
		super();
		this.agendaEntryService = agendaEntryService;
	}

	@Override
	public List<CalendarEntryDTO> getUnallocatedAndEnabledAgendaEntries() {
		List<AgendaEntry> agendaEntrys = agendaEntryService.getEnabledAndUnallocated();
		return CalendarEntryDTO.listFrom(agendaEntrys);
	}

	@Override
	public List<CalendarEntryDTO> getAllocatedAndEnabledAgendaEntriesByPeriod(LocalDate start, LocalDate end) {
		List<AgendaEntry> agendaEntrys = agendaEntryService.getEnabledAndAllocatedByPeriod(start, end);
		return CalendarEntryDTO.listFrom(agendaEntrys);
	}

	@Override
	public AgendaEntryEditDTO getAgendaEntryEditDTOByHash(UUID agendaEntryHash) throws InexistentOrDisabledEntity {
		Optional<AgendaEntry> agendaEntryOptional = this.agendaEntryService.getByHash(agendaEntryHash);
		if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
			AgendaEntry agendaEntry = agendaEntryOptional.get();

			return AgendaEntryEditDTO.from(agendaEntry);
		} else {
			throw new InexistentOrDisabledEntity("The informed agendaEntry does not exist or is disabled");
		}
	}

	@Override
	public AgendaEntryRepetitionEditDTO getAgendaEntryRepetitionDTOByHash(UUID agendaEntryHash)
			throws InexistentOrDisabledEntity {
		Optional<AgendaEntry> agendaEntryOptional = this.agendaEntryService.getByHash(agendaEntryHash);
		if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
			AgendaEntry agendaEntry = agendaEntryOptional.get();

			return AgendaEntryRepetitionEditDTO.from(agendaEntry);
		} else {
			throw new InexistentOrDisabledEntity("The informed agenda entry does not exist or is disabled");
		}
	}

	@Override
	public void saveAgendaEntry(AgendaEntryEditDTO agendaEntryEditDTO, User loggedUser) {
		AgendaEntry agendaEntry = new AgendaEntry();
		if (null != agendaEntryEditDTO.getHashString() && !agendaEntryEditDTO.getHashString().trim().isEmpty()) {
			Optional<AgendaEntry> agendaEntryOptional = this.agendaEntryService
					.getByHash(FriendlyId.toUuid(agendaEntryEditDTO.getHashString()));
			if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
				agendaEntry = agendaEntryOptional.get();
			}
		}

		agendaEntryEditDTO.fill(agendaEntry);

		this.agendaEntryService.save(agendaEntry);
	}

	@Override
	public void generateRepetitions(AgendaEntryRepetitionEditDTO agendaEntryRepetitionDTO, User loggedUser)
			throws BadRequestException {
		if (null != agendaEntryRepetitionDTO.getHashString()
				&& !agendaEntryRepetitionDTO.getHashString().trim().isEmpty()) {
			Optional<AgendaEntry> agendaEntryOptional = this.agendaEntryService
					.getByHash(FriendlyId.toUuid(agendaEntryRepetitionDTO.getHashString()));
			AgendaEntry agendaEntry;
			if (agendaEntryOptional.isPresent() && agendaEntryOptional.get().isEnabled()) {
				agendaEntry = agendaEntryOptional.get();
			} else {
				throw new ForbiddenException("The agenda entry does not exist or is disabled");
			}

			LocalDate startDate = LocalDate.now();
			LocalDate endDate = startDate
					.plusDays(agendaEntry.getStartDate().until(agendaEntry.getEndDate(), ChronoUnit.DAYS));
			List<AgendaEntry> copies = new ArrayList<>();
			if (RepetitionPolicy.ANNUALY == agendaEntryRepetitionDTO.getRepetition()) {
				while (startDate.isBefore(agendaEntryRepetitionDTO.getDateLimit())) {
					AgendaEntry copy = agendaEntry.copy();
					copy.setStartDate(startDate.plusYears(1));
					copy.setEndDate(endDate.plusYears(1));
					copies.add(copy);
					startDate = startDate.plusYears(1);
					endDate = endDate.plusYears(1);
				}
			} else if (RepetitionPolicy.EVERY_DAY == agendaEntryRepetitionDTO.getRepetition()) {
				while (startDate.isBefore(agendaEntryRepetitionDTO.getDateLimit())) {
					AgendaEntry copy = agendaEntry.copy();
					copy.setStartDate(startDate.plusDays(1));
					copy.setEndDate(endDate.plusDays(1));
					copies.add(copy);
					startDate = startDate.plusDays(1);
					endDate = endDate.plusDays(1);
				}
			} else if (RepetitionPolicy.EVERY_OTHER_DAY == agendaEntryRepetitionDTO.getRepetition()) {
				while (startDate.isBefore(agendaEntryRepetitionDTO.getDateLimit())) {
					AgendaEntry copy = agendaEntry.copy();
					copy.setStartDate(startDate.plusDays(2));
					copy.setEndDate(endDate.plusDays(2));
					copies.add(copy);
					startDate = startDate.plusDays(2);
					endDate = endDate.plusDays(2);
				}
			} else if (RepetitionPolicy.EVERY_OTHER_WEEK == agendaEntryRepetitionDTO.getRepetition()) {
				while (startDate.isBefore(agendaEntryRepetitionDTO.getDateLimit())) {
					AgendaEntry copy = agendaEntry.copy();
					copy.setStartDate(startDate.plusWeeks(2));
					copy.setEndDate(endDate.plusWeeks(2));
					copies.add(copy);
					startDate = startDate.plusWeeks(2);
					endDate = endDate.plusWeeks(2);
				}
			} else if (RepetitionPolicy.WEEKLY == agendaEntryRepetitionDTO.getRepetition()) {
				while (startDate.isBefore(agendaEntryRepetitionDTO.getDateLimit())) {
					AgendaEntry copy = agendaEntry.copy();
					copy.setStartDate(startDate.plusWeeks(1));
					copy.setEndDate(endDate.plusWeeks(1));
					copies.add(copy);
					startDate = startDate.plusWeeks(1);
					endDate = endDate.plusWeeks(1);
				}
			} else if (RepetitionPolicy.EVERY_WORKING_DAY == agendaEntryRepetitionDTO.getRepetition()) {
				while (startDate.isBefore(agendaEntryRepetitionDTO.getDateLimit())) {
					if (!(startDate.getDayOfWeek() == DayOfWeek.SATURDAY
							|| startDate.getDayOfWeek() == DayOfWeek.SUNDAY)) {
						continue;
					}
					AgendaEntry copy = agendaEntry.copy();
					copy.setStartDate(startDate.plusDays(1));
					copy.setEndDate(endDate.plusDays(1));
					copies.add(copy);
					startDate = startDate.plusDays(1);
					endDate = endDate.plusDays(1);
				}
			} else if (RepetitionPolicy.MONTHLY == agendaEntryRepetitionDTO.getRepetition()) {
				while (startDate.isBefore(agendaEntryRepetitionDTO.getDateLimit())) {
					AgendaEntry copy = agendaEntry.copy();
					copy.setStartDate(startDate.plusMonths(1));
					copy.setEndDate(endDate.plusMonths(1));
					copies.add(copy);
					startDate = startDate.plusMonths(1);
					endDate = endDate.plusMonths(1);
				}
			} else {
				throw new BadRequestException("Unknown repetition policy");
			}

			for (AgendaEntry copy : copies) {
				copy.setBaseAgendaEntry(agendaEntry);
			}

			agendaEntryService.save(copies);

		} else {
			throw new ForbiddenException("The agenda entry does not exist or is disabled");
		}
	}

	@Override
	public TableDTO<AgendaEntryTableRowDTO> getTable(SearchCriteria searchCriteria, int draw) {

		Page<AgendaEntry> agendaEntryPage = this.agendaEntryService.getEnabledByFilter(searchCriteria);
		TableDTO<AgendaEntryTableRowDTO> table = new TableDTO<>();
		table.setDraw(draw);
		table.setRecordsTotal((int) agendaEntryService.countEnabled().longValue());
		table.setRecordsFiltered((int) agendaEntryPage.getTotalElements());

		table.setData(AgendaEntryTableRowDTO.listFrom(agendaEntryPage.getContent()));

		return table;
	}

}
