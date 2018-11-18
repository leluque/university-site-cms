package br.com.fatecmogidascruzes.employee.service.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.course.Course;
import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.employee.Employee;
import br.com.fatecmogidascruzes.employee.service.EmployeeService;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.service.FileService;

@Service
public class EmployeeWebServiceImpl implements EmployeeWebService {

	private final EmployeeService employeeService;
	private final FileService fileService;

	@Autowired
	public EmployeeWebServiceImpl(EmployeeService employeeService, FileService fileService) {
		super();
		this.employeeService = employeeService;
		this.fileService = fileService;
	}

	@Override
	public void save(EmployeeEditDTO employeeEditDTO)
			throws BadRequestException, ForbiddenException, InternalErrorException {
		Employee employee = new Employee();
		if (null != employeeEditDTO.getHashString() && !employeeEditDTO.getHashString().trim().isEmpty()) {
			Optional<Employee> categoryOptional = this.employeeService
					.getEnabledByHash(FriendlyId.toUuid(employeeEditDTO.getHashString()));
			if (!categoryOptional.isPresent() || !categoryOptional.get().isEnabled()) {
				throw new BadRequestException(
						"The specified employee does not exist or is disabled and then cannot be updated");
			}
			employee = categoryOptional.get();
		}
		employeeEditDTO.fill(employee);

		if (null != employeeEditDTO.getImage()) {
			if (!employeeEditDTO.getImage().isEmpty()) {
				// If the user has specified an image but another one exists, delete the old
				// one.
				if (null != employee.getImage()) {
					fileService.removeByKey(employee.getImage().getId());
				}

				try {
					File newImage = fileService.saveImage(employeeEditDTO.getImage(),
							employeeEditDTO.getImageAlternativeDescription());
					employee.setImage(newImage);
				} catch (IOException error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the employee image");
				}
			}

			employee.getImage().setAlternativeDescription(employeeEditDTO.getImageAlternativeDescription());
		}

		this.employeeService.save(employee);
	}

	@Override
	public TableDTO<EmployeeTableRowDTO> getTable(SearchCriteria searchCriteria, int draw) {

		Page<Employee> categoriesPage = this.employeeService.getEnabledByFilter(searchCriteria);
		TableDTO<EmployeeTableRowDTO> table = new TableDTO<>();
		table.setDraw(draw);
		table.setRecordsTotal((int) employeeService.countEnabled().longValue());
		table.setRecordsFiltered((int) categoriesPage.getTotalElements());

		table.setData(EmployeeTableRowDTO.listFrom(categoriesPage.getContent()));

		return table;
	}

	public List<EmployeeEntryDTO> getEnabledProfessors() {
		return EmployeeEntryDTO.listFrom(this.employeeService.getEnabledProfessors());
	}
	
	public List<EmployeeEntryDTO> getEnabledByCourse(Course course) {
		return EmployeeEntryDTO.listFrom(this.employeeService.getEnabledByCourse(course));
	}
}
