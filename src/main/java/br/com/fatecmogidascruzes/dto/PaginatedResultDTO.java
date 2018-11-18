package br.com.fatecmogidascruzes.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PaginatedResultDTO<T> {

	private int numberOfPages;
	private int currentPage;
	private List<T> data = new ArrayList<>();

}
