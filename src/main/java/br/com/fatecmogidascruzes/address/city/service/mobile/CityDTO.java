package br.com.fatecmogidascruzes.address.city.service.mobile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CityDTO {

	@JsonProperty("cityHash")
	public String cityHash;
	@JsonProperty("name")
	public String name;

}