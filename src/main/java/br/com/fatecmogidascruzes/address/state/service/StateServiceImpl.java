package br.com.fatecmogidascruzes.address.state.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.address.state.State;
import br.com.fatecmogidascruzes.address.state.data.StateDAO;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class StateServiceImpl extends BaseServiceImpl<State, Long> implements StateService {

	@SuppressWarnings("unused")
	private final StateDAO stateDAO;

	@Autowired
	public StateServiceImpl(StateDAO stateDAO) {
		super(stateDAO);
		this.stateDAO = stateDAO;
	}

	@Override
	public Optional<State> getEnabledByAcronym(String acronym) {
		return this.stateDAO.findByEnabledTrueAndAcronym(acronym);
	}

}