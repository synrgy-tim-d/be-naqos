package com.binar.kelompokd.repos.oauth;

import com.binar.kelompokd.models.entity.oauath.Roles;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RoleRepository extends PagingAndSortingRepository<Roles, Long> {
    Roles findOneByName(String name);

    List<Roles> findByNameIn(String[] names);
}
