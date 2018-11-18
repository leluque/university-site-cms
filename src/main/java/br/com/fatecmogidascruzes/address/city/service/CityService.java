package br.com.fatecmogidascruzes.address.city.service;

import java.util.List;

import br.com.fatecmogidascruzes.address.city.City;
import br.com.fatecmogidascruzes.address.state.State;
import br.com.fatecmogidascruzes.service.BaseService;

public interface CityService extends BaseService<City, Long> {

	List<City> getEnabledByState(State state);

}
