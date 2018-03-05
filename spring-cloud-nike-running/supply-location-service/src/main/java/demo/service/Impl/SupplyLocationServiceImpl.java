package demo.service.Impl;

        import demo.domain.SupplyLocation;
        import demo.domain.SupplyLocationRepository;
        import demo.service.SupplyLocationService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.ArrayList;
        import java.util.List;

@Service
public class SupplyLocationServiceImpl implements SupplyLocationService {

    @Autowired
    private SupplyLocationRepository repository;

    @Override
    public List<SupplyLocation> saveSupplyLocationZipContains504(List<SupplyLocation> locations) {
        return (ArrayList<SupplyLocation>)this.repository.save(this.filterList(locations,"504"));
    }

    private List<SupplyLocation> filterList(List<SupplyLocation> listToFilter,String keyword){
        List<SupplyLocation> save=new ArrayList<>();
        for(SupplyLocation supplyLocation:listToFilter){
            if(supplyLocation.getZip().contains(keyword))
            {
                save.add(supplyLocation);
            }
        }
        return save;
    }
}
