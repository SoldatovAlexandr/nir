package edu.asoldatov.nir.model.cadet;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ohis")
public class Ohis {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cadet_id", referencedColumnName = "id")
    private Cadet cadet;

    @Column(name = "t16")
    private Integer t16;

    @Column(name = "t11")
    private Integer t11;

    @Column(name = "t26")
    private Integer t26;

    @Column(name = "t46")
    private Integer t46;

    @Column(name = "t31")
    private Integer t31;

    @Column(name = "t36")
    private Integer t36;

}
