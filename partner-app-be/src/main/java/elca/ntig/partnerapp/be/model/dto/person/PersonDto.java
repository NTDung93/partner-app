package elca.ntig.partnerapp.be.model.dto.person;

import elca.ntig.partnerapp.be.model.enums.person.Nationality;
import elca.ntig.partnerapp.be.model.enums.person.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private Integer id;
    private String lastName;
    private String firstName;
    private SexEnum sex;
    private Nationality nationality;
    private LocalDate birthDate;
    private String avsNumber;
    private String maritalStatus;
    private int version;
}
