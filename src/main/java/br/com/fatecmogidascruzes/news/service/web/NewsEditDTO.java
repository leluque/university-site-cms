package br.com.fatecmogidascruzes.news.service.web;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatecmogidascruzes.dto.BaseEditDTO;
import br.com.fatecmogidascruzes.news.News;
import br.com.fatecmogidascruzes.news.service.validator.BigImageMustHaveDescription;
import br.com.fatecmogidascruzes.news.service.validator.HighlightImageMustHaveDescription;
import br.com.fatecmogidascruzes.news.service.validator.HighlightedNewsMustHaveHighlightImage;
import br.com.fatecmogidascruzes.news.service.validator.ImageMustHaveDescription;
import br.com.fatecmogidascruzes.news.service.validator.MustHaveFileOrLongDescriptionOrLink;
import br.com.fatecmogidascruzes.news.service.validator.MustHaveImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@MustHaveImage
@ImageMustHaveDescription
@BigImageMustHaveDescription
@HighlightedNewsMustHaveHighlightImage
@HighlightImageMustHaveDescription
@MustHaveFileOrLongDescriptionOrLink
public class NewsEditDTO extends BaseEditDTO {

	@NotBlank(message = "O título da notícia é obrigatório")
	@Length(max = 100, message = "O título da notícia não pode exceder 100 caracteres")
	private String name;
	@NotBlank(message = "A breve descrição da notícia é obrigatória")
	@Length(max = 150, message = "A breve descrição da notícia não pode exceder 150 caracteres")
	private String shortDescription;
	private boolean showOnlyOnMobile;
	@Length(max = 30000, message = "O conteúdo da notícia não pode exceder 4096 caracteres")
	private String longDescription;
	private String link;
	private boolean highlight;
	private boolean currentlyHasImage;
	private MultipartFile image;
	private String imageAlternativeDescription;
	private boolean currentlyHasBigImage;
	private MultipartFile bigImage;
	private String bigImageAlternativeDescription;
	private boolean currentlyHasHighlightImage;
	private MultipartFile highlightImage;
	private String highlightImageAlternativeDescription;
	private boolean currentlyHasFile;
	private MultipartFile file;
	private boolean removeImage;
	
	@NotNull(message = "A data de referência é obrigatória")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate referenceDate;

	private boolean hide;

	public boolean hasNewHighlightImage() {
		return null != highlightImage && !highlightImage.isEmpty();
	}

	public boolean hasHighlightImageDescription() {
		return null != highlightImageAlternativeDescription && !highlightImageAlternativeDescription.trim().isEmpty();
	}

	public boolean hasNewImage() {
		return null != image && !image.isEmpty();
	}

	public boolean hasImageDescription() {
		return null != imageAlternativeDescription && !imageAlternativeDescription.trim().isEmpty();
	}

	public boolean hasNewBigImage() {
		return null != bigImage && !bigImage.isEmpty();
	}

	public boolean hasBigImageDescription() {
		return null != bigImageAlternativeDescription && !bigImageAlternativeDescription.trim().isEmpty();
	}

	public boolean hasNewFile() {
		return null != file && !file.isEmpty();
	}

	public boolean hasContent() {
		return null != longDescription && !longDescription.trim().isEmpty();
	}

	public boolean hasLink() {
		return null != link && !link.trim().isEmpty();
	}

	public static NewsEditDTO from(News news) {
		NewsEditDTO newsEditDTO = new NewsEditDTO();
		newsEditDTO.setHashString(FriendlyId.toFriendlyId(news.getHash()));
		newsEditDTO.setName(news.getName());
		newsEditDTO.setShortDescription(news.getShortDescription());
		newsEditDTO.setLongDescription(news.getLongDescription());
		newsEditDTO.setHighlight(news.isHighlight());
		if (null != news.getImage()) {
			newsEditDTO.setCurrentlyHasImage(true);
			newsEditDTO.setImageAlternativeDescription(news.getImage().getAlternativeDescription());
		}
		if (null != news.getHighlightImage()) {
			newsEditDTO.setCurrentlyHasHighlightImage(true);
			newsEditDTO.setHighlightImageAlternativeDescription(news.getHighlightImage().getAlternativeDescription());
		}
		if (null != news.getFile()) {
			newsEditDTO.setCurrentlyHasFile(true);
		}
		if (null != news.getBigImage()) {
			newsEditDTO.setCurrentlyHasBigImage(true);
			newsEditDTO.setBigImageAlternativeDescription(news.getBigImage().getAlternativeDescription());
		}
		newsEditDTO.setLink(news.getLink());
		newsEditDTO.setShowOnlyOnMobile(news.isShowNewsOnlyOnMobile());
		newsEditDTO.setHide(news.isHide());
		newsEditDTO.setReferenceDate(news.getReferenceDate());

		return newsEditDTO;
	}

	public void fill(News news) {
		news.setName(name);
		news.setShortDescription(shortDescription);
		news.setLongDescription(longDescription);
		news.setHighlight(highlight);
		news.setShowNewsOnlyOnMobile(showOnlyOnMobile);
		news.setLink(link);
		news.setHide(hide);
		news.setReferenceDate(referenceDate);
	}

}
