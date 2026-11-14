package edu.asoldatov.nir.model.cadet;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hygiene_index")
public class HygieneIndex {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "index")
    private Integer index;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "cadet_id")
    private Cadet cadet;

}
