package elca.ntig.partnerapp.be.repository.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import elca.ntig.partnerapp.be.model.dto.organisation.SearchOrganisationCriteriasDto;
import elca.ntig.partnerapp.be.model.entity.Organisation;
import elca.ntig.partnerapp.be.model.entity.Person;
import elca.ntig.partnerapp.be.model.entity.QOrganisation;
import elca.ntig.partnerapp.be.model.entity.QPerson;
import elca.ntig.partnerapp.be.repository.custom.OrganisationRepositoryCustom;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class OrganisationRepositoryCustomImpl implements OrganisationRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    private static Logger logger = Logger.getLogger(OrganisationRepositoryCustomImpl.class);

    @Override
    public Page<Organisation> searchOrganisationPagination(SearchOrganisationCriteriasDto criterias, Pageable pageable) {
        QOrganisation organisation = QOrganisation.organisation;
        BooleanBuilder builder = new BooleanBuilder();

        // Mandatory criteria
        builder.and(organisation.name.containsIgnoreCase(criterias.getName()));

        // Optional criteria
        if (StringUtils.isNotBlank(criterias.getAdditionalName())) {
            builder.and(organisation.additionalName.containsIgnoreCase(criterias.getAdditionalName()));
        }
        if (criterias.getLanguage() != null) {
            builder.and(organisation.partner.language.eq(criterias.getLanguage()));
        }
        if (criterias.getLegalStatus() != null) {
            builder.and(organisation.legalStatus.eq(criterias.getLegalStatus()));
        }
        if (StringUtils.isNotBlank(criterias.getIdeNumber())) {
            builder.and(organisation.ideNumber.containsIgnoreCase(criterias.getIdeNumber()));
        }
        if (criterias.getCreationDate() != null) {
            builder.and(organisation.creationDate.eq(criterias.getCreationDate()));
        }
        if (criterias.getStatus() != null) {
            if (criterias.getStatus().size() == 1) {
                builder.and(organisation.partner.status.eq(criterias.getStatus().get(0)));
            }
        }

        JPAQuery<Organisation> query = new JPAQuery<Organisation>(em).from(organisation).where(builder);
        Sort.Order sortOrder = pageable.getSort().iterator().next();
        String property = sortOrder.getProperty();
        Sort.Direction direction = sortOrder.getDirection();

        OrderSpecifier<?> orderSpecifier = mapSortPropertyToOrderSpecifier(property, direction, organisation);
        if (orderSpecifier != null) {
            query.orderBy(orderSpecifier);
        } else {
            query.orderBy(organisation.id.asc());
        }

        List<Organisation> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = new JPAQuery<Organisation>(em).from(organisation)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);
    }

    private OrderSpecifier<?> mapSortPropertyToOrderSpecifier(String property, Sort.Direction direction, QOrganisation organisation) {
        switch (property) {
            case "id":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.id);
            case "name":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.name);
            case "additionalName":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.additionalName);
            case "language":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.partner.language);
            case "legalStatus":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.legalStatus);
            case "ideNumber":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.ideNumber);
            case "creationDate":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.creationDate);
            case "codeNoga":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.codeNoga);
            case "phoneNumber":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.partner.phoneNumber);
            case "status":
                return new OrderSpecifier<>(
                        direction.isAscending() ? Order.ASC : Order.DESC, organisation.partner.status);
            default:
                logger.warn("Unknown sort property: " + property);
                return null;
        }
    }
}
