package br.com.fatecmogidascruzes.event;

import java.time.LocalDate;
import java.time.LocalTime;

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
import br.com.fatecmogidascruzes.gallery.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "_event", indexes = { @Index(name = "ind_event_name", columnList = "name", unique = false) })
public class Event extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "start_date", nullable = true)
	private LocalDate startDate;

	@Column(name = "end_date", nullable = true)
	private LocalDate endDate;

	@Column(name = "start_time", nullable = true)
	private LocalTime startTime;

	@Column(name = "end_time", nullable = true)
	private LocalTime endTime;

	@Basic
	@Column(name = "link", nullable = true, length = 200)
	private String link;

	@Basic
	@Column(name = "short_description", nullable = true, length = 150)
	private String shortDescription;

	@Column(name = "long_description", nullable = true)
	@Lob
	private String longDescription;

	@Basic
	@Column(name = "place", nullable = true, length = 100)
	private String place;

	@JoinColumn(name = "album", nullable = true)
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private Album album;

	@JoinColumn(name = "image", nullable = false)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private File image;

	@JoinColumn(name = "big_image", nullable = true)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private File bigImage;

	@Basic
	@Column(name = "views", nullable = false)
	private int views;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_event")
	@Override
	@SequenceGenerator(name = "seq_event", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}