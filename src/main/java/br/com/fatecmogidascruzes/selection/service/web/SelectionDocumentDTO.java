package br.com.fatecmogidascruzes.selection.service.web;

import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.file.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SelectionDocumentDTO {

	private UUID hash;
	private String hashString;
	private String title;
	private boolean enabled;

	public static SelectionDocumentDTO from(File file) {
		SelectionDocumentDTO postDocumentDTO = new SelectionDocumentDTO();

		postDocumentDTO.setHash(file.getHash());
		postDocumentDTO.setHashString(FriendlyId.toFriendlyId(file.getHash()));
		postDocumentDTO.setTitle(file.getDescription());
		postDocumentDTO.setEnabled(file.isEnabled());

		return postDocumentDTO;
	}

}
