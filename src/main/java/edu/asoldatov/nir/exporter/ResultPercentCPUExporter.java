package edu.asoldatov.nir.exporter;

import edu.asoldatov.nir.common.Bite;
import edu.asoldatov.nir.dao.CadetRepository;
import edu.asoldatov.nir.dao.YearDentalConditionRepository;
import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.Tooth;
import edu.asoldatov.nir.model.cadet.YearDentalCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class ResultPercentCPUExporter {

    private final CadetRepository repository;
    private final YearDentalConditionRepository yearDentalConditionRepository;

    private final static Map<String, Integer> YEARS = Map.of(
            "2017-2018", 0,
            "2016-2017", 1,
            "2015-2016", 2,
            "2014-2015", 3,
            "2013-2014", 4,
            "2012-2013", 5,
            "2011-2012", 6
    );

    private class KPUkp {
        int K;
        int P;
        int U;
        int k;
        int p;

        KPUkp(List<Tooth> teeth) {
            for (Tooth tooth : teeth) {
                if (isTemp(tooth)) {
                    if (tooth.getValue() == 1 || tooth.getValue() == 2) {
                        ++k;
                    } else if (tooth.getValue() == 3) {
                        ++p;
                    }
                } else {
                    if (tooth.getValue() == 1 || tooth.getValue() == 2) {
                        ++K;
                    } else if (tooth.getValue() == 3) {
                        ++P;
                    } else if (tooth.getValue() == 4 || tooth.getValue() == 5 ||
                            tooth.getValue() == 6 || tooth.getValue() == 7) {
                        ++U;
                    }
                }
            }
        }

        private boolean isTemp(Tooth tooth) {
            return tooth.getNumber() > 47;
        }
    }

    /**
     * Выгрузка процента пораженных зубов по возрастам
     */
    //@EventListener(ApplicationStartedEvent.class)
    void calculateCPUpercent() {
        Map<Integer, Map<Integer, List<Tooth>>> toothByAge = new HashMap<>();

        log.info("НАЧАЛО ПОДСЧЕТА");
        var cadets = (List<Cadet>) repository.findAll();

        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            for (YearDentalCondition condition : years) {
                if (!condition.getTeeth().isEmpty()) {
                    int calculatedAge = cadet.getAge() - YEARS.get(condition.getYear());
                    if (!toothByAge.containsKey(calculatedAge)) {
                        toothByAge.put(calculatedAge, new HashMap<>());
                    }
                    Map<Integer, List<Tooth>> map = toothByAge.get(calculatedAge);
                    for (Tooth tooth : condition.getTeeth()) {
                        int number = tooth.getNumber() > 50 ? tooth.getNumber() - 40 : tooth.getNumber();
                        if (!map.containsKey(number)) {
                            map.put(number, new ArrayList<>());
                        }
                        map.get(number).add(tooth);
                    }
                }
            }

        }
        log.info("КОНЕЦ АГРЕГАЦИИ");

        for (int tooth : List.of(17, 16, 15, 14, 13, 12, 11, 21, 22, 23, 24, 25, 26, 27, 37, 36, 35, 34, 33, 32, 31, 41, 42, 43, 44, 45, 46, 47)) {
            StringBuilder builder = new StringBuilder();
            for (int age : List.of(10, 11, 12, 13, 14, 15, 16, 17, 18)) {
                Map<Integer, List<Tooth>> map = toothByAge.getOrDefault(age, Map.of());
                builder.append(format(tooth, map.getOrDefault(tooth, List.of())));
            }
            System.out.println(tooth / 10 + "." + tooth % 10 + "\t" + builder);
        }
    }


    /**
     * Выгрузка процента пораженных зубов
     */
    //@EventListener(ApplicationStartedEvent.class)
    void calculateCPUpercentAll() {
        Map<Integer, List<Tooth>> map = new HashMap<>();

        log.info("НАЧАЛО ПОДСЧЕТА");
        var cadets = (List<Cadet>) repository.findAll();

        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            for (YearDentalCondition condition : years) {
                if (!condition.getTeeth().isEmpty()) {
                    for (Tooth tooth : condition.getTeeth()) {
                        int number = tooth.getNumber() > 50 ? tooth.getNumber() - 40 : tooth.getNumber();
                        if (!map.containsKey(number)) {
                            map.put(number, new ArrayList<>());
                        }
                        map.get(number).add(tooth);
                    }
                }
            }

        }
        log.info("КОНЕЦ АГРЕГАЦИИ");

        for (int tooth : List.of(17, 16, 15, 14, 13, 12, 11, 21, 22, 23, 24, 25, 26, 27, 37, 36, 35, 34, 33, 32, 31, 41, 42, 43, 44, 45, 46, 47)) {
            System.out.println(tooth / 10 + "." + tooth % 10 + "\t" + format(tooth, map.getOrDefault(tooth, List.of())));
        }
    }

    private String format(int tooth, List<Tooth> teeth) {
        KPUkp kpUkp = new KPUkp(teeth);
        return f(kpUkp.K * 100.0 / teeth.size()) + "\t" +
                f(kpUkp.P * 100.0 / teeth.size()) + "\t" +
                f(kpUkp.U * 100.0 / teeth.size()) + "\t" +
                f(kpUkp.k * 100.0 / teeth.size()) + "\t" +
                f(kpUkp.p * 100.0 / teeth.size()) + "\t" +
                f((kpUkp.K + kpUkp.P + kpUkp.U + kpUkp.k + kpUkp.p) * 100.0 / teeth.size()) + "\t";
    }

    private String f(double value) {
        return String.format(Locale.FRANCE, "%,.2f", value);
    }

    /**
     * Выгрузка всех записей КПУ по возрастам
     */
    // @EventListener(ApplicationStartedEvent.class)
    void calculateCPUbyAge() {
        Map<Integer, List<KPUkp>> total = new HashMap<>();

        var cadets = repository.findAll();

        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            for (YearDentalCondition condition : years) {
                if (!condition.getTeeth().isEmpty()) {
                    int calculatedAge = cadet.getAge() - YEARS.get(condition.getYear());
                    KPUkp kpUkp = new KPUkp(condition.getTeeth());
                    if (!total.containsKey(calculatedAge)) {
                        total.put(calculatedAge, new ArrayList<>());
                    }
                    total.get(calculatedAge).add(kpUkp);
                }
            }
        }
        for (int age : List.of(10, 11, 12, 13, 14, 15, 16, 17, 18)) {
            System.out.println(age);
            for (KPUkp kpUkp : total.get(age)) {
                System.out.println(
                        kpUkp.K + "\t" +
                                kpUkp.P + "\t" +
                                kpUkp.U + "\t" +
                                kpUkp.k + "\t" +
                                kpUkp.p + "\t" +
                                (kpUkp.K + kpUkp.P + kpUkp.U + kpUkp.k + kpUkp.p) + "\t"
                );
            }
        }
    }

    /**
     * Выгрузка средних значений КПУкп по типам прикуса
     */
    //@EventListener(ApplicationStartedEvent.class)
    void calculateAverageCPUbyByteType() {
        Map<Bite, List<KPUkp>> total = new HashMap<>();

        var cadets = repository.findAll();

        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            years.stream()
                    .filter(v -> v.getYear().equals("2017-2018"))
                    .findAny()
                    .ifPresent(yearDentalCondition -> {
                        KPUkp kpUkp = new KPUkp(yearDentalCondition.getTeeth());
                        if (!total.containsKey(cadet.getBite())) {
                            total.put(cadet.getBite(), new ArrayList<>());
                        }
                        total.get(cadet.getBite()).add(kpUkp);
                    });
        }
        for (Bite bite : total.keySet()) {
            List<Integer> kpus = total.get(bite).stream().map(kpu -> kpu.K + kpu.P + kpu.U + kpu.k + kpu.p).collect(Collectors.toList());
            int count = kpus.size();
            double kpu = kpus.stream().mapToInt(v -> v).average().orElse(0D);
            double sko = Math.pow(
                    kpus.stream()
                            .mapToDouble(v -> Math.pow((v - kpu), 2))
                            .sum() / count,
                    0.5
            );
            System.out.println(
                    bite.getLabel() + "\t" +
                            f(count) + "\t" +
                            f(kpu) + "\t" +
                            f(sko) + "\t"
            );
        }
    }

    /**
     * Выгрузка средних значений КПУкп по группам здоровья
     */
    //@EventListener(ApplicationStartedEvent.class)
    void calculateAverageCPUbyHealthGroup() {
        Map<Integer, List<KPUkp>> total = new HashMap<>();

        var cadets = repository.findAll();

        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            years.stream()
                    .filter(v -> v.getYear().equals("2017-2018"))
                    .findAny()
                    .ifPresent(yearDentalCondition -> {
                        KPUkp kpUkp = new KPUkp(yearDentalCondition.getTeeth());
                        if (!total.containsKey(cadet.getHealthGroup())) {
                            total.put(cadet.getHealthGroup(), new ArrayList<>());
                        }
                        total.get(cadet.getHealthGroup()).add(kpUkp);
                    });
        }
        for (Integer healthGroup : total.keySet()) {
            List<Integer> kpus = total.get(healthGroup).stream().map(kpu -> kpu.K + kpu.P + kpu.U + kpu.k + kpu.p).collect(Collectors.toList());
            int count = kpus.size();
            double kpu = kpus.stream().mapToInt(v -> v).average().orElse(0D);
            double sko = Math.pow(
                    kpus.stream()
                            .mapToDouble(v -> Math.pow((v - kpu), 2))
                            .sum() / count,
                    0.5
            );
            System.out.println(
                    healthGroup + "\t" +
                            count + "\t" +
                            f(kpu) + "\t" +
                            f(sko) + "\t"
            );
        }
    }

    /**
     * Выгрузка средних значений КПУ за проф мероприятия
     */
    //@EventListener(ApplicationStartedEvent.class)
    void calculateAverageCPUbyPreventiveActions() {
        Map<String, List<KPUkp>> total = new HashMap<>();
        Map<String, List<KPUkp>> details = new HashMap<>();

        var cadets = repository.findAll();

        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            years.stream()
                    .filter(v -> v.getYear().equals("2017-2018"))
                    .findAny()
                    .ifPresent(yearDentalCondition -> {
                        KPUkp kpUkp = new KPUkp(yearDentalCondition.getTeeth());
                        if (!total.containsKey(cadet.getPreventiveActions())) {
                            total.put(cadet.getPreventiveActions(), new ArrayList<>());
                        }
                        total.get(cadet.getPreventiveActions()).add(kpUkp);

                        if (cadet.getPreventiveActions().equals("Да")) {
                            if (!details.containsKey(cadet.getPreventiveActionsDetails())) {
                                details.put(cadet.getPreventiveActionsDetails(), new ArrayList<>());
                            }
                            details.get(cadet.getPreventiveActionsDetails()).add(kpUkp);
                        }
                    });
        }
        for (String preventiveActions : total.keySet()) {
            List<Integer> kpus = total
                    .get(preventiveActions)
                    .stream()
                    .map(kpu -> kpu.K + kpu.P + kpu.U + kpu.k + kpu.p)
                    .collect(Collectors.toList());
            int count = kpus.size();
            double kpu = kpus.stream().mapToInt(v -> v).average().orElse(0D);
            double sko = Math.pow(
                    kpus.stream()
                            .mapToDouble(v -> Math.pow((v - kpu), 2))
                            .sum() / count,
                    0.5
            );
            System.out.println(
                    preventiveActions + "\t" +
                            count + "\t" +
                            f(kpu) + "\t" +
                            f(sko) + "\t"
            );
        }

        for (String preventiveActionsDetails : details.keySet()) {
            List<Integer> kpus = details
                    .get(preventiveActionsDetails)
                    .stream()
                    .map(kpu -> kpu.K + kpu.P + kpu.U + kpu.k + kpu.p)
                    .collect(Collectors.toList());
            int count = kpus.size();
            double kpu = kpus.stream().mapToInt(v -> v).average().orElse(0D);
            double sko = Math.pow(
                    kpus.stream()
                            .mapToDouble(v -> Math.pow((v - kpu), 2))
                            .sum() / count,
                    0.5
            );
            System.out.println(
                    preventiveActionsDetails + "\t" +
                            count + "\t" +
                            f(kpu) + "\t" +
                            f(sko) + "\t"
            );
        }
    }

    /**
     * Выгрузка средних значений КПУкп по группам здоровья
     */
    //@EventListener(ApplicationStartedEvent.class)
    void calculateAverageCPUbyLevelOfHygiene() {
        Map<Integer, List<KPUkp>> total = new HashMap<>();

        var cadets = repository.findAll();

        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            years.stream()
                    .filter(v -> v.getYear().equals("2017-2018"))
                    .findAny()
                    .ifPresent(yearDentalCondition -> {
                        KPUkp kpUkp = new KPUkp(yearDentalCondition.getTeeth());
                        if (!total.containsKey(cadet.getLevelOfHygiene())) {
                            total.put(cadet.getLevelOfHygiene(), new ArrayList<>());
                        }
                        total.get(cadet.getLevelOfHygiene()).add(kpUkp);
                    });
        }
        for (Integer levelOfHygiene : total.keySet()) {
            List<Integer> kpus = total.get(levelOfHygiene).stream().map(kpu -> kpu.K + kpu.P + kpu.U + kpu.k + kpu.p).collect(Collectors.toList());
            int count = kpus.size();
            double kpu = kpus.stream().mapToInt(v -> v).average().orElse(0D);
            double sko = Math.pow(
                    kpus.stream()
                            .mapToDouble(v -> Math.pow((v - kpu), 2))
                            .sum() / count,
                    0.5
            );
            System.out.println(
                    levelOfHygiene + "\t" +
                            count + "\t" +
                            f(kpu) + "\t" +
                            f(sko) + "\t"
            );
        }
    }
}
