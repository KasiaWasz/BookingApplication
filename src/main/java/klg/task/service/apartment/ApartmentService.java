package klg.task.service.apartment;

import klg.task.models.apartment.Apartment;
import klg.task.queries.apartment.ApartmentQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ApartmentService {

    private final ApartmentQueries apartmentQueries;


    @Autowired
    private ApartmentService(ApartmentQueries apartmentQueries) {

        Assert.notNull(apartmentQueries, "apartmentQueries must not be null");

        this.apartmentQueries = apartmentQueries;
    }


    public Apartment getById(Long id) {

        Assert.notNull(id, "id must not be null");

        return apartmentQueries.getById(id);
    }

    public List<Apartment> getAll() {

        return apartmentQueries.getAll();
    }
}
