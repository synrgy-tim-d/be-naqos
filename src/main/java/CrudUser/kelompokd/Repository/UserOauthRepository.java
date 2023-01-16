package CrudUser.kelompokd.Repository;

import CrudUser.kelompokd.Models.Entity.UserOauth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOauthRepository extends JpaRepository<UserOauth, Integer> {
}
