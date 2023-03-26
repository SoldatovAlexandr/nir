package edu.asoldatov.nir.model.cadet;


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
@Table(name = "tooth")
public class Tooth {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "value")
    private Integer value;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "year_dental_condition_id", referencedColumnName = "id")
    private YearDentalCondition yearDentalCondition;

}
