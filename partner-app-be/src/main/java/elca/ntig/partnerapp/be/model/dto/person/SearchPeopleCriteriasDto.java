package elca.ntig.partnerapp.be.model.dto.person;

import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPeopleCriteriasDto {
    @NotEmpty(message = "Person's last name cannot be blank")
    @Size(min = 2, message = "Person's last name must have at least 2 characters")
    private String lastName;
    private String firstName;
    private Language language;
    private SexEnum sex;
    private Nationality nationality;
    private String avsNumber;
    private LocalDate birthDate;
    private List<Status> status;
}
