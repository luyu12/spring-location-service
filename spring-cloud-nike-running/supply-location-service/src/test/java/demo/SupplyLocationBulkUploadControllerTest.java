package demo;

import demo.domain.SupplyLocation;
import demo.domain.SupplyLocationRepository;
import demo.rest.SupplyLocationRestController;
import demo.service.SupplyLocationService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SupplyLocationBulkUploadControllerTest {

    private SupplyLocationRepository repository;

    private SupplyLocationService service;

    private SupplyLocationRestController controller;

    private List<SupplyLocation> inputLocations;

    @Before
    public void setupMock() {
        repository = mock(SupplyLocationRepository.class);
        service = mock(SupplyLocationService.class);
        //要测试的对象
        controller = new SupplyLocationRestController(repository, service);

        inputLocations = new ArrayList<>();
        inputLocations.add(generateSupplyLocations(4, 4, "504"));
        inputLocations.add(generateSupplyLocations(5, 5, "505"));
        inputLocations.add(generateSupplyLocations(6, 6, "506"));
        inputLocations.add(generateSupplyLocations(7, 7, "507"));
        inputLocations.add(generateSupplyLocations(8, 8, "508"));
        inputLocations.add(generateSupplyLocations(9, 9, "509"));
    }

    @Test
    public void whenListFiltered_returnSavedList() {
        List<SupplyLocation> locations = new ArrayList<>();
        locations.add(generateSupplyLocations(4, 4, "504"));
        when(service.saveSupplyLocationZipContains504(inputLocations)).thenReturn(locations);

        assertThat(controller.uploadFilteredLocations(inputLocations)).size().isEqualTo(1);
        assertThat(controller.uploadFilteredLocations(inputLocations).get(0).getZip()).isEqualTo("504");
    }

    private SupplyLocation generateSupplyLocations(double latitude, double longitude, String zip) {
        SupplyLocation location = new SupplyLocation(latitude, longitude);
        location.setZip(zip);
        return location;
    }

}
