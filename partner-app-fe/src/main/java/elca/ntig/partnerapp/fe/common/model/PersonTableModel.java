package elca.ntig.partnerapp.fe.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonTableModel {
    private Integer id;
    private String lastName;
    private String firstName;
    private String language;
    private String gender;
    private String nationality;
    private String avsNumber;
    private String birthDate;
    private String civilStatus;
    private String phoneNumber;
    private String status;
}
