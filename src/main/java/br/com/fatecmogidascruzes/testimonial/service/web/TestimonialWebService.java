package br.com.fatecmogidascruzes.testimonial.service.web;

import java.util.List;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.web.BadRequestException;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.exception.web.InternalErrorException;

public interface TestimonialWebService {

	void save(TestimonialEditDTO expenseEditDTO) throws BadRequestException, ForbiddenException, InternalErrorException;

	TableDTO<TestimonialTableRowDTO> getTable(SearchCriteria searchCriteria, int draw);

	List<TestimonialHomeDTO> getHomeTestimonials();
	
}
