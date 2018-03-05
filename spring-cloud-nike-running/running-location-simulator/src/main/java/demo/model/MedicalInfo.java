package demo.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access= AccessLevel.PUBLIC)
public class MedicalInfo {
    private String bandMake;
    private String medCode;
    private String MedicalInfoId;
    private String MedialInfoClassification;
    private String description;
    private String aidInstructions;
    private String fmi;
    private String bfr;
}
