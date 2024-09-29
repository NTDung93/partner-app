package elca.ntig.partnerapp.be.model.dto.person;

import elca.ntig.partnerapp.be.model.dto.address.AddressResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonAlongWithAddressResponseDto {
    private PersonResponseDto person;
    private List<AddressResponseDto> addresses;
}
