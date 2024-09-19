package elca.ntig.partnerapp.be.repository;

import elca.ntig.partnerapp.be.model.entity.Organisation;
import elca.ntig.partnerapp.be.repository.custom.OrganisationRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer>, QuerydslPredicateExecutor<Organisation>, OrganisationRepositoryCustom {
}
