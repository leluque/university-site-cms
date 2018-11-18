package br.com.fatecmogidascruzes.testimonial.service.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.service.FileService;
import br.com.fatecmogidascruzes.testimonial.Testimonial;
import br.com.fatecmogidascruzes.testimonial.service.TestimonialService;

@Service
public class TestimonialWebServiceImpl implements TestimonialWebService {

	private final TestimonialService testimonialService;
	private final FileService fileService;

	@Autowired
	public TestimonialWebServiceImpl(TestimonialService categoryService, FileService fileService) {
		super();
		this.testimonialService = categoryService;
		this.fileService = fileService;
	}

	@Override
	public void save(TestimonialEditDTO testimonialEditDTO)
			throws BadRequestException, ForbiddenException, InternalErrorException {
		Testimonial testimonial = new Testimonial();
		if (null != testimonialEditDTO.getHashString() && !testimonialEditDTO.getHashString().trim().isEmpty()) {
			Optional<Testimonial> categoryOptional = this.testimonialService
					.getEnabledByHash(FriendlyId.toUuid(testimonialEditDTO.getHashString()));
			if (!categoryOptional.isPresent() || !categoryOptional.get().isEnabled()) {
				throw new BadRequestException(
						"The specified testimonial does not exist or is disabled and then cannot be updated");
			}
			testimonial = categoryOptional.get();
		}
		testimonialEditDTO.fill(testimonial);

		if (null != testimonialEditDTO.getImage()) {
			if (!testimonialEditDTO.getImage().isEmpty()) {
				// If the user has specified an image but another one exists, delete the old
				// one.
				if (null != testimonial.getImage()) {
					fileService.removeByKey(testimonial.getImage().getId());
				}

				try {
					File newImage = fileService.saveImage(testimonialEditDTO.getImage(),
							testimonialEditDTO.getImageAlternativeDescription());
					testimonial.setImage(newImage);
				} catch (IOException error) {
					error.printStackTrace();
					throw new InternalErrorException("An error happened while trying to save the testimonial image");
				}
			}

			testimonial.getImage().setAlternativeDescription(testimonialEditDTO.getImageAlternativeDescription());
		}

		this.testimonialService.save(testimonial);
	}

	@Override
	public TableDTO<TestimonialTableRowDTO> getTable(SearchCriteria searchCriteria, int draw) {

		Page<Testimonial> categoriesPage = this.testimonialService.getEnabledByFilter(searchCriteria);
		TableDTO<TestimonialTableRowDTO> table = new TableDTO<>();
		table.setDraw(draw);
		table.setRecordsTotal((int) testimonialService.countEnabled().longValue());
		table.setRecordsFiltered((int) categoriesPage.getTotalElements());

		table.setData(TestimonialTableRowDTO.listFrom(categoriesPage.getContent()));

		return table;
	}

	@Override
	public List<TestimonialHomeDTO> getHomeTestimonials() {
		return TestimonialHomeDTO.listFrom(testimonialService.getEnabledToShow());
	}
	
}
