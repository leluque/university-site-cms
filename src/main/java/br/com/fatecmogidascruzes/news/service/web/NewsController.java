package br.com.fatecmogidascruzes.news.service.web;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.WebDataBinder;
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
import br.com.fatecmogidascruzes.news.News;
import br.com.fatecmogidascruzes.news.service.NewsService;
import br.com.fatecmogidascruzes.user.service.UserService;
import br.com.fatecmogidascruzes.util.Constants;

@Controller
@PreAuthorize("hasRole('ACADEMIC')")
@RequestMapping("/admin/news")
public class NewsController extends MVCController {

	private final NewsService newsService;
	private final NewsWebService newsWebService;
	private final FileWebService fileWebService;

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
						return DateTimeFormatter.ofPattern("yyyy-MM-dd").format((LocalDate) getValue());
					} catch (Exception error) {
						return null;
					}
				} else {
					return null;
				}
			}
		});
	}

	@Autowired
	public NewsController(HttpSession httpSession, UserService userService, NewsService newsService,
			NewsWebService newsWebService, FileWebService fileWebService) {
		super(httpSession, userService);
		this.newsService = newsService;
		this.newsWebService = newsWebService;
		this.fileWebService = fileWebService;
	}

	@RequestMapping(path = "/new", method = RequestMethod.GET)
	public ModelAndView newNews(@ModelAttribute("news") NewsEditDTO newsDTO, RedirectAttributes redirectAttributes) {

		ModelAndView modelAndView = new ModelAndView("/news/edit");
		modelAndView.addObject("news", newsDTO);
		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));

		return modelAndView;
	}

	@RequestMapping(path = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("news") NewsEditDTO newsDTO, BindingResult binding,
			RedirectAttributes redirectAttributes) {

		if (binding.hasErrors()) {
			redirectAttributes.addFlashAttribute("message", "error");
			return newNews(newsDTO, redirectAttributes);
		}

		try {

			newsWebService.save(newsDTO);
			redirectAttributes.addFlashAttribute("message", "success");
			return new ModelAndView("redirect:/admin/news");

		} catch (Exception error) {
			error.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "error");
			return search(redirectAttributes);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("/news/search");
		modelAndView.addObject("message", redirectAttributes.getFlashAttributes().get("message"));
		return modelAndView;
	}

	@RequestMapping(path = "/{newsHash}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable(name = "newsHash", required = true) UUID newsHash,
			RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView("/news/edit");
		try {

			Optional<News> newsOptional = this.newsService.getEnabledByHash(newsHash);
			if (newsOptional.isPresent()) {
				modelAndView.addObject("news", NewsEditDTO.from(newsOptional.get()));
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
			Optional<News> newsOptional = newsService.getByHash(hash);
			if (newsOptional.isPresent()) {
				newsService.removeLogicallyByHash(hash);
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
	private static String[] fields = { null, "name", "highlight", "creationDate", null };

	@RequestMapping(path = "/table", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	public @ResponseBody TableDTO<NewsTableRowDTO> getTable(@RequestParam(name = "draw", required = false) Integer draw,
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

		return newsWebService.getTable(searchCriteria, draw);
	}

	@RequestMapping(path = "{newsHash}/image", method = RequestMethod.GET)
	public String image(@PathVariable("newsHash") UUID newsHash,
			@RequestParam(name = "width", required = false, defaultValue = "384") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "250") Integer height,
			HttpServletRequest request) {

		try {
			Optional<News> newsOptional = this.newsService.getEnabledByHash(newsHash);
			if (newsOptional.isPresent()) {
				FileDTO fileDTO = this.fileWebService.getImage(newsOptional.get().getImage().getHash(), width, height);
				if (null != fileDTO) {
					return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
				} else {
					throw new NotFoundException("The news image has not been found.");
				}

			} else {
				throw new BadRequestException("The news does not exist");
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

	@RequestMapping(path = "{newsHash}/highlightImage", method = RequestMethod.GET)
	public String highlightImage(@PathVariable("newsHash") UUID newsHash,
			@RequestParam(name = "width", required = false, defaultValue = "1140") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "350") Integer height,
			HttpServletRequest request) {

		try {
			Optional<News> newsOptional = this.newsService.getEnabledByHash(newsHash);
			if (newsOptional.isPresent()) {
				FileDTO fileDTO = this.fileWebService.getImage(newsOptional.get().getHighlightImage().getHash(), width,
						height);
				if (null != fileDTO) {
					return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
				} else {
					throw new NotFoundException("The news highlight image has not been found.");
				}

			} else {
				throw new BadRequestException("The news does not exist");
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

	@RequestMapping(path = "{newsHash}/file", method = RequestMethod.GET)
	public String file(@PathVariable("newsHash") UUID newsHash, HttpServletRequest request) {

		try {
			Optional<News> newsOptional = this.newsService.getEnabledByHash(newsHash);
			if (newsOptional.isPresent()) {
				FileDTO fileDTO = this.fileWebService.getFile(newsOptional.get().getFile().getHash());
				if (null != fileDTO) {
					return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
				} else {
					throw new NotFoundException("The news file has not been found.");
				}

			} else {
				throw new BadRequestException("The news does not exist");
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
