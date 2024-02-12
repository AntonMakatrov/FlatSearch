package by.itacademy.flats_service.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import by.itacademy.flats_service.core.dto.FlatFilter;
import by.itacademy.flats_service.core.entity.Flat;
import by.itacademy.flats_service.repository.FlatRepositoryWithFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlatRepositoryWithFiltersImpl implements FlatRepositoryWithFilters {

    private final EntityManagerFactory entityManagerFactory;

    public FlatRepositoryWithFiltersImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Page<Flat> getFlatsWithFilters(Pageable pageable) {
        return getFlatsWithFilters(null, pageable);
    }

    @Override
    public Page<Flat> getFlatsWithFilters(FlatFilter flatFilter, Pageable pageable) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Flat> query = criteriaBuilder.createQuery(Flat.class);
        Root<Flat> root = query.from(Flat.class);
        Predicate finalPredicate = buildPredicate(flatFilter, criteriaBuilder, root);
        query.where(finalPredicate);
        List<Flat> flats = entityManager.createQuery(query)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return new PageImpl<Flat>(flats, pageable, getSearchTotal(flatFilter, criteriaBuilder, entityManager));
    }

    private Predicate buildPredicate(FlatFilter filter, CriteriaBuilder criteriaBuilder, Root<Flat> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getPriceFrom() != null) {
            Predicate priceFromPredicate = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("price"), filter.getPriceFrom());
            predicates.add(priceFromPredicate);
        }
        if (filter.getPriceTo() != null) {
            Predicate priceToPredicate = criteriaBuilder
                    .lessThanOrEqualTo(root.get("price"), filter.getPriceTo());
            predicates.add(priceToPredicate);
        }
        if (filter.getBedroomsFrom() != null) {
            Predicate bedroomsFromPredicate = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("bedrooms"), filter.getBedroomsFrom());
            predicates.add(bedroomsFromPredicate);
        }
        if (filter.getBedroomsTo() != null) {
            Predicate bedroomsToPredicate = criteriaBuilder
                    .lessThanOrEqualTo(root.get("bedrooms"), filter.getBedroomsTo());
            predicates.add(bedroomsToPredicate);
        }
        if (filter.getAreaFrom() != null) {
            Predicate areaFromPredicate = criteriaBuilder
                    .greaterThanOrEqualTo(root.get("area"), filter.getAreaFrom());
            predicates.add(areaFromPredicate);
        }
        if (filter.getAreaTo() != null) {
            Predicate areaToPredicate = criteriaBuilder
                    .lessThanOrEqualTo(root.get("area"), filter.getAreaTo());
            predicates.add(areaToPredicate);
        }
        if (filter.getFloors() != null && filter.getFloors().length != 0) {
            predicates.add(root.<Integer>get("floor").in(List.of(filter.getFloors())));
        }
        if (filter.getPhoto() != null && filter.getPhoto()) {
            predicates.add(root.get("photoUrls").isNotNull());
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    private Long getSearchTotal(FlatFilter filter, CriteriaBuilder criteriaBuilder, EntityManager entityManager) {
        final CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        final Root<Flat> from = countQuery.from(Flat.class);
        countQuery.select(criteriaBuilder.countDistinct(from));

        Predicate predicate = buildPredicate(filter, criteriaBuilder, from);
        if (predicate != null) {
            countQuery.where(predicate);
        }

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
