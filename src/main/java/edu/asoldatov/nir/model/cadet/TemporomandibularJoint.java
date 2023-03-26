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
@Table(name = "temporomandibular_joint")
public class TemporomandibularJoint {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "symptoms")
    private Integer symptoms;

    @Column(name = "clicking")
    private Integer clicking;

    @Column(name = "soreness")
    private Integer soreness;

    @Column(name = "limitation_of_jaw_movement")
    private Integer limitationOfJawMovement;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cadet_id", referencedColumnName = "id")
    private Cadet cadet;
}
