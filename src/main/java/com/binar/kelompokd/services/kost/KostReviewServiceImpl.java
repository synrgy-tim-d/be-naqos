package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.interfaces.IKostReviewService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.kost.KostReview;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.kost.KostReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class KostReviewServiceImpl implements IKostReviewService {
  private KostService kostService;
  private IUserAuthService userAuthService;
  private KostReviewRepository kostReviewRepository;
  @Override
  public void addReviewKost(UUID kostId, Long userId, Integer rating, String reviewText) {
    KostReview kostReview = new KostReview();
    Kost kost = kostService.getKostById(kostId);
    Users user = userAuthService.findUsersById(userId);
    kostReview.setKostId(kost);
    kostReview.setUserId(user);
    kostReview.setRating(rating);
    kostReview.setReviewText(reviewText);
    kostReview.setReviewDate(new Date());
    kostReviewRepository.save(kostReview);
  }

  @Override
  public List<KostReview> getReviewByKostId(UUID kostId) {
    return kostReviewRepository.findReviewByKostId(kostId);
  }
}
