package br.com.fatecmogidascruzes.user.service.web;

import java.util.UUID;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.exception.AlreadyUsedUsernameException;

public interface UserWebService {

	TableDTO<UserTableRowDTO> getUserTable(SearchCriteria searchCriteria, int draw);

	UserEditDTO findUser(UUID userHash) throws InexistentOrDisabledEntity;

	void saveUser(UserEditDTO userEditDTO, User loggedUser) throws AlreadyUsedUsernameException;

	void updatePassword(PasswordEditDTO passwordEditDTO);

}
