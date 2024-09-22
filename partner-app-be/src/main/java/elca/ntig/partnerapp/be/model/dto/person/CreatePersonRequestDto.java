package elca.ntig.partnerapp.be.model.dto.person;

import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.enums.person.MaritalStatus;
import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
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
public class CreatePersonRequestDto {
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotBlank(message = "First name is mandatory")
    private String firstName;
    private String avsNumber;
    private MaritalStatus maritalStatus;

    @NotNull(message = "Language is mandatory")
    private Language language;

    @NotNull(message = "Sex is mandatory")
    private SexEnum sex;
    private Nationality nationality;
    private LocalDate birthDate;
    private String phoneNumber;
}
