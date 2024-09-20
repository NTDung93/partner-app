package elca.ntig.partnerapp.fe.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationTableModel {
    private Integer id;
    private String name;
    private String additionalName;
    private String language;
    private String legalStatus;
    private String ideNumber;
    private String creationDate;
    private String codeNoga;
    private String phoneNumber;
    private String status;
}
