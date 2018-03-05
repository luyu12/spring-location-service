package demo.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Leg {
    private Point startPosition;
    private Point endPosition;
    private Integer id;
    private Double length;
    private Double heading;
}
