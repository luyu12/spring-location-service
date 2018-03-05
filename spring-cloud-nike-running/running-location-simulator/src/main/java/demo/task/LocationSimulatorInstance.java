package demo.task;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.Future;

@Data
@AllArgsConstructor(access= AccessLevel.PUBLIC)
public class LocationSimulatorInstance {
    private long instanceId;
    private LocationSimulator locationSimulator;
    private Future<?> locationSimulatorTask;

    @Override
    public String toString(){
        return "LocationSimulatorInstance [instanceId="+instanceId+", locationSimulator="+locationSimulator+
                ", locationSimulatorTask="+locationSimulatorTask+"]";


    }
}
