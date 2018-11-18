package br.com.fatecmogidascruzes.news.service.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.service.FileService;
import br.com.fatecmogidascruzes.news.News;
import br.com.fatecmogidascruzes.news.service.NewsService;

@Service
public class NewsWebServiceImpl implements NewsWebService {

	private final NewsService newsService;
	private final FileService fileService;

	@Value("${highlights.max}")
	private int maxHighlights;

	@Autowired
	public NewsWebServiceImpl(NewsService newsService, FileService fileService) {
		super();
		this.newsService = newsService;
		this.fileService = fileService;
	}

	@Override
	public void save(NewsEditDTO newsEditDTO) throws BadRequestException, ForbiddenException, InternalErrorException {
		News news = new News();
		if (null != newsEditDTO.getHashString() && !newsEditDTO.getHashString().trim().isEmpty()) {
			Optional<News> newsOptional = this.newsService
					.getEnabledByHash(FriendlyId.toUuid(newsEditDTO.getHashString()));
			if (!newsOptional.isPresent() || !newsOptional.get().isEnabled()) {
				throw new BadRequestException(
						"The specified news does not exist or is disabled and then cannot be updated");
			}
			news = newsOptional.get();
		}
		newsEditDTO.fill(news);

		if (newsEditDTO.isRemoveImage() && null != news.getImage()) {
			fileService.removeByKey(news.getImage().getId());
			news.setImage(null);
		}

		if (null != newsEditDTO.getImage()) {
			if (!newsEditDTO.getImage().isEmpty()) {
				// If the user has specified an image but another one exists, delete the old
				// one.
				if (null != news.getImage()) {
					fileService.removeByKey(news.getImage().getId());
				}

				try {
					File newImage = fileService.saveImage(newsEditDTO.getImage(),
							newsEditDTO.getImageAlternativeDescription());
					news.setImage(newImage);
				} catch (IOException error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the news image");
				}
			}

			news.getImage().setAlternativeDescription(newsEditDTO.getImageAlternativeDescription());
		}

		if (null != newsEditDTO.getBigImage()) {
			if (!newsEditDTO.getBigImage().isEmpty()) {
				// If the user has specified an image but another one exists, delete the old
				// one.
				if (null != news.getBigImage()) {
					fileService.removeByKey(news.getBigImage().getId());
				}

				try {
					File newImage = fileService.saveImage(newsEditDTO.getBigImage(),
							newsEditDTO.getBigImageAlternativeDescription());
					news.setBigImage(newImage);
				} catch (IOException error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the news big image");
				}
			}

			if (null != news.getBigImage()) {
				news.getBigImage().setAlternativeDescription(newsEditDTO.getBigImageAlternativeDescription());
			}
		}

		if (null != newsEditDTO.getHighlightImage()) {
			if (!newsEditDTO.getHighlightImage().isEmpty()) {
				// If the user has specified a highlight image but another one exists, delete
				// the old
				// one.
				if (null != news.getHighlightImage()) {
					fileService.removeByKey(news.getHighlightImage().getId());
				}

				try {
					File newImage = fileService.saveImage(newsEditDTO.getHighlightImage(),
							newsEditDTO.getHighlightImageAlternativeDescription());
					news.setHighlightImage(newImage);
				} catch (IOException error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the news highlight image");
				}
			}

			if (null != news.getHighlightImage()) {
				news.getHighlightImage()
						.setAlternativeDescription(newsEditDTO.getHighlightImageAlternativeDescription());
			}
		}

		if (null != newsEditDTO.getFile()) {
			if (!newsEditDTO.getFile().isEmpty()) {
				// If the user has specified a file but another one exists, delete the old
				// one.
				if (null != news.getFile()) {
					fileService.removeByKey(news.getFile().getId());
				}

				try {
					File newFile = fileService.saveFile(newsEditDTO.getFile());
					news.setFile(newFile);
				} catch (Exception error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the news file");
				}
			}
		}

		this.newsService.save(news);
	}

	@Override
	public TableDTO<NewsTableRowDTO> getTable(SearchCriteria searchCriteria, int draw) {

		Page<News> newsPage = this.newsService.getEnabledByFilter(searchCriteria);
		TableDTO<NewsTableRowDTO> table = new TableDTO<>();
		table.setDraw(draw);
		table.setRecordsTotal((int) newsService.countEnabled().longValue());
		table.setRecordsFiltered((int) newsPage.getTotalElements());

		table.setData(NewsTableRowDTO.listFrom(newsPage.getContent()));

		return table;
	}

	@Override
	public List<NewsBannerDTO> getHighlights() {

		List<News> news = this.newsService.getEnabledVisibleAndHighlighted();
		while (news.size() > maxHighlights) {
			news.remove((int) (Math.random() * news.size()));
		}
		return NewsBannerDTO.listFrom(news);

	}

	@Override
	public List<NewsHomeDTO> getHomeNews() {
		List<News> newsList = this.newsService.getEnabledAndVisibleHomeNews();
		return NewsHomeDTO.listFrom(newsList);
	}

	@Override
	public List<NewsHomeDTO> getEnabledNews() {
		List<News> newsList = this.newsService.getEnabled();
		return NewsHomeDTO.listFrom(newsList);
	}

	@Override
	public NewsDetailDTO getNewsDetail(UUID hash) {
		Optional<News> newsOptional = newsService.getByHash(hash);
		if (newsOptional.isPresent()) {
			News news = newsOptional.get();
			news.setViews(news.getViews() + 1);
			newsService.update(news);

			return NewsDetailDTO.from(news);
		} else {
			return new NewsDetailDTO();
		}
	}

	@Override
	public News abrirLink(UUID hash) {
		Optional<News> newsOptional = newsService.getByHash(hash);
		if (newsOptional.isPresent() && null != newsOptional.get().getLink()) {
			News news = newsOptional.get();
			news.setViews(news.getViews() + 1);
			newsService.update(news);

			return news;
		} else {
			return null;
		}
	}

}
