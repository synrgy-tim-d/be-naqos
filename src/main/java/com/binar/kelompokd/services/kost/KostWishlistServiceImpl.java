package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.interfaces.IKostWishlistService;
import com.binar.kelompokd.models.entity.kost.KostWishlist;
import com.binar.kelompokd.repos.kost.KostWishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class KostWishlistServiceImpl implements IKostWishlistService {
  private KostWishlistRepository wishlistRepository;
  @Override
  public List<KostWishlist> getAWishlist(UUID kostId, Long userId) {
    return wishlistRepository.getAWishlistByKostIdAndUserId(kostId, userId);
  }

  @Override
  public void createWishList(KostWishlist wishlist) {
    wishlistRepository.save(wishlist);
  }

  @Override
  public List<KostWishlist> readWishList(Long userId) {
    return wishlistRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
  }

  @Override
  public void deleteWishlistByKostIdAndUserId(UUID kostId, Long userId) {
    wishlistRepository.deleteWishlistByKostIdAndUserId(kostId, userId);
  }
}
