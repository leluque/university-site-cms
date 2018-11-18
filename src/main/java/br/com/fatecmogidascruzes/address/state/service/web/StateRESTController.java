package br.com.fatecmogidascruzes.address.state.service.web;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatecmogidascruzes.address.city.service.CityService;
import br.com.fatecmogidascruzes.address.city.service.web.CityDTO;
import br.com.fatecmogidascruzes.address.state.service.StateService;
import br.com.fatecmogidascruzes.controller.Controller;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;

@RestController
public class StateRESTController extends Controller {

	private final StateService stateService;
	private final CityService cityService;

	private static final Logger logger = Logger.getLogger(StateRESTController.class.getName());

	@Autowired
	public StateRESTController(StateService stateService, CityService cityService) {
		this.stateService = stateService;
		this.cityService = cityService;
	}

	@RequestMapping(path = "/states/{stateHash}/cities", method = RequestMethod.GET, produces = {
			"application/json; charset=UTF-8" })
	public @ResponseBody CityDTO[] getCities(@PathVariable("stateHash") UUID stateHash) {
		try {
			return CityDTO
					.arrayFrom(this.cityService.getEnabledByState(this.stateService.getEnabledByHash(stateHash).get()));
		} catch (Exception e) {
			logger.log(Level.WARNING, String.format(
					"An error happened while trying to recovery the cities for the state with hash %s.", stateHash), e);
			throw new InternalErrorException();
		}
	}

}
