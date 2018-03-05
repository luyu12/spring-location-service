package demo.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "locations")
public interface LocationRepository extends JpaRepository<location, Long> {
    //对第一个method进行改造
    @RestResource(path = "runners")
    Page<location> findByRunnerMovementType(@Param("movementType") location.RunnerMovementType movementType,
                                            Pageable pageable);

    Page<location> findByUnitInfoRunningId(@Param("runningId") String runningId, Pageable pageable);

}
