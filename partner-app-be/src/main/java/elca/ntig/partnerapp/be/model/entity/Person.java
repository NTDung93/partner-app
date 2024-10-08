package elca.ntig.partnerapp.be.model.entity;

import elca.ntig.partnerapp.be.model.enums.person.MaritalStatus;
import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseEntity {
    @Id
    @Column(name = "PARTNER_ID")
    private Integer id;

    @OneToOne
    @MapsId("id")
    @JoinColumn(name = "PARTNER_ID")
    private Partner partner;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "SEX", nullable = false, length = 1)
    private SexEnum sex;

    @Column(name = "NATIONALITY", length = 2)
    private Nationality nationality;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "AVS_NUMBER", length = 13)
    private String avsNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "MARITAL_STATUS", length = 10)
    private MaritalStatus maritalStatus;
}
