package br.com.fatecmogidascruzes.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import br.com.fatecmogidascruzes.config.captcha.ReCaptchaService;

@Component
public class SignInReCaptchaFilter extends GenericFilterBean {

	private final ReCaptchaService reCaptchaService;

	@Autowired
	public SignInReCaptchaFilter(ReCaptchaService reCaptchaService) {
		super();
		this.reCaptchaService = reCaptchaService;
	}

	@Override
	public void doFilter(ServletRequest originalRequest, ServletResponse originalResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) originalRequest;
		HttpServletResponse response = (HttpServletResponse) originalResponse;

		if (!request.getRequestURI().startsWith("/doSignIn")) {
			chain.doFilter(originalRequest, originalResponse);
		} else {
			try {
				String ip = request.getRemoteAddr();
				String captchaVerifyMessage = reCaptchaService.verifyRecaptcha(ip,
						request.getParameter("g-recaptcha-response"));

				if (null != captchaVerifyMessage) {
					response.sendError(HttpServletResponse.SC_BAD_REQUEST);
				}
				chain.doFilter(originalRequest, originalResponse);
			} catch (Exception error) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}

}