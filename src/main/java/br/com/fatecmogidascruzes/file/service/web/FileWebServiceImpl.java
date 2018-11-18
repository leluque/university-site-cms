package br.com.fatecmogidascruzes.file.service.web;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.exception.DoesNotHaveAccessException;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.FileDTO;
import br.com.fatecmogidascruzes.file.ImageVariation;
import br.com.fatecmogidascruzes.file.service.FileService;

@Service
public class FileWebServiceImpl implements FileWebService {

	private final FileService fileService;

	@Autowired
	public FileWebServiceImpl(FileService fileService) {
		super();
		this.fileService = fileService;
	}

	@Override
	public FileDTO getImage(UUID fileHash, Integer width, Integer height)
			throws InexistentOrDisabledEntity, DoesNotHaveAccessException {

		Optional<File> fileOptional = this.fileService.getByHash(fileHash);

		// Is the file valid?
		if (fileOptional.isPresent() && fileOptional.get().isEnabled()) {

			File file = fileOptional.get();
			ImageVariation imageVariation = file.getVariationFor(width, height);

			// byte[] bytes = fileService.loadBytes(file.getId(), width, height);
			// return new FileDTO(file.getContentType(), file.getSize(), bytes);
			if (null != imageVariation) {
				return new FileDTO(imageVariation.getName(), imageVariation.getContentType(), imageVariation.getSize(),
						null);
			} else {
				return new FileDTO(file.getName(), file.getContentType(), file.getSize(), null);
			}

		} else { // Is the file valid?
			throw new InexistentOrDisabledEntity("The specified file does not exist or is disabled");
		}

	}

	@Override
	public FileDTO getFile(UUID fileHash) throws InexistentOrDisabledEntity, DoesNotHaveAccessException {

		Optional<File> fileOptional = this.fileService.getByHash(fileHash);

		// Is the file valid?
		if (fileOptional.isPresent() && fileOptional.get().isEnabled()) {

			File file = fileOptional.get();

			// byte[] bytes = fileService.loadBytes(file.getId());
			// return new FileDTO(file.getContentType(), file.getSize(), bytes);
			return new FileDTO(file.getName(), file.getContentType(), file.getSize(), null);

		} else { // Is the file valid?
			throw new InexistentOrDisabledEntity("The specified file does not exist or is disabled");
		}

	}

}
