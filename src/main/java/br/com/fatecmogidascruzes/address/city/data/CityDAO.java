package br.com.fatecmogidascruzes.address.city.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmogidascruzes.address.city.City;
import br.com.fatecmogidascruzes.address.state.State;
import br.com.fatecmogidascruzes.data.DAOImpl;

public interface CityDAO extends DAOImpl<City, Long>, JpaRepository<City, Long> {

	List<City> findByEnabledTrueAndState(State state);

}
