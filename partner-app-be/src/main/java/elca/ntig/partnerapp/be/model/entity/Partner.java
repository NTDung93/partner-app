package elca.ntig.partnerapp.be.model.entity;

import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Partner extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PARTNER_ID")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "LANG", nullable = false, length = 2)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false, length = 10)
    private Status status;

    @Column(name = "PHONE_NUMBER", length = 20)
    private String phoneNumber;

    @OneToOne(mappedBy = "partner", fetch = FetchType.LAZY)
    private Organisation organisation;

    @OneToOne(mappedBy = "partner", fetch = FetchType.LAZY)
    private Person person;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private Set<Address> addresses;
}
