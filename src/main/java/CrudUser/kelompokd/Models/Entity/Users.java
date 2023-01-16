package CrudUser.kelompokd.Models.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.DateTimeException;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tb_user"
)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String first_name;

    private String last_name;

    private String username;

    private String phone;

    private String email;

    private String password;

    private boolean gender;

    private Date birth_date;

    private String bank_number;

    private boolean is_active;

    private DateTimeException created_at;

    private DateTimeException update_at;

    @ManyToOne
    private UserAddress address_id;

    @ManyToOne
    private UserRole role_id;

    @ManyToOne
    private UserOtp otp_id;

    @ManyToOne
    private UserImage image_id;

    @ManyToOne
    private UserOauth oauth_id;
}