package br.com.fatecmogidascruzes.gallery.service.web;

import java.util.List;

import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;

public interface AlbumWebService {

	List<AlbumDTO> getEnabled();

	List<AlbumDTO> getEnabledForGallery();

	void save(AlbumEditDTO albumEditDTO) throws BadRequestException, ForbiddenException, InternalErrorException;

	void upload(AlbumPhotosEditDTO albumEditDTO) throws BadRequestException, ForbiddenException, InternalErrorException;

}
