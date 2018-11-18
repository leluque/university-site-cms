package br.com.fatecmogidascruzes.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.fatecmogidascruzes.domain.Entity;

public interface DAOImpl<O extends Entity, K> {

	Long countByEnabledTrue();

	List<O> findByEnabledTrue();

	Page<O> findByEnabledTrue(Pageable pageable);

	Optional<O> findByHash(UUID hash);

	Optional<O> findByHash(String hash);

	Optional<O> findByHashAndEnabledTrue(UUID hash);

	Optional<O> findByHashAndEnabledTrue(String hash);

}