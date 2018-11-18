package br.com.fatecmogidascruzes.file.util;

import java.io.IOException;
import java.util.Set;

import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.ImageVariation;

public interface ImageUtil {

	Set<ImageVariation> generateVariations(File file) throws IOException;

}