package br.com.fatecmogidascruzes.employee.service.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.employee.Employee;
import br.com.fatecmogidascruzes.employee.Employee.Allocation;
import br.com.fatecmogidascruzes.employee.Employee.EducationLevel;
import br.com.fatecmogidascruzes.employee.Employee.SelectionType;
import br.com.fatecmogidascruzes.employee.service.validator.EmployeeImageMustHaveDescription;
import br.com.fatecmogidascruzes.employee.service.validator.EmployeeMustHaveImage;
import br.com.fatecmogidascruzes.employee.service.validator.ProTemporeOnlyIfHasAllocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ProTemporeOnlyIfHasAllocation
@EmployeeMustHaveImage
@EmployeeImageMustHaveDescription
public class EmployeeEditDTO {

	private String hashString;
	@NotBlank(message = "O nome do funcionário é obrigatório")
	@Length(max = 100, message = "O nome do funcionário não pode exceder 100 caracteres")
	private String name;
	@Length(max = 2000, message = "O currículo do funcionário não pode exceder 2000 caracteres")
	private String curriculum;
	@NotNull(message = "O tipo de contrataçao do funcionário é obrigatório")
	private SelectionType selectionType;
	@NotNull(message = "O maior nível educacional do funcionário é obrigatório")
	private EducationLevel educationLevel;
	private Allocation allocation;
	private boolean proTempore;
	private boolean ads;
	private boolean agro;
	private boolean gestao;
	private boolean log;
	private boolean rh;
	private MultipartFile image;
	private String imageAlternativeDescription;
	private String lattes;
	private String homepage;

	public static EmployeeEditDTO from(Employee employee) {
		EmployeeEditDTO employeeEditDTO = new EmployeeEditDTO();
		employeeEditDTO.setHashString(FriendlyId.toFriendlyId(employee.getHash()));
		employeeEditDTO.setName(employee.getName());
		employeeEditDTO.setSelectionType(employee.getSelectionType());
		employeeEditDTO.setEducationLevel(employee.getEducationLevel());
		employeeEditDTO.setAllocation(employee.getAllocation());
		employeeEditDTO.setProTempore(employee.isProTempore());
		employeeEditDTO.setAds(employee.isAds());
		employeeEditDTO.setAgro(employee.isAgro());
		employeeEditDTO.setGestao(employee.isGestao());
		employeeEditDTO.setLog(employee.isLog());
		employeeEditDTO.setRh(employee.isRh());
		employeeEditDTO.setCurriculum(employee.getCurriculum());
		employeeEditDTO.setLattes(employee.getLattes());
		employeeEditDTO.setHomepage(employee.getHomepage());
		if (null != employee.getImage()) {
			employeeEditDTO.setImageAlternativeDescription(employee.getImage().getAlternativeDescription());
		}

		return employeeEditDTO;
	}

	public void fill(Employee employee) {
		employee.setName(name);
		employee.setSelectionType(selectionType);
		employee.setEducationLevel(educationLevel);
		employee.setAllocation(allocation);
		employee.setProTempore(proTempore);
		employee.setAds(ads);
		employee.setAgro(agro);
		employee.setGestao(gestao);
		employee.setLog(log);
		employee.setRh(rh);
		employee.setCurriculum(curriculum);
		employee.setLattes(lattes);
		employee.setHomepage(homepage);

	}

}
