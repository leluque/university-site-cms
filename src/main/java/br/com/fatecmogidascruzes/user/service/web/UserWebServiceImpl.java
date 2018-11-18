package br.com.fatecmogidascruzes.user.service.web;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.exception.web.ForbiddenException;
import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.UserStatus;
import br.com.fatecmogidascruzes.user.exception.AlreadyUsedUsernameException;
import br.com.fatecmogidascruzes.user.role.service.RoleService;
import br.com.fatecmogidascruzes.user.service.UserService;

@Service
public class UserWebServiceImpl implements UserWebService {

	private final RoleService roleService;
	private final UserService userService;

	@Autowired
	public UserWebServiceImpl(RoleService roleService, UserService userService) {
		super();
		this.roleService = roleService;
		this.userService = userService;
	}

	@Override
	public TableDTO<UserTableRowDTO> getUserTable(SearchCriteria searchCriteria, int draw) {
		Page<User> users = this.userService.getEnabledByFilter(searchCriteria);
		TableDTO<UserTableRowDTO> table = new TableDTO<>();
		table.setDraw(draw);
		table.setRecordsTotal((int) userService.countEnabled().longValue());
		table.setRecordsFiltered((int) users.getTotalElements());

		for (User user : users) {
			table.add(UserTableRowDTO.from(user));
		}
		return table;
	}

	@Override
	public UserEditDTO findUser(UUID userHash) throws InexistentOrDisabledEntity {

		Optional<User> userOptional = this.userService.getByHash(userHash);
		if (userOptional.isPresent() && userOptional.get().isEnabled() && userOptional.get().hasRole("ROLE_ADMIN")) {
			User user = userOptional.get();

			return UserEditDTO.from(user);
		} else {
			throw new InexistentOrDisabledEntity("The informed user does not exist or is disabled");
		}

	}

	@Override
	public void saveUser(UserEditDTO userDTO, User loggedUser) throws AlreadyUsedUsernameException {
		User user = new User();
		user.setStatus(UserStatus.NOT_ACTIVATED); // When the user is new, he/she has not activated yet.

		if (null != userDTO.getHashString() && !userDTO.getHashString().trim().isEmpty()) {
			Optional<User> userOptional = this.userService.getByHash(FriendlyId.toUuid(userDTO.getHashString()));
			if (userOptional.isPresent() && userOptional.get().isEnabled()) {
				user = userOptional.get();
			}
		}
		String currentUserName = user.getName();

		userDTO.fill(user);

		// Check whether there is a user with the same username.
		try {
			if ((userService.getByUsername(userDTO.getName())).isPresent()
					&& !currentUserName.equals(userDTO.getName())) {
				throw new AlreadyUsedUsernameException("There is another user with the same username");
			}
		} catch (Exception error) {
			throw new AlreadyUsedUsernameException("There is another user with the same username");
		}

		user.setRoles(roleService.getEnabledByName("ROLE_ADMIN").get());

		this.userService.save(user);
	}

	@Override
	public void updatePassword(PasswordEditDTO passwordEditDTO) {
		Optional<User> userOptional = Optional.empty();
		if (null != passwordEditDTO.getHashString() && !passwordEditDTO.getHashString().trim().isEmpty()) {
			userOptional = this.userService.getByHash(FriendlyId.toUuid(passwordEditDTO.getHashString()));
		}

		if (userOptional.isPresent() && userOptional.get().isEnabled()) {
			User user = userOptional.get();

			passwordEditDTO.fill(user);

			this.userService.save(user);
		} else {
			throw new ForbiddenException("You cannot change the user password");
		}
	}
}
