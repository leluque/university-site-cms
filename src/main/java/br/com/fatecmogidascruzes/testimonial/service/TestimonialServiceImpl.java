package br.com.fatecmogidascruzes.testimonial.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;
import br.com.fatecmogidascruzes.testimonial.Testimonial;
import br.com.fatecmogidascruzes.testimonial.data.TestimonialDAO;

@Service
public class TestimonialServiceImpl extends BaseServiceImpl<Testimonial, Long> implements TestimonialService {

	private TestimonialDAO testimonialDAO;

	public TestimonialServiceImpl(TestimonialDAO testimonialDAO) {
		super(testimonialDAO);
		this.testimonialDAO = testimonialDAO;
	}

	@Override
	public Page<Testimonial> getEnabledByFilter(SearchCriteria searchCriteria) {
		return testimonialDAO.getEnabledByFilter(searchCriteria.getFilter(), prepareCriteria(searchCriteria));
	}

	@Override
	public List<Testimonial> getEnabledToShow() {
		return testimonialDAO.getByEnabledTrueAndShowTrue();
	}
	
}
