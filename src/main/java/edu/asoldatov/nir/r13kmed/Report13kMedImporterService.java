package edu.asoldatov.nir.r13kmed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class Report13kMedImporterService {

    private final static String IMPORT_DIRECTORY = "/Users/asoldatov/Downloads/Отчеты 13кМед для Сани Версия 2/2021";

    private final ReportRepository reportRepository;

    // @EventListener(ApplicationReadyEvent.class)
    public void handleSuccessful() throws IOException {
        int success = 0, exception = 0;
        for (File file : getAllFilesFromDirectory(IMPORT_DIRECTORY)) {

            try (Workbook workbook = new HSSFWorkbook(new FileInputStream(file))) {

                Report.ReportBuilder builder = Report.builder();

                appendCommonInfo(builder, workbook.getSheetAt(0));

                appendFirstSheet(builder, workbook.getSheetAt(1));

                appendSecondSheet(builder, workbook.getSheetAt(2));

                //appendForthSheet(builder, workbook.getSheetAt(6));

                appendFifthOneSheet(builder, workbook.getSheetAt(7));

                appendFifthTwoSheet(builder, workbook.getSheetAt(8));

                Report report = builder.build();

                prepareAndSave(report);
                success++;
            } catch (Exception e) {
                exception++;
                log.error("Can not import file [{}]. Reason [{}]", file.getName(), e);
            }
        }
        log.info("Success [{}], exception [{}]", success, exception);
    }

    private List<File> getAllFilesFromDirectory(String name) throws IOException {
        return Files.walk(Paths.get(name))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    private void prepareAndSave(Report report) {
        report.getDentalOfficeScopes().forEach(v -> v.setReport(report));
        report.getIndicators().forEach(v -> v.setReport(report));
        report.getReportClasses().forEach(v -> v.setReport(report));
        reportRepository.save(report);
    }

    private void appendCommonInfo(Report.ReportBuilder builder, Sheet sheet) {
        builder.school(sheet.getRow(21).getCell(0).getStringCellValue())
                .yearStart(extractYearStart(sheet));
    }

    private int extractYearStart(Sheet sheet) {
        try {
            return Integer.parseInt(sheet.getRow(23).getCell(0).getStringCellValue().split(" ")[2]);
        } catch (Exception exception) {
            return Integer.parseInt(sheet.getRow(24).getCell(0).getStringCellValue().split(" ")[2]);
        }
    }

    private void appendSecondSheet(Report.ReportBuilder builder, Sheet sheet) {
        builder.allClassPrimaryIncidenceAbsolute(extractInteger(sheet.getRow(146).getCell(4)))
                .allClassPrimaryIncidencePpm(sheet.getRow(146).getCell(5).getNumericCellValue())
                .allClassPrimaryIncidencePercent(sheet.getRow(146).getCell(6).getNumericCellValue())

                .oralDiseasesPrimaryIncidenceAbsolute(extractInteger(sheet.getRow(148).getCell(4)))
                .oralDiseasesPrimaryIncidencePpm(sheet.getRow(148).getCell(5).getNumericCellValue())
                .oralDiseasesPrimaryIncidencePercent(sheet.getRow(148).getCell(6).getNumericCellValue());
    }

    private void appendFirstSheet(Report.ReportBuilder builder, Sheet sheet) {
        Row totalCountRow = sheet.getRow(9);
        Row firstYearRow = sheet.getRow(10);

        builder.totalCountCasesOfDiseases(extractInteger(totalCountRow.getCell(1)))
                .totalCountFirstCasesOfDiseases(extractInteger(totalCountRow.getCell(2)))
                .totalCountTreatmentMP(extractInteger(totalCountRow.getCell(3)))
                .totalCountTreatmentMilitaryHospitals(extractInteger(totalCountRow.getCell(4)))
                .totalCountTreatmentCivilianHospitals(extractInteger(totalCountRow.getCell(5)))
                .totalCountLaborLossesCases(extractInteger(totalCountRow.getCell(6)))
                .totalCountLaborLossesDay(extractInteger(totalCountRow.getCell(7)))

                .firstYearCasesOfDiseases(extractInteger(firstYearRow.getCell(1)))
                .firstYearFirstCasesOfDiseases(extractInteger(firstYearRow.getCell(2)))
                .firstYearTreatmentMP(extractInteger(firstYearRow.getCell(3)))
                .firstYearTreatmentMilitaryHospitals(extractInteger(firstYearRow.getCell(4)))
                .firstYearTreatmentCivilianHospitals(extractInteger(firstYearRow.getCell(5)))
                .firstYearLaborLossesCases(extractInteger(firstYearRow.getCell(6)))
                .firstYearLaborLossesDay(extractInteger(firstYearRow.getCell(7)));
    }

    private int extractInteger(Cell cell) {
        return Double.valueOf(cell.getNumericCellValue()).intValue();
    }


    private void appendFifthOneSheet(Report.ReportBuilder builder, Sheet sheet) {
        builder.dentalOfficeScopes(List.of(
                extractDentalOfficeScope(sheet.getRow(5)),
                extractDentalOfficeScope(sheet.getRow(6)),
                extractDentalOfficeScope(sheet.getRow(7))
        ));
    }

    private void appendFifthTwoSheet(Report.ReportBuilder builder, Sheet sheet) {
        List<Indicator> indicators = new ArrayList<>();

        for (int i = 2; i <= 70; i++) {
            indicators.add(extractIndicator(sheet.getRow(i)));
        }

        builder.indicators(indicators);
    }

    private ReportClass extractReportClass(Row row) {
        return ReportClass.builder()
                .category(row.getCell(2).getStringCellValue())
                .medicalSupervision(extractInteger(row.getCell(3)))
                .firstHealthGroup(extractInteger(row.getCell(4)))
                .secondHealthGroup(extractInteger(row.getCell(5)))
                .thirdHealthGroup(extractInteger(row.getCell(6)))
                .normalNutritionGroup(extractInteger(row.getCell(7)))
                .lowNutritionGroup(extractInteger(row.getCell(8)))
                .highNutritionGroup(extractInteger(row.getCell(9)))
                .unfitForHealthStudySchool(extractInteger(row.getCell(10)))
                .unfitForHealthStudyUniversity(extractInteger(row.getCell(11)))
                .build();
    }

    private DentalOfficeScope extractDentalOfficeScope(Row row) {
        return DentalOfficeScope.builder()
                .contingentsOfPatients(String.valueOf(row.getRowNum()).trim())
                .totalNumberOfVisits(extractInteger(row.getCell(2)))
                .firstNumberOfVisits(extractInteger(row.getCell(3)))
                .countInspected(extractInteger(row.getCell(4)))
                .countInspectedNeedSanitation(extractInteger(row.getCell(5)))
                .countInspectedSanitized(extractInteger(row.getCell(6)))
                .countInspectedNeedProsthetics(extractInteger(row.getCell(7)))
                .countInspectedProsthetic(extractInteger(row.getCell(8)))
                .build();
    }

    private Indicator extractIndicator(Row row) {
        return Indicator.builder()
                .number(extractString(row.getCell(0)))
                .name(extractString(row.getCell(1)))
                .previousValue(extractNumber(row.getCell(2)))
                .currentValue(extractNumber(row.getCell(3)))
                .build();
    }

    private String extractString(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return cell.getStringCellValue();
    }

    private Double extractNumber(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return Double.valueOf(cell.getStringCellValue());
        }
        return cell.getNumericCellValue();
    }
}
