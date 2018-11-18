package br.com.fatecmogidascruzes.address.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.address.Address;
import br.com.fatecmogidascruzes.address.data.AddressDAO;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, Long> implements AddressService {

	@SuppressWarnings("unused")
	private final AddressDAO addressDAO;

	@Autowired
	public AddressServiceImpl(AddressDAO addressDAO) {
		super(addressDAO);
		this.addressDAO = addressDAO;
	}

}