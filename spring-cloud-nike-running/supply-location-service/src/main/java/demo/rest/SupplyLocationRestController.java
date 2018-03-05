package demo.rest;

import demo.domain.SupplyLocation;
import demo.domain.SupplyLocationRepository;
import demo.service.SupplyLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplyLocationRestController {

    @Autowired
    private SupplyLocationRepository repository;

    @Autowired
    private SupplyLocationService service;

  //  @Autowired
    public SupplyLocationRestController(SupplyLocationRepository repository,SupplyLocationService service){
        this.repository=repository;
        this.service=service;
    }


    @RequestMapping(value="/bulk/supplyLocations",method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<SupplyLocation> locations){
        this.repository.save(locations);

    }

    @RequestMapping(value="/purge",method=RequestMethod.POST)
    public void purge(){this.repository.deleteAll();
    }

    public List<SupplyLocation> uploadFilteredLocations(List<SupplyLocation> locations){
        return this.service.saveSupplyLocationZipContains504(locations);}
}
