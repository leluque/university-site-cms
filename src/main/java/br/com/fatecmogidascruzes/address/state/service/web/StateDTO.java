package br.com.fatecmogidascruzes.address.state.service.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.address.state.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StateDTO {

	private UUID hash;
	private String hashString;
	private String name;
	private String acronym;

	public static StateDTO from(State state) {
		StateDTO stateDTO = new StateDTO();
		stateDTO.setHash(state.getHash());
		stateDTO.setHashString(FriendlyId.toFriendlyId(state.getHash()));
		stateDTO.setName(state.getName());
		stateDTO.setAcronym(state.getAcronym());
		return stateDTO;
	}

	public static StateDTO[] arrayFrom(List<State> states) {
		StateDTO[] statesDTO = new StateDTO[states.size()];
		int i = 0;
		for (State state : states) {
			statesDTO[i] = StateDTO.from(state);
			i++;
		}
		return statesDTO;
	}

	public static List<StateDTO> listFrom(List<State> states) {
		List<StateDTO> statesDTO = new ArrayList<>(states.size());
		for (State state : states) {
			statesDTO.add(StateDTO.from(state));
		}
		return statesDTO;
	}

}
