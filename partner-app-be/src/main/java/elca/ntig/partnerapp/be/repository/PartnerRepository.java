package elca.ntig.partnerapp.be.repository;

import elca.ntig.partnerapp.be.model.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {

}
