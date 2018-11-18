package br.com.fatecmogidascruzes.selection.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.selection.Selection;
import br.com.fatecmogidascruzes.selection.data.SelectionDAO;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class SelectionServiceImpl extends BaseServiceImpl<Selection, Long> implements SelectionService {

	@SuppressWarnings("unused")
	private final SelectionDAO selectionDAO;

	public SelectionServiceImpl(SelectionDAO selectionDAO) {
		super(selectionDAO);
		this.selectionDAO = selectionDAO;
	}

	@Override
	public Page<Selection> getEnabledByFilter(SearchCriteria searchCriteria) {
		return selectionDAO.getEnabledByFilter(searchCriteria.getFilter(), prepareCriteria(searchCriteria));
	}

}
