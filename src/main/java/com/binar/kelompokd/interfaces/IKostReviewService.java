package com.binar.kelompokd.interfaces;

import com.binar.kelompokd.models.entity.kost.KostReview;

import java.util.List;
import java.util.UUID;

public interface IKostReviewService {
  void addReviewKost(UUID kostId, Long userId, double rating, String reviewText);
  List<KostReview> getReviewByKostId(UUID kostId);
}
