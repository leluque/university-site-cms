package br.com.fatecmogidascruzes.agenda;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
@Setter
@Table(name = "_agenda_entry", indexes = { @Index(name = "ind_age_ent_name", columnList = "name", unique = false) })
public class AgendaEntry extends NamedEntity {

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
	@Column(name = "short_description", nullable = true, length = 100)
	private String shortDescription;

	@Column(name = "long_description", nullable = true)
	@Lob
	private String longDescription;

	@JoinColumn(name = "base_agenda_entry", nullable = true)
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private AgendaEntry baseAgendaEntry;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_agenda_entry")
	@Override
	@SequenceGenerator(name = "seq_agenda_entry", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

	public AgendaEntry copy() {
		AgendaEntry event = new AgendaEntry();
		event.setEndDate(endDate);
		event.setEndTime(endTime);
		event.setName(name);
		event.setShortDescription(shortDescription);
		event.setStartDate(startDate);
		event.setStartTime(startTime);
		return event;
	}

}