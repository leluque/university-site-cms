package br.com.fatecmogidascruzes.storage;

import static java.nio.file.FileSystems.getDefault;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Profile("local-storage")
@Component
public class LocalStorage implements Storage {

	@Value("${storage.path}")
	private String storagePath;

	private Path path;

	// The @Value is available only after constructions.
	@PostConstruct
	public void init() {
		// this.path = getDefault().getPath(storagePath, "files");
		this.path = getDefault().getPath(storagePath, "");

		try {
			Files.createDirectories(path);
		} catch (IOException error) {
			throw new RuntimeException("Error creating files' folder.", error);
		}
		try {
			Files.createDirectories(getDefault().getPath(storagePath, "tmp"));
		} catch (IOException error) {
			throw new RuntimeException("Error creating temporary files' folder.", error);
		}
	}

	@Override
	public String store(MultipartFile file) {
		store(file, file.getName());
		return file.getName();
	}

	@Override
	public Path getPath() {
		return this.path;
	}

	@Override
	public void store(MultipartFile arquivo, String name) {
		try {
			arquivo.transferTo(new File(this.path.toAbsolutePath().toString() + getDefault().getSeparator() + name));
		} catch (IOException error) {
			throw new RuntimeException("Error saving local file.", error);
		}
	}

	@Override
	public byte[] loadBytes(String name) {
		try {
			return Files.readAllBytes(this.path.resolve(name));
		} catch (IOException error) {
			error.printStackTrace();
			throw new RuntimeException("Error loading local file.", error);
		}
	}

	@Override
	public File loadFile(String name) {
		return this.path.resolve(name).toFile();
	}

	@Override
	public Path getPathTo(String fileName) {
		return this.path.resolve(fileName);
	}

	@Override
	public BufferedImage loadImage(String name) throws IOException {
		return ImageIO.read(this.path.resolve(name).toFile());
	}

	@Override
	public void remove(String name) {
		try {
			Files.delete(this.path.resolve(name));
		} catch (IOException error) {
			throw new RuntimeException("Error removing local file.", error);
		}
	}

}
