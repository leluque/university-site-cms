package br.com.fatecmogidascruzes.selection.service.web;

import java.util.UUID;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;

public interface SelectionWebService {

	SelectionEditDTO getSelectionEditDTOByHash(UUID selectionHash) throws InexistentOrDisabledEntity;

	void save(SelectionEditDTO selectionEditDTO);

	TableDTO<SelectionTableRowDTO> getTable(SearchCriteria searchCriteria, int draw);

	SelectionEditDTO find(UUID postHash) throws InexistentOrDisabledEntity;

}
