package edu.asoldatov.nir.model.cadet;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mucous_membrane")
public class MucousMembrane {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cadet_id", referencedColumnName = "id")
    private Cadet cadet;

    @Column(name = "value")
    private Integer value;

}
