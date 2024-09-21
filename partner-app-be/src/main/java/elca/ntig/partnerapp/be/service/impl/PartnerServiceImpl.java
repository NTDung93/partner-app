package elca.ntig.partnerapp.be.service.impl;

import elca.ntig.partnerapp.be.model.dto.partner.DeletePartnerResponseDto;
import elca.ntig.partnerapp.be.model.entity.Address;
import elca.ntig.partnerapp.be.model.entity.Partner;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.exception.DeleteInactivePartnerException;
import elca.ntig.partnerapp.be.model.exception.ResourceNotFoundException;
import elca.ntig.partnerapp.be.repository.AddressRepository;
import elca.ntig.partnerapp.be.repository.PartnerRepository;
import elca.ntig.partnerapp.be.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {
    private final PartnerRepository partnerRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public DeletePartnerResponseDto deletePartnerById(Integer id) {
        Partner partner = partnerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Partner", "id", id));
        if (partner != null && partner.getStatus().equals(Status.INACTIVE)) {
            throw new DeleteInactivePartnerException("Cannot delete inactive partner");
        }

        if (partner != null && partner.getStatus().equals(Status.ACTIVE)) {
            partner.setStatus(Status.INACTIVE);
            partnerRepository.save(partner);

            List<Address> addresses = addressRepository.findByPartnerIdAndStatus(id, Status.ACTIVE);
            if (!addresses.isEmpty()) {
                addresses.stream()
                        .forEach(address -> {
                            address.setStatus(Status.INACTIVE);
                            addressRepository.save(address);
                        });
            }
            return new DeletePartnerResponseDto("Partner is deleted successfully");
        }
        return new DeletePartnerResponseDto("Partner is not deleted");
    }
}
