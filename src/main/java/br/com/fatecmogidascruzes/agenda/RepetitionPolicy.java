package br.com.fatecmogidascruzes.agenda;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RepetitionPolicy {

	EVERY_DAY("Todos os dias"), EVERY_WORKING_DAY("Todos os dias úteis (segunda à sexta)"), WEEKLY("Toda semana"),
	EVERY_OTHER_DAY("Dia sim, dia não"), EVERY_OTHER_WEEK("Semana sim, semana não"), MONTHLY("Mesmo dia, todo mês"),
	ANNUALY("Todo ano");

	private String name;

}
