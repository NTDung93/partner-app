package elca.ntig.partnerapp.be.repository;

import elca.ntig.partnerapp.be.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
