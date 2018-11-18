package br.com.fatecmogidascruzes.employee.service.web;

import java.util.List;

import br.com.fatecmogidascruzes.course.Course;
import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;

public interface EmployeeWebService {

	void save(EmployeeEditDTO expenseEditDTO) throws BadRequestException, ForbiddenException, InternalErrorException;

	TableDTO<EmployeeTableRowDTO> getTable(SearchCriteria searchCriteria, int draw);

	List<EmployeeEntryDTO> getEnabledProfessors();

	List<EmployeeEntryDTO> getEnabledByCourse(Course course);

}
