package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.KostWishlist;

import java.util.List;
import java.util.UUID;

public interface IKostWishlistService {
  List<KostWishlist> getAWishlist(UUID kostId, Long userId);
  void createWishList(KostWishlist wishlist);
  List<KostWishlist> readWishList(Long userId);
  void deleteWishlistByKostIdAndUserId(UUID kostId, Long userId);
}
