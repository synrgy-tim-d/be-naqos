package CrudUser.kelompokd.Repository;

import CrudUser.kelompokd.Models.Entity.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Integer> {
}
