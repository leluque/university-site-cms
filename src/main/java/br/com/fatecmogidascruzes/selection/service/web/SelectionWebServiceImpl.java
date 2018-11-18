package br.com.fatecmogidascruzes.selection.service.web;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devskiller.friendly_id.FriendlyId;

import br.com.fatecmogidascruzes.data.SearchCriteria;
import br.com.fatecmogidascruzes.dto.TableDTO;
import br.com.fatecmogidascruzes.exception.InexistentOrDisabledEntity;
import br.com.fatecmogidascruzes.file.File;
import br.com.fatecmogidascruzes.file.service.FileService;
import br.com.fatecmogidascruzes.selection.Selection;
import br.com.fatecmogidascruzes.selection.service.SelectionService;

@Service
public class SelectionWebServiceImpl implements SelectionWebService {

	private final SelectionService selectionService;
	private final FileService fileService;

	@Autowired
	public SelectionWebServiceImpl(SelectionService selectionService, FileService fileService) {
		super();
		this.selectionService = selectionService;
		this.fileService = fileService;
	}

	@Override
	public SelectionEditDTO find(UUID postHash) throws InexistentOrDisabledEntity {
		Optional<Selection> selectionOptional = this.selectionService.getByHash(postHash);
		if (selectionOptional.isPresent() && selectionOptional.get().isEnabled()) {
			Selection selection = selectionOptional.get();

			return SelectionEditDTO.from(selection);
		} else {
			throw new InexistentOrDisabledEntity("The informed selection does not exist or is disabled");
		}
	}

	@Override
	public SelectionEditDTO getSelectionEditDTOByHash(UUID selectionHash) throws InexistentOrDisabledEntity {
		Optional<Selection> selectionOptional = this.selectionService.getByHash(selectionHash);
		if (selectionOptional.isPresent() && selectionOptional.get().isEnabled()) {
			Selection selection = selectionOptional.get();

			return SelectionEditDTO.from(selection);
		} else {
			throw new InexistentOrDisabledEntity("The informed selection does not exist or is disabled");
		}
	}

	@Override
	public void save(SelectionEditDTO selectionEditDTO) {
		Selection selection = new Selection();
		if (null != selectionEditDTO.getHashString() && !selectionEditDTO.getHashString().trim().isEmpty()) {
			Optional<Selection> selectionOptional = this.selectionService
					.getByHash(FriendlyId.toUuid(selectionEditDTO.getHashString()));
			if (selectionOptional.isPresent() && selectionOptional.get().isEnabled()) {
				selection = selectionOptional.get();
			}
		}

		selectionEditDTO.fill(selection);

		// Remove documents.
		if (null != selectionEditDTO.getDocumentsHashesToRemove()) {
			for (String friendlyId : selectionEditDTO.getDocumentsHashesToRemove()) {
				UUID hash = FriendlyId.toUuid(friendlyId);
				fileService.removeByHash(hash);
				selection.removeDocumentByHash(hash);
			}
		}

		// Add new documents.
		if (null != selectionEditDTO.getNewDocuments()) {
			int i = 0;
			for (MultipartFile multipartFile : selectionEditDTO.getNewDocuments()) {
				if (!multipartFile.isEmpty()) {
					File newDocument = fileService.saveFile(multipartFile);
					if (selectionEditDTO.getNewDocumentsDescriptions().length > i) {
						if (null != selectionEditDTO.getNewDocumentsDescriptions()[i]
								&& !selectionEditDTO.getNewDocumentsDescriptions()[i].trim().isEmpty()) {
							newDocument.setDescription(selectionEditDTO.getNewDocumentsDescriptions()[i]);
						} else {
							newDocument.setDescription("Sem descrição");
						}
					}
					fileService.save(newDocument);
					selection.addDocument(newDocument);
					i++;
				}
			}
		}

		this.selectionService.save(selection);
	}

	@Override
	public TableDTO<SelectionTableRowDTO> getTable(SearchCriteria searchCriteria, int draw) {

		Page<Selection> selectionPage = this.selectionService.getEnabledByFilter(searchCriteria);
		TableDTO<SelectionTableRowDTO> table = new TableDTO<>();
		table.setDraw(draw);
		table.setRecordsTotal((int) selectionService.countEnabled().longValue());
		table.setRecordsFiltered((int) selectionPage.getTotalElements());

		table.setData(SelectionTableRowDTO.listFrom(selectionPage.getContent()));

		return table;
	}

}
