package edu.asoldatov.nir.service;

import edu.asoldatov.nir.common.School;
import edu.asoldatov.nir.dao.CadetRepository;
import edu.asoldatov.nir.dao.YearDentalConditionRepository;
import edu.asoldatov.nir.model.cadet.Cadet;
import edu.asoldatov.nir.model.cadet.Tooth;
import edu.asoldatov.nir.model.cadet.YearDentalCondition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CadetExporter {

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

    private final static List<List<Integer>> NUMBERS = List.of(
            List.of(17),
            List.of(16),
            List.of(15, 55),
            List.of(14, 54),
            List.of(13, 53),
            List.of(12, 52),
            List.of(11, 51),
            List.of(21, 61),
            List.of(22, 62),
            List.of(23, 63),
            List.of(24, 64),
            List.of(25, 65),
            List.of(26),
            List.of(27),
            List.of(37),
            List.of(36),
            List.of(35, 75),
            List.of(34, 74),
            List.of(33, 73),
            List.of(32, 72),
            List.of(31, 71),
            List.of(41, 81),
            List.of(42, 82),
            List.of(43, 83),
            List.of(44, 84),
            List.of(45, 85),
            List.of(46),
            List.of(47)
    );

    //@PostConstruct
    public void exportKPU_2() {
        List<TempCadet> cadets = new ArrayList<>();

        for (Cadet cadet : repository.findAll()) {
            yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition())
                    .stream()
                    .filter(v -> v.getTeeth().size() > 0)
                    .forEach(v -> cadets.add(new TempCadet(cadet.getAge() - YEARS.get(v.getYear()), cadet.getGroup().getSchool(), new CPU(v.getTeeth()))));
        }

        List<String> result15 = new ArrayList<>();

        for (TempCadet cadet : cadets.stream().filter(c -> c.age == 15).collect(Collectors.toList())) {
            //К	П	У	к	п	КПУ(кп)
            result15.add(cadet.age + ";" + cadet.cpu.K() + ";" + cadet.cpu.P() + ";" + cadet.cpu.U() + ";" + cadet.cpu.k() + ";" + cadet.cpu.p() + ";" + cadet.cpu.calculateCpu());
        }
        List<String> result16 = new ArrayList<>();

        for (TempCadet cadet : cadets.stream().filter(c -> c.age == 16).collect(Collectors.toList())) {
            //К	П	У	к	п	КПУ(кп)
            result16.add(cadet.age + ";" + cadet.cpu.K() + ";" + cadet.cpu.P() + ";" + cadet.cpu.U() + ";" + cadet.cpu.k() + ";" + cadet.cpu.p() + ";" + cadet.cpu.calculateCpu());
        }
        List<String> result17 = new ArrayList<>();

        for (TempCadet cadet : cadets.stream().filter(c -> c.age == 17).collect(Collectors.toList())) {
            //К	П	У	к	п	КПУ(кп)
            result17.add(cadet.age + ";" + cadet.cpu.K() + ";" + cadet.cpu.P() + ";" + cadet.cpu.U() + ";" + cadet.cpu.k() + ";" + cadet.cpu.p() + ";" + cadet.cpu.calculateCpu());
        }
        List<String> result18 = new ArrayList<>();

        for (TempCadet cadet : cadets.stream().filter(c -> c.age == 18).collect(Collectors.toList())) {
            //К	П	У	к	п	КПУ(кп)
            result18.add(cadet.age + ";" + cadet.cpu.K() + ";" + cadet.cpu.P() + ";" + cadet.cpu.U() + ";" + cadet.cpu.k() + ";" + cadet.cpu.p() + ";" + cadet.cpu.calculateCpu());
        }

        save("KPU_KP_15", result15);
        save("KPU_KP_16", result16);
        save("KPU_KP_17", result17);
        save("KPU_KP_18", result18);
    }

    //@PostConstruct
    public void exportKPU() {
        List<TempCadet2> cadets = new ArrayList<>();

        for (Cadet cadet : repository.findAll()) {
            yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition())
                    .stream()
                    .filter(v -> v.getTeeth().size() > 0)
                    .forEach(
                            v -> cadets.add(new TempCadet2(
                                    cadet.getAge() - YEARS.get(v.getYear()),
                                    cadet.getGroup().getSchool(),
                                    v.getTeeth()
                            ))
                    );
        }

        List<String> result = new ArrayList<>();

        List<List<Integer>> numbers = List.of(
                List.of(17),
                List.of(16),
                List.of(15, 55),
                List.of(14, 54),
                List.of(13, 53),
                List.of(12, 52),
                List.of(11, 51),
                List.of(21, 61),
                List.of(22, 62),
                List.of(23, 63),
                List.of(24, 64),
                List.of(25, 65),
                List.of(26),
                List.of(27),
                List.of(37),
                List.of(36),
                List.of(35, 75),
                List.of(34, 74),
                List.of(33, 73),
                List.of(32, 72),
                List.of(31, 71),
                List.of(41, 81),
                List.of(42, 82),
                List.of(43, 83),
                List.of(44, 84),
                List.of(45, 85),
                List.of(46),
                List.of(47)
        );

        for (List<Integer> number : numbers) {
            result.add(
                    kpuRow(cadets, 10, number) +
                            kpuRow(cadets, 11, number) +
                            kpuRow(cadets, 12, number) +
                            kpuRow(cadets, 13, number) +
                            kpuRow(cadets, 14, number) +
                            kpuRow(cadets, 15, number) +
                            kpuRow(cadets, 16, number) +
                            kpuRow(cadets, 17, number) +
                            kpuRow(cadets, 18, number)
            );
        }
        save("KPU_KP_2", result);
    }

    private static String kpuRow(List<TempCadet2> cadets, int age, List<Integer> numbers) {
        List<Tooth> teeth = cadets.stream()
                .filter(v -> v.age == age)
                .flatMap(v -> v.teeth.stream())
                .filter(v -> numbers.contains(v.getNumber()))
                .collect(Collectors.toList());

        CPU cpu = new CPU(teeth);

        if (teeth.size() == 0) {
            return "0;0;0;0;0;0;";
        }
        return String.format("%.2f", (cpu.K() / (teeth.size() * 1.0)) * 100) + ";" +
                String.format("%.2f", (cpu.P() / (teeth.size() * 1.0)) * 100) + ";" +
                String.format("%.2f", (cpu.U() / (teeth.size() * 1.0)) * 100) + ";" +
                String.format("%.2f", (cpu.k() / (teeth.size() * 1.0)) * 100) + ";" +
                String.format("%.2f", (cpu.p() / (teeth.size() * 1.0)) * 100) + ";" +
                String.format("%.2f", 0.0) + ";";
    }

    @RequiredArgsConstructor
    private class DeltaCadetTemplate {

        private final int age;
        private final CPU cpu;
        private final School school;

    }

    //@PostConstruct
    public void export_delta_KPU() {
        List<DeltaCadetTemplate> cadets = new ArrayList<>();

        for (Cadet cadet : repository.findAll()) {
            List<YearDentalCondition> years = yearDentalConditionRepository.findAllByDentalCondition(cadet.getDentalCondition())
                    .stream()
                    .filter(v -> v.getTeeth().size() > 0)
                    .collect(Collectors.toList());

            for (int i = 1; i < years.size(); i++) {
                YearDentalCondition prev = years.get(i - 1);
                YearDentalCondition current = years.get(i);

                CPU cpuPrev = new CPU(prev.getTeeth());
                CPU cpuCur = new CPU(current.getTeeth());

                cadets.add(new DeltaCadetTemplate(
                        cadet.getAge() - YEARS.get(current.getYear()),
                        CPU.delta(cpuCur, cpuPrev),
                        cadet.getGroup().getSchool()));
            }
        }

        System.out.printf("ВСЕ ВОЗРАСТА КОТОРЫЕ Я ПОСЧИТАЛ [%s]%n", cadets.stream().map(c -> c.age).distinct().collect(Collectors.toList()));

        List<String> result = Arrays.stream(School.values()).map(
                        school -> kpuRow(cadets, school, 10) +
                                kpuRow(cadets, school, 11) +
                                kpuRow(cadets, school, 12) +
                                kpuRow(cadets, school, 13) +
                                kpuRow(cadets, school, 14) +
                                kpuRow(cadets, school, 15) +
                                kpuRow(cadets, school, 16) +
                                kpuRow(cadets, school, 17) +
                                kpuRow(cadets, school, 18)
                )
                .collect(Collectors.toList());

        save("export_delta_KPU", result);
    }

    private static String kpuRow(List<DeltaCadetTemplate> cadets, School school, int age) {
        List<CPU> result = cadets.stream()
                .filter(v -> v.school.equals(school) && v.age == age)
                .map(v -> v.cpu)
                .collect(Collectors.toList());

        if (result.size() == 0) {
            return "0,00;0,00;0,00;0,00;0,00;0,00;";
        }
        return String.format("%.2f", result.stream().mapToInt(CPU::K).sum() / (result.size() * 1.0)) + ";" +
                String.format("%.2f", result.stream().mapToInt(CPU::P).sum() / (result.size() * 1.0)) + ";" +
                String.format("%.2f", result.stream().mapToInt(CPU::U).sum() / (result.size() * 1.0)) + ";" +
                String.format("%.2f", result.stream().mapToInt(CPU::k).sum() / (result.size() * 1.0)) + ";" +
                String.format("%.2f", result.stream().mapToInt(CPU::p).sum() / (result.size() * 1.0)) + ";" +
                String.format("%.2f", result.stream().mapToInt(CPU::calculateCpu).sum() / (result.size() * 1.0)) + ";";
    }

    private class TempCadet {

        private final int age;
        private final School school;
        private final CPU cpu;

        private TempCadet(int age, School school, CPU cpu) {
            this.age = age;
            this.school = school;
            this.cpu = cpu;
        }
    }

    @RequiredArgsConstructor
    private class TempCadet2 {

        private final int age;
        private final School school;
        private final List<Tooth> teeth;

    }


    public void export() {

        List<String> bites = List.of(
                "Аномалия соотношений зубных дуг не уточненная (К07.29)",
                "Дистальный прикус (К07.20)",
                "Мезиальный прикус (К07.21)",
                "Микрогнатия обеих челюстей (К07.05)",
                "Нарушение межзубных промежутков (К07.33)",
                "Скученность (К07.30)",
                "Открытый прикус (К07.24)",
                "Поворот (К07.32)",
                "Чрезмерно глубокий вертикальный прикус (К07.23)",
                "Отсутвуют"
        );


        List<Cadet> cadets = new ArrayList<>();
        repository.findAll().forEach(cadets::add);

        for (School school : School.values()) {

            List<String> result = new ArrayList<>();

            for (String bite : bites) {
                result.add(
                        count(cadets, school, 10, bite) + ";" +
                                count(cadets, school, 11, bite) + ";" +
                                count(cadets, school, 12, bite) + ";" +
                                count(cadets, school, 13, bite) + ";" +
                                count(cadets, school, 14, bite) + ";" +
                                count(cadets, school, 15, bite) + ";" +
                                count(cadets, school, 16, bite) + ";" +
                                count(cadets, school, 17, bite) + ";" +
                                count(cadets, school, 18, bite) + ";"

                );
            }
            save(school.name(), result);
        }
    }

    private static long count(List<Cadet> cadets, School school,
                              int age, String anomaly) {
        return cadets.stream()
                .filter(v -> v.getGroup().getSchool().equals(school) &&
                        Objects.equals(v.getAge(), age) &&
                        split(v.getDentalAnomalies()).contains(anomaly))
                .count();
    }

    private static List<String> split(String str) {
        return Arrays.stream(str.split(";"))
                .map(String::trim)
                .collect(Collectors.toList());
    }


    private static void save(String name, List<String> result) {
        try {
            Path path = Files.createFile(Path.of(String.format("/Users/asoldatov/ИТМО/%s.csv", name)));
            Files.write(path, result);
        } catch (IOException e) {
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

        private CPU(int intact,
                    int caries,
                    int fillingWithCaries,
                    int fillingWithoutCaries,
                    int removalCaries,
                    int removalOtherReason,
                    int sealedFissure,
                    int veneer,
                    int impacted,
                    int notRegistered,
                    int intactTemp,
                    int cariesTemp,
                    int fillingWithCariesTemp,
                    int fillingWithoutCariesTemp,
                    int removalCariesTemp,
                    int sealedFissureTemp,
                    int veneerTemp) {
            this.intact = intact;
            this.caries = caries;
            this.fillingWithCaries = fillingWithCaries;
            this.fillingWithoutCaries = fillingWithoutCaries;
            this.removalCaries = removalCaries;
            this.removalOtherReason = removalOtherReason;
            this.sealedFissure = sealedFissure;
            this.veneer = veneer;
            this.impacted = impacted;
            this.notRegistered = notRegistered;
            this.intactTemp = intactTemp;
            this.cariesTemp = cariesTemp;
            this.fillingWithCariesTemp = fillingWithCariesTemp;
            this.fillingWithoutCariesTemp = fillingWithoutCariesTemp;
            this.removalCariesTemp = removalCariesTemp;
            this.sealedFissureTemp = sealedFissureTemp;
            this.veneerTemp = veneerTemp;
        }


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

        public int K() {
            return (caries + fillingWithCaries);
        }

        public int P() {
            return (fillingWithoutCaries + sealedFissure);
        }

        public int U() {
            return (removalCaries + removalOtherReason + veneer);
        }

        public int k() {
            return (cariesTemp + fillingWithCariesTemp);
        }

        public int p() {
            return (fillingWithoutCariesTemp + sealedFissureTemp);
        }


        //КПУ=К(1,2)+П(3,6)+У(4,5,7)+кп=к(B,C)+п(D,F)
        public int calculateCpu() {
            return (caries + fillingWithCaries) +
                    (fillingWithoutCaries + sealedFissure) +
                    (removalCaries + removalOtherReason + veneer) +

                    (cariesTemp + fillingWithCariesTemp) +
                    (fillingWithoutCariesTemp + sealedFissureTemp);
        }

        private boolean isTemp(Tooth tooth) {
            return tooth.getNumber() > 47;
        }

        public static CPU delta(CPU curr, CPU prev) {
            return new CPU(
                    curr.intact = prev.intact,
                    curr.caries = prev.caries,
                    curr.fillingWithCaries = prev.fillingWithCaries,
                    curr.fillingWithoutCaries = prev.fillingWithoutCaries,
                    curr.removalCaries = prev.removalCaries,
                    curr.removalOtherReason = prev.removalOtherReason,
                    curr.sealedFissure = prev.sealedFissure,
                    curr.veneer = prev.veneer,
                    curr.impacted = prev.impacted,
                    curr.notRegistered = prev.notRegistered,
                    curr.intactTemp = prev.intactTemp,
                    curr.cariesTemp = prev.cariesTemp,
                    curr.fillingWithCariesTemp = prev.fillingWithCariesTemp,
                    curr.fillingWithoutCariesTemp = prev.fillingWithoutCariesTemp,
                    curr.removalCariesTemp = prev.removalCariesTemp,
                    curr.sealedFissureTemp = prev.sealedFissureTemp,
                    curr.veneerTemp = prev.veneerTemp
            );
        }
    }
}
