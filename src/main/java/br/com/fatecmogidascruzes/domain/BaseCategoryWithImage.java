package br.com.fatecmogidascruzes.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import br.com.fatecmogidascruzes.file.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@MappedSuperclass
@NoArgsConstructor
@Setter
public class BaseCategoryWithImage extends BaseCategory {

	protected static final long serialVersionUID = 1L;

	@JoinColumn(name = "image", nullable = false)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private File image;

	@Basic
	@Column(name = "alt_desc", nullable = true, length = 100)
	protected String imageAlternativeDescription;

}
