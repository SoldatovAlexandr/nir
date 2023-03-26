package edu.asoldatov.nir.importer.cadet;

import edu.asoldatov.nir.common.School;
import edu.asoldatov.nir.controller.dto.ImportResultDto;
import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.Group;
import edu.asoldatov.nir.service.CadetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImporterService {

    private final DefaultCadetImporter defaultCadetImporter;

    private final Map<School, CadetImporter> importers;
    private final CadetService cadetService;

    public ImportResultDto importDirectory(String directoryName, Group group) {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryName))) {
            List<Path> files = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            log.info("Start import directory [{}]", directoryName);

            int count = 0;
            List<String> errors = new ArrayList<>();

            for (Path path : files) {
                try {
                    Cadet cadet = importFile(path, group.getSchool());
                    cadet.setGroup(group);
                    cadetService.save(cadet);
                    count++;
                    log.info("Success imported file [{}]", path);
                } catch (Exception e) {
                    String errorText = String.format("Something went wrong [%s]. Can not import file [%s]", e.getMessage(), path);
                    errors.add(errorText);
                    log.error(errorText);
                }
            }
            log.info("Finish import directory [{}]. Imported files count [{}]", directoryName, count);
            return new ImportResultDto(count, errors.size(), errors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Cadet importFile(Path path, School school) throws IOException, InvalidFormatException {
        try (Workbook workbook = new XSSFWorkbook(path.toFile())) {
            Sheet sheet = workbook.getSheetAt(0);
            CadetImporter cadetImporter = importers.getOrDefault(school, defaultCadetImporter);
            return cadetImporter.importInternal(sheet);
        }
    }
}
