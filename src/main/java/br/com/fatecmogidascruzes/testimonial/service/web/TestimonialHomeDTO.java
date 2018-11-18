package br.com.fatecmogidascruzes.testimonial.service.web;

import java.util.ArrayList;
import java.util.List;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.testimonial.Testimonial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TestimonialHomeDTO {

	private String hashString;
	private String name;
	private String description;
	private String position;
	private String imageAlternativeDescription;

	public static TestimonialHomeDTO from(Testimonial testimonial) {
		TestimonialHomeDTO testimonialEditDTO = new TestimonialHomeDTO();
		testimonialEditDTO.setHashString(FriendlyId.toFriendlyId(testimonial.getHash()));
		testimonialEditDTO.setName(testimonial.getName());
		testimonialEditDTO.setPosition(testimonial.getPosition());
		testimonialEditDTO.setDescription(testimonial.getDescription());
		testimonialEditDTO.setImageAlternativeDescription(testimonial.getImage().getAlternativeDescription());

		return testimonialEditDTO;
	}

	public static <T extends Testimonial> List<TestimonialHomeDTO> listFrom(List<T> testimonials) {
		List<TestimonialHomeDTO> testimonialHomeDTOs = new ArrayList<>();
		testimonials.forEach(news -> testimonialHomeDTOs.add(TestimonialHomeDTO.from(news)));
		return testimonialHomeDTOs;
	}

}
