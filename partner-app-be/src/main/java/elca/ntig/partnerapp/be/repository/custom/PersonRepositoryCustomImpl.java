package elca.ntig.partnerapp.be.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.model.entity.QPerson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

public class PersonRepositoryCustomImpl implements PersonRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Person> searchPeoplePagination(SearchPeopleCriteriasDto criterias, Pageable pageable) {
        QPerson person = QPerson.person;
        BooleanBuilder builder = new BooleanBuilder();

        // Mandatory criteria
        builder.and(person.lastName.eq(criterias.getLastName()));

        // Optional criteria
        if (StringUtils.isNotBlank(criterias.getFirstName())) {
            builder.and(person.firstName.eq(criterias.getFirstName()));
        }
        if (criterias.getLanguage() != null) {
            builder.and(person.partner.language.eq(criterias.getLanguage()));
        }
        if (criterias.getSex() != null) {
            builder.and(person.sex.eq(criterias.getSex()));
        }
        if (criterias.getNationality() != null) {
            builder.and(person.nationality.eq(criterias.getNationality()));
        }
        if (StringUtils.isNotBlank(criterias.getAvsNumber())) {
            builder.and(person.avsNumber.eq(criterias.getAvsNumber()));
        }
        if (criterias.getBirthDate() != null) {
            builder.and(person.birthDate.eq(criterias.getBirthDate()));
        }
        if (criterias.getStatus() != null) {
            builder.and(person.partner.status.eq(criterias.getStatus()));
        }

        List<Person> results = new JPAQuery<Person>(em).from(person)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = new JPAQuery<Person>(em).from(person)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }
}
