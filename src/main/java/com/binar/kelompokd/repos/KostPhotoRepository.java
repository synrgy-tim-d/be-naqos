package com.binar.kelompokd.repos;

import com.binar.kelompokd.models.entity.KostPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostPhotoRepository extends JpaRepository<KostPhoto, Integer> {
}
