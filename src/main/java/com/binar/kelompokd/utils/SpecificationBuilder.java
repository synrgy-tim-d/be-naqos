package com.binar.kelompokd.utils;

import com.binar.kelompokd.models.Filter;
import com.binar.kelompokd.models.QueryParams;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import javax.annotation.Nullable;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecificationBuilder {

    public ArrayList<Filter> toFilters(JSONArray jsonArray) {
        ArrayList<Filter> list = new ArrayList<>();
        if (jsonArray == null) {
            return list;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = new JSONObject(jsonArray.get(i).toString());
            Filter filter = new Filter();
            filter.setField(json.get("field").toString());
            filter.setValue(json.get("value").toString());
            filter.setOp(json.get("op").toString());
            if (json.get("valueIn") != null) {
                List<String> valueIn = new ArrayList<>();
                for (int j = 0; j < ((JSONArray) json.get("valueIn")).length(); j++) {
                    valueIn.add(((JSONArray) json.get("valueIn")).getString(j));
                }
                filter.setValueIn(valueIn);
            }
            list.add(filter);
        }
        return list;
    }

    public ArrayList<Filter> convertToFilter(QueryParams queryParams) {
        ArrayList<Filter> list = new ArrayList<>();
        if (queryParams.getSearch() == null || queryParams.getFields() == null) {
            return list;
        }
        for (int i = 0; i < queryParams.getSearch().length(); i++) {
            for (int j = 0; j < queryParams.getFields().length(); j++) {
                String field = queryParams.getFields().getString(j);
                String search = queryParams.getSearch().getString(i);
                Filter filter = new Filter();
                filter.setField(field);
                filter.setValue(search);
                filter.setOp("like");
                list.add(filter);
            }
        }
        return list;
    }

    public Specification generateSpec(List<Specification> specs, ArrayList<Filter> filters) {
        Specification result = specs.get(0);

        for (int i = 1; i < filters.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }

    public Specification generateSpec2(List<Specification> specs, List<Specification> specs2, ArrayList<Filter> filters, ArrayList<Filter> searchs) {
        Specification result = specs.size() > 0 ? specs.get(0) : null;
        Specification result2 = specs2.size() > 0 ? specs2.get(0) : null;

        for (int i = 1; i < filters.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }

        for (int i = 1; i < searchs.size(); i++) {
            result2 = Specification.where(result2)
                    .or(specs2.get(i));
        }
        if (specs.size() > 0 && specs2.size() > 0) {
            return result.and(result2);
        } else if (specs.size() > 0 && specs2.size() < 1) {
            return result;
        } else if (specs.size() < 1 && specs2.size() > 0) {
            return result2;
        } else {
            return null;
        }
    }

    public Specification generateSpecPermission(List<Specification> specs, List<Specification> specs2, List<Specification> specs3, ArrayList<Filter> filters, ArrayList<Filter> searchs, ArrayList<Filter> dataPermissions) {
        Specification result = specs.size() > 0 ? specs.get(0) : null;
        Specification result2 = specs2.size() > 0 ? specs2.get(0) : null;
        Specification result3 = specs3.size() > 0 ? specs3.get(0) : null;

        for (int i = 1; i < filters.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }

        for (int i = 1; i < searchs.size(); i++) {
            result2 = Specification.where(result2)
                    .or(specs2.get(i));
        }

        for (int i = 1; i < dataPermissions.size(); i++) {
            if (dataPermissions.get(i).getJoin().equals("&")) {
                result3 = Specification.where(result3)
                        .and(specs3.get(i));
            } else {
                result3 = Specification.where(result3)
                        .or(specs3.get(i));
            }
        }

        if (specs.size() > 0 && specs2.size() > 0 && specs3.size() > 0) {
            return result.and(result2).and(result3);
        } else if (specs.size() > 0 && specs2.size() > 0) {
            return result.and(result2);
        } else if (specs.size() > 0 && specs3.size() > 0) {
            return result.and(result3);
        } else if (specs2.size() > 0 && specs3.size() > 0) {
            return result2.and(result3);
        } else if (specs.size() > 0 && specs2.size() < 1 && specs3.size() < 1) {
            return result;
        } else if (specs.size() < 1 && specs2.size() > 0 && specs3.size() < 1) {
            return result2;
        } else if (specs.size() < 1 && specs2.size() < 1 && specs3.size() > 0) {
            return result3;
        } else {
            return null;
        }
    }

    public Predicate getPredicate(Root<?> root, CriteriaBuilder criteriaBuilder, Filter filter) {
        String[] value = filter.getField().replace("__", ".").split("\\.");
        if (value.length >= 2) {
            return getPredicateRelation(root, criteriaBuilder, filter, value);
        } else {
            return getPredicateNoRelation(root, criteriaBuilder, filter);
        }
    }

    @Nullable
    private Predicate getPredicateRelation(Root<?> root, CriteriaBuilder criteriaBuilder, Filter filter, String[] value) {
        Path path = null;

//        if (value.length == 2) {
//            path = root.join(value[0].concat("Relations"), JoinType.LEFT).get(value[1]);
//        } else {
//            path = root.join(value[0].concat("Relations"), JoinType.LEFT).join(value[1].concat("Relations"), JoinType.LEFT).get(value[2]);
//        }

        Join<?,?> relations = root.join(value[0], JoinType.LEFT);

        if (value.length > 2) {
            for(int i = 1; i < value.length - 1; i++) {
                relations = relations.join(value[i], JoinType.LEFT);
            }
        }

        path = relations.get(value[value.length - 1]);

        if (filter.getOp().equals(">")) {
            return criteriaBuilder.greaterThan(
                    path.as(String.class), filter.getValue());
        } else if (filter.getOp().equals("<")) {
            return criteriaBuilder.lessThan(
                    path.as(String.class), filter.getValue());
        } else if (filter.getOp().equals(">=")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    path.as(String.class), filter.getValue());
        } else if (filter.getOp().equals("<=")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    path.as(String.class), filter.getValue());
        } else if (filter.getOp().equals("^")) {
            return criteriaBuilder.like(
                    path.as(String.class), filter.getValue().concat("%"));
        } else if (filter.getOp().equals("%")) {
            return criteriaBuilder.like(
                    path.as(String.class), "%".concat(filter.getValue()));
        } else if (filter.getOp().equals("like")) {
            return criteriaBuilder.like(
                    criteriaBuilder.lower(path.as(String.class)), "%".concat(filter.getValue().toLowerCase()).concat("%"));
        } else if (filter.getOp().equals("null")) {
            return criteriaBuilder.isNull(path.as(String.class));
        } else if (filter.getOp().equals("notnull")) {
            return criteriaBuilder.isNotNull(path.as(String.class));
        } else if (filter.getOp().equals("between")) {
            if (filter.getValueIn().size() < 2)
                return null;
            return criteriaBuilder.between(
                    path.as(String.class), filter.getValueIn().get(0), filter.getValueIn().get(1));
        } else if (filter.getOp().equals("in")) {
            if (filter.getValueIn().size() < 1)
                return null;
            return path.as(String.class).in(filter.getValueIn());
        } else if (filter.getOp().equals("=")) {
            return criteriaBuilder.equal(
                    path.as(String.class), filter.getValue());
        }else if (filter.getOp().equals("!=")) {
            return criteriaBuilder.notEqual(
                    path.as(String.class), filter.getValue());
        }
        return null;
    }

    @Nullable
    private Predicate getPredicateNoRelation(Root<?> root, CriteriaBuilder criteriaBuilder, Filter filter) {
        if (filter.getOp().equals(">")) {
            return criteriaBuilder.greaterThan(
                    root.get(filter.getField()).as(String.class), filter.getValue());
        } else if (filter.getOp().equals("<")) {
            return criteriaBuilder.lessThan(
                    root.get(filter.getField()).as(String.class), filter.getValue());
        } else if (filter.getOp().equals(">=")) {
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(filter.getField()).as(String.class), filter.getValue());
        } else if (filter.getOp().equals("<=")) {
            return criteriaBuilder.lessThanOrEqualTo(
                    root.get(filter.getField()).as(String.class), filter.getValue());
        } else if (filter.getOp().equals("^")) {
            return criteriaBuilder.like(
                    root.get(filter.getField()).as(String.class), filter.getValue().concat("%"));
        } else if (filter.getOp().equals("%")) {
            return criteriaBuilder.like(
                    root.get(filter.getField()).as(String.class), "%".concat(filter.getValue()));
        } else if (filter.getOp().equals("like")) {
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get(filter.getField()).as(String.class)), "%".concat(filter.getValue().toLowerCase()).concat("%"));
        } else if (filter.getOp().equals("null")) {
            return criteriaBuilder.isNull(root.get(filter.getField()));
        } else if (filter.getOp().equals("notnull")) {
            return criteriaBuilder.isNotNull(root.get(filter.getField()));
        } else if (filter.getOp().equals("between")) {
            if (filter.getValueIn().size() < 2)
                return null;
            return criteriaBuilder.between(
                    root.get(filter.getField()).as(String.class), filter.getValueIn().get(0), filter.getValueIn().get(1));
        } else if (filter.getOp().equals("in")) {
            if (filter.getValueIn().size() < 1) {
                return null;
            }
            return root.get(filter.getField()).as(String.class).in(filter.getValueIn());
        } else if (filter.getOp().equals("=")) {
            return criteriaBuilder.equal(
                    root.get(filter.getField()).as(String.class), filter.getValue());
        }
        return null;
    }
}
