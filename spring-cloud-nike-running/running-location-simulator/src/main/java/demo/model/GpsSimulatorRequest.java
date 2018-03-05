package demo.model;

import lombok.Data;

@Data
public class GpsSimulatorRequest {
    private String runningId;
    private Double speed;
    private  boolean move=true;
    private boolean exportPositionsToMessaging=true;
    private Integer reportInterval=500;
    private int secondsToError=0;
    private RunnerStatus runnerStatus=RunnerStatus.NONE;
    private String polyline;

    private MedicalInfo medicalInfo;



    @Override
    public String toString(){
        return "GpsSimulatorRequest [runningId="+runningId+", speed=" +speed+", move="+move+
                ", reportInterval="+reportInterval+"]";
    }

}
