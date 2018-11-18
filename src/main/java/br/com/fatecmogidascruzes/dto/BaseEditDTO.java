package br.com.fatecmogidascruzes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEditDTO {

	protected String hashString;

	public boolean isNew() {
		return null == hashString || hashString.trim().isEmpty();
	}

}
