package br.com.fatecmogidascruzes.file.util;

import static java.nio.file.FileSystems.getDefault;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.ImageVariation;
import br.com.fatecmogidascruzes.storage.Storage;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class ImageUtilImpl implements ImageUtil {

	private static final double[] RESOLUTIONS = { 320, 640, 1280, 1920, 2560 };

	private final Storage storage;

	@Autowired
	public ImageUtilImpl(Storage storage) {
		this.storage = storage;
	}

	/*
	 * @see br.com.fatecmogidascruzes.file.ImageUtil#generateVariations(File)
	 */
	@Override
	public Set<ImageVariation> generateVariations(File file) throws IOException {

		final BufferedImage bufferedImage = storage.loadImage(file.getName());
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();
		int biggest = Math.max(width, height);

		String extension = file.getName().substring(file.getName().lastIndexOf("."));
		String baseFileName = file.getName().substring(0, file.getName().lastIndexOf("."));

		Set<ImageVariation> variations = new HashSet<>();
		for (Double resolution : RESOLUTIONS) {
			if (biggest > resolution) {
				double proportion = resolution / biggest;

				String variationName = baseFileName + "_" + resolution.intValue() + extension;

				Thumbnails.of(storage.loadFile(file.getName())).scale(proportion).toFile(new java.io.File(
						storage.getPath().toAbsolutePath().toString() + getDefault().getSeparator() + variationName));

				java.io.File variationFile = new java.io.File(variationName);
				long size = variationFile.length();

				ImageVariation variation = new ImageVariation();
				variation.setName(variationName);
				variation.setContentType(file.getContentType());
				variation.setSize(size);
				variation.setWidth((int) (width * proportion));
				variation.setHeight((int) (height * proportion));
				variation.setImage(file);
				variations.add(variation);

			}
		}

		return variations;

	}

}