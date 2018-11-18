package br.com.fatecmogidascruzes.selection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SelectionType {

	PUBLIC_SELECTION("Seleção Pública"), CONTEST("Concurso");

	private String name;

}