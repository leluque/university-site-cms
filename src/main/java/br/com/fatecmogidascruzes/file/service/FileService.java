package br.com.fatecmogidascruzes.file.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.service.BaseService;

public interface FileService extends BaseService<File, Long> {

	File saveFile(MultipartFile file);

	File saveImage(MultipartFile multipartFile, String alternativeDescription) throws IOException;

	byte[] loadBytes(Long id);

	byte[] loadBytes(Long id, int width, int height);

}
