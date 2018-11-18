package br.com.fatecmogidascruzes.address.state.service;

import java.util.Optional;

import br.com.fatecmogidascruzes.address.state.State;
import br.com.fatecmogidascruzes.service.BaseService;

public interface StateService extends BaseService<State, Long> {

	Optional<State> getEnabledByAcronym(String acronym);

}
