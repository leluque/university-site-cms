package br.com.fatecmogidascruzes.testimonial.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.service.BaseService;
import br.com.fatecmogidascruzes.testimonial.Testimonial;

public interface TestimonialService extends BaseService<Testimonial, Long> {

	Page<Testimonial> getEnabledByFilter(SearchCriteria searchCriteria);

	List<Testimonial> getEnabledToShow();
	
	
}
