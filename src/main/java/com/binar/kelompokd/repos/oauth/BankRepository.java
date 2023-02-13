package com.binar.kelompokd.repos.oauth;

import com.binar.kelompokd.models.entity.oauth.Bank;
import com.binar.kelompokd.models.entity.oauth.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BankRepository extends PagingAndSortingRepository<Bank, Long> {
  Bank findByName(String name);
}
