package demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable //相当于grouping
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicalInfo {
    private Long bfr;
    private Long fmi;


    public MedicalInfo(Long bfr, Long fmi) {
        this.bfr = bfr;
        this.fmi = fmi;
    }


}
