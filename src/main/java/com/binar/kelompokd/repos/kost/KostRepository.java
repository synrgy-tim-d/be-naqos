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

    Kost getKostByName(String name);

    @Query(
            nativeQuery = true,
            value = "select * from t_kost where city_id=:cityId"
    )
    Page<Kost> getKostsByCityId(@Param("cityId") Integer cityId, Pageable pageable);

    @Modifying
    @Query(value = "update t_kost set name=?2, description=?3, kost_type=?4, is_available=?5, latitude=?6, longitude=?7, address=?8, subdistrict=?9, district=?10, postal_code=?11, city_id=?12 where id=?1", nativeQuery = true)
    Integer updateKost(UUID uuid, String name, String description, String kostType, Boolean isAvailable, Double latitude, Double longitude, String address, String subdistrict, String district, String postalCode, Integer city);

    void deleteKostById(UUID id);
}
