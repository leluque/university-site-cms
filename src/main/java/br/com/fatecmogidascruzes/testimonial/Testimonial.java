package br.com.fatecmogidascruzes.testimonial;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.domain.BaseCategoryWithImage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
@Table(name = "_testimo")
public class Testimonial extends BaseCategoryWithImage {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "_position", nullable = true, length = 50)
	private String position;

	@Basic
	@Column(name = "_show", nullable = false)
	private boolean show;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_testimo")
	@Id
	@Override
	@SequenceGenerator(name = "seq_testimo", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}