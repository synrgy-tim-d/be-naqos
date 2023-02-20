package com.binar.kelompokd.repos.kost;

import com.binar.kelompokd.models.entity.kost.Kost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public interface KostRepository extends JpaRepository<Kost, UUID>, JpaSpecificationExecutor<Kost> {

    @Query(
            nativeQuery = true,
            value = "update t_kost set is_available = false where id = :id"
    )
    @Transactional
    @Modifying
    void softDeleteKost(@Param("id") UUID id);

    Kost getKostByName(String name);

    void deleteKostById(UUID id);
}
