package br.com.fatecmogidascruzes.gallery.service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.gallery.Album;
import br.com.fatecmogidascruzes.gallery.service.web.validator.AlbumCoverMustHaveDescription;
import br.com.fatecmogidascruzes.gallery.service.web.validator.AlbumMustHaveCover;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@AlbumCoverMustHaveDescription
@AlbumMustHaveCover
public class AlbumEditDTO {

	private String hashString;
	@Length(max = 100, message = "A identificação do álbum não pode exceder 100 caracteres")
	private String event;
	@NotBlank(message = "O título do álbum é obrigatório")
	@Length(max = 100, message = "O título do álbum não pode exceder 100 caracteres")
	private String name;
	@Length(max = 200, message = "A descrição do álbum não pode exceder 100 caracteres")
	private String description;
	private MultipartFile cover;
	private String coverAlternativeDescription;
	private List<String> images = new ArrayList<>();
	private String[] imagesHashesToRemove;
	private boolean isFatecAlbum;

	public void setImages(Set<File> images) {
		if (null != images) {
			for (File image : images) {
				this.images.add(FriendlyId.toFriendlyId(image.getHash()));
			}
		}
	}

	public static AlbumEditDTO from(Album album) {
		AlbumEditDTO albumEditDTO = new AlbumEditDTO();
		albumEditDTO.setHashString(FriendlyId.toFriendlyId(album.getHash()));
		if (null != album.getEvent()) {
			albumEditDTO.setEvent(FriendlyId.toFriendlyId(album.getEvent().getHash()));
		}
		albumEditDTO.setName(album.getName());
		albumEditDTO.setDescription(album.getDescription());
		albumEditDTO.setCoverAlternativeDescription(album.getCover().getAlternativeDescription());
		albumEditDTO.setImages(album.getImages());
		albumEditDTO.setFatecAlbum(album.isFatecAlbum());

		return albumEditDTO;
	}

	public void fill(Album album) {
		album.setName(name);
		album.setDescription(description);
		album.setFatecAlbum(isFatecAlbum);
	}

}
