package br.com.fatecmogidascruzes.selection.service.web;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
import br.com.fatecmogidascruzes.selection.Selection;
import br.com.fatecmogidascruzes.selection.service.SelectionService;
import br.com.fatecmogidascruzes.user.service.UserService;
import br.com.fatecmogidascruzes.util.Constants;

@Controller
@PreAuthorize("hasRole('ADMINISTRATIVE')")
@RequestMapping("/admin/selections")
public class SelectionController extends MVCController {

	private final SelectionService selectionService;
	private final SelectionWebService selectionWebService;
	private final FileWebService fileWebService;

	@Autowired
	public SelectionController(HttpSession httpSession, UserService userService, SelectionService selectionService,
			SelectionWebService selectionWebService, FileWebService fileWebService) {
		super(httpSession, userService);
		this.selectionService = selectionService;
		this.selectionWebService = selectionWebService;
		this.fileWebService = fileWebService;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if (null != text && !text.isEmpty()) {
					try {
						setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
					} catch (Exception error) {
					}
				}
			}

			@Override
			public String getAsText() throws IllegalArgumentException {
				if (null != getValue()) {
					try {
						return DateTimeFormatter.ofPattern("dd/MM/yyyy").format((LocalDate) getValue());
					} catch (Exception error) {
						return null;
					}
				} else {
					return null;
				}
			}
		});
		binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				if (null != text && !text.isEmpty()) {
					try {
						setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
					} catch (Exception error) {
					}
				}
			}

			@Override
			public String getAsText() throws IllegalArgumentException {
				if (null != getValue()) {
					try {
						return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format((LocalDateTime) getValue());
					} catch (Exception error) {
						return null;
					}
				} else {
					return null;
				}
			}
		});
	}

	@RequestMapping(path = "new", method = RequestMethod.GET)
	public ModelAndView newSelection(@ModelAttribute("selection") SelectionEditDTO selectionEditDTO,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("selection/edit");

		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
		modelAndView.addObject("selection", selectionEditDTO);

		return modelAndView;
	}

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("selection") SelectionEditDTO selectionEditDTO,
			BindingResult binding, RedirectAttributes redirectAttributes) {
		if (binding.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "error");
			return newSelection(selectionEditDTO, redirectAttributes);
		}

		try {
			this.selectionWebService.save(selectionEditDTO);
			redirectAttributes.addFlashAttribute("message", "success");
			return new ModelAndView("redirect:/admin/selections");

		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
			return newSelection(selectionEditDTO, redirectAttributes);
		}
	}

	@GetMapping
	public ModelAndView search(RedirectAttributes redirectAttributes) {

		ModelAndView modelAndView = new ModelAndView("selection/search");
		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
		return modelAndView;

	}

	@RequestMapping(path = "/{selectionHash}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable(name = "selectionHash", required = true) UUID selectionHash,
			RedirectAttributes redirectAttributes) {

		ModelAndView modelAndView = new ModelAndView("/selection/edit");
		try {
			SelectionEditDTO selectionEditDTO = this.selectionWebService.find(selectionHash);
			modelAndView.addObject("isUpdate", true);
			modelAndView.addObject("selection", selectionEditDTO);

		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
		}

		return modelAndView;
	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(name = "hash", required = true) UUID hash,
			RedirectAttributes redirectAttributes) {
		try {
			Optional<Selection> selectionOptional = selectionService.getByHash(hash);
			if (selectionOptional.isPresent()) {
				Selection selection = selectionOptional.get();
				selection.setEnabled(false);
				selectionService.update(selection);

				redirectAttributes.addFlashAttribute("message", "success");
			} else {
				redirectAttributes.addFlashAttribute("message", "error.notFound");
			}
		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
		}

		return new ModelAndView("redirect:/admin/selections");
	}

	// Fields to filter the table - related to the JPQL.
	private static String[] fields = { null, "number", "course", "discipline", null };

	@RequestMapping(path = "table", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody TableDTO<SelectionTableRowDTO> getTable(
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

		return selectionWebService.getTable(searchCriteria, draw);
	}

	@RequestMapping(path = "{selectionHash}/documents/{documentHash}", method = RequestMethod.GET)
	public /* ResponseEntity<byte[]> */ ModelAndView document(@PathVariable("selectionHash") UUID selectionHash,
			@PathVariable("documentHash") UUID documentHash) {

		try {
			Optional<Selection> selectionOptional = this.selectionService.getEnabledByHash(selectionHash);
			if (selectionOptional.isPresent()) {

				FileDTO fileDTO = this.fileWebService.getFile(documentHash);
				if (null != fileDTO) {

					return new ModelAndView("redirect:/" + Constants.FILES_PATH + fileDTO.getFileName());

				} else {
					throw new NotFoundException("The selection document has not been found.");
				}

			} else {
				throw new BadRequestException("The selection does not exist");
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
