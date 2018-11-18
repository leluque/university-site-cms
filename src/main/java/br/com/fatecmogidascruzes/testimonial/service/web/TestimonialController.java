package br.com.fatecmogidascruzes.testimonial.service.web;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fatecmogidascruzes.controller.MVCController;
import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
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
@PreAuthorize("hasRole('ACADEMIC')")
@RequestMapping("/admin/testimonials")
public class TestimonialController extends MVCController {

	private final TestimonialService testimonialService;
	private final TestimonialWebService testimonialWebService;
	private final FileWebService fileWebService;

	@Autowired
	public TestimonialController(HttpSession httpSession, UserService userService,
			TestimonialService testimonialService, TestimonialWebService testimonialWebService,
			FileWebService fileWebService) {
		super(httpSession, userService);
		this.testimonialService = testimonialService;
		this.testimonialWebService = testimonialWebService;
		this.fileWebService = fileWebService;
	}

	@RequestMapping(path = "/new", method = RequestMethod.GET)
	public ModelAndView newTestimonial(@ModelAttribute("testimonial") TestimonialEditDTO testimonialDTO,
			RedirectAttributes redirectAttributes) {

		ModelAndView modelAndView = new ModelAndView("/testimonial/edit");
		modelAndView.addObject("testimonial", testimonialDTO);
		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));

		return modelAndView;
	}

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("testimonial") TestimonialEditDTO testimonialDTO,
			BindingResult binding, RedirectAttributes redirectAttributes) {

		if (binding.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "error");
			return newTestimonial(testimonialDTO, redirectAttributes);
		}

		try {

			testimonialWebService.save(testimonialDTO);
			redirectAttributes.addFlashAttribute("message", "success");
			return new ModelAndView("redirect:/admin/testimonials");

		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
			return search(redirectAttributes);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("/testimonial/search");
		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
		return modelAndView;
	}

	@RequestMapping(path = "/{testimonialHash}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable(name = "testimonialHash", required = true) UUID testimonialHash,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("/testimonial/edit");
		try {

			Optional<Testimonial> testimonialOptional = this.testimonialService.getEnabledByHash(testimonialHash);
			if (testimonialOptional.isPresent()) {
				modelAndView.addObject("testimonial", TestimonialEditDTO.from(testimonialOptional.get()));
				modelAndView.addObject("isUpdate", true);
			} else {
				redirectAttributes.addFlashAttribute("message", "error.notFound");
			}

		} catch (ForbiddenException forbiddenException) {
			forbiddenException.printStackTrace();
			throw forbiddenException;
		} catch (Exception exception) {
			exception.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
			return search(redirectAttributes);
		}

		return modelAndView;
	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(name = "hash", required = true) UUID hash,
			RedirectAttributes redirectAttributes) {
		try {
			Optional<Testimonial> testimonialOptional = testimonialService.getByHash(hash);
			if (testimonialOptional.isPresent()) {
				testimonialService.removeLogicallyByHash(hash);
				redirectAttributes.addFlashAttribute("message", "success");
			} else {
				redirectAttributes.addFlashAttribute("message", "error.notFound");
			}
		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
		}

		return search(redirectAttributes);
	}

	// Fields to filter the table - related to the JPQL.
	private static String[] fields = { null, "name", "show", null };

	@RequestMapping(path = "/table", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody TableDTO<TestimonialTableRowDTO> getTable(
			@RequestParam(name = "draw", required = false) Integer draw,
			@RequestParam(name = "start", required = false, defaultValue = "0") Integer initialPage,
			@RequestParam(name = "length", required = false, defaultValue = "10") Integer numberOfRegisters,
			@RequestParam(name = "search[value]", required = false, defaultValue = "") String filter,
			@RequestParam(name = "order[0][column]", required = false, defaultValue = "1") Integer columnsToSort,
			@RequestParam(name = "order[0][dir]", required = false, defaultValue = "asc") String columnsOrder) {

		SearchCriteria searchCriteria = new SearchCriteria();
		if (filter != null && !filter.equals("")) {
			searchCriteria.setFilter(filter);
		}
		if (columnsToSort < fields.length) {
			String fieldName = fields[columnsToSort];
			searchCriteria.addSortBy(fieldName);
			searchCriteria.setOrder(columnsOrder.equalsIgnoreCase("asc") ? SearchCriteria.Order.ASCENDING
					: SearchCriteria.Order.DESCENDING);
		}

		searchCriteria.setWhatToFilter(Arrays.asList(fields));
		searchCriteria.setInitialRegister(initialPage / numberOfRegisters);
		searchCriteria.setNumberOfRegisters(numberOfRegisters);

		return testimonialWebService.getTable(searchCriteria, draw);
	}

	@RequestMapping(path = "{testimonialHash}/image", method = RequestMethod.GET)
	public String image(@PathVariable("testimonialHash") UUID testimonialHash,
			@RequestParam(name = "width", required = false, defaultValue = "1024") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "768") Integer height,
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
