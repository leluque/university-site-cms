package br.com.fatecmogidascruzes.news.service.web;

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
public class NewsDetailDTO {

	protected String hashString;
	private String name;
	private String longDescription;
	private boolean hasBigImage;
	private String imageAlternativeDescription;

	public static NewsDetailDTO from(News news) {
		NewsDetailDTO newsDetailDTO = new NewsDetailDTO();
		newsDetailDTO.setHashString(FriendlyId.toFriendlyId(news.getHash()));
		newsDetailDTO.setName(news.getName());
		newsDetailDTO.setLongDescription(news.getLongDescription());
		if (null != news.getBigImage()) {
			newsDetailDTO.setHasBigImage(true);
			newsDetailDTO.setImageAlternativeDescription(news.getBigImage().getAlternativeDescription());
		}

		return newsDetailDTO;
	}

	public static <T extends News> List<NewsDetailDTO> listFrom(List<T> newsList) {
		List<NewsDetailDTO> newsDetailDTOs = new ArrayList<>();
		newsList.forEach(news -> newsDetailDTOs.add(NewsDetailDTO.from(news)));
		return newsDetailDTOs;
	}

}
