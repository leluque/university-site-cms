package br.com.fatecmogidascruzes.user.role.service;

import java.util.Optional;

import br.com.fatecmogidascruzes.service.BaseService;
import br.com.fatecmogidascruzes.user.role.Role;

public interface RoleService extends BaseService<Role, Long> {

	Optional<Role> getEnabledByName(String name);

}