package br.com.fatecmogidascruzes.news.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.data.SearchCriteria.Order;
import br.com.fatecmogidascruzes.news.News;
import br.com.fatecmogidascruzes.news.data.NewsDAO;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class NewsServiceImpl extends BaseServiceImpl<News, Long> implements NewsService {

	private NewsDAO newsDAO;

	public NewsServiceImpl(NewsDAO newsDAO) {
		super(newsDAO);
		this.newsDAO = newsDAO;
	}

	@Override
	public Page<News> getEnabledByFilter(SearchCriteria searchCriteria) {
		return newsDAO.getEnabledByFilter(searchCriteria.getFilter(), prepareCriteria(searchCriteria));
	}

	@Override
	public List<News> getEnabledVisibleAndHighlighted() {
		List<News> unmodifiableList = newsDAO.findByEnabledTrueAndHideFalseAndHighlightTrueOrderByReferenceDateDesc();
		List<News> news = new ArrayList<>();
		for (News newsItem : unmodifiableList) {
			news.add(newsItem);
		}
		return news;
	}

	/**
	 * This method returns 0 to 3 news if there are less or exactly 3 news
	 * available. It can also return 3 to 6 news if there are at least 6 news
	 * available.
	 */
	@Override
	public List<News> getEnabledAndVisibleHomeNews() {
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setInitialRegister(0);
		searchCriteria.setNumberOfRegisters(6);
		searchCriteria.setOrder(Order.DESCENDING);
		searchCriteria.addSortBy("referenceDate");
		List<News> unmodifiableList = newsDAO
				.getByEnabledTrueAndHideFalseAndHighlightFalse(prepareCriteria(searchCriteria)).getContent();
		List<News> news = new ArrayList<>();
		for (News newsItem : unmodifiableList) {
			news.add(newsItem);
		}
		if (news.size() >= 3 && news.size() < 6) {
			while (3 != news.size()) {
				news.remove(news.size() - 1);
			}
		}
		return news;
	}

}
