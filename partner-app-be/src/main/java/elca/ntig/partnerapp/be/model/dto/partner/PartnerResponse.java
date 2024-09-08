package elca.ntig.partnerapp.be.model.dto.partner;

import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerResponse {
    private Integer id;
    private Language language;
    private String phoneNumber;
    private Status status;
}
