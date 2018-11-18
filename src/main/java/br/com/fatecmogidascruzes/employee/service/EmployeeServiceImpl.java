package br.com.fatecmogidascruzes.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.course.Course;
import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.employee.Employee;
import br.com.fatecmogidascruzes.employee.data.EmployeeDAO;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, Long> implements EmployeeService {

	private EmployeeDAO employeeDAO;

	public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
		super(employeeDAO);
		this.employeeDAO = employeeDAO;
	}

	@Override
	public Page<Employee> getEnabledByFilter(SearchCriteria searchCriteria) {
		return employeeDAO.getEnabledByFilter(searchCriteria.getFilter(), prepareCriteria(searchCriteria));
	}

	@Override
	public List<Employee> getEnabledProfessors() {
		return employeeDAO.getEnabledProfessors();
	}

	public List<Employee> getEnabledByCourse(Course course) {
		if (Course.ADS == course) {
			return this.employeeDAO.getEnabledADSProfessors();
		} else if (Course.AGRO == course) {
			return this.employeeDAO.getEnabledAgroProfessors();
		} else if (Course.GESTAO == course) {
			return this.employeeDAO.getEnabledGestaoProfessors();
		} else if (Course.LOG == course) {
			return this.employeeDAO.getEnabledLogProfessors();
		} else if (Course.RH == course) {
			return this.employeeDAO.getEnabledRHProfessors();
		}
		return new ArrayList<>();
	}

}
