package elca.ntig.partnerapp.be.model.dto.person;

import elca.ntig.partnerapp.be.model.enums.common.Status;
import elca.ntig.partnerapp.be.model.enums.partner.Language;
import elca.ntig.partnerapp.be.model.enums.person.MaritalStatus;
import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private Language language;
    private SexEnum sex;
    private Nationality nationality;
    private String avsNumber;
    private LocalDate birthDate;
    private MaritalStatus maritalStatus;
    private String phoneNumber;
    private Status status;
}
