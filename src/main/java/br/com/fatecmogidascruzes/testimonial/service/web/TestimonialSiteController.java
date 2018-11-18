package br.com.fatecmogidascruzes.testimonial.service.web;

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
import br.com.fatecmogidascruzes.exception.DoesNotHaveAccessException;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.exception.web.NotFoundException;
import br.com.fatecmogidascruzes.file.FileDTO;
import br.com.fatecmogidascruzes.file.service.web.FileWebService;
import br.com.fatecmogidascruzes.testimonial.Testimonial;
import br.com.fatecmogidascruzes.testimonial.service.TestimonialService;
import br.com.fatecmogidascruzes.user.service.UserService;
import br.com.fatecmogidascruzes.util.Constants;

@Controller
@RequestMapping("/testimonials")
public class TestimonialSiteController extends MVCController {

	private final TestimonialService testimonialService;
	private final FileWebService fileWebService;

	@Autowired
	public TestimonialSiteController(HttpSession httpSession, UserService userService,
			TestimonialService testimonialService, FileWebService fileWebService) {
		super(httpSession, userService);
		this.testimonialService = testimonialService;
		this.fileWebService = fileWebService;
	}

	@RequestMapping(path = "{testimonialHash}/image", method = RequestMethod.GET)
	public String image(@PathVariable("testimonialHash") UUID testimonialHash,
			@RequestParam(name = "width", required = false, defaultValue = "300") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "300") Integer height,
			HttpServletRequest request) {

		try {
			Optional<Testimonial> testimonialOptional = this.testimonialService.getEnabledByHash(testimonialHash);
			if (testimonialOptional.isPresent()) {
				FileDTO fileDTO = this.fileWebService.getImage(testimonialOptional.get().getImage().getHash(), width,
						height);
				if (null != fileDTO) {
					return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
				} else {
					throw new NotFoundException("The testimonial image has not been found.");
				}

			} else {
				throw new BadRequestException("The testimonial does not exist");
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
