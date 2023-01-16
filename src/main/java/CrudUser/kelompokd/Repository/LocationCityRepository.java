package CrudUser.kelompokd.Repository;

import CrudUser.kelompokd.Models.Entity.LocationCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCityRepository extends JpaRepository<LocationCity, Integer> {

}
