package edu.asoldatov.nir.importer.cadet;

import edu.asoldatov.nir.controller.dto.ImportResultDto;
import edu.asoldatov.nir.dao.CadetRepository;
import edu.asoldatov.nir.dao.MucousMembraneRepository;
import edu.asoldatov.nir.dao.OhisRepository;
import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.Group;
import edu.asoldatov.nir.model.cadet.MucousMembrane;
import edu.asoldatov.nir.model.cadet.Ohis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdditionalImporter {

    private final DefaultCadetImporter defaultCadetImporter;

    private final CadetRepository cadetRepository;
    private final OhisRepository ohisRepository;
    private final MucousMembraneRepository mucousMembraneRepository;

    public ImportResultDto importDirectory(String directoryName, Group group) {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryName))) {
            List<Path> files = paths.filter(Files::isRegularFile).collect(Collectors.toList());
            log.info("Start import directory [{}]", directoryName);

            int count = 0;
            List<String> errors = new ArrayList<>();

            for (Path path : files) {
                try {
                    importFile(path, group);
                    count++;
                    log.info("Success imported file [{}]", path);
                } catch (Exception e) {
                    String errorText = String.format("Something went wrong [%s]. Can not import file [%s]", e.getMessage(), path);
                    errors.add(errorText);
                    log.error(errorText);
                    e.printStackTrace();
                }
            }
            log.info("Finish import directory [{}]. Imported files count [{}]", directoryName, count);
            return new ImportResultDto(count, errors.size(), errors);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void importFile(Path path, Group group) throws IOException, InvalidFormatException {
        try (Workbook workbook = new XSSFWorkbook(path.toFile())) {
            Sheet sheet = workbook.getSheetAt(0);
            importInternal(sheet, group);
        }
    }

    private void importInternal(Sheet sheet, Group group) {
        String fullName = extractString(sheet, 6, 1);
        List<Cadet> cadets = group.getCadets().stream().filter(v -> v.getFullName().equals(fullName)).collect(Collectors.toList());
        if (cadets.size() != 1) {
            log.error("Cadets size not 1, actual [{}]", cadets.size());
        }

        for (Cadet cadet : cadets) {
            if (ohisRepository.existsByCadetId(cadet.getId())) {
                continue;
            }
            var ohis = Ohis.builder()
                    .cadet(cadet)
                    .t16(getOhis(sheet, 89, 45))
                    .t11(getOhis(sheet, 89, 47))
                    .t26(getOhis(sheet, 89, 49))
                    .t46(getOhis(sheet, 93, 45))
                    .t31(getOhis(sheet, 93, 47))
                    .t36(getOhis(sheet, 93, 49))
                    .build();

            var mucousMembranes = new ArrayList<MucousMembrane>();

            for (int row : List.of(85, 87, 89)) {
                Cell c = sheet.getRow(row).getCell(23);
                if (c.getCellType() == CellType.BLANK) {
                    break;
                }
                MucousMembrane mucousMembrane = MucousMembrane.builder()
                        .cadet(cadet)
                        .value(Double.valueOf(c.getNumericCellValue()).intValue())
                        .build();
                mucousMembranes.add(mucousMembrane);
            }
            ohisRepository.save(ohis);
            mucousMembraneRepository.saveAll(mucousMembranes);
        }
    }

    private Integer getOhis(Sheet sheet, int row, int cell) {
        Cell c = sheet.getRow(row).getCell(cell);
        if (c.getCellType() == CellType.BLANK) {
            return null;
        }
        return Double.valueOf(c.getNumericCellValue()).intValue();
    }

    private String extractString(Sheet sheet, int row, int cell) {
        Cell c = sheet.getRow(row).getCell(cell);
        if (c.getCellType() == CellType.NUMERIC) {
            return String.valueOf(Double.valueOf(c.getNumericCellValue()).intValue());
        }
        return sheet.getRow(row).getCell(cell).getStringCellValue();
    }
}
