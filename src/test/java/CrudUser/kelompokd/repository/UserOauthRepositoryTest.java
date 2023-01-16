package CrudUser.kelompokd.repository;

import CrudUser.kelompokd.Models.Entity.UserOauth;
import CrudUser.kelompokd.Repository.UserOauthRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserOauthRepositoryTest {
    @Autowired
    UserOauthRepository userOauthRepository;

    @Test
    void testAddData(){
        UserOauth userOath = new UserOauth();
        userOath.setProvider_name("kiw kiw");
        userOath.setAccess_token("aji aji");
        userOath.setRefresh_token("kiu kiw");
        userOath.setExpired_date("aku aku");

        //userOauthRepository.save(userOath);
        userOauthRepository.delete(userOath);
    }

}
