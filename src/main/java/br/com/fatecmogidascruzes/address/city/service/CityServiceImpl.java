package br.com.fatecmogidascruzes.address.city.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.address.city.City;
import br.com.fatecmogidascruzes.address.city.data.CityDAO;
import br.com.fatecmogidascruzes.address.state.State;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class CityServiceImpl extends BaseServiceImpl<City, Long> implements CityService {

	@SuppressWarnings("unused")
	private final CityDAO cityDAO;

	@Autowired
	public CityServiceImpl(CityDAO cityDAO) {
		super(cityDAO);
		this.cityDAO = cityDAO;
	}

	@Override
	public List<City> getEnabledByState(State state) {
		return this.cityDAO.findByEnabledTrueAndState(state);
	}

}