package CrudUser.kelompokd.Models.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "tb_user_address"
)
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String district;

    private String subsdistrict;

    private String street;

    private Integer postal_code;

    @ManyToOne
    private LocationProvince province_id;

    @ManyToOne
    private LocationCity city_id;
}
