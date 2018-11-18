package br.com.fatecmogidascruzes.gallery.service.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.event.service.EventService;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.service.FileService;
import br.com.fatecmogidascruzes.gallery.Album;
import br.com.fatecmogidascruzes.gallery.AlbumService;

@Service
public class AlbumWebServiceImpl implements AlbumWebService {

	private final AlbumService albumService;
	private final EventService eventService;
	private final FileService fileService;

	@Autowired
	public AlbumWebServiceImpl(AlbumService albumService, EventService eventService, FileService fileService) {
		super();
		this.albumService = albumService;
		this.eventService = eventService;
		this.fileService = fileService;
	}

	@Override
	public List<AlbumDTO> getEnabled() {
		return AlbumDTO.listFrom(this.albumService.getEnabled());
	}

	@Override
	public List<AlbumDTO> getEnabledForGallery() {
		return AlbumDTO.listFrom(this.albumService.getEnabled());
	}

	@Override
	public void save(AlbumEditDTO albumEditDTO) throws BadRequestException, ForbiddenException, InternalErrorException {
		Album album = new Album();
		if (null != albumEditDTO.getHashString() && !albumEditDTO.getHashString().trim().isEmpty()) {
			Optional<Album> albumOptional = this.albumService
					.getEnabledByHash(FriendlyId.toUuid(albumEditDTO.getHashString()));
			if (!albumOptional.isPresent() || !albumOptional.get().isEnabled()) {
				throw new BadRequestException(
						"The specified album does not exist or is disabled and then cannot be updated");
			}
			album = albumOptional.get();
		}
		albumEditDTO.fill(album);

		if (null != album.getEvent()) {
			album.setEvent(eventService.getByHash(FriendlyId.toUuid(albumEditDTO.getEvent())).get());
		} else {
			album.setEvent(null);
		}

		if (null != albumEditDTO.getCover()) {
			if (!albumEditDTO.getCover().isEmpty()) {
				// If the user has specified an image but another one exists, delete the old
				// one.
				if (null != album.getCover()) {
					fileService.removeByKey(album.getCover().getId());
				}

				try {
					File newCover = fileService.saveImage(albumEditDTO.getCover(),
							albumEditDTO.getCoverAlternativeDescription());
					album.setCover(newCover);
				} catch (IOException error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the album cover");
				}
			}

			album.getCover().setAlternativeDescription(albumEditDTO.getCoverAlternativeDescription());
		}

		// Remove the images if necessary.
		if (null != albumEditDTO.getImagesHashesToRemove()) {
			for (String hashString : albumEditDTO.getImagesHashesToRemove()) {
				Optional<File> fileOptional = fileService.getByHash(FriendlyId.toUuid(hashString));
				if (fileOptional.isPresent()) {
					File file = fileOptional.get();
					file.setEnabled(false);
					fileService.update(file);
					album.removeImage(file.getHash());
				}
			}
		}

		this.albumService.save(album);
	}

	@Override
	public void upload(AlbumPhotosEditDTO albumPhotosEditDTO)
			throws BadRequestException, ForbiddenException, InternalErrorException {
		Album album;
		if (null != albumPhotosEditDTO.getHashString() && !albumPhotosEditDTO.getHashString().trim().isEmpty()) {
			Optional<Album> albumOptional = this.albumService
					.getEnabledByHash(FriendlyId.toUuid(albumPhotosEditDTO.getHashString()));
			if (!albumOptional.isPresent() || !albumOptional.get().isEnabled()) {
				throw new BadRequestException(
						"The specified album does not exist or is disabled and then cannot be updated");
			}
			album = albumOptional.get();
		} else {
			throw new BadRequestException("You cannot upload photos to an unexistent album");
		}

		// Upload the new images.
		if (null != albumPhotosEditDTO.getImages()) {
			for (MultipartFile multipartFile : albumPhotosEditDTO.getImages()) {
				if (!multipartFile.isEmpty()) {
					try {
						File newImage = fileService.saveImage(multipartFile, "");
						album.addImage(newImage);
					} catch (IOException error) {
						error.printStackTrace();
					}
				}
			}
		}

		albumService.save(album);
	}

}