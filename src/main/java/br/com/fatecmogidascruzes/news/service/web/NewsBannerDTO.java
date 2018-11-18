package br.com.fatecmogidascruzes.news.service.web;

import java.util.ArrayList;
import java.util.List;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.dto.BaseEditDTO;
import br.com.fatecmogidascruzes.news.News;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class NewsBannerDTO extends BaseEditDTO {

	private String name;
	private String shortDescription;
	private String imageAlternativeDescription;
	private String internalPath;
	private String externalLink;
	private boolean showOnlyOnMobile;

	public static NewsBannerDTO from(News news) {
		NewsBannerDTO newsBannerDTO = new NewsBannerDTO();
		newsBannerDTO.setHashString(FriendlyId.toFriendlyId(news.getHash()));
		newsBannerDTO.setName(news.getName());
		newsBannerDTO.setShortDescription(news.getShortDescription());
		newsBannerDTO.setImageAlternativeDescription(news.getHighlightImage().getAlternativeDescription());
		if (null == news.getLink() || news.getLink().trim().isEmpty()) {
			newsBannerDTO.setExternalLink(null);
		} else {
			newsBannerDTO.setExternalLink(news.getLink());
		}

		if (null != news.getFile()) {
			newsBannerDTO.setInternalPath(newsBannerDTO.getHashString() + "/file");
		} else {
			newsBannerDTO.setInternalPath("detalheNoticia?hash=" + newsBannerDTO.getHashString());
		}
		newsBannerDTO.setShowOnlyOnMobile(news.isShowNewsOnlyOnMobile());

		return newsBannerDTO;
	}

	public static <T extends News> List<NewsBannerDTO> listFrom(List<T> newsList) {
		List<NewsBannerDTO> newsBannerDTOs = new ArrayList<>();
		newsList.forEach(news -> newsBannerDTOs.add(NewsBannerDTO.from(news)));
		return newsBannerDTOs;
	}

}
