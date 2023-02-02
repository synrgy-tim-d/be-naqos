package com.binar.kelompokd.repos.kost;

import com.binar.kelompokd.models.entity.kost.KostReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface KostReviewRepository extends JpaRepository<KostReview, Integer> {

  @Query(value = "select * from t_kost_review where kost_id=?1", nativeQuery = true)
  List<KostReview> findReviewByKostId(UUID kostId);
}
