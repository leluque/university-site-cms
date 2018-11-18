package br.com.fatecmogidascruzes.selection.service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.selection.Selection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SelectionTableRowDTO {

	private UUID hash;
	private String hashString;
	private String number;
	private String course;
	private String discipline;

	public static SelectionTableRowDTO from(Selection selection) {
		SelectionTableRowDTO selectionTableRowDTO = new SelectionTableRowDTO();
		selectionTableRowDTO.setHash(selection.getHash());
		selectionTableRowDTO.setHashString(FriendlyId.toFriendlyId(selection.getHash()));
		selectionTableRowDTO.setNumber(selection.getNumber());
		selectionTableRowDTO.setCourse(selection.getCourse().getName());
		selectionTableRowDTO.setDiscipline(selection.getDiscipline());
		return selectionTableRowDTO;
	}

	public static List<SelectionTableRowDTO> listFrom(List<Selection> selections) {
		List<SelectionTableRowDTO> selectionTableRowDTOs = new ArrayList<>();
		selections.forEach(selection -> selectionTableRowDTOs.add(SelectionTableRowDTO.from(selection)));
		return selectionTableRowDTOs;
	}

}
