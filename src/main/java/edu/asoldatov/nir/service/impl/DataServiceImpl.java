package edu.asoldatov.nir.service.impl;

import edu.asoldatov.nir.common.Years;
import edu.asoldatov.nir.dao.CadetRepository;
import edu.asoldatov.nir.dao.DentalConditionRepository;
import edu.asoldatov.nir.dao.QuestionnaireCadetRepository;
import edu.asoldatov.nir.dao.YearDentalConditionRepository;
import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.DentalCondition;
import edu.asoldatov.nir.model.cadet.Tooth;
import edu.asoldatov.nir.model.cadet.YearDentalCondition;
import edu.asoldatov.nir.model.questionnaire.QuestionnaireCadet;
import edu.asoldatov.nir.service.DataService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class DataServiceImpl implements DataService {

    private final CadetRepository cadetRepository;
    private final QuestionnaireCadetRepository questionnaireCadetRepository;
    private final DentalConditionRepository dentalConditionRepository;
    private final YearDentalConditionRepository yearDentalConditionRepository;

    private final Map<String, Integer> dentalConditions = Map.of(
            "Хорошее", 3,
            "Очень хорошее", 4,
            "Среднее", 2,
            "Отличное", 5,
            "Плохое", 1,
            "Не знаю", 0
    );

    private final Map<String, Integer> toothSensitivity = Map.of(
            "Редко", 2,
            "Никогда", 1,
            "Иногда", 3,
            "Не знаю", 0,
            "Часто", 4
    );

    private final Map<String, Integer> visitNumbers = Map.of(
            "Один раз", 1,
            "Два раза", 2,
            "Ни разу", 0,
            "Три раза", 3,
            "Не помню", 0,
            "Более четырех", 5,
            "Четыре раза", 4
    );

    public Map<Integer, String> prepareSociology() {
        List<QuestionnaireCadet> questionnaireCadets = new ArrayList<>();
        for (QuestionnaireCadet qc : questionnaireCadetRepository.findAll()) {
            questionnaireCadets.add(qc);
        }

        Map<Integer, String> dataByAge = new HashMap<>();

        for (int i = 10; i <= 18; i++) {
            dataByAge.put(i, getSociologyByAge(questionnaireCadets, i));
        }
        return dataByAge;
    }

    //"dental_condition;is_hide_smile;is_missed_class;tooth_sensitivity;visit_number;brush_properly;how_you_brush;toothpick;wooden_stick;floss;thread;irrigator;interdental_brush"
    private String getSociologyByAge(List<QuestionnaireCadet> questionnaireCadets, final int age) {
        questionnaireCadets = questionnaireCadets.stream().filter(v -> v.getAge() == age).collect(Collectors.toList());
        StringBuilder result = new StringBuilder();
        int dc = (int) (questionnaireCadets.stream()
                .mapToInt(v -> dentalConditions.get(v.getDentalCondition()))
                .average()
                .orElse(0) * 100);
        result.append(dc).append(";");

        int isHideSmile = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.isHideSmile() ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(isHideSmile).append(";");

        int isMissedClass = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.isMissedClass() ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(isMissedClass).append(";");

        int toothSen = (int) (questionnaireCadets.stream()
                .mapToInt(v -> toothSensitivity.get(v.getToothSensitivity()))
                .average()
                .orElse(0) * 100);
        result.append(toothSen).append(";");

        int visitNumber = (int) (questionnaireCadets.stream()
                .mapToInt(v -> visitNumbers.get(v.getVisitNumber()))
                .average()
                .orElse(0) * 100);
        result.append(visitNumber).append(";");

        int brushProperly = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.getBrushProperly().equals("Умею чистить зубы") ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(brushProperly).append(";");

        int howYouBrush = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.getHowYouBrush().equals("Несколько раз в день (утром/вечером)") ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(howYouBrush).append(";");

        int toothpick = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.isToothpick() ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(toothpick).append(";");

        int woodenStick = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.isWoodenStick() ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(woodenStick).append(";");

        int floss = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.isFloss() ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(floss).append(";");

        int thread = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.isThread() ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(thread).append(";");

        int irrigator = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.isIrrigator() ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(irrigator).append(";");

        int interdentalBrush = (int) (questionnaireCadets.stream()
                .mapToInt(v -> v.isInterdentalBrush() ? 1 : 0)
                .average()
                .orElse(0) * 100);
        result.append(interdentalBrush).append(";");

        return result.toString();
    }


    /*@EventListener(ApplicationReadyEvent.class)
    @Override*/
    public void build() {
        int emptyYears = 0;
        int oneYear = 0;
        int success = 0;

        Iterable<Cadet> cadets = cadetRepository.findAll();
        Map<Integer, String> sociology = prepareSociology();

        List<String> result = new ArrayList<>();

        result.add("age;nationality;place_of_birth;previous_place_of_living;school;bite_type;health_group;sum_hygiene_index;" +
                "tooth_erosion;tooth_erosion_count;dental_trauma;dental_trauma_count;" +
                "num_intact;num_caries;num_filling_with_caries;num_filling_without_caries;num_removal_caries;num_removal_other_reason;num_sealed_fissure;num_veneer;num_impacted;num_not_registered;" +
                "num_intact_temp;num_caries_temp;num_filling_with_caries_temp;num_filling_without_caries_temp;num_removal_caries_temp;num_sealed_fissure_temp;num_veneer_temp;" +
                "joint_symptoms;joint_clicking;joint_soreness;joint_limitation;" +
                "emergency_help;dental_condition;is_hide_smile;is_missed_class;tooth_sensitivity;visit_number;brush_properly;how_you_brush;toothpick;wooden_stick;floss;thread;irrigator;interdental_brush;" +
                "cpu;cpu_next;delta_cpu");

        for (Cadet cadet : cadets) {
            DentalCondition dentalCondition = dentalConditionRepository.findByCadet(cadet);

            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(dentalCondition)
                    .stream()
                    .filter(v -> v.getTeeth().size() != 0)
                    .sorted(Comparator.comparing(YearDentalCondition::getYear))
                    .collect(Collectors.toList());

            if (years.size() == 0) {
                emptyYears++;
            } else if (years.size() == 1) {
                oneYear++;
            } else {
                for (int i = 0; i < years.size() - 1; i++) {
                    YearDentalCondition current = years.get(i);
                    YearDentalCondition next = years.get(i + 1);

                    int age = Years.getByLabel(current.getYear()).calculateAgeByYear(cadet.getAge());

                    if (age >= 10 && age <= 18) {

                        CPU currentCpu = new CPU(current.getTeeth());
                        CPU nextCpu = new CPU(next.getTeeth());

                        int deltaCpu = nextCpu.calculateCpu() - currentCpu.calculateCpu();
                        if (deltaCpu <= 6 && deltaCpu >= 0) {
                            result.add(
                                    age + ";" + cadet.getEthnicGroup() + ";" + cadet.getPlaceOfBirth() + ";" + cadet.getRegionOfResidence() + ";" + cadet.getGroup().getSchool().getLabel() + ";" + cadet.getBite().name() + ";" + cadet.getHealthGroup() + ";" + cadet.getLevelOfHygiene() + ";" +
                                            cadet.getToothErosion() + ";" + cadet.getToothErosionCount() + ";" + cadet.getDentalTrauma() + ";" + cadet.getDentalTraumaCount() + ";" +
                                            currentCpu.intact + ";" + currentCpu.caries + ";" + currentCpu.fillingWithCaries + ";" + currentCpu.fillingWithoutCaries + ";" + currentCpu.removalCaries + ";" + currentCpu.removalOtherReason + ";" + currentCpu.sealedFissure + ";" + currentCpu.veneer + ";" + currentCpu.impacted + ";" + currentCpu.notRegistered + ";" +
                                            currentCpu.intactTemp + ";" + currentCpu.cariesTemp + ";" + currentCpu.fillingWithCariesTemp + ";" + currentCpu.fillingWithoutCariesTemp + ";" + currentCpu.removalCariesTemp + ";" + currentCpu.sealedFissureTemp + ";" + currentCpu.veneerTemp + ";" +
                                            cadet.getTemporomandibularJoint().getSymptoms() + ";" + cadet.getTemporomandibularJoint().getClicking() + ";" + cadet.getTemporomandibularJoint().getSoreness() + ";" + cadet.getTemporomandibularJoint().getLimitationOfJawMovement() + ";" +
                                            cadet.getEmergencyHelp() + ";" + sociology.get(age) +
                                            currentCpu.calculateCpu() + ";" + nextCpu.calculateCpu() + ";" + deltaCpu
                            );
                            success++;
                        }
                    }
                }
            }
        }

        log.info("Success import [{}], empty years [{}], one year [{}].", success, emptyYears, oneYear);
        //Success import [2460], empty years [20], one year [1221].

        try {
            Path path = Files.createFile(Path.of("/Users/asoldatov/ИТМО/cpu_FOR_ESTIMATE.csv"));
            Files.write(path, result);
        } catch (IOException e) {
            log.error("Something went wrong");
            throw new RuntimeException(e);
        }
    }

    //@EventListener(ApplicationReadyEvent.class)
    public void kkffk() {
        List<Long> ids = new ArrayList<>();
        ids.addAll(List.of(
                2515926L, 2518800L, 2516221L, 2516572L, 2517859L, 2516923L, 2516338L, 2515281L, 2517976L, 2515692L
        ));
        ids.addAll(List.of(
                2405536L, 2394999L, 2394027L, 2399368L, 2396798L, 2404251L, 2401167L, 2398340L, 2398083L, 2404765L
        ));
        ids.addAll(List.of(
                2400910L, 2398854L, 2397312L, 2394541L, 2394284L, 2399882L, 2401681L, 2403737L, 2405793L, 2406821L
        ));
        ids.addAll(List.of(
                2393513L, 2395256L, 2405022L, 2407335L, 2393256L, 2395513L, 2406050L, 2406564L, 2401938L, 2403480L
        ));
        ids.addAll(List.of(
                2396284L, 2400139L, 2402709L, 2406307L, 2392999L, 2395770L, 2396027L, 2400396L, 2402195L, 2403223L
        ));

        cadetRepository.findAllById(ids).forEach(v-> System.out.println(v.getFullName()));



        /*int id = 1;
        int cID = 1;
        for (Cadet cadet : cadets) {
            DentalCondition dentalCondition = dentalConditionRepository.findByCadet(cadet);

            List<YearDentalCondition> r = yearDentalConditionRepository.findAllByDentalCondition(dentalCondition)
                    .stream()
                    .filter(v -> v.getTeeth().size() != 0)
                    .sorted(Comparator.comparing(YearDentalCondition::getYear)).collect(Collectors.toList());

            for (YearDentalCondition year : r) {
                int cpu = new CPU(year.getTeeth()).calculateCpu();
                System.out.println(String.format("(%s, %s, 'В %s учебном году индекс КПУ составил: %s'),", cID++, id, year.getYear(), cpu));
            }
            id++;
        }*/

    }

    //@EventListener(ApplicationReadyEvent.class)
    public void build2() {
        int emptyYears = 0;
        int oneYear = 0;
        int success = 0;

        List<Long> ids = new ArrayList<>();
        ids.addAll(List.of(
                2515926L, 2518800L, 2516221L, 2516572L, 2517859L, 2516923L, 2516338L, 2515281L, 2517976L, 2515692L
        ));
        ids.addAll(List.of(
                2405536L, 2394999L, 2394027L, 2399368L, 2396798L, 2404251L, 2401167L, 2398340L, 2398083L, 2404765L
        ));
        ids.addAll(List.of(
                2400910L, 2398854L, 2397312L, 2394541L, 2394284L, 2399882L, 2401681L, 2403737L, 2405793L, 2406821L
        ));
        ids.addAll(List.of(
                2393513L, 2395256L, 2405022L, 2407335L, 2393256L, 2395513L, 2406050L, 2406564L, 2401938L, 2403480L
        ));
        ids.addAll(List.of(
                2396284L, 2400139L, 2402709L, 2406307L, 2392999L, 2395770L, 2396027L, 2400396L, 2402195L, 2403223L
        ));
        Iterable<Cadet> cadets = cadetRepository.findAllById(ids);

        Map<Integer, String> sociology = prepareSociology();

        List<String> result = new ArrayList<>();

        result.add("age;nationality;place_of_birth;previous_place_of_living;school;bite_type;health_group;sum_hygiene_index;" +
                "tooth_erosion;tooth_erosion_count;dental_trauma;dental_trauma_count;" +
                "num_intact;num_caries;num_filling_with_caries;num_filling_without_caries;num_removal_caries;num_removal_other_reason;num_sealed_fissure;num_veneer;num_impacted;num_not_registered;" +
                "num_intact_temp;num_caries_temp;num_filling_with_caries_temp;num_filling_without_caries_temp;num_removal_caries_temp;num_sealed_fissure_temp;num_veneer_temp;" +
                "joint_symptoms;joint_clicking;joint_soreness;joint_limitation;" +
                "emergency_help;dental_condition;is_hide_smile;is_missed_class;tooth_sensitivity;visit_number;brush_properly;how_you_brush;toothpick;wooden_stick;floss;thread;irrigator;interdental_brush;" +
                "cpu;cpu_next;delta_cpu");

        for (Cadet cadet : cadets) {
            DentalCondition dentalCondition = dentalConditionRepository.findByCadet(cadet);

            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(dentalCondition)
                    .stream()
                    .filter(v -> v.getYear().equals("2017-2018"))
                    .collect(Collectors.toList());

            for (int i = 0; i < years.size(); i++) {
                YearDentalCondition current = years.get(i);

                int age = Years.getByLabel(current.getYear()).calculateAgeByYear(cadet.getAge());

                CPU currentCpu = new CPU(current.getTeeth());

                result.add(
                        age + ";" + cadet.getEthnicGroup() + ";" + cadet.getPlaceOfBirth() + ";" + cadet.getRegionOfResidence() + ";" + cadet.getGroup().getSchool().getLabel() + ";" + cadet.getBite().name() + ";" + cadet.getHealthGroup() + ";" + cadet.getLevelOfHygiene() + ";" +
                                cadet.getToothErosion() + ";" + cadet.getToothErosionCount() + ";" + cadet.getDentalTrauma() + ";" + cadet.getDentalTraumaCount() + ";" +
                                currentCpu.intact + ";" + currentCpu.caries + ";" + currentCpu.fillingWithCaries + ";" + currentCpu.fillingWithoutCaries + ";" + currentCpu.removalCaries + ";" + currentCpu.removalOtherReason + ";" + currentCpu.sealedFissure + ";" + currentCpu.veneer + ";" + currentCpu.impacted + ";" + currentCpu.notRegistered + ";" +
                                currentCpu.intactTemp + ";" + currentCpu.cariesTemp + ";" + currentCpu.fillingWithCariesTemp + ";" + currentCpu.fillingWithoutCariesTemp + ";" + currentCpu.removalCariesTemp + ";" + currentCpu.sealedFissureTemp + ";" + currentCpu.veneerTemp + ";" +
                                cadet.getTemporomandibularJoint().getSymptoms() + ";" + cadet.getTemporomandibularJoint().getClicking() + ";" + cadet.getTemporomandibularJoint().getSoreness() + ";" + cadet.getTemporomandibularJoint().getLimitationOfJawMovement() + ";" +
                                cadet.getEmergencyHelp() + ";" + sociology.get(age) +
                                currentCpu.calculateCpu() + ";" + currentCpu.calculateCpu() + ";" + 0
                );
            }
        }

        log.info("Success import [{}], empty years [{}], one year [{}].", success, emptyYears, oneYear);
        //Success import [2460], empty years [20], one year [1221].

        try {
            Path path = Files.createFile(Path.of("/Users/asoldatov/ИТМО/cpu_FOR_ESTIMATE.csv"));
            Files.write(path, result);
        } catch (IOException e) {
            log.error("Something went wrong");
            throw new RuntimeException(e);
        }
    }

    @Getter
    private static class CPU {
        private int intact;
        private int caries;
        private int fillingWithCaries;
        private int fillingWithoutCaries;
        private int removalCaries;
        private int removalOtherReason;
        private int sealedFissure;
        private int veneer;
        private int impacted;
        private int notRegistered;
        private int intactTemp;
        private int cariesTemp;
        private int fillingWithCariesTemp;
        private int fillingWithoutCariesTemp;
        private int removalCariesTemp;
        private int sealedFissureTemp;
        private int veneerTemp;

        //А		=		0 = интактный
        //
        //B		=		1 = кариес
        //
        //C		=		2 = пломба,с кариесом
        //
        //D		=		3 = пломба, без кариеса
        //
        //E		=		4 = удаление из-за осложнений кариеса
        //
        //–		=		5 = удаление по другим причинам
        //
        //F		=		6 = герметизированная фиссура
        //
        //G		=		7 = опорный зуб мостовидн. протез/коронка, винир
        //
        //–		=		8 = непрорезавшийся зуб
        //
        //–		=		9 = не регистрируется

        CPU(List<Tooth> teeth) {
            for (Tooth tooth : teeth) {
                if (!isTemp(tooth)) {
                    switch (tooth.getValue()) {
                        case 0: {
                            intact++;
                            break;
                        }
                        case 1: {
                            caries++;
                            break;
                        }
                        case 2: {
                            fillingWithCaries++;
                            break;
                        }
                        case 3: {
                            fillingWithoutCaries++;
                            break;
                        }
                        case 4: {
                            removalCaries++;
                            break;
                        }
                        case 5: {
                            removalOtherReason++;
                            break;
                        }
                        case 6: {
                            sealedFissure++;
                            break;
                        }
                        case 7: {
                            veneer++;
                            break;
                        }
                        case 8: {
                            impacted++;
                            break;
                        }
                        case 9: {
                            notRegistered++;
                            break;
                        }
                    }
                } else {
                    switch (tooth.getValue()) {
                        case 0: {
                            intactTemp++;
                            break;
                        }
                        case 1: {
                            cariesTemp++;
                            break;
                        }
                        case 2: {
                            fillingWithCariesTemp++;
                            break;
                        }
                        case 3: {
                            fillingWithoutCariesTemp++;
                            break;
                        }
                        case 4: {
                            removalCariesTemp++;
                            break;
                        }
                        case 6: {
                            sealedFissureTemp++;
                            break;
                        }
                        case 7: {
                            veneerTemp++;
                            break;
                        }
                    }
                }
            }

        }

        //КПУ=К(1,2)+П(3,6)+У(4,5,7)+кп=к(B,C)+п(D,F)
        public int calculateCpu() {
            return (caries + fillingWithCaries) +
                    (fillingWithoutCaries + sealedFissure) +
                    (removalCaries + removalOtherReason + veneer) /*+

                    (cariesTemp + fillingWithCariesTemp) +
                    (fillingWithoutCariesTemp + sealedFissureTemp)*/;
        }

        private boolean isTemp(Tooth tooth) {
            return tooth.getNumber() > 47;
        }
    }
}
