package com.binar.kelompokd.repos.kost;

import com.binar.kelompokd.models.entity.kost.KostWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public interface KostWishlistRepository extends JpaRepository<KostWishlist, Integer> {
  @Query(value = "select * from t_user_wishlist where user_id=?1 order by created_at desc ", nativeQuery = true)
  List<KostWishlist> findAllByUserIdOrderByCreatedAtDesc(Long userId);

  @Query(value = "select * from t_user_wishlist where kost_id=?1 and user_id=?2", nativeQuery = true)
  List<KostWishlist> getAWishlistByKostIdAndUserId(UUID kostId, Long userId);

  @Modifying
  @Query(value = "delete from t_user_wishlist where kost_id=?1 and user_id=?2", nativeQuery = true)
  void deleteWishlistByKostIdAndUserId(UUID kostId, Long userId);
}
