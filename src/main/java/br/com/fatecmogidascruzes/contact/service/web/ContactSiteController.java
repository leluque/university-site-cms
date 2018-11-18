package br.com.fatecmogidascruzes.contact.service.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatecmogidascruzes.config.captcha.ReCaptchaService;
import br.com.fatecmogidascruzes.controller.MVCController;
import br.com.fatecmogidascruzes.email.EmailService;
import br.com.fatecmogidascruzes.user.service.UserService;

@Controller
@RequestMapping("/contact")
public class ContactSiteController extends MVCController {

	private final EmailService emailService;
	private final ReCaptchaService reCaptchaService;

	public ContactSiteController(HttpSession httpSession, UserService userService, EmailService emailService,
			ReCaptchaService reCaptchaService) {
		super(httpSession, userService);
		this.emailService = emailService;
		this.reCaptchaService = reCaptchaService;
	}

	@RequestMapping("/new")
	public ModelAndView newContact(@ModelAttribute("contact") ContactDTO contactDTO,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("site/faleConosco");
		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
		modelAndView.addObject("captchaMessage", redirectAttributes.getFlashAttributes().get("captchaMessage"));
		modelAndView.addObject("contact", contactDTO);

		return modelAndView;
	}

	@RequestMapping("/send")
	public ModelAndView send(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse,
			@Valid @ModelAttribute("contact") ContactDTO contactDTO, BindingResult binding,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (binding.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "error");
			return newContact(contactDTO, redirectAttributes);
		}

		try {
			String ip = request.getRemoteAddr();
			String captchaVerifyMessage = reCaptchaService.verifyRecaptcha(ip, recaptchaResponse);

			if (null != captchaVerifyMessage) {
				redirectAttributes.addFlashAttribute("captchaMessage", "error");
				throw new Exception("Captcha Error");
			}

			emailService.sendContactEmail(contactDTO.getEmail(), contactDTO.getPhone(), contactDTO.getName(),
					contactDTO.getMessage());
			redirectAttributes.addFlashAttribute("message", "success");
			return newContact(new ContactDTO(), redirectAttributes);

		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
			return newContact(contactDTO, redirectAttributes);
		}
	}

}
