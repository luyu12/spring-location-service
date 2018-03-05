package demo.service;

import demo.domain.location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    List<location> saveRunningLocation(List<location> runningLocation);

    void deleteAll();

    Page<location> findByRunnerMovementType(String MovementType, Pageable pageable);

    Page<location> findByRunningId(String runningId, Pageable pageable);

}
