package br.com.fatecmogidascruzes.user.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.email.EmailService;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.service.BaseServiceImpl;
import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.data.UserDAO;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	private final UserDAO userDAO;
	private final EmailService emailService;

	public UserServiceImpl(UserDAO userDAO, EmailService emailService) {
		super(userDAO);
		this.userDAO = userDAO;
		this.emailService = emailService;
	}

	@Override
	public Optional<User> getByActionToken(String accessToken) {
		return this.userDAO.findByActionToken(accessToken);
	}

	@Override
	public Optional<User> getByAccessToken(String accessToken) {
		return this.userDAO.findByAccessToken(accessToken);
	}

	@Override
	public Optional<User> getByUsername(String name) {
		return this.userDAO.findByEnabledTrueAndName(name);
	}

	@Override
	public Optional<User> checkWhetherExistesOtherUserWithTheSameUserName(String userName, Long id) {
		if (null == id) {
			return getByUsername(userName);
		} else {
			return userDAO.checkWhetherExistsOtherUserWithTheSameUserName(id, userName);
		}
	}

	@Override
	public void doRecoveryPassword(String username) throws InexistentOrDisabledEntity, InternalError {
		Optional<User> userOptional = getByUsername(username);
		if (userOptional.isPresent() && userOptional.get().isEnabled()) {
			User user = userOptional.get();

			user.setActionToken(FriendlyId.toFriendlyId(UUID.randomUUID()));
			LocalDateTime tomorrow = LocalDateTime.now().plusHours(24);
			user.setActionTokenValidity(tomorrow);
			userDAO.save(user);
			try {
				emailService.sendRecoveryPasswordEMail(user.getName(), user.getActionToken());
			} catch (Exception error) {
				error.printStackTrace();
				throw new InternalError("An error happened while sending the e-mail.");
			}

		} else {
			throw new InexistentOrDisabledEntity("The user does not exist or is disabled.");
		}
	}

	@Override
	public Page<User> getEnabledByFilter(SearchCriteria searchCriteria) {
		return userDAO.getEnabledByFilter(searchCriteria.getFilter(), prepareCriteria(searchCriteria));
	}

}
