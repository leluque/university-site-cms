package br.com.fatecmogidascruzes.gallery.service.web;

import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.gallery.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class AlbumPhotosEditDTO {

	private String hashString;
	private MultipartFile[] images;

	public AlbumPhotosEditDTO(String hashString) {
		super();
		this.hashString = hashString;
	}

	public static AlbumPhotosEditDTO from(Album album) {
		AlbumPhotosEditDTO albumPhotosEditDTO = new AlbumPhotosEditDTO();
		albumPhotosEditDTO.setHashString(FriendlyId.toFriendlyId(album.getHash()));

		return albumPhotosEditDTO;
	}

}
