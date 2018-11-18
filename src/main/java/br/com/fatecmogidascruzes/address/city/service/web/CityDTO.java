package br.com.fatecmogidascruzes.address.city.service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.address.city.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CityDTO {

	private UUID hash;
	private String hashString;
	private String name;

	public static CityDTO from(City city) {
		CityDTO stateDTO = new CityDTO();
		stateDTO.setHash(city.getHash());
		stateDTO.setHashString(FriendlyId.toFriendlyId(city.getHash()));
		stateDTO.setName(city.getName());
		return stateDTO;
	}

	public static CityDTO[] arrayFrom(List<City> cities) {
		CityDTO[] citiesDTO = new CityDTO[cities.size()];
		int i = 0;
		for (City city : cities) {
			citiesDTO[i] = CityDTO.from(city);
			i++;
		}
		return citiesDTO;
	}

	public static List<CityDTO> listFrom(List<City> cities) {
		List<CityDTO> citiesDTO = new ArrayList<>(cities.size());
		for (City city : cities) {
			citiesDTO.add(CityDTO.from(city));
		}
		return citiesDTO;
	}

}