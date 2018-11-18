package br.com.fatecmogidascruzes.gender;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {

	MALE("Masculino"), FEMALE("Feminino"), NOT_SPECIFIED("Não Especificado");

	private String name;

};
