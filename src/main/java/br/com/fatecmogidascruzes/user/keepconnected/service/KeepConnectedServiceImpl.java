package br.com.fatecmogidascruzes.user.keepconnected.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.user.keepconnected.KeepConnected;
import br.com.fatecmogidascruzes.user.keepconnected.data.KeepConnectedDAO;
import br.com.fatecmogidascruzes.user.service.UserService;

@Service
public class KeepConnectedServiceImpl implements KeepConnectedService {

	private final KeepConnectedDAO keepConnectedDAO;
	private final UserService userService;

	@Autowired
	public KeepConnectedServiceImpl(KeepConnectedDAO keepConnectedDAO, UserService userService) {
		this.keepConnectedDAO = keepConnectedDAO;
		this.userService = userService;
	}

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		KeepConnected keepConnected = new KeepConnected();
		keepConnected.setLastUse(LocalDateTime.ofInstant(token.getDate().toInstant(), ZoneOffset.UTC));
		keepConnected.setSeries(token.getSeries());
		keepConnected.setToken(token.getTokenValue());
		keepConnected.setUser(userService.getByUsername(token.getUsername()).get());
		keepConnectedDAO.save(keepConnected);
	}

	@Override
	public void updateToken(String series, String token, Date lastUse) {
		Optional<KeepConnected> permanecerConectadoOpcional = keepConnectedDAO.findBySeries(series);
		if (permanecerConectadoOpcional.isPresent()) {
			KeepConnected keepConnected = permanecerConectadoOpcional.get();
			keepConnected.setToken(token);
			keepConnected.setLastUse(LocalDateTime.ofInstant(lastUse.toInstant(), ZoneOffset.UTC));
			keepConnectedDAO.save(keepConnected);
		}
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		Optional<KeepConnected> permanecerConectado = keepConnectedDAO.findBySeries(series);
		if (permanecerConectado.isPresent()) {
			PersistentRememberMeToken persistentRememberMeToken = new PersistentRememberMeToken(
					permanecerConectado.get().getUser().getName(), permanecerConectado.get().getSeries(),
					permanecerConectado.get().getToken(),
					Date.from(permanecerConectado.get().getLastUse().toInstant(ZoneOffset.UTC)));
			return persistentRememberMeToken;
		}
		return null;
	}

	@Override
	public void removeUserTokens(String email) {
		userService.getByUsername(email).ifPresent(user -> keepConnectedDAO.removeByUser(user));
	}

}
