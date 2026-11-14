package edu.asoldatov.nir.soc_middle_cadet;

import edu.asoldatov.nir.dao.QuestionnaireCadetRepository;
import edu.asoldatov.nir.model.questionnaire.QuestionnaireCadet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MiddleCadetService {

    private final QuestionnaireCadetRepository repository;

    //@EventListener(ApplicationReadyEvent.class)
    public void analyze() {

        System.out.println();
        System.out.println();
        System.out.println();

        var cadets = new ArrayList<QuestionnaireCadet>();
        repository.findAll().forEach(cadets::add);

        int middle = cadets.size() / 2;

        cadets.sort(Comparator.comparing(QuestionnaireCadet::getAge));
        System.out.printf("Медиана по возрасту: %s%n", cadets.get(middle).getAge());

        cadets.sort(Comparator.comparing(questionnaireCadet -> DentalCondition.getByValue(questionnaireCadet.getDentalCondition())));
        System.out.printf("Медиана по состоянию зубов: %s%n", cadets.get(middle).getDentalCondition());

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isHideSmile));
        System.out.printf("Медиана по сокрытию улыбки: %s%n", cadets.get(middle).isHideSmile());

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isMissedClass));
        System.out.printf("Медиана по пропуску занятий: %s%n", cadets.get(middle).isMissedClass());

        cadets.sort(Comparator.comparing(questionnaireCadet -> ToothSensitivity.getByValue(questionnaireCadet.getToothSensitivity())));
        System.out.printf("Медиана по чувствительности зубов: %s%n", cadets.get(middle).getToothSensitivity());

        cadets.sort(Comparator.comparing(questionnaireCadet -> VisitNumber.getByValue(questionnaireCadet.getVisitNumber())));
        System.out.printf("Медиана по кол-ву визитов: %s%n", cadets.get(middle).getVisitNumber());

        String lastVisitReason = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getLastVisitReason().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по причине последнего визита: %s%n", lastVisitReason);

        String lastDentalAction = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getLastDentalAction().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по последнему стоматологическому действию: %s%n", lastDentalAction);

        String dentalAdvice = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getDentalAdvice().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по стоматологическому совету: %s%n", dentalAdvice);

        String dentalHelp = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getDentalHelp().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по стоматологической помощи: %s%n", dentalHelp);

        String anotherOrganization = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getAnotherOrganization().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по другой организации: %s%n", anotherOrganization);

        String difficulties = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getDifficulties().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по трудностям: %s%n", difficulties);

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isSatisfiedWorkspace));
        System.out.printf("Медиана по доволен рабочим пространством: %s%n", cadets.get(middle).isSatisfiedWorkspace());

        cadets.sort(Comparator.comparing(questionnaireCadet -> DentalOfficeWork.getByValue(questionnaireCadet.getDentalOfficeWork())));
        System.out.printf("Медиана по оценке работы кабинета: %s%n", cadets.get(middle).getDentalOfficeWork());

        String offerForDentalOffice = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getOfferForDentalOffice().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по предложению для стоматологического кабинета: %s%n", offerForDentalOffice);

        cadets.sort(Comparator.comparing(questionnaireCadet -> DentalOfficeWork.getByValue(questionnaireCadet.getDentalOfficeWork())));
        System.out.printf("Медиана по оценке работы кабинета: %s%n", cadets.get(middle).getDentalOfficeWork());

        cadets.sort(Comparator.comparing(questionnaireCadet -> BrushProperly.getByValue(questionnaireCadet.getBrushProperly())));
        System.out.printf("Медиана по умею правильно чистить зубы: %s%n", cadets.get(middle).getBrushProperly());

        String rulesInformation = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getRulesInformation().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по информации о чистке зубов: %s%n", rulesInformation);

        String teacher = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getTeacher().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по \"учитель\": %s%n", teacher);

        cadets.sort(Comparator.comparing(questionnaireCadet -> HowYouBrush.getByValue(questionnaireCadet.getHowYouBrush())));
        System.out.printf("Медиана по как часто вы чистите зубы: %s%n", cadets.get(middle).getHowYouBrush());

        cadets.sort(Comparator.comparing(questionnaireCadet -> ToothbrushChange.getByValue(questionnaireCadet.getToothbrushChange())));
        System.out.printf("Медиана по как часто вы меняете зубную щетку: %s%n", cadets.get(middle).getToothbrushChange());

        String fluoridePaste = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getFluoridePaste().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по фторидная паста: %s%n", fluoridePaste);

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isToothbrush));
        System.out.printf("Медиана по Зубная щетка: %s%n", cadets.get(middle).isToothbrush());

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isToothpick));
        System.out.printf("Медиана по зубочистка: %s%n", cadets.get(middle).isToothpick());

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isWoodenStick));
        System.out.printf("Медиана по деревянная палочка: %s%n", cadets.get(middle).isWoodenStick());

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isFloss));
        System.out.printf("Медиана по флосс: %s%n", cadets.get(middle).isFloss());

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isThread));
        System.out.printf("Медиана по зубной нити: %s%n", cadets.get(middle).isThread());

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isIrrigator));
        System.out.printf("Медиана по ирригатор: %s%n", cadets.get(middle).isIrrigator());

        cadets.sort(Comparator.comparing(QuestionnaireCadet::isInterdentalBrush));
        System.out.printf("Медиана по межзубная щетка: %s%n", cadets.get(middle).isInterdentalBrush());

        String additionalSource = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getAdditionalSource().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по дополнительный источник: %s%n", additionalSource);

        String howChooseOralProducts = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getHowChooseOralProducts().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по как вы выбираете средства для чистки полости рта: %s%n", howChooseOralProducts);

        String frequencyUseAdditionalSource = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getFrequencyUseAdditionalSource().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по частота использования дополнительных средств: %s%n", frequencyUseAdditionalSource);

        String favouriteFood = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getFavoriteFood().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по любимая еда: %s%n", favouriteFood);

        String favouriteSweet = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getFavoriteSweet().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по любимое сладкое: %s%n", favouriteSweet);

        String aim = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getAim().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по цель: %s%n", aim);

        String cariesRuinsTeeth = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getCariesRuinsTeeth().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по кариес разрушает зубы: %s%n", cariesRuinsTeeth);

        String saveOriginalTeeth = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getSaveOriginalTeeth().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по сохранить оригинальные зубы: %s%n", saveOriginalTeeth);

        String afraidPain = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getAfraidPain().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по боюсь боли: %s%n", afraidPain);

        String teethProblem = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getTeethProblem().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по проблемы с зубами: %s%n", teethProblem);

        String brushStopCaries = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getBrushStopCaries().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по чистка зубов останавливает кариес: %s%n", brushStopCaries);

        String brushStopGumDisease = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getBrushStopGumDisease().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по чистка зубов останавливает болезнь десен: %s%n", brushStopGumDisease);

        String eatsSweet = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getEatsSweet().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по есть сладкое: %s%n", eatsSweet);

        String fluorine = getMaxByCount(cadets.stream().flatMap(v -> Arrays.stream(v.getFluorine().split(";"))).collect(Collectors.toList()));
        System.out.printf("Максимальная величина по фтор: %s%n", fluorine);
    }

    private String getMaxByCount(List<String> collect) {
        Map<String, Integer> counting = new HashMap<>();
        for (String v : collect) {
            if (counting.containsKey(v)) {
                counting.put(v, counting.get(v) + 1);
            } else {
                counting.put(v, 1);
            }
        }
        String maxValue = "";
        int maxItem = 0;
        for (String co : counting.keySet()) {
            if (maxItem <= counting.get(co)) {
                maxValue = co;
                maxItem = counting.get(co);
            }
        }
        return maxValue;
    }

    @RequiredArgsConstructor
    private enum DentalCondition {
        GREAT("Отличное"),
        VERY_GOOD("Очень хорошее"),
        GOOD("Хорошее"),
        MIDDLE("Среднее"),
        DONT_KNOW("Не знаю"),
        BAD("Плохое");

        private final String value;

        static DentalCondition getByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny().get();
        }

    }

    @RequiredArgsConstructor
    private enum ToothSensitivity {
        NEVER("Никогда"),
        DONT_KNOW("Не знаю"),
        RARELY("Редко"),
        SOMETIMES("Иногда"),
        OFTEN("Часто");

        private final String value;

        static ToothSensitivity getByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny().get();
        }
    }

    @RequiredArgsConstructor
    private enum VisitNumber {
        NEVER("Ни разу"),
        DONT_KNOW("Не помню"),
        ONE("Один раз"),
        TWO("Два раза"),
        THREE("Три раза"),
        FOUR("Четыре раза"),
        MORE("Более четырех");

        private final String value;

        static VisitNumber getByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny().get();
        }
    }

    @RequiredArgsConstructor
    private enum DentalOfficeWork {
        BAD("Неудовлетворительно"),
        OK("Удовлетворительно"),
        GOOD("Хорошо"),
        GREAT("Отлично"),
        SUPER("Безукоризненно");

        private final String value;

        static DentalOfficeWork getByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny().get();
        }
    }

    @RequiredArgsConstructor
    private enum BrushProperly {

        BAD("Нет, не умею"),
        OK("Безразлично"),
        GOOD("Не совсем уверен, что умею"),
        GREAT("Умею чистить зубы");

        private final String value;

        static BrushProperly getByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny().get();
        }
    }

    @RequiredArgsConstructor
    private enum HowYouBrush {

        BAD("Никогда"),
        OK("Один раз в неделю"),
        GOOD("Один раз в день"),
        GREAT("Несколько раз в день (утром/вечером)");

        private final String value;

        static HowYouBrush getByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny().get();
        }
    }

    @RequiredArgsConstructor
    private enum ToothbrushChange {

        BAD("затурдняются в ответе"),
        OK("один раз в двенадцать месяца"),
        GOOD("один раз в шесть месяца"),
        GOOD2("один раз в три месяца"),
        GOOD3("один раз в два месяца"),
        GOOD4("один раз в полтора месяца"),
        GOOD5("по мере износа, без учета времени"),
        GOOD6("один раз в месяц"),
        GREAT("чаще одного раза в месяц");

        private final String value;

        static ToothbrushChange getByValue(String value) {
            return Arrays.stream(values()).filter(v -> v.value.equals(value)).findAny().get();
        }
    }
}
