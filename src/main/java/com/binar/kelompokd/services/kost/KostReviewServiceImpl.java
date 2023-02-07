package com.binar.kelompokd.services.kost;

import com.binar.kelompokd.interfaces.IKostReviewService;
import com.binar.kelompokd.interfaces.IUserAuthService;
import com.binar.kelompokd.interfaces.KostService;
import com.binar.kelompokd.models.entity.kost.Kost;
import com.binar.kelompokd.models.entity.kost.KostReview;
import com.binar.kelompokd.models.entity.oauth.Users;
import com.binar.kelompokd.repos.kost.KostRepository;
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
  private KostRepository kostRepository;
  @Override
  public void addReviewKost(UUID kostId, Long userId, double rating, String reviewText) {
    KostReview kostReview = new KostReview();
    Kost kost = kostService.getKostById(kostId);
    Users user = userAuthService.findUsersById(userId);
    kostReview.setKostId(kost);
    kostReview.setUserId(user);
    kostReview.setRating(rating);
    kostReview.setReviewText(reviewText);
    kostReview.setReviewDate(new Date());
    kostReviewRepository.save(kostReview);
    calculateMeanKostRating(kostId);
  }

  @Override
  public List<KostReview> getReviewByKostId(UUID kostId) {
    return kostReviewRepository.findReviewByKostId(kostId);
  }

  @Override
  public void calculateMeanKostRating(UUID kostId){
    Kost kost = kostService.getKostById(kostId);
    List<KostReview> reviews = kostReviewRepository.findReviewByKostId(kostId);
    double totalRating=0;
    for (KostReview review: reviews){
      totalRating += review.getRating();
    }
    double meanRating = reviews.isEmpty() ? 0 : totalRating/reviews.size();
    kost.setKostRating(meanRating);
    kostRepository.save(kost);
  }
}
