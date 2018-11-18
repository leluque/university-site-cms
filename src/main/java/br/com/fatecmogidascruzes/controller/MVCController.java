package br.com.fatecmogidascruzes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.service.UserService;

public abstract class MVCController extends Controller {

	@SuppressWarnings("unused")
	private final HttpSession httpSession;
	private final UserService userService;

	@Autowired
	public MVCController(HttpSession httpSession, UserService userService) {
		this.httpSession = httpSession;
		this.userService = userService;
	}

	@ModelAttribute("loggedUser")
	public User getLoggedUser() {
		try {
			return userService.getByUsername(((br.com.fatecmogidascruzes.config.UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal()).getUsername()).get();
		} catch (Exception error) {
			return null;
		}
	}

	public String getBaseURI(HttpServletRequest request) {
		return request.getScheme() + "://" + // "http" + "://
	 request.getServerName() + // "myhost"
				":" + request.getServerPort();
	}

//	public String getBaseURI(HttpServletRequest request) {
//		return "https://www.fatecmogidascruzes.com.br";
//	}

}
