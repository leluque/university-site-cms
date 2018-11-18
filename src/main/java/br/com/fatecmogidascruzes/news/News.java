package br.com.fatecmogidascruzes.news;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.domain.NamedEntity;
import br.com.fatecmogidascruzes.file.File;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
@Table(name = "_news", indexes = { @Index(name = "ind_news_name", columnList = "name", unique = false) })
public class News extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "short_description", nullable = false, length = 150)
	protected String shortDescription;

	@Lob
	@Column(name = "long_description", nullable = true)
	protected String longDescription;

	@Basic
	@Column(name = "link", nullable = true, length = 200)
	protected String link;

	@JoinColumn(name = "file", nullable = true)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private File file;

	@JoinColumn(name = "highlight_image", nullable = true)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private File highlightImage;

	@JoinColumn(name = "image", nullable = false)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private File image;

	@JoinColumn(name = "big_image", nullable = true)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private File bigImage;

	@Column(name = "reference_date", nullable = false)
	private LocalDate referenceDate;

	@Basic
	@Column(name = "views", nullable = false)
	private int views;

	@Basic
	@Column(name = "highlight", nullable = false)
	protected boolean highlight;

	@Basic
	@Column(name = "show_only_mobile", nullable = false)
	protected boolean showNewsOnlyOnMobile;

	@Basic
	@Column(name = "hide", nullable = false)
	protected boolean hide;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_news")
	@Id
	@Override
	@SequenceGenerator(name = "seq_news", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}