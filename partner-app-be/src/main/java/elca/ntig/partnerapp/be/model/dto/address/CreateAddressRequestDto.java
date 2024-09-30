package elca.ntig.partnerapp.be.model.dto.address;

import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.addess.CantonAbbr;
import elca.ntig.partnerapp.be.model.enums.addess.Country;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequestDto {
    @NotNull(message = "Address type is mandatory")
    private AddressType category;

    @NotBlank(message = "Locality is mandatory")
    private String locality;
    private String street;
    private Country country;

    @NotNull(message = "Validity start is mandatory")
    private LocalDate validityStart;

    @NotBlank(message = "Zip code is mandatory")
    private String zipCode;
    private String houseNumber;
    private CantonAbbr canton;
    private LocalDate validityEnd;
    private Integer partnerId;
}
