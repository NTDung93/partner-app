package elca.ntig.partnerapp.be.repository.custom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import elca.ntig.partnerapp.be.model.dto.person.SearchPeopleCriteriasDto;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.model.entity.QPerson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import org.apache.log4j.Logger;

public class PersonRepositoryCustomImpl implements PersonRepositoryCustom{
    @PersistenceContext
    private EntityManager em;
    private static Logger logger = Logger.getLogger(PersonRepositoryCustomImpl.class);


    @Override
    public Page<Person> searchPeoplePagination(SearchPeopleCriteriasDto criterias, Pageable pageable) {
        QPerson person = QPerson.person;
        BooleanBuilder builder = new BooleanBuilder();

        // Mandatory criteria
        builder.and(person.lastName.containsIgnoreCase(criterias.getLastName()));

        // Optional criteria
        if (StringUtils.isNotBlank(criterias.getFirstName())) {
            builder.and(person.firstName.containsIgnoreCase(criterias.getFirstName()));
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
            builder.and(person.avsNumber.containsIgnoreCase(criterias.getAvsNumber()));
        }
        if (criterias.getBirthDate() != null) {
            builder.and(person.birthDate.eq(criterias.getBirthDate()));
        }
        if (criterias.getStatus() != null) {
            if (criterias.getStatus().size() == 1) {
                builder.and(person.partner.status.eq(criterias.getStatus().get(0)));
            }
        }

        JPAQuery<Person> query = new JPAQuery<Person>(em).from(person).where(builder);

        // Apply single-column sorting
        if (pageable.getSort().isSorted()) {
            Sort.Order sortOrder = pageable.getSort().iterator().next(); // Get the first sort order
            String property = sortOrder.getProperty();
            Sort.Direction direction = sortOrder.getDirection();

            // Map property to QPerson's fields
            OrderSpecifier<?> orderSpecifier = mapSortPropertyToOrderSpecifier(property, direction, person);
            if (orderSpecifier != null) {
                query.orderBy(orderSpecifier);
            } else {
                // Optional: Apply default sorting if property is unknown
                query.orderBy(person.id.asc());
            }
        } else {
            // Optional: Apply default sorting if no sort is specified
            query.orderBy(person.id.asc());
        }

        // Apply pagination
        List<Person> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = new JPAQuery<Person>(em).from(person)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

    private OrderSpecifier<?> mapSortPropertyToOrderSpecifier(String property, Sort.Direction direction, QPerson person) {
        switch (property) {
            case "id":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.id);
            case "lastName":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.lastName);
            case "firstName":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.firstName);
            case "language":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.partner.language);
            case "gender":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.sex);
            case "nationality":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.nationality);
            case "avsNumber":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.avsNumber);
            case "birthDate":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.birthDate);
            case "civilStatus":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.maritalStatus);
            case "phoneNumber":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.partner.phoneNumber);
            case "status":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, person.partner.status);
            default:
                // Unknown sort property
                logger.warn("Unknown sort property: " + property);
                return null;
        }
    }
}
