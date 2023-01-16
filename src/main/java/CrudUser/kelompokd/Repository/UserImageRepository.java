package CrudUser.kelompokd.Repository;

import CrudUser.kelompokd.Models.Entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
}
