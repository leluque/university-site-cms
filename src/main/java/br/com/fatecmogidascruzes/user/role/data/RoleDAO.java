package br.com.fatecmogidascruzes.user.role.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatecmogidascruzes.data.DAOImpl;
import br.com.fatecmogidascruzes.user.role.Role;

public interface RoleDAO extends DAOImpl<Role, Long>, JpaRepository<Role, Long> {

	public Optional<Role> findByEnabledTrueAndName(String name);

}