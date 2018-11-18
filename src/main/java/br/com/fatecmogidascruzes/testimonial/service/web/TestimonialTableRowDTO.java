package br.com.fatecmogidascruzes.testimonial.service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.testimonial.Testimonial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TestimonialTableRowDTO {

	private UUID hash;
	private String hashString;
	private String name;
	private boolean show;

	public static TestimonialTableRowDTO from(Testimonial testimonial) {
		TestimonialTableRowDTO baseCategoryTableRowDTO = new TestimonialTableRowDTO();
		baseCategoryTableRowDTO.setHash(testimonial.getHash());
		baseCategoryTableRowDTO.setHashString(FriendlyId.toFriendlyId(testimonial.getHash()));
		baseCategoryTableRowDTO.setName(testimonial.getName());
		baseCategoryTableRowDTO.setShow(testimonial.isShow());
		return baseCategoryTableRowDTO;
	}

	public static <T extends Testimonial> List<TestimonialTableRowDTO> listFrom(List<T> testimonials) {
		List<TestimonialTableRowDTO> testimonialTableRowDTOs = new ArrayList<>();
		testimonials.forEach(baseCategory -> testimonialTableRowDTOs.add(TestimonialTableRowDTO.from(baseCategory)));
		return testimonialTableRowDTOs;
	}

}
