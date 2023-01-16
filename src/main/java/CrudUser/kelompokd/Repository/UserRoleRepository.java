package CrudUser.kelompokd.Repository;

import CrudUser.kelompokd.Models.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    //@Query(value = "SELECT tb_user_role FROM tb_user_role")
    //public List<UserRole> findtb_user_role();

}
