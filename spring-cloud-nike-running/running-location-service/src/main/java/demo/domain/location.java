package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//  Java Object model
//Database Relational Model
//JPA ORM

//JSON String(JAVAscript model)
//java object model
//jackson library json seri/deseri


@Data
@Entity
@Table(name = "LOCATION")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class location {
    enum GpsStatus {
        EXCELLENT, OK, UNRELIABLE, BAD, NOFIX, UNKOWN;
    }

    public enum RunnerMovementType {
        STOPPED, IN_MOVEMENT;

        public boolean isMoving() {
            return this != STOPPED;
        }
    }

    @Id
    @GeneratedValue  //(id可以自己生成)
    private Long id;

    @Embedded
    @AttributeOverride(name = "bandMake", column = @Column(name = "Unit_band_make"))
    private final UnitInfo unitInfo;

    @Embedded
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AttributeOverrides({
            @AttributeOverride(name = "bfr", column = @Column(name = "medical_bfr")),
            @AttributeOverride(name = "fmi", column = @Column(name = "medical_fmi"))
    })
    private MedicalInfo medicalInfo;

    @Column(name = "LATITUDE")//不写column就会按照field默认来处理
    private double latitude;
    private double longitude;
    private String heading;
    private double GpsSpeed;
    private GpsStatus gpsStatus;
    private double odometer;
    private double totalRunningTime;
    private double totalIdleTime;
    private double totalCalorieBurnt;
    private String address;
    private Date timestamp = new Date();
    private String gearProvider;
    private RunnerMovementType runnerMovementType = RunnerMovementType.STOPPED;//initialize the runnerMovementType
    private String serviceType;

    public location() {
        this.unitInfo = null;//initilize object unitInfo to be NULL
    }
    @JsonCreator
    public location(@JsonProperty("runningId") String runningId) {
        this.unitInfo = new UnitInfo(runningId);
    }

    public location(UnitInfo unitInfo) {
        this.unitInfo = unitInfo;
    }

    public String getRunningId()
    {
        return this.unitInfo.getRunningId()!=null? this.unitInfo.getRunningId(): null;
    }


}

