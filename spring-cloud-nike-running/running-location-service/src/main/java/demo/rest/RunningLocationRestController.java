package demo.rest;

import demo.domain.location;
import demo.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RunningLocationRestController {

    private LocationService locationService;

    @Autowired
    public RunningLocationRestController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping(value = "/running", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody  List<location> runningLocations) {
        this.locationService.saveRunningLocation(runningLocations);
    }

    public void purge() {
        this.locationService.deleteAll();
    }

    //提供两个查询的方法

    @RequestMapping(value = "/running/{movementType}", method = RequestMethod.GET)
    public Page<location> findByMovementType(@PathVariable String movementType, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        return this.locationService.findByRunnerMovementType(movementType, new PageRequest(page, size));
    }


}
