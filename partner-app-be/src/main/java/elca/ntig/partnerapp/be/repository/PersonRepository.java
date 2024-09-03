package elca.ntig.partnerapp.be.repository;

import elca.ntig.partnerapp.be.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
