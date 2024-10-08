package elca.ntig.partnerapp.fe.common.message;

import elca.ntig.partnerapp.common.proto.entity.address.CreateAddressRequestProto;
import elca.ntig.partnerapp.common.proto.enums.common.PartnerTypeProto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAddressMessage {
    private PartnerTypeProto partnerType;
    private CreateAddressRequestProto createAddressRequestProto;
}
