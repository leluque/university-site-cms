package br.com.fatecmogidascruzes.testimonial.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.testimonial.Testimonial;

public interface TestimonialDAO extends DAOImpl<Testimonial, Long>, JpaRepository<Testimonial, Long> {

	@Query("SELECT te FROM Testimonial te WHERE TRUE = te.enabled AND (UPPER(te.name) LIKE CONCAT('%',:filter,'%') OR UPPER(te.description) LIKE CONCAT('%',:filter,'%'))")
	Page<Testimonial> getEnabledByFilter(@Param("filter") String filter, Pageable pageable);
	
	List<Testimonial> getByEnabledTrueAndShowTrue();

}