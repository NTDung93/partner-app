package elca.ntig.partnerapp.be.model.dto;

import elca.ntig.partnerapp.be.model.enums.organisation.CodeNOGA;
import elca.ntig.partnerapp.be.model.enums.organisation.LegalStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganisationDto {
    private Integer id;
    private String name;
    private String additionalName;
    private LegalStatus legalStatus;
    private LocalDate creationDate;
    private String ideNumber;
    private CodeNOGA codeNoga;
    private int version;
}
