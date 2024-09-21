package elca.ntig.partnerapp.be.service;

import elca.ntig.partnerapp.be.model.dto.partner.DeletePartnerResponseDto;

public interface PartnerService {
    DeletePartnerResponseDto deletePartnerById(Integer id);
}
