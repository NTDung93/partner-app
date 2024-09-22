package elca.ntig.partnerapp.be.model.dto.organisation;

import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.enums.organisation.LegalStatus;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrganisationCriteriasDto {
    private String name;
    private String additionalName;
    private Language language;
    private LegalStatus legalStatus;
    private String ideNumber;
    private LocalDate creationDate;
    private List<Status> status;
}
