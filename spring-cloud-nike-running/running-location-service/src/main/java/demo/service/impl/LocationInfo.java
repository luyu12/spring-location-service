package demo.service.impl;

import demo.domain.LocationRepository;
import demo.domain.location;
import demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationInfo implements LocationService {

    private LocationRepository locationRepository;

    @Autowired// dependency-injection
    public LocationInfo(LocationRepository locationRepository){
        this.locationRepository=locationRepository;
    }

    @Override
    public List<location> saveRunningLocation(List<location> runningLocations) {

        return locationRepository.save(runningLocations) ;
    }

    @Override
    public void deleteAll() {
        locationRepository.deleteAll();
    }

    @Override
    public Page<location> findByRunnerMovementType(String MovementType, Pageable pageable) {
        return locationRepository.findByRunnerMovementType(location.RunnerMovementType.valueOf(MovementType),pageable);
    }

    @Override
    public Page<location> findByRunningId(String runningId, Pageable pageable) {
        return locationRepository.findByUnitInfoRunningId(runningId,pageable);
    }
}
