package com.binar.kelompokd.repos.oauth;

import com.binar.kelompokd.models.entity.oauth.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
  Client findOneByClientId(String clientId);
}
