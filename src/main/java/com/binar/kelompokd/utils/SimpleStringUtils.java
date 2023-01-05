package com.binar.kelompokd.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SimpleStringUtils {
    public Pageable getShort(String orderby, String ordertype, Integer page, Integer size) {
        Pageable show_data;
        if (orderby != null) {
            if (ordertype != null) {
                if (ordertype.equals("desc")) {
                    show_data = PageRequest.of(page, size, Sort.by(orderby).descending());
                } else {
                    show_data = PageRequest.of(page, size, Sort.by(orderby).ascending());
                }
            } else {
                show_data = PageRequest.of(page, size, Sort.by(orderby).descending());
            }

        } else {
            show_data = PageRequest.of(page, size, Sort.by("id").descending());
        }
        return show_data;
    }
}
