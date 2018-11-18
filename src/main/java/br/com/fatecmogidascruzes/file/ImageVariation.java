package br.com.fatecmogidascruzes.file;

import java.io.Serializable;

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

import br.com.fatecmogidascruzes.domain.NamedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "_img_variation")
public class ImageVariation extends NamedEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "size", nullable = false)
	protected Long size;

	@Basic
	@Column(name = "content_type", nullable = false, length = 50)
	protected String contentType;

	@Basic
	@Column(name = "width", nullable = false)
	protected Integer width;

	@Basic
	@Column(name = "height", nullable = false)
	protected Integer height;

	@JoinColumn(name = "image")
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	protected File image;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_img_variation")
	@Id
	@Override
	@SequenceGenerator(name = "seq_img_variation", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}
