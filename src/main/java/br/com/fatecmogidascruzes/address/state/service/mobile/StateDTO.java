package br.com.fatecmogidascruzes.address.state.service.mobile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.fatecmogidascruzes.address.city.service.mobile.CityDTO;
import lombok.Data;

@Data
public class StateDTO {

	@JsonProperty("stateHash")
	public String stateHash;
	@JsonProperty("name")
	public String name;
	@JsonProperty("cities")
	public List<CityDTO> cities = null;

}