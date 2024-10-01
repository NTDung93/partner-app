package elca.ntig.partnerapp.fe.common.message;

import elca.ntig.partnerapp.common.proto.entity.address.CreateAddressRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import elca.ntig.partnerapp.common.proto.enums.common.StatusProto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAddressMessage {
    private PartnerTypeProto partnerType;
    private CreateAddressRequestProto updateAddressRequestProto;
    private StatusProto status;
}
