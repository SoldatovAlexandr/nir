package edu.asoldatov.nir.r13kmed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class V2Importer {

    private final static String IMPORT_DIRECTORY = "/Users/asoldatov/projects/myapp/nir/nir/src/main/resources/";

    private final static List<String> FILES = List.of(
            "Формат_сводной_таблицы_с_примеромИТОГ_часть_1.xlsx",
            "Формат_сводной_таблицы_с_примеромИТОГ_часть_2.xlsx",
            "2_5458648226259284512.xlsx"
    );

    private final ReportRepository reportRepository;

    //@EventListener(ApplicationReadyEvent.class)
    public void handleSuccessful() {
        for (String file : FILES) {
            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(IMPORT_DIRECTORY + file))) {
                for (Sheet sheet : workbook) {
                    if (sheet.getRow(0) == null) {
                        continue;
                    }
                    int yearStart = extractInteger(sheet.getRow(1).getCell(1));
                    log.info("START IMPORT [{}] [{}]", sheet.getRow(1).getCell(0).getStringCellValue(), yearStart);
                    Report report = Report.builder()
                            .school(sheet.getRow(1).getCell(0).getStringCellValue())
                            .yearStart(yearStart)

                            .totalCountCasesOfDiseases(extractInteger(sheet.getRow(1).getCell(3)))
                            .totalCountFirstCasesOfDiseases(extractInteger(sheet.getRow(1).getCell(4)))
                            .totalCountTreatmentMP(extractInteger(sheet.getRow(1).getCell(5)))
                            .totalCountTreatmentMilitaryHospitals(extractInteger(sheet.getRow(1).getCell(6)))
                            .totalCountTreatmentCivilianHospitals(extractInteger(sheet.getRow(1).getCell(7)))
                            .totalCountLaborLossesCases(extractInteger(sheet.getRow(1).getCell(8)))
                            .totalCountLaborLossesDay(extractInteger(sheet.getRow(1).getCell(9)))

                            .firstYearCasesOfDiseases(extractInteger(sheet.getRow(2).getCell(3)))
                            .firstYearFirstCasesOfDiseases(extractInteger(sheet.getRow(2).getCell(4)))
                            .firstYearTreatmentMP(extractInteger(sheet.getRow(2).getCell(5)))
                            .firstYearTreatmentMilitaryHospitals(extractInteger(sheet.getRow(2).getCell(6)))
                            .firstYearTreatmentCivilianHospitals(extractInteger(sheet.getRow(2).getCell(7)))
                            .firstYearLaborLossesCases(extractInteger(sheet.getRow(2).getCell(8)))
                            .firstYearLaborLossesDay(extractInteger(sheet.getRow(2).getCell(9)))

                            .allClassPrimaryIncidenceAbsolute(extractInteger(sheet.getRow(1).getCell(11)))
                            .allClassPrimaryIncidencePpm(extract(sheet, 1, 12))
                            .allClassPrimaryIncidencePercent(extract(sheet, 1, 13))

                            .oralDiseasesPrimaryIncidenceAbsolute(extractInteger(sheet.getRow(2).getCell(11)))
                            .oralDiseasesPrimaryIncidencePpm(extract(sheet, 2, 12))
                            .oralDiseasesPrimaryIncidencePercent(extract(sheet, 2, 13))
                            .reportClasses(forthSheet(sheet))
                            .dentalOfficeScopes(appendFifthOneSheet(sheet, yearStart))
                            .indicators(indicators(sheet))

                            .build();

                    prepareAndSave(report);
                }
            } catch (Exception exception) {
                log.error("Can not import", exception);
            }
        }
    }

    private Double extract(Sheet sheet, int rowNumber, int cellNumber) {
        if (sheet.getRow(rowNumber).getCell(cellNumber) == null) {
            return 0.0;
        }
        if (sheet.getRow(rowNumber).getCell(cellNumber).getCellType() != CellType.NUMERIC) {
            return 0.0;
        }
        return sheet.getRow(rowNumber).getCell(cellNumber).getNumericCellValue();
    }

    private List<Indicator> indicators(Sheet sheet) {
        List<Indicator> indicators = new ArrayList<>();

        for (int i = 1; i < 70; i++) {
            indicators.add(extractIndicator(sheet.getRow(i)));
        }

        return indicators;
    }

    private void prepareAndSave(Report report) {
        report.getDentalOfficeScopes().forEach(v -> v.setReport(report));
        report.getIndicators().forEach(v -> v.setReport(report));
        report.getReportClasses().forEach(v -> v.setReport(report));
        reportRepository.save(report);
    }

    private List<ReportClass> forthSheet(Sheet sheet) {
        List<ReportClass> result = new ArrayList<>();
        int row = 1;
        while (sheet.getRow(row) != null && sheet.getRow(row).getCell(14) != null && !Objects.equals(getString(sheet.getRow(row).getCell(14)), "")) {
            result.add(extractReportClass(sheet.getRow(row++)));
        }
        return result;
    }

    private String getString(Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return String.valueOf(cell.getNumericCellValue());
    }

    private List<DentalOfficeScope> appendFifthOneSheet(Sheet sheet, int yearStart) {
        List<DentalOfficeScope> result = new ArrayList<>();
        if(yearStart==2010){
            System.out.println("HERE");
        }
        int row = 1;
        while (sheet.getRow(row) != null && sheet.getRow(row).getCell(24) != null && (sheet.getRow(row).getCell(24).getCellType() == CellType.NUMERIC || sheet.getRow(row).getCell(24).getCellType() == CellType.FORMULA) && sheet.getRow(row).getCell(24).getNumericCellValue() > 0) {
            result.add(extractDentalOfficeScope(sheet.getRow(row++), row));
        }
        return result;
    }

    private int extractInteger(Cell cell) {
        if (cell == null) {
            return 0;
        }
        if (cell.getCellType() == CellType.STRING) {
            if (!cell.getStringCellValue().matches("[0-9].[0-9]+")) {
                return 0;
            }
            return Double.valueOf(cell.getStringCellValue()).intValue();
        }
        return Double.valueOf(cell.getNumericCellValue()).intValue();
    }


    private ReportClass extractReportClass(Row row) {
        return ReportClass.builder()
                .category(getString(row.getCell(14)))
                .medicalSupervision(extractInteger(row.getCell(15)))
                .firstHealthGroup(extractInteger(row.getCell(16)))
                .secondHealthGroup(extractInteger(row.getCell(17)))
                .thirdHealthGroup(extractInteger(row.getCell(18)))
                .normalNutritionGroup(extractInteger(row.getCell(19)))
                .lowNutritionGroup(extractInteger(row.getCell(20)))
                .highNutritionGroup(extractInteger(row.getCell(21)))
                .unfitForHealthStudySchool(0)
                .unfitForHealthStudyUniversity(0)//todo
                .build();
    }

    private DentalOfficeScope extractDentalOfficeScope(Row row, int number) {
        return DentalOfficeScope.builder()
                .contingentsOfPatients(String.valueOf(number - 1))
                .totalNumberOfVisits(extractInteger(row.getCell(22)))
                .firstNumberOfVisits(extractInteger(row.getCell(23)))
                .countInspected(extractInteger(row.getCell(24)))
                .countInspectedNeedSanitation(extractInteger(row.getCell(25)))
                .countInspectedSanitized(extractInteger(row.getCell(26)))
                .countInspectedNeedProsthetics(extractInteger(row.getCell(27)))
                .countInspectedProsthetic(extractInteger(row.getCell(28)))
                .build();
    }

    private Indicator extractIndicator(Row row) {
        return Indicator.builder()
                .number(extractString(row.getCell(29)))
                .name(extractString(row.getCell(30)))
                .previousValue(extractNumber(row.getCell(31)))
                .currentValue(extractNumber(row.getCell(32)))
                .build();
    }

    private String extractString(Cell cell) {
        if (cell.getCellType() != CellType.STRING) {
            return String.valueOf(cell.getNumericCellValue());
        }
        return cell.getStringCellValue();
    }

    private Double extractNumber(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        if (cell.getCellType() == CellType.STRING) {
            if (!cell.getStringCellValue().matches("[0-9]+.[0-9]]+")) {
                return 0.0;
            }
            return Double.valueOf(cell.getStringCellValue());
        }
        if (cell.getCellType() != CellType.NUMERIC) {
            return 0.0;
        }
        return cell.getNumericCellValue();
    }
}
