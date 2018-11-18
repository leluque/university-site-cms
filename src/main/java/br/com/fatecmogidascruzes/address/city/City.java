package br.com.fatecmogidascruzes.address.city;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.address.state.State;
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
@Table(name = "_city")
public class City extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "state", nullable = false)
	@ManyToOne(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private State state;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_city")
	@Id
	@Override
	@SequenceGenerator(name = "seq_city", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}
