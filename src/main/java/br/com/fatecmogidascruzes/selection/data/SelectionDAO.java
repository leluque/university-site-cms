package br.com.fatecmogidascruzes.selection.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.selection.Selection;

public interface SelectionDAO extends DAOImpl<Selection, Long>, JpaRepository<Selection, Long> {

	@Query("SELECT se FROM Selection se WHERE TRUE = se.enabled AND (UPPER(se.number) LIKE CONCAT('%',:filter,'%') OR UPPER(se.description) LIKE CONCAT('%',:filter,'%'))")
	Page<Selection> getEnabledByFilter(@Param("filter") String filemr, Pageable pageable);

}
