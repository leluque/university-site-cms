package br.com.fatecmogidascruzes.file.service.web;

import java.util.UUID;

import br.com.fatecmogidascruzes.exception.DoesNotHaveAccessException;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.file.FileDTO;

public interface FileWebService {

	FileDTO getImage(UUID fileHash, Integer width, Integer height)
			throws InexistentOrDisabledEntity, DoesNotHaveAccessException;

	FileDTO getFile(UUID fileHash)
			throws InexistentOrDisabledEntity, DoesNotHaveAccessException;

}
