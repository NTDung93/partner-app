package elca.ntig.partnerapp.fe.common.model;

import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationModel {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String sortDir;
    private PartnerTypeProto partnerType;
}
