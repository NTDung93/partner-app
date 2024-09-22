package elca.ntig.partnerapp.be.model.dto.organisation;

import elca.ntig.partnerapp.be.model.enums.organisation.CodeNOGA;
import elca.ntig.partnerapp.be.model.enums.organisation.LegalStatus;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganisationRequestDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String additionalName;
    private String ideNumber;
    private CodeNOGA codeNoga;

    @NotNull(message = "Language is mandatory")
    private Language language;
    private LegalStatus legalStatus;
    private LocalDate creationDate;
    private String phoneNumber;
}
