package br.com.fatecmogidascruzes.employee.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.employee.Employee;

public interface EmployeeDAO extends DAOImpl<Employee, Long>, JpaRepository<Employee, Long> {

	@Query("SELECT em FROM Employee em WHERE TRUE = em.enabled AND (UPPER(em.name) LIKE CONCAT('%',:filter,'%') OR UPPER(em.curriculum) LIKE CONCAT('%',:filter,'%'))")
	Page<Employee> getEnabledByFilter(@Param("filter") String filter, Pageable pageable);

	@Query("SELECT em FROM Employee em WHERE TRUE = em.enabled AND ('PROFESSOR' = em.selectionType OR 'PROFESSOR_SELECIONADO' = em.selectionType) ORDER BY em.name")
	List<Employee> getEnabledProfessors();

	@Query("SELECT em FROM Employee em WHERE TRUE = em.enabled AND TRUE = em.ads AND ('PROFESSOR' = em.selectionType OR 'PROFESSOR_SELECIONADO' = em.selectionType) ORDER BY em.name")
	List<Employee> getEnabledADSProfessors();

	@Query("SELECT em FROM Employee em WHERE TRUE = em.enabled AND TRUE = em.agro AND ('PROFESSOR' = em.selectionType OR 'PROFESSOR_SELECIONADO' = em.selectionType) ORDER BY em.name")
	List<Employee> getEnabledAgroProfessors();

	@Query("SELECT em FROM Employee em WHERE TRUE = em.enabled AND TRUE = em.log AND ('PROFESSOR' = em.selectionType OR 'PROFESSOR_SELECIONADO' = em.selectionType) ORDER BY em.name")
	List<Employee> getEnabledLogProfessors();

	@Query("SELECT em FROM Employee em WHERE TRUE = em.enabled AND TRUE = em.rh AND ('PROFESSOR' = em.selectionType OR 'PROFESSOR_SELECIONADO' = em.selectionType) ORDER BY em.name")
	List<Employee> getEnabledRHProfessors();

	@Query("SELECT em FROM Employee em WHERE TRUE = em.enabled AND TRUE = em.gestao AND ('PROFESSOR' = em.selectionType OR 'PROFESSOR_SELECIONADO' = em.selectionType) ORDER BY em.name")
	List<Employee> getEnabledGestaoProfessors();

}