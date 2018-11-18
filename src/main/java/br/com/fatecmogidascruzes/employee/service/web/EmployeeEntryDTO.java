package br.com.fatecmogidascruzes.employee.service.web;

import java.util.ArrayList;
import java.util.List;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.employee.Employee;
import br.com.fatecmogidascruzes.employee.Employee.EducationLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class EmployeeEntryDTO {

	protected String hashString;
	protected String titulation;
	protected String name;
	protected String curriculum;
	protected String lattes;
	protected String homepage;

	public static EmployeeEntryDTO from(Employee employee) {
		EmployeeEntryDTO employeeEntryDTO = new EmployeeEntryDTO();
		employeeEntryDTO.setHashString(FriendlyId.toFriendlyId(employee.getHash()));
		employeeEntryDTO.setTitulation("Prof. " + (EducationLevel.SUPERIOR != employee.getEducationLevel() ? employee.getEducationLevel().getTitulation() + " ": ""));
		employeeEntryDTO.setName(employee.getName());
		employeeEntryDTO.setCurriculum(employee.getCurriculum());
		employeeEntryDTO.setLattes(employee.getLattes());
		employeeEntryDTO.setHomepage(employee.getHomepage());

		return employeeEntryDTO;
	}

	public static <T extends Employee> List<EmployeeEntryDTO> listFrom(List<T> employees) {
		List<EmployeeEntryDTO> employeeEntryDTOs = new ArrayList<>();
		employees.forEach(Employee -> employeeEntryDTOs.add(EmployeeEntryDTO.from(Employee)));
		return employeeEntryDTOs;
	}
}
