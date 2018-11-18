package br.com.fatecmogidascruzes.user.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.service.BaseService;
import br.com.fatecmogidascruzes.user.User;

public interface UserService extends BaseService<User, Long> {

	Optional<User> getByActionToken(String accessToken);

	Optional<User> getByAccessToken(String accessToken);

	Optional<User> getByUsername(String name);

	Optional<User> checkWhetherExistesOtherUserWithTheSameUserName(String userName, Long id);

	void doRecoveryPassword(String username) throws InexistentOrDisabledEntity, InternalError;

	Page<User> getEnabledByFilter(SearchCriteria searchCriteria);

}