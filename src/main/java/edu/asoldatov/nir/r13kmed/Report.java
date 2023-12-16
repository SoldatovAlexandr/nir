package edu.asoldatov.nir.r13kmed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    /**
     * Воспитанники, всего
     **/

    private String school;

    private Integer yearStart;

    //Случаев заболеваний
    private Integer totalCountCasesOfDiseases;

    //Случаев заболеваний первичных
    private Integer totalCountFirstCasesOfDiseases;

    //Число направлений на стац. лечение
    //Изолятор МП
    @Column(name = "total_count_treatment_mp")
    private Integer totalCountTreatmentMP;

    //в военно-лечебные учреждения
    private Integer totalCountTreatmentMilitaryHospitals;

    //в гражданские лечебные учреждения
    private Integer totalCountTreatmentCivilianHospitals;

    //Трудопотери

    // дней
    private Integer totalCountLaborLossesDay;

    // случаев
    private Integer totalCountLaborLossesCases;

    /**
     * в т. ч.1 года обучения
     **/

    //Случаев заболеваний
    private Integer firstYearCasesOfDiseases;

    //Случаев заболеваний первичных
    private Integer firstYearFirstCasesOfDiseases;

    //Число направлений на стац. лечение
    //Изолятор МП
    @Column(name = "first_year_treatment_mp")
    private Integer firstYearTreatmentMP;

    //в военно-лечебные учреждения
    private Integer firstYearTreatmentMilitaryHospitals;

    //в гражданские лечебные учреждения
    private Integer firstYearTreatmentCivilianHospitals;

    //Трудопотери

    // дней
    private Integer firstYearLaborLossesDay;

    // случаев
    private Integer firstYearLaborLossesCases;

    /**
     * 2 страница
     */

    //ИТОГО по вышеперечисленным классам

    //Первичная заболеваемость

    //в абс. числах

    private Integer allClassPrimaryIncidenceAbsolute;

    //в промиллях

    private Double allClassPrimaryIncidencePpm;


    //Структура в %
    private Double allClassPrimaryIncidencePercent;

    //Болезни полости рта, слюнных желез и челюстей

    //в абс. числах

    private Integer oralDiseasesPrimaryIncidenceAbsolute;

    //в промиллях

    private Double oralDiseasesPrimaryIncidencePpm;


    //Структура в %
    private Double oralDiseasesPrimaryIncidencePercent;

    /**
     * Раздел 4. Характеристика состояния здоровья воспитанников
     */

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "report")
    private List<ReportClass> reportClasses;

    /**
     * Раздел 5.1 Объем работы стоматологического кабинета
     */

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "report")
    private List<DentalOfficeScope> dentalOfficeScopes;

    /**
     * Показатели 5.2
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "report", fetch = FetchType.EAGER)
    private List<Indicator> indicators;
}
