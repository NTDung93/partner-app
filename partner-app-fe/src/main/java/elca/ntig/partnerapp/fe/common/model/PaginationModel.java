package elca.ntig.partnerapp.fe.common.model;

import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationModel {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String sortDir;
    private PartnerTypeProto partnerType;
}
