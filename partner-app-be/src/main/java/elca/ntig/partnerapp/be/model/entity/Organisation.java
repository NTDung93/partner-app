package elca.ntig.partnerapp.be.model.entity;

import elca.ntig.partnerapp.be.model.enums.organisation.CodeNOGA;
import elca.ntig.partnerapp.be.model.enums.organisation.LegalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organisation extends BaseEntity{
    @Id
    @Column(name = "PARTNER_ID")
    private Integer id;

    @OneToOne
    @MapsId("id")
    @JoinColumn(name = "PARTNER_ID")
    private Partner partner;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ADDITIONAL_NAME")
    private String additionalName;

    @Enumerated(EnumType.STRING)
    @Column(name = "LEGAL_STATUS", length = 10)
    private LegalStatus legalStatus;

    @Column(name = "CREATION_DATE")
    private LocalDate creationDate;

    @Column(name = "IDE_NUMBER", length = 13)
    private String ideNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "CODE_NOGA", length = 10)
    private CodeNOGA codeNoga;
}
