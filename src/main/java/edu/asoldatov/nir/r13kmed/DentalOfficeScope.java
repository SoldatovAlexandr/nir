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
@Table(name = "dental_office_scope")
public class DentalOfficeScope {

    /**
     * Раздел 5.1 Объем работы стоматологического кабинета
     */

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private Report report;

    /**
     * Контингенты больных
     */

    @Column(name = "contingents_of_patients")
    private String contingentsOfPatients;


    /**
     * Количество обращений в стоматологический кабинет
     * Всего	0
     */
    private Integer totalNumberOfVisits;

    private Integer firstNumberOfVisits;

    /**
     * Осмотрено человек при ежегодном профилактическом медицинском осмотре (ПМО)
     */

    private Integer countInspected;

    /**
     * Из числа осмотренных при ПМО нуждалось в санации
     */

    private Integer countInspectedNeedSanitation;


    /**
     * Из них санировано
     */

    private Integer countInspectedSanitized;

    /**
     * Нуждалось в протезировании
     */

    private Integer countInspectedNeedProsthetics;

    /**
     * Из них протезировано
     */

    private Integer countInspectedProsthetic;

}
