package br.com.fatecmogidascruzes.employee;

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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.fatecmogidascruzes.domain.NamedEntity;
import br.com.fatecmogidascruzes.file.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
@Table(name = "_employee", indexes = { @Index(name = "ind_employee_name", columnList = "name", unique = false) })
public class Employee extends NamedEntity {

	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "image", nullable = false)
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private File image;

	@AllArgsConstructor
	@Getter
	public enum EducationLevel {

		TECNICO("Nível Técnico", ""), SUPERIOR("Superior", ""), ESPECIALIZACAO("Especialização", "Esp."), MESTRADO("Mestrado", "Me."), DOUTORADO("Doutorado", "Dr.");

		private String name;
		private String titulation;

	}

	@Getter
	public enum SelectionType {

		CONFIANCA("Confiança"), PROFESSOR("Concursado como Professor"), PROFESSOR_SELECIONADO("Professor Temporário"),
		AGENTE_TECNICO_ADMINISTRATIVO("Concursado como Agente Técnico Administrativo"),
		ANALISTA_SUPORTE_GESTAO("Concursado como Analista de Suporte e Gestão"),
		AUXILIAR_DOCENTE("Concursado como Auxiliar Docente");

		private String name;

		private SelectionType(String name) {
			this.name = name;
		}

	}

	@Getter
	public enum Allocation {

		NENHUMA("Nenhuma"), COORDENADOR("Coordenador"), DIRETOR("Diretor"), DIRETOR_ACADEMICO("Diretor Acadêmico"),
		DIRETOR_ADMINISTRATIVO("Diretor Administrativo"),
		ASSISTENTE_TECNICO_ADMINISTRATIVO("Assistente Técnico Administrativo"),
		ASSISTENTE_ADMINISTRATIVO("Assistente Administrativo");

		private String name;

		private Allocation(String name) {
			this.name = name;
		}

	}

	@Column(name = "allocation", nullable = false)
	@Enumerated(EnumType.STRING)
	private Allocation allocation;

	@Column(name = "selection_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private SelectionType selectionType;

	@Column(name = "education_level", nullable = false)
	@Enumerated(EnumType.STRING)
	private EducationLevel educationLevel;

	@Basic
	@Column(name = "pro_tempore", nullable = false)
	private boolean proTempore;

	@Column(name = "curriculum")
	@Lob
	private String curriculum;

	@Basic
	@Column(name = "lattes", nullable = false, length = 100)
	private String lattes;

	@Basic
	@Column(name = "homepage", nullable = true, length = 100)
	private String homepage;

	@Basic
	@Column(name = "ads", nullable = false)
	protected boolean ads;

	@Basic
	@Column(name = "agro", nullable = false)
	protected boolean agro;

	@Basic
	@Column(name = "log", nullable = false)
	protected boolean log;

	@Basic
	@Column(name = "rh", nullable = false)
	protected boolean rh;

	@Basic
	@Column(name = "gestao", nullable = false)
	protected boolean gestao;

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_employee")
	@Id
	@Override
	@SequenceGenerator(name = "seq_employee", initialValue = 1, allocationSize = 1)
	public Long getId() {
		return super.getId();
	}

}