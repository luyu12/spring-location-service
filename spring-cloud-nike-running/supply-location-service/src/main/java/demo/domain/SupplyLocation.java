package demo.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
@Document
//为什么这里是Document而不是Entity（来自JPA，而jpa是管relational db）
public class SupplyLocation {

    @Id
    private String id;
    private String address1;
    private String address2;
    private String city;
    @GeoSpatialIndexed//可以查询该点周围的点
    private final Point location;
    private String zip;
    private String type;

    //jaskson lib要求一定要有初始化的constructor
    public SupplyLocation() {
        this.location = new Point(0, 0);
    }

    @JsonCreator
    public SupplyLocation(@JsonProperty("longitude") double longitude,@JsonProperty("latitude")double latitude) {
        this.location = new Point(longitude, latitude);
    }

    public double getLatitude() {
        return this.location.getY();
    }

    public double getLongitude() {
        return this.location.getX();
    }
}
