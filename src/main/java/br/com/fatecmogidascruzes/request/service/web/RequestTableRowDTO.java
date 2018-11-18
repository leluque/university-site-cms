package br.com.fatecmogidascruzes.request.service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.request.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RequestTableRowDTO {

	private UUID hash;
	private String hashString;
	private Long protocol;
	private String registry;
	private String name;
	private String course;
	private String requestSituation;

	public static RequestTableRowDTO from(Request request) {
		RequestTableRowDTO requestTableRowDTO = new RequestTableRowDTO();
		requestTableRowDTO.setHash(request.getHash());
		requestTableRowDTO.setHashString(FriendlyId.toFriendlyId(request.getHash()));
		requestTableRowDTO.setProtocol(request.getProtocol());
		requestTableRowDTO.setRegistry(request.getRegistry());
		requestTableRowDTO.setCourse(request.getCourse().getName());
		requestTableRowDTO.setName(request.getName());
		requestTableRowDTO.setRequestSituation(request.getRequestSituation().getName());
		return requestTableRowDTO;
	}

	public static List<RequestTableRowDTO> listFrom(List<Request> requests) {
		List<RequestTableRowDTO> requestTableRowDTOs = new ArrayList<>();
		requests.forEach(request -> requestTableRowDTOs.add(RequestTableRowDTO.from(request)));
		return requestTableRowDTOs;
	}

}
