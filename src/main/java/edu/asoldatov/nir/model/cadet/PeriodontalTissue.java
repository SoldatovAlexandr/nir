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
@Table(name = "periodontal_tissue")
public class PeriodontalTissue {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "value")
    private Integer value;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "cadet_id")
    private Cadet cadet;

}
