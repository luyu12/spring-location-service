package demo.rest;

import demo.model.GpsSimulatorRequest;
import demo.model.Point;
import demo.model.SimulatorInitLocations;
import demo.service.GpsSimulatorFactory;
import demo.service.PathService;
import demo.task.LocationSimulator;
import demo.task.LocationSimulatorInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
public class LocationSImulatorRestApi {
    @Autowired
    private PathService pathService;

    @Autowired
    private GpsSimulatorFactory gpsSimulatorFactory;

    @Autowired
    private AsyncTaskExecutor taskExecutor;

    private Map<Long, LocationSimulatorInstance> taskFutures=new HashMap<>();

    //1.loadSimulatorFixture
    //2.Transform domain model simulator request to a class that can be executed by taskExecutor
    //3.taskExecutor.submit(simulator);
    //4.simulation starts
    @RequestMapping("/simulation")
    public List<LocationSimulatorInstance> simulation() {
        final SimulatorInitLocations fixture = this.pathService.loadSimulatorFixture();

        final List<LocationSimulatorInstance> instances = new ArrayList<>();
        final List<Point> lookAtPoints = new ArrayList<>();

        final Set<Long> instanceIds = new HashSet<>(taskFutures.keySet());

        for (GpsSimulatorRequest gpsSimulatorRequest : fixture.getGpsSimulatorRequests()) {
            final LocationSimulator locationSimulator = gpsSimulatorFactory.prepareGpsSimulator(gpsSimulatorRequest);
            lookAtPoints.add(locationSimulator.getStartPoint());
            instanceIds.add(locationSimulator.getId());

            final Future<?> future = taskExecutor.submit(locationSimulator);
            final LocationSimulatorInstance instance = new LocationSimulatorInstance(locationSimulator.getId(), locationSimulator, future);
            taskFutures.put(locationSimulator.getId(), instance);
            instances.add(instance);
        }
        return instances;

    }

        @RequestMapping("/status")
        public Collection<LocationSimulatorInstance> status(){
            return taskFutures.values();
        }

        @RequestMapping("/cancel")
        public int cancel(){
            int numberOfCalledTasks=0;
            for(Map.Entry<Long,LocationSimulatorInstance> entry:taskFutures.entrySet()) {
                LocationSimulatorInstance instance = entry.getValue();
                instance.getLocationSimulator().cancel();
                boolean wasCancelled = instance.getLocationSimulatorTask().cancel(true);
                if (wasCancelled) {
                    numberOfCalledTasks++;
                }
            }
                taskFutures.clear();
                return numberOfCalledTasks;
        }


}
