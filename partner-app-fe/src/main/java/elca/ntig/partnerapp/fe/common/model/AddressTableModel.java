package elca.ntig.partnerapp.fe.common.model;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressTableModel {
    private String street;
    private String npaAndLocality;
    private String canton;
    private String country;
    private String addressType;
    private String validityStart;
    private String validityEnd;
    private String status;
}
