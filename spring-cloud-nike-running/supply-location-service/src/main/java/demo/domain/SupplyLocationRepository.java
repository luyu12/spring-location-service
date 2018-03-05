package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="locations")
public interface SupplyLocationRepository extends PagingAndSortingRepository<SupplyLocation,String> {
    Page<SupplyLocation> findByCity(@Param("city") String city, Pageable pageable);

    //该方法只查询附近周围圆圈内碰到的第一个点
    SupplyLocation findFirstByLocationNear(@Param("location")Point location);

    //该方法查询的是附近圆圈内所有的点
    Page<SupplyLocation> findAllByLocationNear(@Param("location") Point location);

}
