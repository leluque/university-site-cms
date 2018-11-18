package br.com.fatecmogidascruzes.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import br.com.fatecmogidascruzes.data.SearchCriteria;

public interface BaseService<O, K> {

	Long countEnabled();

	List<O> getEnabled();

	Page<O> getEnabled(SearchCriteria searchCriteria);

	Long countAll();

	List<O> getAll();

	Page<O> getAll(SearchCriteria searchCriteria);

	Optional<O> getByKey(K key);

	Optional<O> getByHash(UUID hash);

	Optional<O> getEnabledByHash(UUID hash);

	void save(O object);

	void save(Collection<O> objects);

	void update(O object);

	void remove(O object);

	void removeLogically(O object);

	void removeByKey(K key);

	void removeLogicallyByKey(K key);

	void removeByHash(UUID uuid);

	void removeLogicallyByHash(UUID uuid);

}
