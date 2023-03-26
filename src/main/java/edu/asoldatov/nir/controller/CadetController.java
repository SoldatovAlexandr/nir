package edu.asoldatov.nir.controller;

import edu.asoldatov.nir.common.School;
import edu.asoldatov.nir.controller.dto.CadetImportDto;
import edu.asoldatov.nir.controller.dto.ImportResultDto;
import edu.asoldatov.nir.importer.cadet.ImporterService;
import edu.asoldatov.nir.model.cadet.Group;
import edu.asoldatov.nir.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = CadetController.ROOT_MAPPING)
public class CadetController {
    public static final String ROOT_MAPPING = "/cadet";

    private final ImporterService importerService;
    private final GroupService groupService;

    @PostMapping(value = "/import")
    public ImportResultDto add(@RequestBody CadetImportDto cadetImportDto) {
        final School school = School.findByLabel(cadetImportDto.getSchoolName());
        final Group group = groupService.create(cadetImportDto.getGroupName(), school);
        return importerService.importDirectory(cadetImportDto.getPath(), group);
    }
}
