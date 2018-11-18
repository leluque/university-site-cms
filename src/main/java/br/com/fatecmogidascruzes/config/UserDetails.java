package br.com.fatecmogidascruzes.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails extends User {

	private static final long serialVersionUID = 1L;

	private String fullName;
	private String memberSince;
	private br.com.fatecmogidascruzes.user.User user;

	public UserDetails(br.com.fatecmogidascruzes.user.User user, String userName, String password,
			Collection<? extends GrantedAuthority> authorities, String fullName, String memberSince) {
		super(userName, password, authorities);
		this.user = user;
		this.fullName = fullName;
		this.memberSince = memberSince;
	}

	public UserDetails(String fullName, String memberSince, String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.fullName = fullName;
		this.memberSince = memberSince;
	}

}
