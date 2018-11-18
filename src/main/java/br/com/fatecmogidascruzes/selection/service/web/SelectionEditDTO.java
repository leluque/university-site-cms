package br.com.fatecmogidascruzes.selection.service.web;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;
import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatecmogidascruzes.course.Course;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.selection.Selection;
import br.com.fatecmogidascruzes.selection.SelectionStatus;
import br.com.fatecmogidascruzes.selection.SelectionType;
import br.com.fatecmogidascruzes.selection.service.web.validator.EndMustBeAfterStart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@EndMustBeAfterStart(message = "A data de término das inscrições deve ser posterior à data de início")
public class SelectionEditDTO {

	private String hashString;

	@NotBlank(message = "O número da seleção/concurso público é obrigatório")
	@Length(max = 50, message = "O número da seleção/concurso público pode ter no máximo 50 caracteres")
	private String number;

	@NotNull(message = "A data/hora de início da seleção/concurso público é obrigatório")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime startDateTime;

	@NotNull(message = "A data/hora de término da seleção/concurso público é obrigatório")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime endDateTime;

	@NotBlank(message = "A descrição breve da seleção/concurso público é obrigatório")
	@Length(max = 200, message = "A descrição breve da seleção/concurso público é obrigatório")
	private String description;

	private Course course;
	@NotBlank(message = "A disciplina da seleção/concurso público é obrigatório")
	@Length(max = 100, message = "A disciplina da seleção/concurso público pode ter no máximo 100 caracteres")
	private String discipline;

	@NotNull(message = "O tipo de processo seletivo é obrigatório")
	private SelectionType type;
	@NotNull(message = "A situação de processo seletivo é obrigatória")
	private SelectionStatus status;

	@Min(value = 0, message = "A quantidade de horas no período matutino deve ser um numero natural")
	private int howManyMorningHours;
	@Min(value = 0, message = "A quantidade de horas no período vespertino deve ser um numero natural")
	private int howManyAfternoonHours;
	@Min(value = 0, message = "A quantidade de horas no período noturno deve ser um numero natural")
	private int howManyEveningHours;

	private List<SelectionDocumentDTO> currentDocuments;
	private String[] documentsHashesToRemove;
	private MultipartFile[] newDocuments;
	private String[] newDocumentsDescriptions;

	public void setCurrentDocuments(Collection<File> documents) {
		this.currentDocuments = new ArrayList<>();
		for (File document : documents) {
			this.currentDocuments.add(SelectionDocumentDTO.from(document));
		}
	}

	public static SelectionEditDTO from(Selection selection) {
		SelectionEditDTO selectionEditDTO = new SelectionEditDTO();

		selectionEditDTO.setHashString(FriendlyId.toFriendlyId(selection.getHash()));
		selectionEditDTO.setNumber(selection.getNumber());
		selectionEditDTO.setDescription(selection.getDescription());

		if (null != selection.getRegistrationStartDate()) {
			if (null != selection.getRegistrationStartTime()) {
				selectionEditDTO.setStartDateTime(
						LocalDateTime.of(selection.getRegistrationStartDate(), selection.getRegistrationStartTime()));
			} else {
				selectionEditDTO
						.setStartDateTime(LocalDateTime.of(selection.getRegistrationStartDate(), LocalTime.of(0, 0)));
			}
		}
		if (null != selection.getRegistrationEndDate()) {
			if (null != selection.getRegistrationEndTime()) {
				selectionEditDTO.setEndDateTime(
						LocalDateTime.of(selection.getRegistrationEndDate(), selection.getRegistrationEndTime()));
			} else {
				selectionEditDTO
						.setEndDateTime(LocalDateTime.of(selection.getRegistrationEndDate(), LocalTime.of(23, 59)));
			}
		}
		selectionEditDTO.setCurrentDocuments(selection.getFiles());
		selectionEditDTO.setCourse(selection.getCourse());
		selectionEditDTO.setDiscipline(selection.getDiscipline());
		selectionEditDTO.setType(selection.getType());
		selectionEditDTO.setStatus(selection.getCurrentStatus());
		selectionEditDTO.setHowManyMorningHours(selection.getHowManyHoursMorning());
		selectionEditDTO.setHowManyAfternoonHours(selection.getHowManyHoursAfternoon());
		selectionEditDTO.setHowManyEveningHours(selection.getHowManyHoursEvening());
		
		return selectionEditDTO;
	}

	public void fill(Selection selection) {

		selection.setNumber(number);
		selection.setDescription(description);

		selection.setRegistrationStartDate(startDateTime.toLocalDate());
		selection.setRegistrationStartTime(startDateTime.toLocalTime());
		selection.setRegistrationEndDate(endDateTime.toLocalDate());
		selection.setRegistrationEndTime(endDateTime.toLocalTime());

		selection.setCourse(course);
		selection.setDiscipline(discipline);
		selection.setType(type);
		selection.setCurrentStatus(status);
		selection.setHowManyHoursMorning(howManyMorningHours);
		selection.setHowManyHoursAfternoon(howManyAfternoonHours);
		selection.setHowManyHoursEvening(howManyEveningHours);

		selection.setLastUpdate(LocalDateTime.now());

	}

}
