package edu.asoldatov.nir.r13kmed.exporter;

import edu.asoldatov.nir.r13kmed.Report;
import edu.asoldatov.nir.r13kmed.ReportClass;
import edu.asoldatov.nir.r13kmed.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExportEatingGroup {

    private final ReportRepository reportRepository;

    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.GERMAN));

    //@EventListener(ApplicationStartedEvent.class)
    public void export() {
        Map<Integer, List<Report>> reportsByYear = new HashMap<>();
        for (Report report : reportRepository.findAll()) {
            if (!reportsByYear.containsKey(report.getYearStart())) {
                reportsByYear.put(report.getYearStart(), new ArrayList<>());
            }
            reportsByYear.get(report.getYearStart()).add(report);
        }
        for (Integer year : reportsByYear.keySet()) {
            List<Report> reports = reportsByYear.get(year);
            Map<String, Map<String, List<ReportClass>>> reportClasses = Map.of(
                    "10 - 12 лет", new HashMap<>(),
                    "13 - 15 лет", new HashMap<>(),
                    "16 - 18 лет", new HashMap<>()
            );
            for (Report report : reports) {
                for (ReportClass reportClass : report.getReportClasses()) {
                    Map<String, List<ReportClass>> classes = reportClasses.get(reportClass.getCategory());
                    if (!classes.containsKey(reportClass.getReport().getSchool())) {
                        classes.put(reportClass.getReport().getSchool(), new ArrayList<>());
                    }
                    classes.get(reportClass.getReport().getSchool()).add(reportClass);
                }
            }
            System.out.println();
            System.out.println();
            System.out.println(year);

            for (String ages : List.of("10 - 12 лет", "13 - 15 лет", "16 - 18 лет")) {
                System.out.println(ages);

                for (String schoolName : reportClasses.get(ages).keySet()) {
                    Calculate calculate = new Calculate(reportClasses.get(ages).get(schoolName));
                    if (calculate.total > 0) {
                        System.out.println(DECIMAL_FORMAT.format(calculate.normal) + "\t" + DECIMAL_FORMAT.format(calculate.low) + "\t" + DECIMAL_FORMAT.format(calculate.hight));
                    }
                }
            }
        }
    }

    //@EventListener(ApplicationStartedEvent.class)
    public void export2() {
        Map<Integer, Map<String, List<ReportClass>>> reportsByYear = new HashMap<>();
        for (Report report : reportRepository.findAll()) {
            if (!reportsByYear.containsKey(report.getYearStart())) {
                reportsByYear.put(report.getYearStart(), new HashMap<>());
            }
            if (!reportsByYear.get(report.getYearStart()).containsKey(report.getSchool())) {
                reportsByYear.get(report.getYearStart()).put(report.getSchool(), new ArrayList<>());
            }
            reportsByYear.get(report.getYearStart()).get(report.getSchool()).addAll(report.getReportClasses());
        }
        for (Integer year : reportsByYear.keySet()) {
            Map<String, List<ReportClass>> reportClasses = reportsByYear.get(year);

            System.out.println();
            System.out.println();
            System.out.println(year);
            for (List<ReportClass> rc : reportClasses.values()) {
                Calculate calculate = new Calculate(rc);
                if (calculate.total > 0) {
                    System.out.println(DECIMAL_FORMAT.format(calculate.normal) + "\t" + DECIMAL_FORMAT.format(calculate.low) + "\t" + DECIMAL_FORMAT.format(calculate.hight));
                }
            }
        }
    }

   // @EventListener(ApplicationStartedEvent.class)
    public void export3() {
        Map<Integer, List<ReportClass>> reportsByYear = new HashMap<>();
        for (Report report : reportRepository.findAll()) {
            if (!reportsByYear.containsKey(report.getYearStart())) {
                reportsByYear.put(report.getYearStart(), new ArrayList<>());
            }
            reportsByYear.get(report.getYearStart()).addAll(report.getReportClasses());
        }
        for (Integer year : reportsByYear.keySet().stream().sorted().collect(Collectors.toList())) {
            List<ReportClass> reportClasses = reportsByYear.get(year);
            for (String ages : List.of("10 - 12 лет", "13 - 15 лет", "16 - 18 лет")) {
                System.out.println(
                        reportClasses.stream()
                                .filter(v -> v.getCategory().equals(ages))
                                .mapToInt(v -> v.getFirstHealthGroup() + v.getSecondHealthGroup() + v.getThirdHealthGroup())
                                .sum()
                );
            }
        }
    }

    private static class Calculate {
        private final double normal;
        private final double low;
        private final double hight;
        private final double total;

        public Calculate(List<ReportClass> classes) {
            this.total = classes.stream().mapToInt(v -> v.getNormalNutritionGroup() + v.getLowNutritionGroup() + v.getHighNutritionGroup()).sum();
            this.normal = classes.stream().mapToInt(ReportClass::getNormalNutritionGroup).sum() / total;
            this.low = classes.stream().mapToInt(ReportClass::getLowNutritionGroup).sum() / total;
            this.hight = classes.stream().mapToInt(ReportClass::getHighNutritionGroup).sum() / total;
        }
    }
}
