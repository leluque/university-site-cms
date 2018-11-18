package br.com.fatecmogidascruzes.user.role;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.domain.NamedEntity;
import br.com.fatecmogidascruzes.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "_role", indexes = { @Index(name = "ind_role_name", columnList = "name", unique = true) })
public class Role extends NamedEntity {

	private static final long serialVersionUID = 1L;

	public enum Values {
		ROLE_ADMINISTRATOR
	};

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
	protected Set<User> users = new HashSet<>();

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_role")
	@Id
	@Override
	@SequenceGenerator(name = "seq_role", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}
