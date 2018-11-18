package br.com.fatecmogidascruzes.address;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.address.city.City;
import br.com.fatecmogidascruzes.address.state.State;
import br.com.fatecmogidascruzes.domain.EntityImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "_address")
public class Address extends EntityImpl {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "zip_code", nullable = false, length = 9)
	private String zipCode;

	@Basic
	@Column(name = "place", nullable = false, length = 100)
	private String place;

	@Basic
	@Column(name = "number", nullable = false, length = 8)
	private String number;

	@Basic
	@Column(name = "complement", nullable = false, length = 100)
	private String complement;

	@JoinColumn(name = "city")
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	private City city;

	@JoinColumn(name = "state")
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	private State state;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address")
	@Id
	@Override
	@SequenceGenerator(name = "seq_address", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}