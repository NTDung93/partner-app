package elca.ntig.partnerapp.be.model.dto.address;

import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.addess.CantonAbbr;
import elca.ntig.partnerapp.be.model.enums.addess.Country;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private Integer id;
    private String street;
    private String locality;
    private String zipCode;
    private CantonAbbr canton;
    private Country country;
    private AddressType category;
    private String houseNumber;
    private LocalDate validityStart;
    private LocalDate validityEnd;
    private Status status;
}
