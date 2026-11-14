package edu.asoldatov.nir.model.cadet;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attachment_loss")
public class AttachmentLoss {

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
