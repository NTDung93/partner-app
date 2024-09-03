package elca.ntig.partnerapp.be.model.dto;

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
public class PartnerDto {
    private Integer id;
    private Language language;
    private Status status;
    private String phoneNumber;
    private int version;
}
