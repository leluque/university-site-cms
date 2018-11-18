package br.com.fatecmogidascruzes.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TableDTO<T> {

	private Integer recordsTotal;
	private Integer recordsFiltered;
	private Integer draw;
	private List<T> data = new ArrayList<T>();

	public void add(T row) {
		this.data.add(row);
	}

}
