package br.com.fatecmogidascruzes.address.service.web;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SimpleAddressDTO {

	private String state;
	private String city;

	public static SimpleAddressDTO from(Address address) {
		SimpleAddressDTO addressDTO = new SimpleAddressDTO();
		if (null != address) {
			addressDTO.setCity(FriendlyId.toFriendlyId(address.getCity().getHash()));
			addressDTO.setState(FriendlyId.toFriendlyId(address.getState().getHash()));
		}
		return addressDTO;
	}
}
