package com.binar.kelompokd.repos.kost;

import com.binar.kelompokd.models.entity.kost.Kost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface KostRepository extends JpaRepository<Kost, UUID> {

    @Query(
            nativeQuery = true,
            value = "select * from t_kost where id = :id and is_available = true"
    )
    Optional<Kost> getKostByIdWhereIsAvailableTrue(@Param("id") UUID id);

    @Query(
            nativeQuery = true,
            value = "select * from t_kost where is_available = true"
    )
    List<Kost> getAllKostWhereIsAvailableTrue();

    @Query(
            nativeQuery = true,
            value = "select * from t_kost where is_available = true"
    )
    Page<Kost> getAllKostWhereIsAvailableTrue(Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "update t_kost set is_available = false where id = :id"
    )
    @Transactional
    @Modifying
    void softDeleteKost(@Param("id") UUID id);

    @Query(
            nativeQuery = true,
            value = "select * from t_kost where kost_type=:kostType"
    )
    Page<Kost> getKostsByKostType(@Param("kostType") String kostType,Pageable pageable);

    Kost findKostById(UUID id);

    Kost getKostByName(String name);
}
