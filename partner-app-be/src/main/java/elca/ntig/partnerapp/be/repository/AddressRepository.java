package elca.ntig.partnerapp.be.repository;

import elca.ntig.partnerapp.be.model.entity.Address;
import elca.ntig.partnerapp.be.model.enums.addess.AddressType;
import elca.ntig.partnerapp.be.model.enums.common.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByPartnerIdAndStatus(Integer partnerId, Status status);
    List<Address> findByPartnerIdAndStatusAndCategory(Integer partnerId, Status status, AddressType category);
}
