package br.com.fatecmogidascruzes.news.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.news.News;

public interface NewsDAO extends DAOImpl<News, Long>, JpaRepository<News, Long> {

	@Query("SELECT ne FROM News ne WHERE TRUE = ne.enabled AND (UPPER(ne.name) LIKE CONCAT('%',:filter,'%') OR UPPER(ne.shortDescription) LIKE CONCAT('%',:filter,'%') OR UPPER(ne.longDescription) LIKE CONCAT('%',:filter,'%'))")
	Page<News> getEnabledByFilter(@Param("filter") String filter, Pageable pageable);

	Page<News> getByEnabledTrueAndHideFalseAndHighlightFalse(Pageable pageable);

	List<News> findByEnabledTrueAndHideFalseAndHighlightTrueOrderByReferenceDateDesc();

}
