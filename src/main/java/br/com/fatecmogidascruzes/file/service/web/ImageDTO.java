package br.com.fatecmogidascruzes.file.service.web;

import java.util.ArrayList;
import java.util.List;

import br.com.fatecmogidascruzes.file.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ImageDTO {

	private String hashString;
	private String alternativeText;

	public static ImageDTO from(File image) {
		ImageDTO imageDTO = new ImageDTO();
		imageDTO.setHashString(image.getHashString());
		imageDTO.setAlternativeText(image.getAlternativeDescription());
		return imageDTO;
	}

	public static <T extends File> List<ImageDTO> listFrom(List<T> imageList) {
		List<ImageDTO> imageDTOs = new ArrayList<>();
		imageList.forEach(image -> imageDTOs.add(ImageDTO.from(image)));
		return imageDTOs;
	}

}
