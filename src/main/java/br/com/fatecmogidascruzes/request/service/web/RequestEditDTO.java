package br.com.fatecmogidascruzes.request.service.web;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.course.Course;
import br.com.fatecmogidascruzes.dto.BaseEditDTO;
import br.com.fatecmogidascruzes.request.Request;
import br.com.fatecmogidascruzes.request.Request.DocumentType;
import br.com.fatecmogidascruzes.request.Request.LearnerSituation;
import br.com.fatecmogidascruzes.request.Request.RequestSituation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class RequestEditDTO extends BaseEditDTO {

	@NotBlank(message = "O RA do aluno requerente é obrigatório")
	@Length(max = 30, message = "O RA do aluno requerente pode ter no máximo 30 caracteres")
	private String registry;

	@NotBlank(message = "O nome do aluno requerente é obrigatório")
	@Length(max = 100, message = "O nome do aluno requerente pode ter no máximo 100 caracteres")
	private String name;

	@NotBlank(message = "O telefone do aluno requerente é obrigatório")
	@Length(max = 50, message = "O telefone do aluno requerente pode ter no máximo 50 caracteres")
	private String phone;

	@NotBlank(message = "O e-mail do aluno requerente é obrigatório")
	@Length(max = 50, message = "O e-mail do aluno requerente pode ter no máximo 50 caracteres")
	private String email;

	@Length(max = 400, message = "Os comentários sobre a requisição podem ter no máximo 400 caracteres")
	private String comments;

	@NotNull(message = "O curso do aluno é obrigatório")
	private Course course;

	@NotNull(message = "O tipo de requerente é obrigatório")
	private LearnerSituation learnerSituation;

	@NotNull(message = "O tipo de documento é obrigatório")
	private DocumentType documentType;

	private RequestSituation requestSituation;

	private Long protocol;

	public static RequestEditDTO from(Request request) {
		RequestEditDTO requestEditDTO = new RequestEditDTO();

		requestEditDTO.setHashString(FriendlyId.toFriendlyId(request.getHash()));
		requestEditDTO.setRegistry(request.getRegistry());
		requestEditDTO.setName(request.getName());
		requestEditDTO.setComments(request.getComments());
		requestEditDTO.setCourse(request.getCourse());
		requestEditDTO.setLearnerSituation(request.getLearnerSituation());
		requestEditDTO.setRequestSituation(request.getRequestSituation());
		requestEditDTO.setPhone(request.getPhone());
		requestEditDTO.setEmail(request.getEmail());
		requestEditDTO.setDocumentType(request.getDocumentType());
		requestEditDTO.setProtocol(request.getProtocol());

		return requestEditDTO;
	}

	public void fill(Request request) {

		request.setRegistry(registry);
		request.setName(name);
		request.setComments(comments);
		request.setCourse(course);
		request.setLearnerSituation(learnerSituation);
		request.setRequestSituation(requestSituation);
		request.setPhone(phone);
		request.setEmail(email);
		request.setDocumentType(documentType);

	}

}
