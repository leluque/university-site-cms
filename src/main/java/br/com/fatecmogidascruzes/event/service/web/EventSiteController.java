package br.com.fatecmogidascruzes.event.service.web;

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
import br.com.fatecmogidascruzes.event.Event;
import br.com.fatecmogidascruzes.event.service.EventService;
import br.com.fatecmogidascruzes.exception.DoesNotHaveAccessException;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.exception.web.NotFoundException;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.FileDTO;
import br.com.fatecmogidascruzes.file.service.web.FileWebService;
import br.com.fatecmogidascruzes.gallery.Album;
import br.com.fatecmogidascruzes.user.service.UserService;
import br.com.fatecmogidascruzes.util.Constants;

@Controller
@RequestMapping("/events")
public class EventSiteController extends MVCController {

	private final EventService eventService;
	private final FileWebService fileWebService;

	@Autowired
	public EventSiteController(HttpSession httpSession, UserService userService, EventService eventService,
			FileWebService fileWebService) {
		super(httpSession, userService);
		this.eventService = eventService;
		this.fileWebService = fileWebService;
	}

	@RequestMapping(path = "{eventHash}/image", method = RequestMethod.GET)
	public String image(@PathVariable("eventHash") UUID eventHash,
			@RequestParam(name = "width", required = false, defaultValue = "435") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "280") Integer height,
			HttpServletRequest request) {

		try {
			Optional<Event> eventOptional = this.eventService.getEnabledByHash(eventHash);
			if (eventOptional.isPresent()) {
				FileDTO fileDTO = this.fileWebService.getImage(eventOptional.get().getImage().getHash(), width, height);
				if (null != fileDTO) {
					return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
				} else {
					throw new NotFoundException("The event image has not been found.");
				}

			} else {
				throw new BadRequestException("The event does not exist");
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

	@RequestMapping(path = "{eventHash}/bigImage", method = RequestMethod.GET)
	public String bigImage(@PathVariable("eventHash") UUID eventHash,
			@RequestParam(name = "width", required = false, defaultValue = "1410") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "940") Integer height,
			HttpServletRequest request) {

		try {
			Optional<Event> eventOptional = this.eventService.getEnabledByHash(eventHash);
			if (eventOptional.isPresent()) {
				FileDTO fileDTO = this.fileWebService.getImage(eventOptional.get().getBigImage().getHash(), width,
						height);
				if (null != fileDTO) {
					return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
				} else {
					throw new NotFoundException("The event big image has not been found.");
				}

			} else {
				throw new BadRequestException("The event does not exist");
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

	@RequestMapping(path = "{eventHash}/album/{imageHash}", method = RequestMethod.GET)
	public String highlightImage(@PathVariable("eventHash") UUID eventHash, @PathVariable("imageHash") UUID imageHash,
			@RequestParam(name = "width", required = false, defaultValue = "800") Integer width,
			@RequestParam(name = "height", required = false, defaultValue = "600") Integer height,
			HttpServletRequest request) {

		try {
			Optional<Event> eventOptional = this.eventService.getEnabledByHash(eventHash);
			if (eventOptional.isPresent()) {
				Event event = eventOptional.get();

				if (null != event.getAlbum()) {
					Album album = event.getAlbum();
					Optional<File> imageOptional = album.getImage(imageHash);

					if (imageOptional.isPresent()) {
						File image = imageOptional.get();

						FileDTO fileDTO = this.fileWebService.getImage(image.getHash(), width, height);
						if (null != fileDTO) {
							return "redirect:" + getBaseURI(request) + Constants.FILES_PATH + fileDTO.getFileName();
						} else {
							throw new NotFoundException("The event album image has not been found.");
						}
					} else {
						throw new BadRequestException("The event album image does not exist");
					}
				} else {
					throw new BadRequestException("The event does not have an album");
				}

			} else {
				throw new BadRequestException("The event does not exist");
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
