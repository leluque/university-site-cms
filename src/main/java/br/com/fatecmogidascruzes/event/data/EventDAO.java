package br.com.fatecmogidascruzes.event.data;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.event.Event;

public interface EventDAO extends DAOImpl<Event, Long>, JpaRepository<Event, Long> {

	@Query("SELECT ev FROM Event ev WHERE TRUE = ev.enabled AND ev.startDate IS NULL AND ev.endDate IS NULL")
	List<Event> getEnabledAndNotAllocated();

	@Query("SELECT ev FROM Event ev WHERE TRUE = ev.enabled AND ev.startDate BETWEEN :start AND :end AND ev.endDate BETWEEN :start AND :end")
	List<Event> getEnabledAndAllocated(@Param("start") LocalDate start, @Param("end") LocalDate end);

	@Query("SELECT ev FROM Event ev WHERE TRUE = ev.enabled AND YEAR(:date) BETWEEN YEAR(ev.startDate) AND YEAR(ev.endDate)")
	List<Event> getEnabledByYear(@Param("date") LocalDate date);

	@Query("SELECT ev FROM Event ev WHERE TRUE = ev.enabled AND MONTH(:date) BETWEEN MONTH(ev.startDate) AND MONTH(ev.endDate) AND YEAR(:date) BETWEEN YEAR(ev.startDate) AND YEAR(ev.endDate)")
	List<Event> getEnabledByMonth(@Param("date") LocalDate date);

	@Query("SELECT ev FROM Event ev WHERE TRUE = ev.enabled AND :date BETWEEN ev.startDate AND ev.endDate")
	List<Event> getEnabledByDay(@Param("date") LocalDate date);

	@Query("SELECT ev FROM Event ev WHERE TRUE = ev.enabled AND (UPPER(ev.name) LIKE CONCAT('%',:filter,'%') OR UPPER(ev.shortDescription) LIKE CONCAT('%',:filter,'%') OR UPPER(ev.longDescription) LIKE CONCAT('%',:filter,'%'))")
	Page<Event> getEnabledByFilter(@Param("filter") String filter, Pageable pageable);

	Page<Event> getByEnabledTrue(Pageable pageable);

}
