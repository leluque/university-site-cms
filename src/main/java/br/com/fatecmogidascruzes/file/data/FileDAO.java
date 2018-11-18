package br.com.fatecmogidascruzes.file.data;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.file.File;

public interface FileDAO extends DAOImpl<File, Long>, JpaRepository<File, Long> {

}
