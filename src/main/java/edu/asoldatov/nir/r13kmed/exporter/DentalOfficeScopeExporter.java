package edu.asoldatov.nir.r13kmed.exporter;

import edu.asoldatov.nir.r13kmed.DentalOfficeScope;
import edu.asoldatov.nir.r13kmed.Report;
import edu.asoldatov.nir.r13kmed.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DentalOfficeScopeExporter {

    private final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.GERMAN));

    private final ReportRepository reportRepository;

    // @EventListener(ApplicationReadyEvent.class)
    public void export() {
        Map<Integer, List<Report>> reportsByYear = new HashMap<>();
        for (Report report : reportRepository.findAll()) {
            if (!reportsByYear.containsKey(report.getYearStart())) {
                reportsByYear.put(report.getYearStart(), new ArrayList<>());
            }
            reportsByYear.get(report.getYearStart()).add(report);
        }

        for (Integer year : reportsByYear.keySet()) {
            System.out.println();
            System.out.println();
            System.out.println(year);

            print(reportsByYear, year, "2");
            print(reportsByYear, year, "3");
            print(reportsByYear, year, "4");
        }
    }

   // @EventListener(ApplicationReadyEvent.class)
    public void export2() {
        Map<Integer, Map<String, List<DentalOfficeScope>>> reportsByYear = new HashMap<>();
        for (Report report : reportRepository.findAll()) {
            if (!reportsByYear.containsKey(report.getYearStart())) {
                reportsByYear.put(report.getYearStart(), new HashMap<>());
            }
            if (!reportsByYear.get(report.getYearStart()).containsKey(report.getSchool())) {
                reportsByYear.get(report.getYearStart()).put(report.getSchool(), new ArrayList<>());
            }
            for (DentalOfficeScope dentalOfficeScope : report.getDentalOfficeScopes()) {
                reportsByYear.get(report.getYearStart()).get(report.getSchool()).add(dentalOfficeScope);
            }
        }

        for (Integer year : reportsByYear.keySet()) {
            System.out.println();
            System.out.println();
            System.out.println(year);

            for (String school : reportsByYear.get(year).keySet()) {
                List<DentalOfficeScope> dentalOfficeScopes = reportsByYear.get(year).get(school);
                print(dentalOfficeScopes.stream().filter(v -> v.getContingentsOfPatients().equals("2")).collect(Collectors.toList()));
                print(dentalOfficeScopes.stream().filter(v -> v.getContingentsOfPatients().equals("3")).collect(Collectors.toList()));
                print(dentalOfficeScopes.stream().filter(v -> v.getContingentsOfPatients().equals("4")).collect(Collectors.toList()));
                System.out.println();
            }
        }
    }

    private void print(Map<Integer, List<Report>> reportsByYear, int year, String group) {
        print(
                reportsByYear.get(year)
                        .stream()
                        .flatMap(v -> v.getDentalOfficeScopes().stream())
                        .filter(v -> v.getContingentsOfPatients().equals(group))
                        .collect(Collectors.toList())
        );
    }

    private void print(List<DentalOfficeScope> dentalOfficeScopes) {
        System.out.println(
                DECIMAL_FORMAT.format(dentalOfficeScopes.stream().mapToInt(DentalOfficeScope::getTotalNumberOfVisits).sum()) + "\t" +
                        DECIMAL_FORMAT.format(dentalOfficeScopes.stream().mapToInt(DentalOfficeScope::getFirstNumberOfVisits).sum()) + "\t" +
                        DECIMAL_FORMAT.format(dentalOfficeScopes.stream().mapToInt(DentalOfficeScope::getCountInspected).sum()) + "\t" +
                        DECIMAL_FORMAT.format(dentalOfficeScopes.stream().mapToInt(DentalOfficeScope::getCountInspectedNeedSanitation).sum()) + "\t" +
                        DECIMAL_FORMAT.format(dentalOfficeScopes.stream().mapToInt(DentalOfficeScope::getCountInspectedSanitized).sum()) + "\t" +
                        DECIMAL_FORMAT.format(dentalOfficeScopes.stream().mapToInt(DentalOfficeScope::getCountInspectedNeedProsthetics).sum()) + "\t" +
                        DECIMAL_FORMAT.format(dentalOfficeScopes.stream().mapToInt(DentalOfficeScope::getCountInspectedProsthetic).sum())
        );
    }
}
