package br.com.fatecmogidascruzes.user.service.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatecmogidascruzes.controller.MVCController;
import br.com.fatecmogidascruzes.user.exception.AlreadyUsedUsernameException;
import br.com.fatecmogidascruzes.user.service.UserService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/myself")
public class UserYourselfController extends MVCController {

	private final UserWebService userWebService;

	@Autowired
	public UserYourselfController(HttpSession httpSession, UserService userService, UserWebService userWebService) {
		super(httpSession, userService);
		this.userWebService = userWebService;
	}

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("user") UserEditDTO userEditDTO, BindingResult binding,
			RedirectAttributes redirectAttributes) {
		if (binding.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "error");
		}

		try {
			this.userWebService.saveUser(userEditDTO, getLoggedUser());
			redirectAttributes.addFlashAttribute("message", "success");
		} catch (AlreadyUsedUsernameException error) {
			binding.addError(new FieldError("user", "name", "Já existe outro usuário com o mesmo nome"));
			redirectAttributes.addFlashAttribute("message", "error");
		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
		}
		return view(redirectAttributes, userEditDTO);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view(RedirectAttributes redirectAttributes, UserEditDTO userEditDTO) {
		ModelAndView modelAndView = new ModelAndView("/user/editYourself");
		try {
			if (null == userEditDTO || null == userEditDTO.getHashString()
					|| userEditDTO.getHashString().trim().isEmpty()) {
				userEditDTO = this.userWebService.findUser(getLoggedUser().getHash());
			}
			modelAndView.addObject("isUpdate", true);
			modelAndView.addObject("user", userEditDTO);
			modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
		}

		return modelAndView;
	}

}
