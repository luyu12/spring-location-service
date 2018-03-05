package demo.task;


import demo.model.*;
import demo.service.PositionService;
import demo.support.NavUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/*
1.先启动Running location simulator backend serivce -> provide REST API to start simulation, cancel simulation(要提供这两个功能)

2.


 */
public class LocationSimulator implements Runnable{
    private long id;

    private PositionService positionInfoService;

    private AtomicBoolean cancel=new AtomicBoolean();

    public synchronized void cancel(){
        this.cancel.set(true);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PositionService getPositionInfoService() {
        return positionInfoService;
    }

    public void setPositionInfoService(PositionService positionInfoService) {
        this.positionInfoService = positionInfoService;
    }

    public AtomicBoolean getCancel() {
        return cancel;
    }



    public void setCancel(AtomicBoolean cancel) {
        this.cancel = cancel;
    }

    public Double getSpeedInmps() {
        return speedInmps;
    }

    public void setSpeedInmps(Double speedInmps) {
        this.speedInmps = speedInmps;
    }

    public boolean isShouldMove() {
        return shouldMove;
    }

    public void setShouldMove(boolean shouldMove) {
        this.shouldMove = shouldMove;
    }

    public boolean isExportPositionsToMessaging() {
        return exportPositionsToMessaging;
    }

    public void setExportPositionsToMessaging(boolean exportPositionsToMessaging) {
        this.exportPositionsToMessaging = exportPositionsToMessaging;
    }

    public Integer getReportInterval() {
        return reportInterval;
    }

    public void setReportInterval(Integer reportInterval) {
        this.reportInterval = reportInterval;
    }

    public PositionInfo getPositionInfo() {
        return positionInfo;
    }

    public void setPositionInfo(PositionInfo positionInfo) {
        this.positionInfo = positionInfo;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public RunnerStatus getRunnerStatus() {
        return runnerStatus;
    }

    public void setRunnerStatus(RunnerStatus runnerStatus) {
        this.runnerStatus = runnerStatus;
    }

    public String getRunningId() {
        return runningId;
    }

    public void setRunningId(String runningId) {
        this.runningId = runningId;
    }

    public Integer getSecondsToError() {
        return secondsToError;
    }

    public void setSecondsToError(Integer secondsToError) {
        this.secondsToError = secondsToError;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Date getExecutionStartTime() {
        return executionStartTime;
    }

    public void setExecutionStartTime(Date executionStartTime) {
        this.executionStartTime = executionStartTime;
    }

    public MedicalInfo getMedicalInfo() {
        return medicalInfo;
    }

    public void setMedicalInfo(MedicalInfo medicalInfo) {
        this.medicalInfo = medicalInfo;
    }

    private Double speedInmps;
    private boolean shouldMove;
    private boolean exportPositionsToMessaging=true;

    private Integer reportInterval=500;
    private PositionInfo positionInfo=null;
    private List<Leg> legs;
    private RunnerStatus runnerStatus=RunnerStatus.NONE;
    private String runningId;

    private Integer secondsToError=45;
    private Point startPoint;
    private Date executionStartTime;

    private MedicalInfo medicalInfo;

    public LocationSimulator(GpsSimulatorRequest gpsSimulatorRequest) {
        this.shouldMove = gpsSimulatorRequest.isMove();
        this.exportPositionsToMessaging =gpsSimulatorRequest.isExportPositionsToMessaging();
        this.setSpeed(gpsSimulatorRequest.getSpeed());
        this.reportInterval = gpsSimulatorRequest.getReportInterval();

        this.secondsToError = gpsSimulatorRequest.getSecondsToError();
        this.runnerStatus = gpsSimulatorRequest.getRunnerStatus();

        this.medicalInfo=gpsSimulatorRequest.getMedicalInfo();
    }

    public void setCurrentPosition(PositionInfo currentPosition) {
        this.positionInfo = currentPosition;
    }

    @Override
    public void run() {
        try{
            executionStartTime=new Date();
            if(cancel.get()){
                destroy();
                return;
            }
            //当线程正在执行的时候
            while (!Thread.interrupted()){
                long startTime=new Date().getTime();
                if(positionInfo!=null){
                    //如果没有走到终点还要继续跑的话
                    if(shouldMove){
                        moveRunningLocation();
                        positionInfo.setSpeed(speedInmps);
                    }else{
                        positionInfo.setSpeed(0.0);
                    }

                    if(this.secondsToError>0&&startTime-executionStartTime.getTime()>=this.secondsToError*1000){
                        this.runnerStatus=RunnerStatus.SUPPLY_NOW;
                    }

                    positionInfo.setRunnerStatus(this.runnerStatus);

                    final MedicalInfo medicalInfoToUse;

                    switch(this.runnerStatus){
                        case SUPPLY_SOON:
                        case SUPPLY_NOW:
                        case STOP_NOW:
                            medicalInfoToUse=this.medicalInfo;
                            break;
                        default:
                            medicalInfoToUse=null;
                            break;
                    }

                    final CurrentPosition currentPosition=new CurrentPosition(
                            positionInfo.getRunningId(),
                            new Point(positionInfo.getPosition().getLatitude(),positionInfo.getPosition().getLongitude()),
                            positionInfo.getRunnerStatus(),
                            positionInfo.getSpeed(),
                            positionInfo.getLeg().getHeading(),
                            medicalInfoToUse);
                    positionInfoService.processPositionInfo(id,currentPosition,this.exportPositionsToMessaging);
                }

                sleep(startTime);
            }
        }catch(Exception e){
            destroy();
            return;
        }

        destroy();

    }

    private void sleep(long startTime) throws InterruptedException{
        long endTime=new Date().getTime();
        long elapsedTime=endTime-startTime;
        long sleepTime=reportInterval-elapsedTime>0? reportInterval-elapsedTime:0;
        Thread.sleep(sleepTime);
    }

    private void moveRunningLocation() {
        Double distance=speedInmps*reportInterval/1000.0;
        Double distanceFromStart=positionInfo.getDistanceFromStart()+distance;
        Double excess=0.0;

        for(int i=positionInfo.getLeg().getId();i<legs.size();i++){
            Leg currentLeg=legs.get(i);
            excess=distanceFromStart>currentLeg.getLength()? distanceFromStart-currentLeg.getLength():0.0 ;

            if(Double.doubleToRawLongBits(excess)==0){
                //this means new position falls within current leg
                positionInfo.setDistanceFromStart(distanceFromStart);
                positionInfo.setLeg(currentLeg);
                Point newPosition= NavUtils.getPosition(currentLeg.getStartPosition(),distanceFromStart,currentLeg.getHeading());
                positionInfo.setPosition(newPosition);
                return;
            }
            distanceFromStart=excess;
        }
        setStartPosition();
    }

    public void setStartPosition() {
        positionInfo=new PositionInfo();
        positionInfo.setRunningId(this.runningId);
        Leg leg=legs.get(0);
        positionInfo.setLeg(leg);
        positionInfo.setPosition(leg.getStartPosition());
        positionInfo.setDistanceFromStart(0.0);
    }

    private void destroy() {
        positionInfo=null;
    }

    public void setSpeed(Double speed) {
        this.speedInmps = speed;
    }


}
