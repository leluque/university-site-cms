package br.com.fatecmogidascruzes.selection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SelectionStatus {

	STARTED("Aberto"), UNDERGOIND("Em andamento"), FINISHED("Finalizado");

	private String name;

}
