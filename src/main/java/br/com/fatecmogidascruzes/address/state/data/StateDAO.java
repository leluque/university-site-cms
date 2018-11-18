package br.com.fatecmogidascruzes.address.state.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmogidascruzes.address.state.State;
import br.com.fatecmogidascruzes.data.DAOImpl;

public interface StateDAO extends DAOImpl<State, Long>, JpaRepository<State, Long> {

	Optional<State> findByEnabledTrueAndAcronym(String acronym);

}
