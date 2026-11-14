package edu.asoldatov.nir.exporter;

import edu.asoldatov.nir.common.School;
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

import static edu.asoldatov.nir.common.School.*;

@Log4j2
@Component
@RequiredArgsConstructor
public class CPUbySchoolExporter {

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

    //@EventListener(ApplicationStartedEvent.class)
    void calculateCPU() {
        Map<School, Map<Integer, List<KPUkp>>> result = Map.of(
                TULASVU, new HashMap<>(),
                SPBKBK, new HashMap<>(),
                SPBNAHVMU, new HashMap<>(),
                SEVKAVSVU, new HashMap<>(),
                EKBSVU, new HashMap<>(),
                TUMENPKU, new HashMap<>(),
                KAZANSVU, new HashMap<>(),
                ULYANOVSKOESVU, new HashMap<>(),
                PETRAZAVODSKOESVU, new HashMap<>()
        );
        Map<Integer, List<KPUkp>> total = new HashMap<>();

        log.info("НАЧАЛО ПОДСЧЕТА");
        var cadets = repository.findAll();
        int totalCount = 3701;
        int currentCount = 0;
        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            for (YearDentalCondition condition : years) {
                if (!condition.getTeeth().isEmpty()) {
                    int calculatedAge = cadet.getAge() - YEARS.get(condition.getYear());
                    KPUkp kpUkp = new KPUkp(condition.getTeeth());
                    if (!result.get(cadet.getGroup().getSchool()).containsKey(calculatedAge)) {
                        result.get(cadet.getGroup().getSchool()).put(calculatedAge, new ArrayList<>());
                    }
                    result.get(cadet.getGroup().getSchool()).get(calculatedAge).add(kpUkp);

                    if (!total.containsKey(calculatedAge)) {
                        total.put(calculatedAge, new ArrayList<>());
                    }
                    total.get(calculatedAge).add(kpUkp);
                }
            }
            currentCount++;
            if (currentCount % 10 == 0) {
                log.info(currentCount * 100.0 / totalCount + " %");
            }
        }
        log.info("КОНЕЦ ПОДСЧЕТА");

        print(total, "Общий КПУкп");

        for (School school : result.keySet()) {
            print(result.get(school), school.getLabel());
        }
    }

    private double calculateAvg(List<Integer> values) {
        return values.stream().mapToInt(value -> value).average().orElse(0);
    }

    private void print(Map<Integer, List<KPUkp>> map, String label) {
        System.out.println(label);

        for (Integer age : map.keySet().stream().sorted().collect(Collectors.toList())) {
            print(age, map.get(age));
        }
        print(0, map.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));

        System.out.println();
        System.out.println();
    }

    private void print(int age, List<KPUkp> kpu) {
        System.out.println(
                age + "\t" +
                        f(calculateAvg(kpu.stream().map(v -> v.K).collect(Collectors.toList()))) + "\t" +
                        f(calculateAvg(kpu.stream().map(v -> v.P).collect(Collectors.toList()))) + "\t" +
                        f(calculateAvg(kpu.stream().map(v -> v.U).collect(Collectors.toList()))) + "\t" +
                        f(calculateAvg(kpu.stream().map(v -> v.k).collect(Collectors.toList()))) + "\t" +
                        f(calculateAvg(kpu.stream().map(v -> v.p).collect(Collectors.toList()))) + "\t" +
                        f(calculateAvg(kpu.stream().map(v -> v.K + v.P + v.U + v.k + v.p).collect(Collectors.toList()))) + "\t"
        );
    }

    private String f(double value) {
        return String.format(Locale.FRANCE, "%,.3f", value);
    }
}
