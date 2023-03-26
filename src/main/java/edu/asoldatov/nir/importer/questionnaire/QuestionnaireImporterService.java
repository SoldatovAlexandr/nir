package edu.asoldatov.nir.importer.questionnaire;

import edu.asoldatov.nir.controller.dto.ImportResultDto;
import edu.asoldatov.nir.dao.QuestionnaireCadetRepository;
import edu.asoldatov.nir.model.questionnaire.QuestionnaireCadet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionnaireImporterService {

    private final static String IMPORT_FILE_PATH = "/Users/asoldatov/ИТМО/НИР/Анкеты эпидемиологического обследования/Данные_для_загрузки.xlsx";

    private final QuestionnaireImporter questionnaireImporter;
    private final QuestionnaireCadetRepository repository;

    @Transactional
    public ImportResultDto importFile() {
        repository.deleteAll();
        try (Workbook workbook = new XSSFWorkbook(IMPORT_FILE_PATH)) {
            Sheet sheet = workbook.getSheetAt(0);
            List<QuestionnaireCadet> questionnaireCadets = questionnaireImporter.importInternal(sheet);
            repository.saveAll(questionnaireCadets);
/*
            Path path = Files.createFile(Path.of("/Users/asoldatov/ИТМО/НИР/Анкеты эпидемиологического обследования/result.csv"));
            Files.write(path, buildResults(questionnaireCadets));*/
            return new ImportResultDto(questionnaireCadets.size(), 0, List.of());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> buildResults(List<QuestionnaireCadet> questionnaireCadets) {
        List<String> result = new ArrayList<>();
        result.add("Доо;Курс;Возраст;1. Как Вы оцениваете состояние своих зубов и десен?;2. Сдерживаете ли Вы улыбку или смех из-за внешнего вида своих зубов?;3 . Вы пропускали учебные занятия по причине острой зубной боли?;4 . Как часто у Вас бывает чувствительность зубов или боли в зубах;5. Сколько раз Вы посещали врача-стоматолога в этом году;6. По какой причине Вы посещали врача-стоматолога в последний раз?;7. Что делал врач-стоматолог в последнее посещение?;8. Если бы Вы сейчас обратились к врачу-стоматологу, какой совет бы он дал?;9. В каких видах стоматологической помощи Вы нуждаетесь?;10. Лечите ли Вы зубы в других организациях?;11. Укажите трудности при проведения стоматологического лечения в вашем учебном заведении;12. Удовлетворены ли Вы оснащенностью рабочих мест врачей-стоматологов (да, если нет, то почему)?;13. Оцените по пятибалльной системе работу стоматологического кабинета в аспекте оказания Вам стоматологической помощи;14. Сделайте предложения по улучшению работы стоматологического кабинета училища;15. Умеете ли Вы правильно чистить зубы?;16. Какие сведения Вы имеете о правилах чистки зубов?;17. Кто Вам объяснял, как надо ухаживать за зубами?;17.Как часто Вы чистите зубы?;18. Как часто Вы производите смену зубной щётки на новую?;19.Вы пользуетесь фторсодержащей зубной пастой?;20.1 Зубная щетка;20.2 Зубочистка;20.3 Деревянная палочка;20.4 Флосс;20.5 Нитку для шитья;20.6 Ирригатор;20.7 Межзубной ершик;21. Какие дополнительные предметы и средства гигиены полости рта Вы используете?;22. Как Вы подбираете средства гигиены полости рта?;23. Укажите частоту использования дополнительных предметов и средств гигиены полости рта;24.Ваша любимая еда?;25.Ваше любимое «сладкое»?;26. С какой целью Вы чистите зубы?;Кариес может испортить вид моих зубов?;Сохранять натуральные зубы не важно;Я боюсь врача-стоматолога, потому что боюсь боли;Регулярное посещение стоматолога позволяет избежать проблем с зубами;Чистка зубов предотвращает кариес;Чистка зубов предотвращает заболевания дёсен;Употребление сладостей не влияет на возникновение кариеса;Фтор является хорошим средством профилактики кариеса");
        for (QuestionnaireCadet cadet : questionnaireCadets) {
            String str = new StringBuilder()
                    .append(cadet.getDoo()).append(';')
                    .append(cadet.getCourse()).append(';')
                    .append(cadet.getAge()).append(';')
                    .append(cadet.getDentalCondition()).append(';')
                    .append(cadet.isHideSmile()).append(';')
                    .append(cadet.isMissedClass()).append(';')
                    .append(cadet.getToothSensitivity()).append(';')
                    .append(cadet.getVisitNumber()).append(';')
                    .append(cadet.getLastVisitReason()).append(';')
                    .append(cadet.getLastDentalAction()).append(';')
                    .append(cadet.getDentalAdvice()).append(';')
                    .append(cadet.getDentalHelp()).append(';')
                    .append(cadet.getAnotherOrganization()).append(';')
                    .append(cadet.getDifficulties()).append(';')
                    .append(cadet.isSatisfiedWorkspace()).append(';')
                    .append(cadet.getDentalOfficeWork()).append(';')
                    .append(cadet.getOfferForDentalOffice()).append(';')
                    .append(cadet.getBrushProperly()).append(';')
                    .append(cadet.getRulesInformation()).append(';')
                    .append(cadet.getTeacher()).append(';')
                    .append(cadet.getHowYouBrush()).append(';')
                    .append(cadet.getToothbrushChange()).append(';')
                    .append(cadet.getFluoridePaste()).append(';')
                    .append(cadet.isToothbrush()).append(';')
                    .append(cadet.isToothpick()).append(';')
                    .append(cadet.isWoodenStick()).append(';')
                    .append(cadet.isFloss()).append(';')
                    .append(cadet.isThread()).append(';')
                    .append(cadet.isIrrigator()).append(';')
                    .append(cadet.isInterdentalBrush()).append(';')
                    .append(cadet.getAdditionalSource()).append(';')
                    .append(cadet.getHowChooseOralProducts()).append(';')
                    .append(cadet.getFrequencyUseAdditionalSource()).append(';')
                    .append(cadet.getFavoriteFood()).append(';')
                    .append(cadet.getFavoriteSweet()).append(';')
                    .append(cadet.getAim()).append(';')
                    .append(cadet.getCariesRuinsTeeth()).append(';')
                    .append(cadet.getSaveOriginalTeeth()).append(';')
                    .append(cadet.getAfraidPain()).append(';')
                    .append(cadet.getTeethProblem()).append(';')
                    .append(cadet.getBrushStopCaries()).append(';')
                    .append(cadet.getBrushStopGumDisease()).append(';')
                    .append(cadet.getEatsSweet()).append(';')
                    .append(cadet.getFluorine()).append(';')
                    .toString();
            result.add(str);
        }
        return result;
    }

    public ImportResultDto export() {
        //Санкт-Петербург
        //Тула
        //Владикавказ
        //Петразаводск
        //Казань
        //Ульяновск
        //Екатеринбург

        List<String> cites = List.of(
                "Санкт-Петербург",
                "Петразаводск",
                "Казань",
                "Тула",
                "Ульяновск",
                "Владикавказ",
                "Екатеринбург"
        );

        List<Integer> ages = List.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 0);


        List<String> result = new ArrayList<>();
        result.add("age;Отличное;Очень хорошее;Хорошее;Среднее;Плохое;Не знаю;2 Да;2 Нет; 3 Да; 3 No; Часто;Иногда;Редко;Никогда;Не знаю;" +
                "5 Один раз;Два раза;Три раза;Четыре раза;Более четырех;Ни разу;Не помню;" +
                "6 Мне назначили посещение;Регулярно посещаю стоматолога ;Болел зуб;Это был повторный визит;Это был профилактический осмотр;Не помню;" +
                "7 Ставил пломбу;Чистил зубы;Удалял зуб;Только осматривал зубы;Делал рентгеновский снимок;Объяснял как ухаживать за зубами;Не помню;" +
                "8 Лучше чистить зубы;Удалить зубной камень;Поставить пломбу;Выровнять зубной ряд;С твоими зубами все в порядке;" +
                "10 Нет;Да, в государственной стоматологической поликлиники;Да, в частной стоматологической клинике;" +
                "12Да;Нет, потому что;" +
                "13 Неудовлетворительно;Удовлетворительно;Хорошо;Отлично;Безукоризненно;" +
                "15 Умею чистить зубы;Не совсем уверен, что умею;Нет, не умею;Безразлично;" +
                "17.2 Никогда;Один раз в неделю;Один раз в день;Несколько раз в день (утром/вечером);" +
                "19 Да;Нет;Не знаю;" +
                "20 Зубную щетку;Зубочистку;Деревянную палочку;Флосс;Нитку для шитья;Ирригатор;Межзубной ёршик;" +
                "22 По совету врача-стоматолога;Ориентируюсь на цену;Ориентируюсь на торговую марку;Я не подбираю, покупают родители;Для меня это не имеет значения;" +
                "1 Согласен;Не согласен;Безразлично;" +
                "2 Согласен;Не согласен;Безразлично;" +
                "3 Согласен;Не согласен;Безразлично;" +
                "4 Согласен;Не согласен;Безразлично;" +
                "5 Согласен;Не согласен;Безразлично;" +
                "6 Согласен;Не согласен;Безразлично;" +
                "7 Согласен;Не согласен;Безразлично;" +
                "8 Согласен;Не согласен;Безразлично;"
        );

        for (String city : cites) {
            for (Integer age : ages) {
                List<QuestionnaireCadet> cadets = repository.findByDooAndAge(city, age);
                String str = age + ";" +
                        cadets.stream().filter(c -> c.getDentalCondition().equals("Отличное")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalCondition().equals("Очень хорошее")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalCondition().equals("Хорошее")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalCondition().equals("Среднее")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalCondition().equals("Плохое")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalCondition().equals("Не знаю")).count() + ";" +

                        cadets.stream().filter(QuestionnaireCadet::isHideSmile).count() + ";" +
                        cadets.stream().filter(c -> !c.isHideSmile()).count() + ";" +

                        cadets.stream().filter(QuestionnaireCadet::isMissedClass).count() + ";" +
                        cadets.stream().filter(c -> !c.isMissedClass()).count() + ";" +

                        cadets.stream().filter(c -> c.getToothSensitivity().equals("Часто")).count() + ";" +
                        cadets.stream().filter(c -> c.getToothSensitivity().equals("Иногда")).count() + ";" +
                        cadets.stream().filter(c -> c.getToothSensitivity().equals("Редко")).count() + ";" +
                        cadets.stream().filter(c -> c.getToothSensitivity().equals("Никогда")).count() + ";" +
                        cadets.stream().filter(c -> c.getToothSensitivity().equals("Не знаю")).count() + ";" +

                        cadets.stream().filter(c -> c.getVisitNumber().equals("Один раз")).count() + ";" +
                        cadets.stream().filter(c -> c.getVisitNumber().equals("Два раза")).count() + ";" +
                        cadets.stream().filter(c -> c.getVisitNumber().equals("Три раза")).count() + ";" +
                        cadets.stream().filter(c -> c.getVisitNumber().equals("Четыре раза")).count() + ";" +
                        cadets.stream().filter(c -> c.getVisitNumber().equals("Более четырех")).count() + ";" +
                        cadets.stream().filter(c -> c.getVisitNumber().equals("Ни разу")).count() + ";" +
                        cadets.stream().filter(c -> c.getVisitNumber().equals("Не помню")).count() + ";" +

                        cadets.stream().filter(c -> c.getLastVisitReason().equals("Мне назначили посещение")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastVisitReason().equals("Регулярно посещаю стоматолога")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastVisitReason().equals("Болел зуб")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastVisitReason().equals("Это был повторный визит")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastVisitReason().equals("Это был профилактический осмотр")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastVisitReason().equals("Не помню")).count() + ";" +

                        cadets.stream().filter(c -> c.getLastDentalAction().equals("Ставил пломбу")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastDentalAction().equals("Чистил зубы")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastDentalAction().equals("Удалял зуб")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastDentalAction().equals("Только осматривал зубы")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastDentalAction().equals("Делал рентгеновский снимок")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastDentalAction().equals("Объяснял как ухаживать за зубами")).count() + ";" +
                        cadets.stream().filter(c -> c.getLastDentalAction().equals("Не помню")).count() + ";" +

                        cadets.stream().filter(c -> c.getDentalAdvice().equals("Лучше чистить зубы")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalAdvice().equals("Удалить зубной камень")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalAdvice().equals("Поставить пломбу")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalAdvice().equals("Выровнять зубной ряд")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalAdvice().equals("С твоими зубами все в порядке")).count() + ";" +

                        // 9 help

                        cadets.stream().filter(c -> c.getAnotherOrganization().equals("Нет")).count() + ";" +
                        cadets.stream().filter(c -> c.getAnotherOrganization().equals("Да, в государственной стоматологической поликлиники")).count() + ";" +
                        cadets.stream().filter(c -> c.getAnotherOrganization().equals("Да, в частной стоматологической клинике")).count() + ";" +

                        // 11
                        cadets.stream().filter(c -> c.isSatisfiedWorkspace()).count() + ";" +
                        cadets.stream().filter(c -> !c.isSatisfiedWorkspace()).count() + ";" +

                        cadets.stream().filter(c -> c.getDentalOfficeWork().equals("Неудовлетворительно")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalOfficeWork().equals("Удовлетворительно")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalOfficeWork().equals("Хорошо")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalOfficeWork().equals("Отлично")).count() + ";" +
                        cadets.stream().filter(c -> c.getDentalOfficeWork().equals("Безукоризненно")).count() + ";" +

                        //14

                        cadets.stream().filter(c -> c.getBrushProperly().equals("Умею чистить зубы")).count() + ";" +
                        cadets.stream().filter(c -> c.getBrushProperly().equals("Не совсем уверен, что умею")).count() + ";" +
                        cadets.stream().filter(c -> c.getBrushProperly().equals("Нет, не умею")).count() + ";" +
                        cadets.stream().filter(c -> c.getBrushProperly().equals("Безразлично")).count() + ";" +

                        //16
                        //17.1

                        cadets.stream().filter(c -> c.getHowYouBrush().equals("Никогда")).count() + ";" +
                        cadets.stream().filter(c -> c.getHowYouBrush().equals("Один раз в неделю")).count() + ";" +
                        cadets.stream().filter(c -> c.getHowYouBrush().equals("Один раз в день")).count() + ";" +
                        cadets.stream().filter(c -> c.getHowYouBrush().equals("Несколько раз в день (утром/вечером)")).count() + ";" +

                        //18

                        cadets.stream().filter(c -> c.getFluoridePaste().equals("Да")).count() + ";" +
                        cadets.stream().filter(c -> c.getFluoridePaste().equals("Нет")).count() + ";" +
                        cadets.stream().filter(c -> c.getFluoridePaste().equals("Не знаю")).count() + ";" +

                        cadets.stream().filter(QuestionnaireCadet::isToothbrush).count() + ";" +
                        cadets.stream().filter(QuestionnaireCadet::isToothpick).count() + ";" +
                        cadets.stream().filter(QuestionnaireCadet::isWoodenStick).count() + ";" +
                        cadets.stream().filter(QuestionnaireCadet::isFloss).count() + ";" +
                        cadets.stream().filter(QuestionnaireCadet::isThread).count() + ";" +
                        cadets.stream().filter(QuestionnaireCadet::isIrrigator).count() + ";" +
                        cadets.stream().filter(QuestionnaireCadet::isInterdentalBrush).count() + ";" +

                        //21

                        cadets.stream().filter(c -> c.getHowChooseOralProducts().equals("По совету врача-стоматолога")).count() + ";" +
                        cadets.stream().filter(c -> c.getHowChooseOralProducts().equals("Ориентируюсь на цену")).count() + ";" +
                        cadets.stream().filter(c -> c.getHowChooseOralProducts().equals("Ориентируюсь на торговую марку")).count() + ";" +
                        cadets.stream().filter(c -> c.getHowChooseOralProducts().equals("Я не подбираю, покупают родители")).count() + ";" +
                        cadets.stream().filter(c -> c.getHowChooseOralProducts().equals("Для меня это не имеет значения")).count() + ";" +


                        cadets.stream().filter(c -> c.getCariesRuinsTeeth().equals("Согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getCariesRuinsTeeth().equals("Не согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getCariesRuinsTeeth().equals("Безразлично")).count() + ";" +

                        cadets.stream().filter(c -> c.getSaveOriginalTeeth().equals("Согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getSaveOriginalTeeth().equals("Не согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getSaveOriginalTeeth().equals("Безразлично")).count() + ";" +

                        cadets.stream().filter(c -> c.getAfraidPain().equals("Согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getAfraidPain().equals("Не согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getAfraidPain().equals("Безразлично")).count() + ";" +

                        cadets.stream().filter(c -> c.getTeethProblem().equals("Согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getTeethProblem().equals("Не согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getTeethProblem().equals("Безразлично")).count() + ";" +

                        cadets.stream().filter(c -> c.getBrushStopCaries().equals("Согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getBrushStopCaries().equals("Не согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getBrushStopCaries().equals("Безразлично")).count() + ";" +

                        cadets.stream().filter(c -> c.getBrushStopGumDisease().equals("Согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getBrushStopGumDisease().equals("Не согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getBrushStopGumDisease().equals("Безразлично")).count() + ";" +

                        cadets.stream().filter(c -> c.getEatsSweet().equals("Согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getEatsSweet().equals("Не согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getEatsSweet().equals("Безразлично")).count() + ";" +

                        cadets.stream().filter(c -> c.getFluorine().equals("Согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getFluorine().equals("Не согласен")).count() + ";" +
                        cadets.stream().filter(c -> c.getFluorine().equals("Безразлично")).count() + ";";

                result.add(str);
            }
        }
        try {
            Path path = Files.createFile(Path.of("/Users/asoldatov/ИТМО/sasha.csv"));
            Files.write(path, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public ImportResultDto export2() {

        List<String> cites = List.of(
                "Санкт-Петербург",
                "Петразаводск",
                "Казань",
                "Тула",
                "Ульяновск",
                "Владикавказ",
                "Екатеринбург"
        );

        List<Integer> ages = List.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 0);

        List<String> result = new ArrayList<>();
        result.add(
                "age;" +
                        "Трудности отсутвуют;" +
                        "Затрудняюсь ответить;" +
                        "Отсутвие времени на лечение;" +
                        "Загурженность врача-стоматолога (длинная очередь, );" +
                        "Страх лечения (возможная боль, недоверие к врачу, неэфективонсти анесезии);" +
                        "Изношеннсть оборуждования;" +
                        "Страх лечения из-за боли;" +
                        "Отсутвие другого вида помощи (ортодонтической, хирургической);" +
                        "Осбенности лечения (сложная клиническая ситуация, аллергиеские реакции, физиологиеские особенности);" +

                        "; " +

                        "Предложения отсутвуют;" +
                        "Затрудняются в ответе;" +
                        "Закупка нового и доп. оборудования;" +
                        "Увеличить число врачей-стомтаологов и уменьшить очеред санации;" +
                        "Улучшить качество стоматологичкой помощи (эффективность анестезии, беседы с пациентами);" +
                        "Увеличить размер и состяние стоматолологиского кабинета;" +
                        "Увелилить частоту профилактических осмотров;" +
                        "Поощрение за лечение (мотивация пациентов);" +
                        "Расширить спект оказываемоцй помощи (ортодонтиечской);" +

                        "; " +

                        "Родители;" +
                        "Врач-стоматолог;" +
                        "Никто не обяснял о читке зубов (самотоятельно, интуитивно, увидел по телевизоу как делают другие);" +
                        "Затрудняются ответитить на вопрос;" +
                        "Учитель (воспиталеть);" +

                        "; " +

                        "один раз в месяц;" +
                        "один раз в три месяца;" +
                        "один раз в два месяца;" +
                        "один раз в шесть месяца;" +
                        "затурдняются в ответе;" +
                        "по мере износа, без учета времени;" +
                        "чаще одного раза в месяц;" +
                        "один раз в двенадцать месяца;" +
                        "один раз в полтора месяца;" +

                        "; " +

                        "Никаких дополниетльных средств гигиены;" +
                        "Ополаскиватель полости рта;" +
                        "Затурдняются в ответе;" +
                        "Зубная нить;" +
                        "Флосс;" +
                        "Зубной порошок;" +
                        "Межзубные ершики;" +
                        "Зубочитска;" +
                        "Ирргиатор;" +
                        "Жеваетльаня резинка;" +

                        "; " +

                        "Никаких дополниетльных средств гигиены не используют;" +
                        "Один раз в сутки;" +
                        "Затурдняются в ответе;" +
                        "Редко, по мере необходимомсти и возможности, без указаний интервала времени;" +
                        "Часто, без указаний интервала времени;" +
                        "Один раз в неделю;" +
                        "Два раза в сутки;" +
                        "Один раз в два дня;" +
                        "После приема пищи;" +

                        "; " +

                        "Фастфуд (бургеры, пицца, шаверма, пироги);" +
                        "Мясные полуфобрикаты (пельмени, котлеты, голубцы, сосиски, хинкали);" +
                        "Мясо (говядина, свинина, курица);" +
                        "Затрудняются в ответе;" +
                        "Картофель (жаренный, пюре);" +
                        "Макаронные изделья;" +
                        "Плов;" +
                        "Домашняя кухня;" +
                        "Нет предпочтений в еде;" +
                        "Свежие фрукты, овощи и ягоды;" +
                        "Крупы (рис, гречка, перловка);" +
                        "Японская кухня (сушим, роллы);" +
                        "Супы;" +
                        "Здоровая пища;" +
                        "Сладости (шоколоад, конфеты, торты);" +
                        "Овощные салаты;" +
                        "Молочные продукты (твророг, мороженное);" +
                        "Рыба и морепродукты;" +

                        "; " +

                        "Шоколад;" +
                        "Мороженное;" +
                        "Конфеты;" +
                        "Затрудняются в ответе;" +
                        "Торты;" +
                        "Мармелад, зефир, пастила;" +
                        "Пироженное;" +
                        "Нет предпочтений сладкого;" +
                        "Мучное (печенье, кеск, вафли, пончики);" +
                        "Халва;" +
                        "Орехи и фрукты;" +
                        "Мед;" +
                        "Сладкие напитки;" +

                        "; " +

                        "В целях личной гигиены;" +
                        "Здоровье полости рта;" +
                        "Затьрудняются в ответе;" +
                        "Привычка;" +
                        "Красота зубов;" +
                        "Устанине непритяньго запаха изо рта;" +
                        "Профилактика кариеса;" +

                        "; " +

                        "не нуждаюсь;" +
                        "терапевтической;" +
                        "ортодонтической;" +
                        "не знаю;" +
                        "профессиональная гигиена;" +

                        "; " +

                        "Нет сведений о правилах чистки зубов;" +
                        "Частота чистки зубов;" +
                        "Есть понимание, но отсутвуют зания;" +
                        "Правило чистки зубов;" +
                        "Длительность чистики зубов;" +
                        "Указано только общее понятие без указаний действия;" +
                        "Дополнительные средства гигиены;"
        );

        Set<String> uniq = new HashSet<>();
        for (String city : cites) {
            for (Integer age : ages) {
                List<QuestionnaireCadet> cadets = repository.findByDooAndAge(city, age);

                List<String> difficulties = cadets.stream().flatMap(v -> Arrays.stream(v.getDifficulties().split(";"))).map(String::trim).collect(Collectors.toList());

                List<String> offers = cadets.stream().flatMap(v -> Arrays.stream(v.getOfferForDentalOffice().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> teachers = cadets.stream().flatMap(v -> Arrays.stream(v.getTeacher().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> toothbrushChange = cadets.stream().flatMap(v -> Arrays.stream(v.getToothbrushChange().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> additional = cadets.stream().flatMap(v -> Arrays.stream(v.getAdditionalSource().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> additionalFre = cadets.stream().flatMap(v -> Arrays.stream(v.getFrequencyUseAdditionalSource().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> favoriteFood = cadets.stream().flatMap(v -> Arrays.stream(v.getFavoriteFood().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> favoriteSweet = cadets.stream().flatMap(v -> Arrays.stream(v.getFavoriteSweet().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> aim = cadets.stream().flatMap(v -> Arrays.stream(v.getAim().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> dentalHelp = cadets.stream().flatMap(v -> Arrays.stream(v.getDentalHelp().split(";"))).map(String::trim).collect(Collectors.toList());
                List<String> rulesInformation = cadets.stream().flatMap(v -> Arrays.stream(v.getRulesInformation().split(";"))).map(String::trim).collect(Collectors.toList());

                uniq.addAll(teachers);

                String str = age + ";" +
                        difficulties.stream().filter(c -> c.equals("Трудности отсутвуют")).count() + ";" +
                        difficulties.stream().filter(c -> c.equals("Затрудняюсь ответить")).count() + ";" +
                        difficulties.stream().filter(c -> c.equals("Отсутвие времени на лечение")).count() + ";" +
                        difficulties.stream().filter(c -> c.equals("Загурженность врача-стоматолога (длинная очередь, )")).count() + ";" +
                        difficulties.stream().filter(c -> c.equals("Страх лечения (возможная боль, недоверие к врачу, неэфективонсти анесезии)")).count() + ";" +
                        difficulties.stream().filter(c -> c.equals("Изношеннсть оборуждования")).count() + ";" +
                        difficulties.stream().filter(c -> c.equals("Страх лечения из-за боли")).count() + ";" +
                        difficulties.stream().filter(c -> c.equals("Отсутвие другого вида помощи (ортодонтической, хирургической)")).count() + ";" +
                        difficulties.stream().filter(c -> c.equals("Осбенности лечения (сложная клиническая ситуация, аллергиеские реакции, физиологиеские особенности)")).count() + ";" +
                        "; " +
                        offers.stream().filter(c -> c.equals("Предложения отсутвуют")).count() + ";" +
                        offers.stream().filter(c -> c.equals("Затрудняются в ответе")).count() + ";" +
                        offers.stream().filter(c -> c.equals("Закупка нового и доп. оборудования")).count() + ";" +
                        offers.stream().filter(c -> c.equals("Увеличить число врачей-стомтаологов и уменьшить очеред санации")).count() + ";" +
                        offers.stream().filter(c -> c.equals("Улучшить качество стоматологичкой помощи (эффективность анестезии, беседы с пациентами)")).count() + ";" +
                        offers.stream().filter(c -> c.equals("Увеличить размер и состяние стоматолологиского кабинета")).count() + ";" +
                        offers.stream().filter(c -> c.equals("Увелилить частоту профилактических осмотров")).count() + ";" +
                        offers.stream().filter(c -> c.equals("Поощрение за лечение (мотивация пациентов)")).count() + ";" +
                        offers.stream().filter(c -> c.equals("Расширить спект оказываемоцй помощи (ортодонтиечской)")).count() + ";" +
                        "; " +
                        teachers.stream().filter(c -> c.equals("Родители")).count() + ";" +
                        teachers.stream().filter(c -> c.equals("Врач-стоматолог")).count() + ";" +
                        teachers.stream().filter(c -> c.equals("Никто не обяснял о читке зубов (самотоятельно, интуитивно, увидел по телевизоу как делают другие)")).count() + ";" +
                        teachers.stream().filter(c -> c.equals("Затрудняются ответитить на вопрос")).count() + ";" +
                        teachers.stream().filter(c -> c.equals("Учитель (воспиталеть)")).count() + ";" +
                        "; " +
                        toothbrushChange.stream().filter(c -> c.equals("один раз в месяц")).count() + ";" +
                        toothbrushChange.stream().filter(c -> c.equals("один раз в три месяца")).count() + ";" +
                        toothbrushChange.stream().filter(c -> c.equals("один раз в два месяца")).count() + ";" +
                        toothbrushChange.stream().filter(c -> c.equals("один раз в шесть месяца")).count() + ";" +
                        toothbrushChange.stream().filter(c -> c.equals("затурдняются в ответе")).count() + ";" +
                        toothbrushChange.stream().filter(c -> c.equals("по мере износа, без учета времени")).count() + ";" +
                        toothbrushChange.stream().filter(c -> c.equals("чаще одного раза в месяц")).count() + ";" +
                        toothbrushChange.stream().filter(c -> c.equals("один раз в двенадцать месяца")).count() + ";" +
                        toothbrushChange.stream().filter(c -> c.equals("один раз в полтора месяца")).count() + ";" +

                        "; " +

                        additional.stream().filter(c -> c.equals("Никаких дополниетльных средств гигиены")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Ополаскиватель полости рта")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Затурдняются в ответе")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Зубная нить")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Флосс")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Зубной порошок")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Межзубные ершики")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Зубочитска")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Ирргиатор")).count() + ";" +
                        additional.stream().filter(c -> c.equals("Жеваетльаня резинка")).count() + ";" +


                        "; " +
                        additionalFre.stream().filter(c -> c.equals("Никаких дополниетльных средств гигиены не используют")).count() + ";" +
                        additionalFre.stream().filter(c -> c.equals("Один раз в сутки")).count() + ";" +
                        additionalFre.stream().filter(c -> c.equals("Затурдняются в ответе")).count() + ";" +
                        additionalFre.stream().filter(c -> c.equals("Редко, по мере необходимомсти и возможности, без указаний интервала времени")).count() + ";" +
                        additionalFre.stream().filter(c -> c.equals("Часто, без указаний интервала времени")).count() + ";" +
                        additionalFre.stream().filter(c -> c.equals("Один раз в неделю")).count() + ";" +
                        additionalFre.stream().filter(c -> c.equals("Два раза в сутки")).count() + ";" +
                        additionalFre.stream().filter(c -> c.equals("Один раз в два дня")).count() + ";" +
                        additionalFre.stream().filter(c -> c.equals("После приема пищи")).count() + ";" +
                        "; " +

                        favoriteFood.stream().filter(c -> c.equals("Фастфуд (бургеры, пицца, шаверма, пироги)")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Мясные полуфобрикаты (пельмени, котлеты, голубцы, сосиски, хинкали)")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Мясо (говядина, свинина, курица)")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Затрудняются в ответе")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Картофель (жаренный, пюре)")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Макаронные изделья")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Плов")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Домашняя кухня")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Нет предпочтений в еде")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Свежие фрукты, овощи и ягоды")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Крупы (рис, гречка, перловка)")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Японская кухня (сушим, роллы)")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Супы")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Здоровая пища")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Сладости (шоколоад, конфеты, торты)")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Овощные салаты")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Молочные продукты (твророг, мороженное)") || c.equals("Молочные изделья")).count() + ";" +
                        favoriteFood.stream().filter(c -> c.equals("Рыба и морепродукты")).count() + ";" +

                        "; " +

                        favoriteSweet.stream().filter(c -> c.equals("Шоколад")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Мороженное")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Конфеты")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Затрудняются в ответе")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Торты")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Мармелад, зефир, пастила")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Пироженное")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Нет предпочтений сладкого")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Мучное (печенье, кеск, вафли, пончики)")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Халва")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Орехи и фрукты")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Мед")).count() + ";" +
                        favoriteSweet.stream().filter(c -> c.equals("Сладкие напитки")).count() + ";" +

                        "; " +

                        aim.stream().filter(c -> c.equals("В целях личной гигиены")).count() + ";" +
                        aim.stream().filter(c -> c.equals("Здоровье полости рта")).count() + ";" +
                        aim.stream().filter(c -> c.equals("Затьрудняются в ответе")).count() + ";" +
                        aim.stream().filter(c -> c.equals("Привычка")).count() + ";" +
                        aim.stream().filter(c -> c.equals("Красота зубов")).count() + ";" +
                        aim.stream().filter(c -> c.equals("Устанине непритяньго запаха изо рта")).count() + ";" +
                        aim.stream().filter(c -> c.equals("Профилактика кариеса")).count() + ";" +

                        "; " +

                        dentalHelp.stream().filter(c -> c.equals("не нуждаюсь")).count() + ";" +
                        dentalHelp.stream().filter(c -> c.equals("терапевтической")).count() + ";" +
                        dentalHelp.stream().filter(c -> c.equals("ортодонтической")).count() + ";" +
                        dentalHelp.stream().filter(c -> c.equals("не знаю")).count() + ";" +
                        dentalHelp.stream().filter(c -> c.equals("профессиональная гигиена")).count() + ";" +

                        "; " +

                        rulesInformation.stream().filter(c -> c.equals("Нет сведений о правилах чистки зубов")).count() + ";" +
                        rulesInformation.stream().filter(c -> c.equals("Частота чистки зубов")).count() + ";" +
                        rulesInformation.stream().filter(c -> c.equals("Есть понимание, но отсутвуют зания")).count() + ";" +
                        rulesInformation.stream().filter(c -> c.equals("Правило чистки зубов")).count() + ";" +
                        rulesInformation.stream().filter(c -> c.equals("Длительность чистики зубов")).count() + ";" +
                        rulesInformation.stream().filter(c -> c.equals("Указано только общее понятие без указаний действия")).count() + ";" +
                        rulesInformation.stream().filter(c -> c.equals("Дополнительные средства гигиены")).count() + ";";
                result.add(str);
            }
        }
        log.info("uniq difficulties: [{}]", uniq.size());
        try {
            Path path = Files.createFile(Path.of("/Users/asoldatov/ИТМО/sasha2.csv"));
            Files.write(path, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public ImportResultDto toDataset() {

        List<String> result = new ArrayList<>();

        result.add("doo;course;age;dental_condition;is_hide_smile;is_missed_class;tooth_sensitivity;visit_number;last_visit_reason;last_dental_action;dental_advice;" +
                "therapeutic_dental_help;orthodontic_dental_help;professional_hygiene_dental_help;" +
                "another_organization;difficulties;is_satisfied_workspace;dental_office_work;offer_for_dental_office;brush_properly;" +
                "not_rules_information;frequency_rules_information;understanding_rules_information;rule_rules_information;duration_rules_information;general_concept_rules_information;additional_sources_rules_information;" +
                "parents_teacher;dentist_teacher;without_teacher;teacher;" +
                "how_you_brush;toothbrush_change;fluoride_paste;toothbrush;toothpick;wooden_stick;floss;thread;irrigator;interdental_brush;" +
                "mouthwash_additional_source;thread_additional_source;floss_additional_source;dentifrice_additional_source;interdental_brushes_additional_source;toothpick_additional_source;irrigator_additional_source;chewing_gum_additional_source;" +
                "how_choose_oral_products;frequency_use_additional_source;" +
                "fast_food_ff;meat_semi-finished_products_ff;meet_ff;potato_ff;pasta_ff;plov_ff;home_food_ff;fruits_ff;cereals_ff;japanese_food_ff;soup_ff;healthy_food_ff;sweet_ff;milk_products_ff;vegetable_salads_ff;fish_ff;"+
                "chocolate_fs;ice_cream_fs;candies_ff;cake_fs;marmalade_fs;pirognoe_fs;flour_fs;halva_fs;nuts_fruits_fs;honey_fs;drinks_fs;"+
                "personal_hygiene_aim;oral_health_aim;habit_aim;beauty_of_teeth_aim;eliminate_bad_breath_aim;prevention_of_caries_aim;"+
                "caries_ruins_teeth;save_original_teeth;afraid_pain;teeth_problem;brush_stop_caries;brush_stop_gum_disease;eats_sweet;fluorine;"
        );


        for (QuestionnaireCadet cadet : repository.findAll()) {
            StringBuilder builder = new StringBuilder()
                    .append(cadet.getDoo()).append(";")
                    .append(cadet.getCourse()).append(";")
                    .append(cadet.getAge()).append(";")
                    .append(cadet.getDentalCondition()).append(";")
                    .append(cadet.isHideSmile()).append(";")
                    .append(cadet.isMissedClass()).append(";")
                    .append(cadet.getToothSensitivity()).append(";")
                    .append(cadet.getVisitNumber()).append(";")
                    .append(cadet.getLastVisitReason()).append(";")
                    .append(cadet.getLastDentalAction()).append(";")
                    .append(cadet.getDentalAdvice()).append(";")
                    .append(is(cadet.getDentalHelp(), "терапевтической")).append(";")
                    .append(is(cadet.getDentalHelp(), "ортодонтической")).append(";")
                    .append(is(cadet.getDentalHelp(), "профессиональная гигиена")).append(";")
                    .append(cadet.getAnotherOrganization()).append(";")
                    .append(cadet.getDifficulties()).append(";")
                    .append(cadet.isSatisfiedWorkspace()).append(";")
                    .append(cadet.getDentalOfficeWork()).append(";")
                    .append(cadet.getOfferForDentalOffice()).append(";")
                    .append(cadet.getBrushProperly()).append(";")
                    .append(is(cadet.getRulesInformation(), "Нет сведений о правилах чистки зубов")).append(";")
                    .append(is(cadet.getRulesInformation(), "Частота чистки зубов")).append(";")
                    .append(is(cadet.getRulesInformation(), "Есть понимание, но отсутвуют зания")).append(";")
                    .append(is(cadet.getRulesInformation(), "Правило чистки зубов")).append(";")
                    .append(is(cadet.getRulesInformation(), "Длительность чистики зубов")).append(";")
                    .append(is(cadet.getRulesInformation(), "Указано только общее понятие без указаний действия")).append(";")
                    .append(is(cadet.getRulesInformation(), "Дополнительные средства гигиены")).append(";")
                    .append(is(cadet.getTeacher(), "Родители")).append(";")
                    .append(is(cadet.getTeacher(), "Врач-стоматолог")).append(";")
                    .append(is(cadet.getTeacher(), "Никто не обяснял о читке зубов")).append(";")
                    .append(is(cadet.getTeacher(), "Учитель")).append(";")
                    .append(cadet.getHowYouBrush()).append(";")
                    .append(cadet.getToothbrushChange()).append(";")
                    .append(isYes(cadet.getFluoridePaste())).append(";")
                    .append(cadet.isToothbrush()).append(";")
                    .append(cadet.isToothpick()).append(";")
                    .append(cadet.isWoodenStick()).append(";")
                    .append(cadet.isFloss()).append(";")
                    .append(cadet.isThread()).append(";")
                    .append(cadet.isIrrigator()).append(";")
                    .append(cadet.isInterdentalBrush()).append(";")
                    .append(is(cadet.getAdditionalSource(), "Ополаскиватель полости рта")).append(";")
                    .append(is(cadet.getAdditionalSource(), "Зубная нить")).append(";")
                    .append(is(cadet.getAdditionalSource(), "Флосс")).append(";")
                    .append(is(cadet.getAdditionalSource(), "Зубной порошок")).append(";")
                    .append(is(cadet.getAdditionalSource(), "Межзубные ершики")).append(";")
                    .append(is(cadet.getAdditionalSource(), "Зубочитска")).append(";")
                    .append(is(cadet.getAdditionalSource(), "Ирргиатор")).append(";")
                    .append(is(cadet.getAdditionalSource(), "Жеваетльаня резинка")).append(";")
                    .append(cadet.getHowChooseOralProducts()).append(";")
                    .append(cadet.getFrequencyUseAdditionalSource()).append(";")
                    .append(is(cadet.getFavoriteFood(), "Фастфуд (бургеры, пицца, шаверма, пироги)")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Мясные полуфобрикаты (пельмени, котлеты, голубцы, сосиски, хинкали)")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Мясо (говядина, свинина, курица)")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Картофель (жаренный, пюре)")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Макаронные изделья")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Плов")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Домашняя кухня")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Свежие фрукты, овощи и ягоды")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Крупы (рис, гречка, перловка)")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Японская кухня (сушим, роллы)")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Супы")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Здоровая пища")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Сладости (шоколоад, конфеты, торты)")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Молочные продукты (твророг, мороженное)")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Овощные салаты")).append(";")
                    .append(is(cadet.getFavoriteFood(), "Рыба и морепродукты")).append(";")//16
                    .append(is(cadet.getFavoriteSweet(), "Шоколад")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Мороженное")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Конфеты")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Торты")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Мармелад, зефир, пастила")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Пироженное")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Мучное (печенье, кеск, вафли, пончики)")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Халва")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Орехи и фрукты")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Мед")).append(";")
                    .append(is(cadet.getFavoriteSweet(), "Сладкие напитки")).append(";")//11
                    .append(is(cadet.getAim(), "В целях личной гигиены")).append(";")
                    .append(is(cadet.getAim(), "Здоровье полости рта")).append(";")
                    .append(is(cadet.getAim(), "Привычка")).append(";")
                    .append(is(cadet.getAim(), "Красота зубов")).append(";")
                    .append(is(cadet.getAim(), "Устанине непритяньго запаха изо рта")).append(";")
                    .append(is(cadet.getAim(), "Профилактика кариеса")).append(";")//6
                    .append(cadet.getCariesRuinsTeeth()).append(";")
                    .append(cadet.getSaveOriginalTeeth()).append(";")
                    .append(cadet.getAfraidPain()).append(";")
                    .append(cadet.getTeethProblem()).append(";")
                    .append(cadet.getBrushStopCaries()).append(";")
                    .append(cadet.getBrushStopGumDisease()).append(";")
                    .append(cadet.getEatsSweet()).append(";")
                    .append(cadet.getFluorine()).append(";");


            result.add(builder.toString());
        }

        try {

            Path path = Files.createFile(Path.of("/Users/asoldatov/ИТМО/tomo.csv"));
            Files.write(path, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    static boolean is(String value, String type) {
        return value.contains(type);
    }

    static boolean isYes(String value) {
        return value.equals("Да");
    }
}
