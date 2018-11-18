package br.com.fatecmogidascruzes.user.service.web;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.service.UserService;

@Controller
public class SignInController {

	private final UserService userService;
	private final UserWebService userWebService;

	@Autowired
	public SignInController(UserService userService, UserWebService userWebService) {
		super();
		this.userService = userService;
		this.userWebService = userWebService;
	}

	@RequestMapping("/signIn")
	public String signIn() {
		return "/signIn";
	}

	@RequestMapping("/recoveryPassword")
	public ModelAndView recoveryPassword(@RequestParam(name = "username", required = false) String username) {
		ModelAndView modelAndView = new ModelAndView("/recoveryPassword");
		modelAndView.addObject("username", username);
		return modelAndView;
	}

	@PostMapping("/doRecoveryPassword")
	public ModelAndView doRecoveryPassword(@RequestParam(name = "username") String username) {
		ModelAndView modelAndView = new ModelAndView("/recoveryPassword");
		try {
			userService.doRecoveryPassword(username);
			modelAndView.addObject("success", true);
		} catch (Exception error) {
			modelAndView.addObject("error", true);
		}
		return modelAndView;
	}

	@RequestMapping("/changePassword")
	public ModelAndView changePassword(@RequestParam(name = "hash", required = true) String hash,
			@ModelAttribute("user") PasswordEditDTO passwordEditDTO, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("/changePassword");
		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
		try {
			if (null == passwordEditDTO || null == passwordEditDTO.getHashString()
					|| passwordEditDTO.getHashString().trim().isEmpty()) {
				Optional<User> userOptional = userService.getByActionToken(hash);
				if (userOptional.isPresent() && userOptional.get().isEnabled()) {
					User user = userOptional.get();
					LocalDateTime now = LocalDateTime.now();
					if (now.isBefore(user.getActionTokenValidity())) {
						modelAndView.addObject("user", PasswordEditDTO.from(user));
					} else {
						modelAndView.addObject("expired", true);
						// throw new BadRequestException("The informed hash has expired");
					}
				} else {
					modelAndView.addObject("message", "error");
					// throw new BadRequestException("The informed hash is not valid");
				}
			} else {
				modelAndView.addObject("user", passwordEditDTO);
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping(path = "/doChangePassword", method = RequestMethod.POST)
	public ModelAndView doChangePassword(@Valid @ModelAttribute("user") PasswordEditDTO passwordEditDTO,
			BindingResult binding, RedirectAttributes redirectAttributes) {
		if (binding.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "error");
			return changePassword(passwordEditDTO.getHashString(), passwordEditDTO, redirectAttributes);
		}

		try {
			this.userWebService.updatePassword(passwordEditDTO);
			redirectAttributes.addFlashAttribute("success", "success");
			return new ModelAndView("redirect:/changePassword?success=true&hash=");
		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
			return changePassword(passwordEditDTO.getHashString(), passwordEditDTO, redirectAttributes);
		}
	}

}
