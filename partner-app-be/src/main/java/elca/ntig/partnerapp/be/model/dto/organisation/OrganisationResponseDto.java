package elca.ntig.partnerapp.be.model.dto.organisation;

import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.enums.organisation.CodeNOGA;
import elca.ntig.partnerapp.be.model.enums.organisation.LegalStatus;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationResponseDto {
    private Integer id;
    private String name;
    private String additionalName;
    private Language language;
    private LegalStatus legalStatus;
    private String ideNumber;
    private LocalDate creationDate;
    private CodeNOGA codeNoga;
    private String phoneNumber;
    private Status status;
}
