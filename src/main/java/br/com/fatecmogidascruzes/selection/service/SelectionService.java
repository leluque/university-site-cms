package br.com.fatecmogidascruzes.selection.service;

import org.springframework.data.domain.Page;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.selection.Selection;
import br.com.fatecmogidascruzes.service.BaseService;

public interface SelectionService extends BaseService<Selection, Long> {

	Page<Selection> getEnabledByFilter(SearchCriteria searchCriteria);

}