package edu.asoldatov.nir.model.cadet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "year_dental_condition")
public class YearDentalCondition {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "year")
    private String year;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yearDentalCondition", fetch = FetchType.EAGER)
    private List<Tooth> teeth;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dental_condition_id", referencedColumnName = "id")
    private DentalCondition dentalCondition;

}
