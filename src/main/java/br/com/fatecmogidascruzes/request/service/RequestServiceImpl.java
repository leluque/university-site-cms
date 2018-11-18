package br.com.fatecmogidascruzes.request.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.request.Request;
import br.com.fatecmogidascruzes.request.data.RequestDAO;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class RequestServiceImpl extends BaseServiceImpl<Request, Long> implements RequestService {

	private final RequestDAO requestDAO;

	public RequestServiceImpl(RequestDAO requestDAO) {
		super(requestDAO);
		this.requestDAO = requestDAO;
	}

	@Override
	public Page<Request> getEnabledByFilter(SearchCriteria searchCriteria) {
		return requestDAO.getEnabledByFilter(searchCriteria.getFilter(), prepareCriteria(searchCriteria));
	}

	@Override
	public Optional<Request> getEnabledByProtocol(Long protocol) {
		return requestDAO.getByEnabledTrueAndProtocol(protocol);
	}

}
