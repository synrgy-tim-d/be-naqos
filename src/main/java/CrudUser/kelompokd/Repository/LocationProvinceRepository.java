package CrudUser.kelompokd.Repository;

import CrudUser.kelompokd.Models.Entity.LocationProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationProvinceRepository extends JpaRepository<LocationProvince, Integer> {
}
