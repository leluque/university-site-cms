package br.com.fatecmogidascruzes.gallery.service.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatecmogidascruzes.gallery.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class AlbumDTO {

	private UUID hash;
	private String hashString;
	private String name;
	@JsonFormat(pattern = "yyyyMMdd")
	private LocalDate date;
	private int numberOfPhotos;

	public static AlbumDTO from(Album album) {
		AlbumDTO albumDTO = new AlbumDTO();
		albumDTO.setHash(album.getHash());
		albumDTO.setHashString(FriendlyId.toFriendlyId(album.getHash()));
		albumDTO.setName(album.getName());
		if (null != album.getImages()) {
			albumDTO.setNumberOfPhotos(album.getImages().size());
		}

		return albumDTO;
	}

	public static AlbumDTO[] arrayFrom(List<Album> albums) {
		AlbumDTO[] albumsDTO = new AlbumDTO[albums.size()];
		int i = 0;
		for (Album album : albums) {
			albumsDTO[i++] = from(album);
		}
		return albumsDTO;
	}

	public static List<AlbumDTO> listFrom(List<Album> albums) {
		List<AlbumDTO> albumsDTOs = new ArrayList<>();
		for (Album album : albums) {
			albumsDTOs.add(from(album));
		}
		return albumsDTOs;
	}

}