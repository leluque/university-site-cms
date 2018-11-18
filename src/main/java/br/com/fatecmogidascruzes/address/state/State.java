package br.com.fatecmogidascruzes.address.state;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.address.city.City;
import br.com.fatecmogidascruzes.domain.NamedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "_state")
public class State extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "acronym", nullable = false, length = 4)
	private String acronym;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "state")
	private Set<City> cities = new HashSet<>();

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_state")
	@Id
	@Override
	@SequenceGenerator(name = "seq_state", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}
