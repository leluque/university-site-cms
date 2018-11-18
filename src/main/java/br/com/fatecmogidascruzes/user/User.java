package br.com.fatecmogidascruzes.user;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.domain.NamedEntity;
import br.com.fatecmogidascruzes.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "_user", indexes = { @Index(name = "ind_user_name", columnList = "name", unique = true),
		@Index(name = "ind_user_access_tok", columnList = "access_token", unique = true) })
public class User extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "full_name", length = 50, nullable = false)
	protected String fullName;

	// Make name unique (it represents the user name and is unique).
	@Access(AccessType.PROPERTY)
	@Basic
	@Column(name = "name", nullable = false, unique = true)
	@Override
	public String getName() {
		return super.getName();
	}

	@Basic
	@Column(name = "access_token", length = 200, nullable = true)
	protected String accessToken;

	@Column(name = "access_token_validaty", nullable = true)
	protected LocalDateTime accessTokenValidity;

	@Basic
	@Column(name = "action_token", length = 200)
	protected String actionToken;

	@Basic
	@Column(name = "password", length = 200)
	protected String password;

	@Column(name = "action_token_validaty", nullable = true)
	protected LocalDateTime actionTokenValidity;

	@Column(name = "status", nullable = false, length = 15)
	@Enumerated(EnumType.STRING)
	private UserStatus status = UserStatus.NOT_ACTIVATED;

	@JoinTable(name = "_user_roles", joinColumns = @JoinColumn(name = "user"), inverseJoinColumns = @JoinColumn(name = "role"))
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	protected Set<Role> roles = new HashSet<>();

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
	@Id
	@Override
	@SequenceGenerator(name = "seq_user", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

	public boolean hasRole(String roleName) {
		return this.roles.parallelStream().anyMatch(role -> roleName.equalsIgnoreCase(role.getName()));
	}

	public void setRoles(Role... roles) {
		this.roles = new HashSet<Role>(Arrays.asList(roles));
	}

	public void addRole(Role role) {
		if (null == roles) {
			roles = new HashSet<>();
		}
		roles.add(role);
	}

	public void addRoles(Role... roles) {
		if (null == this.roles) {
			this.roles = new HashSet<Role>(Arrays.asList(roles));
		} else {
			this.roles.addAll(Arrays.asList(roles));
		}
	}

	public void removeRole(Role role) {
		this.roles.removeIf(roleEntry -> role.getName().equalsIgnoreCase(roleEntry.getName()));
	}

}
