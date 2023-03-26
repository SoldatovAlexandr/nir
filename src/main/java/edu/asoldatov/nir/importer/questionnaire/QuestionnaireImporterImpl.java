package edu.asoldatov.nir.importer.questionnaire;

import edu.asoldatov.nir.model.questionnaire.QuestionnaireCadet;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class QuestionnaireImporterImpl implements QuestionnaireImporter {

    private final List<String> DEFAULT_CONDITIONS = List.of("Согласен", "Не согласен", "Безразлично");

    @Override

    public List<QuestionnaireCadet> importInternal(Sheet sheet) {
        List<QuestionnaireCadet> results = new ArrayList<>();
        for (int i = 2; i < 2250; i++) {
            final Row row = sheet.getRow(i);
            try {
                int course = Double.valueOf(row.getCell(1).getNumericCellValue()).intValue();
                QuestionnaireCadet questionnaireCadet = QuestionnaireCadet.builder()
                        .doo(row.getCell(0).getStringCellValue())
                        .course(course)
                        .age(extractAge(row.getCell(2), course))
                        .dentalCondition(getDetailsConditions(row))
                        .isHideSmile(isTrue(row.getCell(9)))
                        .isMissedClass(isTrue(row.getCell(11)))
                        .toothSensitivity(getToothSensitivity(row))
                        .visitNumber(getVisitNumber(row))
                        .lastVisitReason(getLastVisitReason(row))
                        .lastDentalAction(getLastDentalAction(row))
                        .dentalAdvice(getDentalAdvice(row))
                        .dentalHelp(getDentalHelp(row))
                        .anotherOrganization(getAnotherOrganization(row))
                        .difficulties(getDifficulties(row))
                        .isSatisfiedWorkspace(isTrue(row.getCell(49)))
                        .dentalOfficeWork(getDentalOfficeWork(row))
                        .offerForDentalOffice(getOfferForDentalOffice(row))
                        .brushProperly(getBrushProperly(row))
                        .rulesInformation(getRulesInformation(row))
                        .teacher(getTeacher(row))
                        .howYouBrush(getHowYouBrush(row))
                        .toothbrushChange(getToothbrushChange(row))
                        .fluoridePaste(getFluoridePaste(row))
                        .toothbrush(isTrue(row.getCell(71)))
                        .toothpick(isTrue(row.getCell(72)))
                        .woodenStick(isTrue(row.getCell(73)))
                        .floss(isTrue(row.getCell(74)))
                        .thread(isTrue(row.getCell(75)))
                        .irrigator(isTrue(row.getCell(76)))
                        .interdentalBrush(isTrue(row.getCell(77)))
                        .additionalSource(getAdditionalSource(row))
                        .howChooseOralProducts(howChooseOralProducts(row))
                        .frequencyUseAdditionalSource(getFrequencyUseAdditionalSource(row))
                        .favoriteFood(getFavoriteFood(row))
                        .favoriteSweet(getFavoriteSweet(row))
                        .aim(getAim(row))
                        .cariesRuinsTeeth(extractCondition(row, DEFAULT_CONDITIONS, 89))
                        .saveOriginalTeeth(extractCondition(row, DEFAULT_CONDITIONS, 92))
                        .afraidPain(extractCondition(row, DEFAULT_CONDITIONS, 95))
                        .teethProblem(extractCondition(row, DEFAULT_CONDITIONS, 98))
                        .brushStopCaries(extractCondition(row, DEFAULT_CONDITIONS, 101))
                        .brushStopGumDisease(extractCondition(row, DEFAULT_CONDITIONS, 104))
                        .eatsSweet(extractCondition(row, DEFAULT_CONDITIONS, 107))
                        .fluorine(extractCondition(row, DEFAULT_CONDITIONS, 110))
                        .build();
                results.add(questionnaireCadet);
            } catch (Exception exception) {
                log.error("Ошибка произошла в row [{}]", i);
            }
        }
        return results;
    }


    private int extractAge(Cell cell, int course) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return Double.valueOf(cell.getNumericCellValue()).intValue();
        }
        return course + 10;
    }

    private String getDetailsConditions(Row row) {
        List<String> conditions = List.of("Отличное", "Очень хорошее", "Хорошее", "Среднее", "Плохое", "Не знаю");
        return extractCondition(row, conditions, 3);
    }

    private String extractCondition(Row row, List<String> conditions, int startCell) {
        for (int i = 0; i < conditions.size(); i++) {
            Cell cell = row.getCell(i + startCell);
            if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().isBlank()) {
                continue;
            }
            int value = Double.valueOf(cell.getNumericCellValue()).intValue();
            if (value == 1) {
                return conditions.get(i);
            }
        }
        return conditions.get(conditions.size() - 1);
    }

    private boolean isTrue(Cell cell) {
        return cell.getNumericCellValue() == 1.0;
    }

    private String getToothSensitivity(Row row) {
        List<String> conditions = List.of("Часто", "Иногда", "Редко", "Никогда", "Не знаю");
        return extractCondition(row, conditions, 13);
    }

    private String getVisitNumber(Row row) {
        List<String> conditions = List.of("Один раз", "Два раза", "Три раза", "Четыре раза", "Более четырех", "Ни разу", "Не помню");
        return extractCondition(row, conditions, 18);
    }

    private String getLastVisitReason(Row row) {
        List<String> conditions = List.of("Мне назначили посещение", "Регулярно посещаю стоматолога", "Болел зуб", "Это был повторный визит", "Это был профилактический осмотр", "Не помню");
        return extractCondition(row, conditions, 25);
    }

    private String getLastDentalAction(Row row) {
        List<String> conditions = List.of("Ставил пломбу", "Чистил зубы", "Удалял зуб", "Только осматривал зубы", "Делал рентгеновский снимок", "Объяснял как ухаживать за зубами", "Не помню");
        return extractCondition(row, conditions, 31);
    }

    private String getDentalAdvice(Row row) {
        List<String> conditions = List.of("Лучше чистить зубы", "Удалить зубной камень", "Поставить пломбу", "Выровнять зубной ряд", "С твоими зубами все в порядке");
        return extractCondition(row, conditions, 38);
    }

    private String getDentalHelp(Row row) {
        String result = row.getCell(43).getStringCellValue();
        return result.isBlank() ? "не знаю" : result;
    }

    private String getAnotherOrganization(Row row) {
        List<String> conditions = List.of("Нет", "Да, в государственной стоматологической поликлиники", "Да, в частной стоматологической клинике");
        return extractCondition(row, conditions, 44);
    }

    private String getDifficulties(Row row) {
        String result = row.getCell(47).getStringCellValue();
        return result.isBlank() ? "ответ отсутствует" : result;
    }

    private String getDentalOfficeWork(Row row) {
        List<String> conditions = List.of("Неудовлетворительно", "Удовлетворительно", "Хорошо", "Отлично", "Безукоризненно");
        return extractCondition(row, conditions, 51);
    }

    private String getOfferForDentalOffice(Row row) {
        String result = row.getCell(56).getStringCellValue();
        return result.isBlank() ? "не знаю" : result;
    }

    private String getBrushProperly(Row row) {
        List<String> conditions = List.of("Умею чистить зубы", "Не совсем уверен, что умею", "Нет, не умею", "Безразлично");
        return extractCondition(row, conditions, 57);
    }

    private String getRulesInformation(Row row) {
        String result = row.getCell(61).getStringCellValue();
        return result.isBlank() ? "не знаю" : result;
    }

    private String getTeacher(Row row) {
        String result = row.getCell(62).getStringCellValue();
        return result.isBlank() ? "ответа нет" : result;
    }

    private String getHowYouBrush(Row row) {
        List<String> conditions = List.of("Никогда", "Один раз в неделю", "Один раз в день", "Несколько раз в день (утром/вечером)");
        return extractCondition(row, conditions, 63);
    }

    private String getToothbrushChange(Row row) {
        String result = row.getCell(67).getStringCellValue();
        return result.isBlank() ? "ответа нет" : result;
    }

    private String getFluoridePaste(Row row) {
        List<String> conditions = List.of("Да", "Нет", "Не знаю");
        return extractCondition(row, conditions, 68);
    }

    private String getAdditionalSource(Row row) {
        String result = row.getCell(78).getStringCellValue();
        return result.isBlank() ? "ответа нет" : result;
    }

    private String howChooseOralProducts(Row row) {
        List<String> conditions = List.of("По совету врача-стоматолога", "Ориентируюсь на цену", "Ориентируюсь на торговую марку", "Я не подбираю, покупают родители", "Для меня это не имеет значения");
        return extractCondition(row, conditions, 79);
    }

    private String getFrequencyUseAdditionalSource(Row row) {
        String result = row.getCell(84).getStringCellValue();
        return result.isBlank() ? "ответа нет" : result;
    }

    private String getFavoriteFood(Row row) {
        String result = row.getCell(85).getStringCellValue();
        return result.isBlank() ? "ответа нет" : result;
    }

    private String getFavoriteSweet(Row row) {
        String result = row.getCell(86).getStringCellValue();
        return result.isBlank() ? "ответа нет" : result;
    }

    private String getAim(Row row) {
        String result = row.getCell(87).getStringCellValue();
        return result.isBlank() ? "ответа нет" : result;
    }
}
