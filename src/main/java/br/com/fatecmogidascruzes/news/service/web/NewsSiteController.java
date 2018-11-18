package br.com.fatecmogidascruzes.news.service.web;

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
import br.com.fatecmogidascruzes.news.News;
import br.com.fatecmogidascruzes.news.service.NewsService;
import br.com.fatecmogidascruzes.user.service.UserService;
import br.com.fatecmogidascruzes.util.Constants;

@Controller
@RequestMapping("/news")
public class NewsSiteController extends MVCController {

	private final NewsService newsService;
	private final FileWebService fileWebService;

	@Autowired
	public NewsSiteController(HttpSession httpSession, UserService userService, NewsService newsService,
			FileWebService fileWebService) {
		super(httpSession, userService);
		this.newsService = newsService;
		this.fileWebService = fileWebService;
	}

	@RequestMapping(path = "{newsHash}/image", method = RequestMethod.GET)
	public String image(@PathVariable("newsHash") UUID newsHash,
			@RequestParam(name = "width", required = false, defaultValue = "435") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "280") Integer height,
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

	@RequestMapping(path = "{newsHash}/bigImage", method = RequestMethod.GET)
	public String bigImage(@PathVariable("newsHash") UUID newsHash,
			@RequestParam(name = "width", required = false, defaultValue = "1410") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "940") Integer height,
			HttpServletRequest request) {

		try {
			Optional<News> newsOptional = this.newsService.getEnabledByHash(newsHash);
			if (newsOptional.isPresent()) {
				FileDTO fileDTO = this.fileWebService.getImage(newsOptional.get().getBigImage().getHash(), width,
						height);
				if (null != fileDTO) {
					return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
				} else {
					throw new NotFoundException("The news big image has not been found.");
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
