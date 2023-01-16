package CrudUser.kelompokd.Models.Entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "tb_user_otp"
)
public class UserOtp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int value;
    private Time expiried_time;
}
