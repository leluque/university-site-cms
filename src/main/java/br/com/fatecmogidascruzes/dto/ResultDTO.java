package br.com.fatecmogidascruzes.dto;

import javax.persistence.Access;
import javax.persistence.AccessType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Access(AccessType.FIELD)
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ResultDTO {

	private boolean success;
	private String message;

}
