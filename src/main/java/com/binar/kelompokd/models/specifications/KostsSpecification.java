package com.binar.kelompokd.models.specifications;

import com.binar.kelompokd.models.Filter;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.utils.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class KostsSpecification implements Specification<Kost> {

    private final Filter filter;

    public KostsSpecification(Filter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Kost> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);
        return new SpecificationBuilder().getPredicate(root, criteriaBuilder, filter);
    }
}
