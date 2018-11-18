package br.com.fatecmogidascruzes.selection;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.course.Course;
import br.com.fatecmogidascruzes.domain.EntityImpl;
import br.com.fatecmogidascruzes.file.File;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
@Table(name = "_selection")
public class Selection extends EntityImpl {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "number", nullable = true, length = 50)
	protected String number;

	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	protected SelectionType type;

	@Basic
	@Column(name = "description", nullable = true, length = 200)
	protected String description;

	@Column(name = "course", nullable = false)
	@Enumerated(EnumType.STRING)
	protected Course course;

	@Basic
	@Column(name = "discipline", nullable = true, length = 100)
	protected String discipline;

	@Basic
	@Column(name = "how_many_hours_morning", nullable = false)
	protected int howManyHoursMorning;

	@Basic
	@Column(name = "how_many_hours_afternoon", nullable = false)
	protected int howManyHoursAfternoon;

	@Basic
	@Column(name = "how_many_hours_evening", nullable = false)
	protected int howManyHoursEvening;

	@Basic
	@Column(name = "reg_start_date", nullable = false)
	private LocalDate registrationStartDate;

	@Basic
	@Column(name = "reg_end_date", nullable = false)
	private LocalDate registrationEndDate;

	@Basic
	@Column(name = "reg_start_time", nullable = false)
	private LocalTime registrationStartTime;

	@Basic
	@Column(name = "reg_end_time", nullable = false)
	private LocalTime registrationEndTime;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	protected SelectionStatus currentStatus;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "_sel_files", joinColumns = @JoinColumn(name = "sel_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
	private Set<File> files = new HashSet<>();

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sel")
	@Id
	@Override
	@SequenceGenerator(name = "seq_sel", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

	public void addDocument(File file) {
		this.files.add(file);
	}

	public void removeDocumentByHash(UUID hash) {
		this.files.removeIf(file -> hash.equals(file.getHash()));
	}

}
