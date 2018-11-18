package br.com.fatecmogidascruzes.agenda.data;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatecmogidascruzes.agenda.AgendaEntry;
import br.com.fatecmogidascruzes.data.DAOImpl;

public interface AgendaEntryDAO extends DAOImpl<AgendaEntry, Long>, JpaRepository<AgendaEntry, Long> {

	@Query("SELECT ae FROM AgendaEntry ae WHERE TRUE = ae.enabled AND ae.startDate IS NULL AND ae.endDate IS NULL")
	List<AgendaEntry> getEnabledAndNotAllocated();

	@Query("SELECT ae FROM AgendaEntry ae WHERE TRUE = ae.enabled AND ae.startDate BETWEEN :start AND :end AND ae.endDate BETWEEN :start AND :end")
	List<AgendaEntry> getEnabledAndAllocated(@Param("start") LocalDate start, @Param("end") LocalDate end);

	@Query("SELECT ae FROM AgendaEntry ae WHERE TRUE = ae.enabled AND YEAR(:date) BETWEEN YEAR(ae.startDate) AND YEAR(ae.endDate)")
	List<AgendaEntry> getEnabledByYear(@Param("date") LocalDate date);

	@Query("SELECT ae FROM AgendaEntry ae WHERE TRUE = ae.enabled AND MONTH(:date) BETWEEN MONTH(ae.startDate) AND MONTH(ae.endDate) AND YEAR(:date) BETWEEN YEAR(ae.startDate) AND YEAR(ae.endDate)")
	List<AgendaEntry> getEnabledByMonth(@Param("date") LocalDate date);

	@Query("SELECT ae FROM AgendaEntry ae WHERE TRUE = ae.enabled AND :date BETWEEN ae.startDate AND ae.endDate")
	List<AgendaEntry> getEnabledByDay(@Param("date") LocalDate date);

	@Query("SELECT ae FROM AgendaEntry ae WHERE TRUE = ae.enabled AND (UPPER(ae.name) LIKE CONCAT('%',:filter,'%') OR UPPER(ae.shortDescription) LIKE CONCAT('%',:filter,'%') OR UPPER(ae.longDescription) LIKE CONCAT('%',:filter,'%'))")
	Page<AgendaEntry> getEnabledByFilter(@Param("filter") String filter, Pageable pageable);

	@Modifying
	@Transactional
	@Query("UPDATE AgendaEntry ae SET ae.enabled = false WHERE :baseAgendaEntry = ae.baseAgendaEntry AND ae.startDate >= :from")
	void deleteCopiesLogicallyAfter(@Param("baseAgendaEntry") AgendaEntry baseAgendaEntry,
			@Param("from") LocalDate from);

}
