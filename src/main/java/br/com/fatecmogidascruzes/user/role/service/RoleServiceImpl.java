package br.com.fatecmogidascruzes.user.role.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.service.BaseServiceImpl;
import br.com.fatecmogidascruzes.user.role.Role;
import br.com.fatecmogidascruzes.user.role.data.RoleDAO;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements RoleService {

	private final RoleDAO roleDAO;

	@Autowired
	public RoleServiceImpl(RoleDAO roleDAO) {
		super(roleDAO);
		this.roleDAO = roleDAO;
	}

	@Override
	public Optional<Role> getEnabledByName(String name) {
		return roleDAO.findByEnabledTrueAndName(name);
	}

}
