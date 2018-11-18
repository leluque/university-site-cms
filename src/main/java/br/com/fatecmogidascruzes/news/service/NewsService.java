package br.com.fatecmogidascruzes.news.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.news.News;
import br.com.fatecmogidascruzes.service.BaseService;

public interface NewsService extends BaseService<News, Long> {

	Page<News> getEnabledByFilter(SearchCriteria searchCriteria);

	List<News> getEnabledVisibleAndHighlighted();
	
	List<News> getEnabledAndVisibleHomeNews();

}
