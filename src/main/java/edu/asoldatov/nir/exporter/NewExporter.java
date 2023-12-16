package edu.asoldatov.nir.exporter;

import edu.asoldatov.nir.dao.CadetRepository;
import edu.asoldatov.nir.dao.YearDentalConditionRepository;
import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.YearDentalCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class NewExporter {

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

    private final Set<Integer> sicketTeeth = Set.of(
            1, 2, 3, 4, 5, 6, 7
    );

    //@EventListener(ApplicationStartedEvent.class)
    void calculateTotalCount() {
        Map<String, Map<Integer, Integer>> result = Map.of(
                "2017-2018", new HashMap<>(),
                "2016-2017", new HashMap<>(),
                "2015-2016", new HashMap<>(),
                "2014-2015", new HashMap<>(),
                "2013-2014", new HashMap<>(),
                "2012-2013", new HashMap<>(),
                "2011-2012", new HashMap<>()
        );
        Map<String, Map<Integer, Integer>> sicked = Map.of(
                "2017-2018", new HashMap<>(),
                "2016-2017", new HashMap<>(),
                "2015-2016", new HashMap<>(),
                "2014-2015", new HashMap<>(),
                "2013-2014", new HashMap<>(),
                "2012-2013", new HashMap<>(),
                "2011-2012", new HashMap<>()
        );
        log.info("НАЧАЛО ПОДСЧЕТА");
        var cadets = repository.findAll();
        int totalCount = 3701;
        int currentCount = 0;
        for (Cadet cadet : cadets) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition());
            for (YearDentalCondition condition : years) {
                if (!condition.getTeeth().isEmpty()) {
                    int calculatedAge = cadet.getAge() - YEARS.get(condition.getYear());
                    result.get(condition.getYear()).merge(calculatedAge, 1, Integer::sum);
                    if (isSick(condition)) {
                        sicked.get(condition.getYear()).merge(calculatedAge, 1, Integer::sum);
                    }
                }
            }
            currentCount++;
            if (currentCount % 10 == 0) {
                log.info(currentCount * 100.0 / totalCount + " %");
            }
        }
        log.info("КОНЕЦ ПОДСЧЕТА");
        printResult(result, "Общее количество");
        printResult(sicked, "Пораженные");
    }


    private void printResult(Map<String, Map<Integer, Integer>> result, String label) {
        System.out.println(label);
        for (String year : result.keySet().stream().sorted().collect(Collectors.toList())) {
            Map<Integer, Integer> report = result.get(year);
            for (Integer age : report.keySet().stream().sorted().collect(Collectors.toList())) {
                System.out.println(age + "\t" + report.get(age) + "\t" + year);
            }
        }
        System.out.println();
        System.out.println();
    }

    private boolean isSick(YearDentalCondition condition) {
        return condition.getTeeth().stream().anyMatch(tooth -> sicketTeeth.contains(tooth.getValue()));
    }
}

