package edu.asoldatov.nir.service.statistics;

import edu.asoldatov.nir.dao.CadetRepository;
import edu.asoldatov.nir.dao.YearDentalConditionRepository;
import edu.asoldatov.nir.model.cadet.Cadet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AgeImporter {

    private final CadetRepository repository;
    private final YearDentalConditionRepository yearDentalConditionRepository;


    //@PostConstruct
    public void test3() {

        Map<Integer, List<Integer>> toothErosion = Map.of(
                0, new ArrayList<>(),
                1, new ArrayList<>()
        );

        Map<Integer, List<Integer>> dentalTrauma = Map.of(
                0, new ArrayList<>(),
                1, new ArrayList<>(),
                2, new ArrayList<>(),
                3, new ArrayList<>(),
                5, new ArrayList<>(),
                6, new ArrayList<>(),
                9, new ArrayList<>()
        );

        for (Cadet cadet : repository.findAll()) {
            yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition())
                    .stream()
                    .filter(v -> v.getTeeth().size() > 0)
                    .filter(v -> v.getYear().equals("2017-2018"))
                    .forEach(v -> {
                        int cpu = new CPU(v.getTeeth()).calculateCpu();
                        toothErosion.get(cadet.getToothErosion()).add(cpu);
                        dentalTrauma.get(cadet.getDentalTrauma()).add(cpu);
                    });
        }

        List<String> toothErosionResult = new ArrayList<>();


        for (Integer key : toothErosion.keySet()) {
            List<Integer> cpu = toothErosion.get(key);
            toothErosionResult.add(key + ";" + cpu.size() + ";" + (cpu.size() == 0 ? 0 : cpu.stream()
                    .mapToInt(v -> v)
                    .sum() * 1.0 / cpu.size()
            ) + ";");
        }
        save("toothErosionResult", toothErosionResult);

        List<String> dentalTraumaResult = new ArrayList<>();


        for (Integer key : dentalTrauma.keySet()) {
            List<Integer> cpu = dentalTrauma.get(key);
            dentalTraumaResult.add(key + ";" + cpu.size() + ";" + (cpu.size() == 0 ? 0 : cpu.stream()
                    .mapToInt(v -> v)
                    .sum() * 1.0 / cpu.size()
            ) + ";");
        }
        save("dentalTraumaResult", dentalTraumaResult);
    }

    //@PostConstruct
    public void age() {
        Map<String, List<Integer>> actions = Map.of(
                "Нет", new ArrayList<>(),
                "Да", new ArrayList<>()
        );

        Map<String, List<Integer>> actionsDetails = Map.of(
                "", new ArrayList<>(),
                "Проф. осмотры", new ArrayList<>(),
                "Уроки гигиены", new ArrayList<>()
        );

        Map<Integer, List<Integer>> healthGroup = Map.of(
                1, new ArrayList<>(),
                2, new ArrayList<>(),
                3, new ArrayList<>()
        );

        Map<Integer, List<Integer>> levelOfHygiene = Map.of(
                0, new ArrayList<>(),
                1, new ArrayList<>(),
                2, new ArrayList<>(),
                3, new ArrayList<>()
        );

        Map<String, List<Integer>> biteType = Map.of(
                "Ортогнатический", new ArrayList<>(),
                "Глубокий", new ArrayList<>(),
                "Прямой", new ArrayList<>(),
                "Дистальный", new ArrayList<>(),
                "Мезиальный", new ArrayList<>(),
                "Перекрестный", new ArrayList<>(),
                "Открытый", new ArrayList<>()
        );

        for (Cadet cadet : repository.findAll()) {
            yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition())
                    .stream()
                    .filter(v -> v.getTeeth().size() > 0)
                    .filter(v -> v.getYear().equals("2017-2018"))
                    .forEach(v -> {
                        int cpu = new CPU(v.getTeeth()).calculateCpu();
                        actions.get(cadet.getPreventiveActions()).add(cpu);
                        actionsDetails.get(cadet.getPreventiveActionsDetails()).add(cpu);
                        healthGroup.get(cadet.getHealthGroup()).add(cpu);
                        biteType.get(cadet.getBiteType()).add(cpu);
                        levelOfHygiene.get(cadet.getLevelOfHygiene()).add(cpu);
                    });
        }

        List<String> actionsResult = new ArrayList<>();


        for (String key : actions.keySet()) {
            List<Integer> cpu = actions.get(key);
            actionsResult.add(key + ";" + cpu.size() + ";" + (cpu.size() == 0 ? 0 : cpu.stream()
                    .mapToInt(v -> v)
                    .sum() * 1.0 / cpu.size()
            ) + ";");
        }
        save("actions", actionsResult);

        List<String> actionsDetailsResult = new ArrayList<>();


        for (String key : actionsDetails.keySet()) {
            List<Integer> cpu = actionsDetails.get(key);
            actionsDetailsResult.add(key + ";" + cpu.size() + ";" + (cpu.size() == 0 ? 0 : cpu.stream()
                    .mapToInt(v -> v)
                    .sum() * 1.0 / cpu.size()
            ) + ";");
        }
        save("actionsDetails", actionsDetailsResult);

        List<String> healthGroupResult = new ArrayList<>();

        for (Integer key : healthGroup.keySet()) {
            List<Integer> cpu = healthGroup.get(key);
            healthGroupResult.add(key + ";" + cpu.size() + ";" + (cpu.size() == 0 ? 0 : cpu.stream()
                    .mapToInt(v -> v)
                    .sum() * 1.0 / cpu.size()
            ) + ";");
        }
        save("healthGroup", healthGroupResult);

        List<String> levelOfHygieneResult = new ArrayList<>();

        for (Integer key : levelOfHygiene.keySet()) {
            List<Integer> cpu = levelOfHygiene.get(key);
            levelOfHygieneResult.add(key + ";" + cpu.size() + ";" + (cpu.size() == 0 ? 0 : cpu.stream()
                    .mapToInt(v -> v)
                    .sum() * 1.0 / cpu.size()
            ) + ";");
        }
        save("levelOfHygiene", levelOfHygieneResult);

        List<String> biteTypeResult = new ArrayList<>();

        for (String key : biteType.keySet()) {
            List<Integer> cpu = biteType.get(key);
            biteTypeResult.add(key + ";" + cpu.size() + ";" + (cpu.size() == 0 ? 0 : cpu.stream()
                    .mapToInt(v -> v)
                    .sum() * 1.0 / cpu.size()
            ) + ";");
        }
        save("biteType", biteTypeResult);
    }

    private static void save(String name, List<String> result) {
        try {
            Path path = Files.createFile(Path.of(String.format("/Users/asoldatov/ИТМО/%s.csv", name)));
            Files.write(path, result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
