package demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access= AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrentPosition {
    private String runningId;
    private Point location;
    private RunnerStatus runningStatus=RunnerStatus.NONE;

    private Double speed;
    private Double heading;

    private MedicalInfo medicalInfo;
}
