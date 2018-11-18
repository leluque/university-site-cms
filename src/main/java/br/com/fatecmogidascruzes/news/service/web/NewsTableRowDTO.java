package br.com.fatecmogidascruzes.news.service.web;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.news.News;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class NewsTableRowDTO {

	private UUID hash;
	private String hashString;
	private String name;
	private boolean highlight;
	private String date;

	public static NewsTableRowDTO from(News news) {
		NewsTableRowDTO newsTableRowDTO = new NewsTableRowDTO();
		newsTableRowDTO.setHash(news.getHash());
		newsTableRowDTO.setHashString(FriendlyId.toFriendlyId(news.getHash()));
		newsTableRowDTO.setName(news.getName());
		newsTableRowDTO.setHighlight(news.isHighlight());
		newsTableRowDTO.setDate(news.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		return newsTableRowDTO;
	}

	public static <T extends News> List<NewsTableRowDTO> listFrom(List<T> newss) {
		List<NewsTableRowDTO> newsTableRowDTOs = new ArrayList<>();
		newss.forEach(baseCategory -> newsTableRowDTOs.add(NewsTableRowDTO.from(baseCategory)));
		return newsTableRowDTOs;
	}

}
