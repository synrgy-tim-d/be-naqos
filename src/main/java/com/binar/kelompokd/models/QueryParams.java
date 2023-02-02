package com.binar.kelompokd.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Embeddable
public class QueryParams implements Serializable {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private Integer start = 0;

    private Integer limit = 10;

    private Integer page = 0;
    private JSONArray search = new JSONArray();

    private JSONArray fields;

    private JSONArray filters;

    private JSONArray sort;

    @JsonIgnore
    private Pageable pagination;

    private JSONObject params;

    private JSONArray columns;

    @JsonIgnore
    private ArrayList<Filter> dataPermissions = new ArrayList<>();

    public Pageable getPagination() throws Exception {

        org.springframework.data.domain.Sort orders = org.springframework.data.domain.Sort.unsorted();

        if (sort != null) {
            if (sort.length() > 0) {

                ArrayList<Sort> sorts = toArrayObject();

                orders = org.springframework.data.domain.Sort.by(generateMultipleOrders(sorts));
            }
        }

        Integer limit = this.getLimit();

        Integer startPage = limit == -1 ? Math.floorDiv(start, Integer.MAX_VALUE) : Math.floorDiv(start, limit);
        Integer size = limit == -1 ? Integer.MAX_VALUE : limit;
        Integer pageIndex = page < 1 ? 0 : page - 1;

        return PageRequest.of(startPage > pageIndex ? startPage : pageIndex, size, orders);
    }

    private ArrayList<Sort> toArrayObject() {
        ArrayList<Sort> sorts = new ArrayList<>();
        for (int i = 0; i < sort.length(); i++) {
            JSONObject json = new JSONObject(sort.get(i).toString());
            Sort objectSort = new Sort();
            objectSort.setField(json.get("field").toString());
            objectSort.setDir(json.get("dir").toString());
            sorts.add(objectSort);
        }
        return sorts;
    }

    private List<org.springframework.data.domain.Sort.Order> generateMultipleOrders(ArrayList<Sort> sorts) {
        System.out.println(sorts.toString());
        List<org.springframework.data.domain.Sort.Order> listSort = new ArrayList<>();
        for (int i = 0; i < sorts.size(); i++) {
            String field = sorts.get(i).getField().replace("__", "Relations.");
            if (sorts.get(i).getDir().equals("desc")) {
                listSort.add(org.springframework.data.domain.Sort.Order.desc(field));
            } else {
                listSort.add(org.springframework.data.domain.Sort.Order.asc(field));
            }
        }
        return listSort;
    }

    public String getParamString(String name, String defaultValue) {
        String result = defaultValue;
        if (params != null && params.has(name) && params.get(name) != null) {
            result = params.get(name).toString();
        }
        return result;
    }

    public Boolean getParamBoolean(String name, Boolean defaultValue) {
        boolean result = defaultValue;
        if (params != null && params.has(name) && params.get(name) != null) {
            result = (Boolean) params.get(name);
        }
        return result;
    }

    private static Map<String, Object> json2map(JSONObject json) {
        Map<String, Object> result = new HashMap<>();
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = json.get(key);
            if (value instanceof JSONArray) {
                value = json2list((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = json2map((JSONObject) value);
            }
            result.put(key, value);
        }
        return result;
    }

    private static <T> List<T> json2list(JSONArray array) {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = json2list((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = json2map((JSONObject) value);
            }
            result.add((T) value);
        }
        return result;
    }

    public Map<String, Object> getParamObject(String name, Map<String, Object> defaultValue) throws Exception {
        Map<String, Object> result = defaultValue;
        if (params != null && params.has(name) && params.get(name) != null) {
//            result = json2map((JSONObject) params.get(name));
            result = OBJECT_MAPPER.readValue(((JSONObject) params.get(name)).toString(), HashMap.class);
        }
        return result;
    }

    public <T> List<T> getParamList(String name, List<T> defaultvalue) throws Exception {
        List<T> result = defaultvalue;
        if (params != null && params.has(name) && params.get(name) != null) {
//            result = json2list((JSONArray) params.get(name));
            result = OBJECT_MAPPER.readValue(((JSONArray) params.get(name)).toString(), ArrayList.class);
        }
        return result;
    }

    public JSONArray getFiltersByField(String field) {
        JSONArray result = new JSONArray();
        if (filters != null) {
            for (int i = 0; i < filters.length(); i++) {
                JSONObject o = new JSONObject(filters.get(i).toString());
                if (o.get("field").equals(field)) {
                    result.put(o);
                }
            }
        }
        return result;
    }

    public JSONArray excludeFiltersByField(String field) {
        JSONArray result = new JSONArray();
        if (filters != null) {
            for (int i = 0; i < filters.length(); i++) {
                JSONObject o = new JSONObject(filters.get(i).toString());
                if (!o.get("field").equals(field)) {
                    result.put(o);
                }
            }
        }
        return result;
    }
}
