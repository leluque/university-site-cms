package br.com.fatecmogidascruzes.request.service.web;

import java.util.UUID;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.request.Request;

public interface RequestWebService {

	RequestEditDTO getRequestEditDTOByHash(UUID requestHash) throws InexistentOrDisabledEntity;

	Request save(RequestEditDTO requestEditDTO);

	TableDTO<RequestTableRowDTO> getTable(SearchCriteria searchCriteria, int draw);

	RequestEditDTO find(UUID postHash) throws InexistentOrDisabledEntity;

	RequestEditDTO getEnabledByProtocol(Long protocol);

}
