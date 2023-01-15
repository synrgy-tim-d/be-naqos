package com.binar.kelompokd.repos.oauth;

import com.binar.kelompokd.models.oauath.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Client findOneByClientId(String clientId);

}
