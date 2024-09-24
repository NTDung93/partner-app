package elca.ntig.partnerapp.be.model.dto.address;
import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.addess.CantonAbbr;
import elca.ntig.partnerapp.be.model.enums.addess.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressRequestDto {
    private Integer id;
    private AddressType category;
    private String locality;
    private String street;
    private Country country;
    private LocalDate validityStart;
    private String zipCode;
    private String houseNumber;
    private CantonAbbr canton;
    private LocalDate validityEnd;
    private Integer partnerId;
}
