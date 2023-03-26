package edu.asoldatov.nir.model.cadet;


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
@Table(name = "dental_condition")
public class DentalCondition {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dentalCondition")
    private List<YearDentalCondition> years;

    @Column(name = "enamel_fluorosis")
    private Integer enamelFluorosis;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cadet_id", referencedColumnName = "id")
    private Cadet cadet;
}
