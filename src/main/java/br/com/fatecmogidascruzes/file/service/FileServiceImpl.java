package br.com.fatecmogidascruzes.file.service;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.ImageVariation;
import br.com.fatecmogidascruzes.file.data.FileDAO;
import br.com.fatecmogidascruzes.file.util.ImageUtil;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;
import br.com.fatecmogidascruzes.storage.Storage;

@Service
public class FileServiceImpl extends BaseServiceImpl<File, Long> implements FileService {

	private final Storage storage;
	private final FileDAO fileDAO;
	private final ImageUtil imageUtil;

	@Autowired
	public FileServiceImpl(Storage storage, FileDAO fileDAO, ImageUtil imageUtil) {
		super(fileDAO);
		this.storage = storage;
		this.fileDAO = fileDAO;
		this.imageUtil = imageUtil;
	}

	@Override
	@Transactional
	public File saveFile(MultipartFile multipartFile) {
		File arquivo = new File();
		arquivo.setOriginalName(multipartFile.getOriginalFilename());
		arquivo.setContentType(multipartFile.getContentType());
		arquivo.setSize(multipartFile.getSize());
		fileDAO.save(arquivo);

		String extension = ".unknown";
		if (multipartFile.getOriginalFilename().lastIndexOf(".") >= 0) {
			extension = multipartFile.getOriginalFilename()
					.substring(multipartFile.getOriginalFilename().lastIndexOf("."));
		}
		String fileName = "file_" + arquivo.getId() + extension;
		storage.store(multipartFile, fileName);

		arquivo.setName(fileName);
		fileDAO.save(arquivo);
		return arquivo;
	}

	@Override
	@Transactional
	public File saveImage(MultipartFile multipartFile, String alternativeDescription) throws IOException {
		File file = new File();
		file.setOriginalName(multipartFile.getOriginalFilename());
		file.setContentType(multipartFile.getContentType());
		file.setSize(multipartFile.getSize());
		file.setAlternativeDescription(alternativeDescription);
		fileDAO.save(file);

		String extension = file.getOriginalName().substring(file.getOriginalName().lastIndexOf("."));
		String fileName = "image_" + file.getId() + extension;
		Set<ImageVariation> variations = null;
		try {

			storage.store(multipartFile, fileName);

			file.setName(fileName);
			fileDAO.save(file);

			variations = imageUtil.generateVariations(file);
			file.setVariations(variations);
			fileDAO.save(file);

		} catch (Exception error) {
			error.printStackTrace();
			try {
				removeByKey(file.getId());
			} catch (Exception error1) {
				error1.printStackTrace();
			}
			throw new IOException("Error saving the files.", error);
		}
		return file;
	}

	@Override
	public byte[] loadBytes(Long id) {
		File image = fileDAO.findById(id).get();
		return storage.loadBytes(image.getName());
	}

	@Override
	public byte[] loadBytes(Long id, int width, int height) {
		File image = fileDAO.findById(id).get();
		return storage.loadBytes(image.getName());
	}

	@Override
	@Transactional
	public void removeByKey(Long id) {
		File file = fileDAO.findById(id).get();
		storage.remove(file.getName());

		if (null != file.getVariations()) {
			for (ImageVariation variacao : file.getVariations()) {
				storage.remove(variacao.getName());
			}
		}

		fileDAO.deleteById(id);
	}

	@Override
	@Transactional
	public void removeByHash(UUID hash) {
		File file = fileDAO.findByHash(hash).get();
		storage.remove(file.getName());

		if (null != file.getVariations()) {
			for (ImageVariation imageVariation : file.getVariations()) {
				storage.remove(imageVariation.getName());
			}
		}

		fileDAO.findByHash(hash).ifPresent(fileEntry -> fileDAO.delete(fileEntry));
	}

}
