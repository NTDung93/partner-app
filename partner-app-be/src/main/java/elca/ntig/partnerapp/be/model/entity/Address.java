package elca.ntig.partnerapp.be.model.entity;

import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.addess.CantonAbbr;
import elca.ntig.partnerapp.be.model.enums.addess.Country;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "PARTNER_ID")
    private Partner partner;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY", nullable = false, length = 20)
    private AddressType category;

    @Column(name = "ZIP_CODE", nullable = false, length = 15)
    private String zipCode;

    @Column(name = "LOCALITY", nullable = false, length = 50)
    private String locality;

    @Column(name = "STREET", length = 60)
    private String street;

    @Column(name = "HOUSE_NUMBER", length = 12)
    private String houseNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "CANTON", length = 2)
    private CantonAbbr canton;

    @Enumerated(EnumType.STRING)
    @Column(name = "COUNTRY", length = 2)
    private Country country;

    @Column(name = "VALIDITY_START", nullable = false)
    private LocalDate validityStart;

    @Column(name = "VALIDITY_END")
    private LocalDate validityEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;
}
