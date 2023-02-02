package com.binar.kelompokd.models.specifications;

import com.binar.kelompokd.models.Filter;
import com.binar.kelompokd.models.QueryParams;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.utils.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KostsSpecificationBuilder {

    public Specification<Kost> build(QueryParams queryParams) {
        ArrayList<Filter> filters = new SpecificationBuilder().toFilters(queryParams.getFilters());

        ArrayList<Filter> searchs = new SpecificationBuilder().convertToFilter(queryParams);

        if (filters.size() == 0 && searchs.size() == 0 && queryParams.getDataPermissions().size() == 0) {
            return null;
        }
        List<Specification> specs = filters.stream()
                .map(KostsSpecification::new)
                .collect(Collectors.toList());

        List<Specification> specs2 = searchs.stream()
                .map(KostsSpecification::new)
                .collect(Collectors.toList());

        List<Specification> specs3 = queryParams.getDataPermissions().stream()
                .map(KostsSpecification::new)
                .collect(Collectors.toList());

        return new SpecificationBuilder().generateSpecPermission(specs, specs2, specs3, filters, searchs, queryParams.getDataPermissions());
    }
}
