package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
  Image findImageKostByUrl(String url);
}
