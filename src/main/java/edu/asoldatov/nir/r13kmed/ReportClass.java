package edu.asoldatov.nir.r13kmed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_class")
public class ReportClass {

    /**
     * Раздел 4. Характеристика состояния здоровья воспитанников
     */

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private Report report;

    /**
     * Категория воспитанников
     */
    private String category;

    /**
     * Находится под медицинским наблюдением
     */
    private Integer medicalSupervision;

    /**
     * Отнесено к группам состояния здоровья
     * 1	2	3
     */
    private Integer firstHealthGroup;

    private Integer secondHealthGroup;

    private Integer thirdHealthGroup;

    /**
     * Отнесено к группам состояния питания
     * Нормальное	Пониженное (включая недостаточность)	Повышенное (включая ожирение)
     */
    private Integer normalNutritionGroup;

    private Integer lowNutritionGroup;

    private Integer highNutritionGroup;

    /**
     * Признано негодными по состоянию здоровья к обучению в училище
     */
    private Integer unfitForHealthStudySchool;

    /**
     * Признано негодными по состоянию здоровья к поступлению в ВВУЗ из числа выпускников
     */
    private Integer unfitForHealthStudyUniversity;

}
