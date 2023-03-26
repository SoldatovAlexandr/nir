package edu.asoldatov.nir.model.questionnaire;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questionnaire_cadet")
public class QuestionnaireCadet {

    @Id
    @GeneratedValue(generator = "h_sequence")
    @SequenceGenerator(name = "h_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "doo")
    private String doo;

    @Column(name = "course")
    private Integer course;

    @Column(name = "age")
    private Integer age;

    @Column(name = "dental_condition")
    private String dentalCondition;

    @Column(name = "is_hide_smile")
    private boolean isHideSmile;

    @Column(name = "is_missed_class")
    private boolean isMissedClass;

    @Column(name = "tooth_sensitivity")
    private String toothSensitivity;

    @Column(name = "visit_number")
    private String visitNumber;

    @Column(name = "last_visit_reason")
    private String lastVisitReason;

    @Column(name = "last_dental_action")
    private String lastDentalAction;

    @Column(name = "dental_advice")
    private String dentalAdvice;

    @Column(name = "dental_help")
    private String dentalHelp;

    @Column(name = "another_organization")
    private String anotherOrganization;

    @Column(name = "difficulties")
    private String difficulties;

    @Column(name = "is_satisfied_workspace")
    private boolean isSatisfiedWorkspace;

    @Column(name = "dental_office_work")
    private String dentalOfficeWork;

    @Column(name = "offer_for_dental_office")
    private String offerForDentalOffice;

    @Column(name = "brush_properly")
    private String brushProperly;

    @Column(name = "rules_information")
    private String rulesInformation;

    @Column(name = "teacher")
    private String teacher;

    @Column(name = "how_you_brush")
    private String howYouBrush;

    @Column(name = "toothbrush_change")
    private String toothbrushChange;

    @Column(name = "fluoride_paste")
    private String fluoridePaste;

    @Column(name = "toothbrush")
    private boolean toothbrush;

    @Column(name = "toothpick")
    private boolean toothpick;

    @Column(name = "wooden_stick")
    private boolean woodenStick;

    @Column(name = "floss")
    private boolean floss;

    @Column(name = "thread")
    private boolean thread;

    @Column(name = "irrigator")
    private boolean irrigator;

    @Column(name = "interdental_brush")
    private boolean interdentalBrush;

    @Column(name = "additional_source")
    private String additionalSource;

    @Column(name = "how_choose_oral_products")
    private String howChooseOralProducts;

    @Column(name = "frequency_use_additional_source")
    private String frequencyUseAdditionalSource;

    @Column(name = "favorite_food")
    private String favoriteFood;

    @Column(name = "favorite_sweet")
    private String favoriteSweet;

    @Column(name = "aim")
    private String aim;


    @Column(name = "caries_ruins_teeth")
    private String cariesRuinsTeeth;

    @Column(name = "save_original_teeth")
    private String saveOriginalTeeth;

    @Column(name = "afraid_pain")
    private String afraidPain;

    @Column(name = "teeth_problem")
    private String teethProblem;

    @Column(name = "brush_stop_caries")
    private String brushStopCaries;

    @Column(name = "brush_stop_gum_disease")
    private String brushStopGumDisease;

    @Column(name = "eats_sweet")
    private String eatsSweet;

    @Column(name = "fluorine")
    private String fluorine;
}
