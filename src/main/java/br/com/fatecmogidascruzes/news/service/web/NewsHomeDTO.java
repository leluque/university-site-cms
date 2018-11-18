package br.com.fatecmogidascruzes.news.service.web;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.news.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NewsHomeDTO {

	protected String hashString;
	private String name;
	private String shortDescription;
	private String internalPath;
	private String externalLink;
	private String imageAlternativeDescription;
	private String date;
	private int views;

	public static NewsHomeDTO from(News news) {
		NewsHomeDTO newsHomeDTO = new NewsHomeDTO();
		newsHomeDTO.setHashString(FriendlyId.toFriendlyId(news.getHash()));
		newsHomeDTO.setName(news.getName());
		newsHomeDTO.setShortDescription(news.getShortDescription());
		if (null == news.getLink() || news.getLink().trim().isEmpty()) {
			newsHomeDTO.setExternalLink(null);
		} else {
			newsHomeDTO.setExternalLink(news.getLink());
		}

		if (null != news.getFile()) {
			newsHomeDTO.setInternalPath(newsHomeDTO.getHashString() + "/file");
		} else {
			newsHomeDTO.setInternalPath("detalheNoticia?hash=" + newsHomeDTO.getHashString());
		}
		newsHomeDTO.setImageAlternativeDescription(news.getImage().getAlternativeDescription());
		newsHomeDTO.setViews(news.getViews());

		newsHomeDTO.setDate(news.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		return newsHomeDTO;
	}

	public static <T extends News> List<NewsHomeDTO> listFrom(List<T> newsList) {
		List<NewsHomeDTO> newsHomeDTOs = new ArrayList<>();
		newsList.forEach(news -> newsHomeDTOs.add(NewsHomeDTO.from(news)));
		return newsHomeDTOs;
	}

}
