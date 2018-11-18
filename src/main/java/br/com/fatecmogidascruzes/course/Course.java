package br.com.fatecmogidascruzes.course;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Course {

	ADS("Análise e Desenvolvimento de Sistemas"), AGRO("Agronegócio"), LOG("Logística"), RH("Recursos-Humanos"),
	GESTAO("Gestão Pública");

	private String name;

}