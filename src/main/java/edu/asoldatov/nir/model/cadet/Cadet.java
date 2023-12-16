package edu.asoldatov.nir.model.cadet;

import edu.asoldatov.nir.common.Bite;
import edu.asoldatov.nir.common.Gender;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cadet")
public class Cadet {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "age")
    private Integer age;

    @Column(name = "ethnic_group")
    private String ethnicGroup;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "region_of_residence")
    private String regionOfResidence;

    @Column(name = "preventive_actions")
    private String preventiveActions;

    @Column(name = "preventive_actions_details")
    private String preventiveActionsDetails;

    @Column(name = "health_group")
    private Integer healthGroup;

    @Column(name = "bite_type")
    private String biteType;

    @Column(name = "vvk")
    private String vvk;

    @Column(name = "dental_anomalies")
    private String dentalAnomalies;

    @Column(name = "level_of_hygiene")
    private Integer levelOfHygiene;

    @Column(name = "bite")
    @Enumerated(EnumType.STRING)
    private Bite bite;

    @Column(name = "tooth_erosion")
    private Integer toothErosion;

    @Column(name = "tooth_erosion_count")
    private Integer toothErosionCount;

    @Column(name = "dental_trauma")
    private Integer dentalTrauma;

    @Column(name = "dental_trauma_count")
    private Integer dentalTraumaCount;

    @OneToOne(mappedBy = "cadet", cascade = CascadeType.ALL)
    private DentalCondition dentalCondition;

    @OneToOne(mappedBy = "cadet", cascade = CascadeType.ALL)
    private TemporomandibularJoint temporomandibularJoint;

    @OneToOne(mappedBy = "cadet", cascade = CascadeType.ALL)
    private DamageOralMucosa damageOralMucosa;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cadet")
    private List<PeriodontalTissue> periodontalTissue;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cadet")
    private List<EnamelSpotting> enamelSpotting;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cadet")
    private List<AttachmentLoss> attachmentLosses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cadet")
    private List<HygieneIndex> hygieneIndices;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "emergency_help")
    private Integer emergencyHelp;
}
