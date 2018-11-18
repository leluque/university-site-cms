package br.com.fatecmogidascruzes.employee.service.web;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.fatecmogidascruzes.controller.MVCController;
import br.com.fatecmogidascruzes.employee.Employee;
import br.com.fatecmogidascruzes.employee.service.EmployeeService;
import br.com.fatecmogidascruzes.exception.DoesNotHaveAccessException;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.exception.web.NotFoundException;
import br.com.fatecmogidascruzes.file.FileDTO;
import br.com.fatecmogidascruzes.file.service.web.FileWebService;
import br.com.fatecmogidascruzes.user.service.UserService;
import br.com.fatecmogidascruzes.util.Constants;

@Controller
@RequestMapping("/employees")
public class EmployeeSiteController extends MVCController {

	private final EmployeeService employeeService;
	private final FileWebService fileWebService;

	@Autowired
	public EmployeeSiteController(HttpSession httpSession, UserService userService, EmployeeService employeeService,
			FileWebService fileWebService) {
		super(httpSession, userService);
		this.employeeService = employeeService;
		this.fileWebService = fileWebService;
	}

	@RequestMapping(path = "{employeeHash}/image", method = RequestMethod.GET)
	public /* ResponseEntity<byte[]> */ String image(@PathVariable("employeeHash") UUID employeeHash,
			@RequestParam(name = "width", required = false, defaultValue = "300") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "300") Integer height,
			HttpServletRequest request) {

		try {
			Optional<Employee> employeeOptional = this.employeeService.getEnabledByHash(employeeHash);
			if (employeeOptional.isPresent()) {
				FileDTO fileDTO = this.fileWebService.getImage(employeeOptional.get().getImage().getHash(), width,
						height);
				if (null != fileDTO) {
					return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
				} else {
					// return new ResponseEntity<>(HttpStatus.NO_CONTENT);
					throw new NotFoundException("The employee image has not been found.");
				}

			} else {
				throw new BadRequestException("The employee does not exist");
			}

		} catch (InexistentOrDisabledEntity e) {
			e.printStackTrace();
			throw new BadRequestException(e);
		} catch (DoesNotHaveAccessException e) {
			e.printStackTrace();
			throw new ForbiddenException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalErrorException(e);
		}

	}

}
