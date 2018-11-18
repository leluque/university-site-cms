package br.com.fatecmogidascruzes.request.service.web;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.request.Request;
import br.com.fatecmogidascruzes.request.service.RequestService;

@Service
public class RequestWebServiceImpl implements RequestWebService {

	private final RequestService requestService;

	@Autowired
	public RequestWebServiceImpl(RequestService requestService) {
		super();
		this.requestService = requestService;
	}

	@Override
	public RequestEditDTO find(UUID postHash) throws InexistentOrDisabledEntity {
		Optional<Request> requestOptional = this.requestService.getByHash(postHash);
		if (requestOptional.isPresent() && requestOptional.get().isEnabled()) {
			Request request = requestOptional.get();

			return RequestEditDTO.from(request);
		} else {
			throw new InexistentOrDisabledEntity("The informed request does not exist or is disabled");
		}
	}

	@Override
	public RequestEditDTO getRequestEditDTOByHash(UUID requestHash) throws InexistentOrDisabledEntity {
		Optional<Request> requestOptional = this.requestService.getByHash(requestHash);
		if (requestOptional.isPresent() && requestOptional.get().isEnabled()) {
			Request request = requestOptional.get();

			return RequestEditDTO.from(request);
		} else {
			throw new InexistentOrDisabledEntity("The informed request does not exist or is disabled");
		}
	}

	@Override
	public Request save(RequestEditDTO requestEditDTO) {
		Request request = new Request();
		if (null != requestEditDTO.getHashString() && !requestEditDTO.getHashString().trim().isEmpty()) {
			Optional<Request> requestOptional = this.requestService
					.getByHash(FriendlyId.toUuid(requestEditDTO.getHashString()));
			if (requestOptional.isPresent() && requestOptional.get().isEnabled()) {
				request = requestOptional.get();
			}
		}

		requestEditDTO.fill(request);

		this.requestService.save(request);

		return request;
	}

	@Override
	public TableDTO<RequestTableRowDTO> getTable(SearchCriteria searchCriteria, int draw) {

		Page<Request> requestPage = this.requestService.getEnabledByFilter(searchCriteria);
		TableDTO<RequestTableRowDTO> table = new TableDTO<>();
		table.setDraw(draw);
		table.setRecordsTotal((int) requestService.countEnabled().longValue());
		table.setRecordsFiltered((int) requestPage.getTotalElements());

		table.setData(RequestTableRowDTO.listFrom(requestPage.getContent()));

		return table;
	}

	@Override
	public RequestEditDTO getEnabledByProtocol(Long protocol) {
		return RequestEditDTO.from(this.requestService.getEnabledByProtocol(protocol).get());
	}

}
