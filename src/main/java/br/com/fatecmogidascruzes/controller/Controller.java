package br.com.fatecmogidascruzes.controller;

import javax.servlet.http.HttpServletRequest;

public abstract class Controller {

	/**
	 * Return a full URL to the specified resource considering the URL of another
	 * resource.
	 * 
	 * @param request  The request of the other resource.
	 * @param resource The resource for which the full URL must be created.
	 * @return A full URL to the specified resource.
	 */
	protected String getURLFor(HttpServletRequest request, String resource) {
		String currentRequestURL = request.getRequestURL().toString();
		String currentResource = request.getServletPath().substring(1);
		String resourceWithoutSlash = resource.startsWith("/") ? resource.substring(1) : resource;
		return currentRequestURL.substring(0, currentRequestURL.indexOf(currentResource)) + resourceWithoutSlash;
	}

}
