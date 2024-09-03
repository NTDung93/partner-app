package elca.ntig.partnerapp.be.repository;

import elca.ntig.partnerapp.be.model.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
}
