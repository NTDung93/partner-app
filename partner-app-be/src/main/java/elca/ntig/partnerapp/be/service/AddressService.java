package elca.ntig.partnerapp.be.service;

import elca.ntig.partnerapp.be.model.dto.address.AddressResponseDto;
import elca.ntig.partnerapp.be.model.dto.address.CreateAddressRequestDto;
import elca.ntig.partnerapp.be.model.entity.Partner;

import java.util.List;

public interface AddressService {
    void createAddressForPartner(Partner partner, List<CreateAddressRequestDto> addresses);
    List<AddressResponseDto> getAddressesByPartnerId(Integer partnerId);
    void updateAddressForPartner(Partner partner, List<AddressResponseDto> addresses);
}
