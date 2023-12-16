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

@Component
@RequiredArgsConstructor
public class ExportHealthGroup {

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
                        System.out.println(DECIMAL_FORMAT.format(calculate.first) + "\t" + DECIMAL_FORMAT.format(calculate.second) + "\t" + DECIMAL_FORMAT.format(calculate.third));
                    }
                }
            }
        }
    }

    private static class Calculate {
        private final double first;
        private final double second;
        private final double third;
        private final double total;

        public Calculate(List<ReportClass> classes) {
            this.total = classes.stream().mapToInt(v -> v.getFirstHealthGroup() + v.getSecondHealthGroup() + v.getThirdHealthGroup()).sum();
            this.first = classes.stream().mapToInt(ReportClass::getFirstHealthGroup).sum() / total;
            this.second = classes.stream().mapToInt(ReportClass::getSecondHealthGroup).sum() / total;
            this.third = classes.stream().mapToInt(ReportClass::getThirdHealthGroup).sum() / total;
        }
    }
}
