package br.com.fatecmogidascruzes.testimonial.service.web;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.testimonial.Testimonial;
import br.com.fatecmogidascruzes.testimonial.service.web.validator.TestimonialImageMustHaveDescription;
import br.com.fatecmogidascruzes.testimonial.service.web.validator.TestimonialMustHaveImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@TestimonialMustHaveImage
@TestimonialImageMustHaveDescription
public class TestimonialEditDTO {

	private String hashString;
	@NotBlank(message = "O nome do depoente é obrigatório")
	@Length(max = 100, message = "O nome do depoente não pode exceder 100 caracteres")
	private String name;
	@Length(max = 2000, message = "O depoimento do depoente não pode exceder 200 caracteres")
	private String description;
	@NotBlank(message = "A posição (professor, aluno, funcionário, comunidade etc.) do depoente é obrigatória")
	@Length(max = 50, message = "A posição (professor, aluno, funcionário, comunidade etc.) do depoente não pode exceder 50 caracteres")
	private String position;
	private boolean show;
	private MultipartFile image;
	private String imageAlternativeDescription;

	public static TestimonialEditDTO from(Testimonial testimonial) {
		TestimonialEditDTO testimonialEditDTO = new TestimonialEditDTO();
		testimonialEditDTO.setHashString(FriendlyId.toFriendlyId(testimonial.getHash()));
		testimonialEditDTO.setName(testimonial.getName());
		testimonialEditDTO.setPosition(testimonial.getPosition());
		testimonialEditDTO.setShow(testimonial.isShow());
		testimonialEditDTO.setDescription(testimonial.getDescription());
		testimonialEditDTO.setImageAlternativeDescription(testimonial.getImage().getAlternativeDescription());

		return testimonialEditDTO;
	}

	public void fill(Testimonial testimonial) {
		testimonial.setName(name);
		testimonial.setDescription(description);
		testimonial.setPosition(position);
		testimonial.setShow(show);
		testimonial.setImageAlternativeDescription(testimonial.getImageAlternativeDescription());
	}

}
