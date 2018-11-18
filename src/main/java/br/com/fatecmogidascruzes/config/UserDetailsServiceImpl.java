package br.com.fatecmogidascruzes.config;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fatecmogidascruzes.user.User;
import br.com.fatecmogidascruzes.user.role.Role;
import br.com.fatecmogidascruzes.user.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final UserService userService;

	@Autowired
	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		if (email == null || email.trim().isEmpty()) {
			throw new UsernameNotFoundException("The username is empty.");
		}
		Optional<User> user = userService.getByUsername(email);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException("The user " + email + " has not been found.");
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if (user.get().getRoles() != null) {
			for (Role role : user.get().getRoles()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}
		}

		return new UserDetails(user.get(), user.get().getName(), user.get().getPassword(), grantedAuthorities,
				user.get().getName(), user.get().getCreationDate().format(formatter));
	}

}