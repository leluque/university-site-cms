package br.com.fatecmogidascruzes.news.service.web;

import java.util.List;
import java.util.UUID;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.news.News;

public interface NewsWebService {

	void save(NewsEditDTO newsEditDTO) throws BadRequestException, ForbiddenException, InternalErrorException;

	TableDTO<NewsTableRowDTO> getTable(SearchCriteria searchCriteria, int draw);

	List<NewsBannerDTO> getHighlights();

	List<NewsHomeDTO> getHomeNews();
	
	List<NewsHomeDTO> getEnabledNews();

	NewsDetailDTO getNewsDetail(UUID hash);

	News abrirLink(UUID hash);
}
