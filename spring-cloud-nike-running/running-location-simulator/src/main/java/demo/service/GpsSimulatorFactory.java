package demo.service;

import demo.model.GpsSimulatorRequest;
import demo.model.Point;
import demo.task.LocationSimulator;

import javax.xml.stream.Location;
import java.util.List;

public interface GpsSimulatorFactory {
    LocationSimulator prepareGpsSimulator(GpsSimulatorRequest gpsSimulatorRequest);

    LocationSimulator prepareGpsSimulator(LocationSimulator locationSimulator, List<Point> points);
}
