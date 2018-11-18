package br.com.fatecmogidascruzes.gallery;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.gallery.data.AlbumDAO;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;

@Service
public class AlbumServiceImpl extends BaseServiceImpl<Album, Long> implements AlbumService {

	private final AlbumDAO albumDAO;

	public AlbumServiceImpl(AlbumDAO albumDAO) {
		super(albumDAO);
		this.albumDAO = albumDAO;
	}

	@Override
	public List<Album> getEnabledByFilter(String filter) {
		return this.albumDAO.getEnabledByFilter(filter);
	}

}
