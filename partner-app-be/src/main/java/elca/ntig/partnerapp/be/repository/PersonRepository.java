package elca.ntig.partnerapp.be.repository;

import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.repository.custom.PersonRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PersonRepository extends JpaRepository<Person, Integer>, QuerydslPredicateExecutor<Person>, PersonRepositoryCustom {
}
