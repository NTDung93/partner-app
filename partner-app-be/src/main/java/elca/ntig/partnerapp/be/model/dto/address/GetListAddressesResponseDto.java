package elca.ntig.partnerapp.be.model.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListAddressesResponseDto {
    List<AddressResponseDto> addresses;
}
