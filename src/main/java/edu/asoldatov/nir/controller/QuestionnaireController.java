package edu.asoldatov.nir.controller;

import edu.asoldatov.nir.controller.dto.ImportResultDto;
import edu.asoldatov.nir.importer.questionnaire.QuestionnaireImporterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = QuestionnaireController.ROOT_MAPPING)
public class QuestionnaireController {

    public static final String ROOT_MAPPING = "/questionnaire";

    private final QuestionnaireImporterService importerService;


    @PostMapping(value = "/import")
    public ImportResultDto importQuestionnaire() {
        return importerService.importFile();
    }

    @PostMapping(value = "/export")
    public ImportResultDto exportQuestionnaire() {
        return importerService.export();
    }

    @PostMapping(value = "/export2")
    public ImportResultDto exportQuestionnaire2() {
        return importerService.export2();
    }

    @PostMapping(value = "/toDataset")
    public ImportResultDto toDataset() {
        return importerService.toDataset();
    }
}
