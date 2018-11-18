package br.com.fatecmogidascruzes.request.service.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatecmogidascruzes.config.captcha.ReCaptchaService;
import br.com.fatecmogidascruzes.controller.MVCController;
import br.com.fatecmogidascruzes.email.EmailService;
import br.com.fatecmogidascruzes.request.Request;
import br.com.fatecmogidascruzes.request.Request.RequestSituation;
import br.com.fatecmogidascruzes.request.service.RequestService;
import br.com.fatecmogidascruzes.user.service.UserService;

@Controller
@RequestMapping("/requests")
public class RequestSiteController extends MVCController {

	@SuppressWarnings("unused")
	private final RequestService requestService;
	private final RequestWebService requestWebService;
	private final EmailService emailService;
	private final ReCaptchaService reCaptchaService;

	@Autowired
	public RequestSiteController(HttpSession httpSession, UserService userService, RequestService requestService,
			RequestWebService requestWebService, EmailService emailService, ReCaptchaService reCaptchaService) {
		super(httpSession, userService);
		this.requestService = requestService;
		this.requestWebService = requestWebService;
		this.emailService = emailService;
		this.reCaptchaService = reCaptchaService;
	}

	@RequestMapping(path = "new", method = RequestMethod.GET)
	public ModelAndView newRequest(@ModelAttribute("request") RequestEditDTO requestEditDTO,
			@ModelAttribute("requestSearch") RequestSituationDTO requestSituationDTO,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("site/solicitarDocumentos");

		modelAndView.addObject("protocol", redirectAttributes.getFlashAttributes().get("protocol"));
		modelAndView.addObject("captchaMessage", redirectAttributes.getFlashAttributes().get("captchaMessage"));
		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
		modelAndView.addObject("request", requestEditDTO);
		modelAndView.addObject("requestSearch", requestSituationDTO);

		return modelAndView;
	}

	@RequestMapping(path = "/save", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse,
			@Valid @ModelAttribute("request") RequestEditDTO requestEditDTO, BindingResult binding,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		if (binding.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "error");
			return newRequest(requestEditDTO, new RequestSituationDTO(), redirectAttributes);
		}

		try {
			String ip = request.getRemoteAddr();
			String captchaVerifyMessage = reCaptchaService.verifyRecaptcha(ip, recaptchaResponse);

			if (null != captchaVerifyMessage) {
				redirectAttributes.addFlashAttribute("captchaMessage", "error");
				throw new Exception("Captcha Error");
			}

			requestEditDTO.setRequestSituation(RequestSituation.RECEIVED);
			Request savedRequest = this.requestWebService.save(requestEditDTO);
			redirectAttributes.addFlashAttribute("protocol", savedRequest.getProtocol());
			redirectAttributes.addFlashAttribute("message", "success");

			emailService.sendRequestEMail(requestEditDTO.getEmail(), requestEditDTO.getName(),
					String.valueOf(savedRequest.getProtocol()), savedRequest.getRequestSituation().getName(),
					savedRequest.getDocumentType().getName());

			return newRequest(new RequestEditDTO(), new RequestSituationDTO(), redirectAttributes);

		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
			return newRequest(requestEditDTO, new RequestSituationDTO(), redirectAttributes);
		}
	}

	@RequestMapping(path = "/search", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse,
			@Valid @ModelAttribute("requestSearch") RequestSituationDTO requestSituationDTO,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("site/solicitarDocumentos");
		modelAndView.addObject("request", new RequestEditDTO());
		modelAndView.addObject("requestSearch", requestSituationDTO);

		try {
			String ip = request.getRemoteAddr();
			String captchaVerifyMessage = reCaptchaService.verifyRecaptcha(ip, recaptchaResponse);

			if (null != captchaVerifyMessage) {
				modelAndView.addObject("captchaMessage", "error");
				throw new Exception("Captcha Error");
			}

			RequestEditDTO requestEditDTO = this.requestWebService
					.getEnabledByProtocol(requestSituationDTO.getProtocol());
			modelAndView.addObject("requestResult", requestEditDTO);
			modelAndView.addObject("requestSearch", new RequestSituationDTO());

		} catch (Exception error) {
			error.printStackTrace();
			modelAndView.addObject("message", "protocolError");
			redirectAttributes.addFlashAttribute("message", "protocolError");
		}

		return modelAndView;
	}

}
