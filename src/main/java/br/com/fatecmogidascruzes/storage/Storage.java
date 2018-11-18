package br.com.fatecmogidascruzes.storage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface Storage {

	Path getPath();
	
	Path getPathTo(String fileName);

	void store(MultipartFile file, String name);

	String store(MultipartFile file);

	byte[] loadBytes(String name);

	BufferedImage loadImage(String name) throws IOException;

	File loadFile(String name);

	void remove(String name);

}
