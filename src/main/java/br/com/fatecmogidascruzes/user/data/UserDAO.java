package br.com.fatecmogidascruzes.user.data;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.user.User;

public interface UserDAO extends DAOImpl<User, Long>, JpaRepository<User, Long> {

	Optional<User> findByActionToken(String accessToken);

	Optional<User> findByAccessToken(String accessToken);

	Optional<User> findByEnabledTrueAndName(String name);

	@Query("SELECT us FROM User us WHERE :username = name AND :id <> us.id")
	Optional<User> checkWhetherExistsOtherUserWithTheSameUserName(@Param("id") Long id, @Param("username") String name);

	@Query("SELECT us FROM User us WHERE TRUE = us.enabled AND (UPPER(us.fullName) LIKE CONCAT('%',:filter,'%') OR UPPER(us.name) LIKE CONCAT('%',:filter,'%'))")
	Page<User> getEnabledByFilter(@Param("filter") String filter, Pageable pageable);

}
