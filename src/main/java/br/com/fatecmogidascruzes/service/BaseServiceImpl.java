package br.com.fatecmogidascruzes.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.data.SearchCriteria.Order;
import br.com.fatecmogidascruzes.domain.Entity;

public abstract class BaseServiceImpl<O extends Entity, K> {

	private final DAOImpl<O, K> dao;
	private final JpaRepository<O, K> daoJPA;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl(Object dao) {
		if (!(dao instanceof DAOImpl) || !(dao instanceof JpaRepository)) {
			throw new IllegalArgumentException("The specified DAO is not an instance of the required interfaces.");
		}
		this.dao = (DAOImpl<O, K>) dao;
		this.daoJPA = (JpaRepository<O, K>) dao;
	}

	protected Sort prepareSortObject(SearchCriteria searchCriteria) {
		Sort.Direction ORDER = Order.ASCENDING == searchCriteria.getOrder() ? Sort.Direction.ASC : Sort.Direction.DESC;
		return new Sort(ORDER, searchCriteria.getOrderBy());
	}

	protected PageRequest prepareCriteria(SearchCriteria searchCriteria) {
		if (!searchCriteria.hasPagination())
			return null;

		return PageRequest.of(searchCriteria.getInitialRegister(), searchCriteria.getNumberOfRegisters(),
				prepareSortObject(searchCriteria));
	}

	public Long countEnabled() {
		return this.dao.countByEnabledTrue();
	}

	public List<O> getEnabled() {
		return this.dao.findByEnabledTrue();
	}

	public Page<O> getEnabled(SearchCriteria searchCriteria) {
		return this.dao.findByEnabledTrue(this.prepareCriteria(searchCriteria));
	}

	public Long countAll() {
		return this.daoJPA.count();
	}

	public List<O> getAll() {
		return this.daoJPA.findAll();
	}

	public Page<O> getAll(SearchCriteria searchCriteria) {
		return this.daoJPA.findAll(this.prepareCriteria(searchCriteria));
	}

	public Optional<O> getByKey(K key) {
		return this.daoJPA.findById(key);
	}

	public Optional<O> getByHash(UUID hash) {
		return this.dao.findByHash(hash);
	}

	public Optional<O> getEnabledByHash(UUID hash) {
		return this.dao.findByHashAndEnabledTrue(hash);
	}

	public void save(O object) {
		this.daoJPA.saveAndFlush(object);
	}

	public void save(Collection<O> objects) {
		this.daoJPA.saveAll(objects);
	}

	public void update(O object) {
		this.daoJPA.saveAndFlush(object);
	}

	public void remove(O object) {
		this.daoJPA.delete(object);
	}

	public void removeLogically(O object) {
		object.setEnabled(false);
		update(object);
	}

	public void removeByHash(UUID hash) {
		this.getByHash(hash).ifPresent(object -> this.daoJPA.delete(object));
	}

	public void removeLogicallyByHash(UUID hash) {
		this.getByHash(hash).ifPresent(object -> this.removeLogically(object));
	}

	public void removeByKey(K key) {
		this.getByKey(key).ifPresent(object -> this.daoJPA.delete(object));
	}

	public void removeLogicallyByKey(K key) {
		this.getByKey(key).ifPresent(object -> this.removeLogically(object));
	}

}
