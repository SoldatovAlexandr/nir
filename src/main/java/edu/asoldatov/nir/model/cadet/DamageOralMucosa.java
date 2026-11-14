package edu.asoldatov.nir.model.cadet;


import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "demage_oral_musoca")
public class DamageOralMucosa {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "first_state")
    private Integer firstState;

    @Column(name = "second_state")
    private Integer secondState;

    @Column(name = "third_state")
    private Integer thirdState;

    @Column(name = "first_localization")
    private Integer firstLocalization;

    @Column(name = "second_localization")
    private Integer secondLocalization;

    @Column(name = "third_localization")
    private Integer thirdLocalization;

    @OneToOne(cascade = CascadeType.ALL)
    private Cadet cadet;

}
