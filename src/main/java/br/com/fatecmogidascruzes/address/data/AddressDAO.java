package br.com.fatecmogidascruzes.address.data;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmogidascruzes.address.Address;
import br.com.fatecmogidascruzes.data.DAOImpl;

public interface AddressDAO extends DAOImpl<Address, Long>, JpaRepository<Address, Long> {

}
