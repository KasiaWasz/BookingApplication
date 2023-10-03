package klg.task.service.landlord;

import klg.task.models.landlord.Landlord;
import klg.task.queries.landlord.LandlordQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class LandlordService {

    private final LandlordQueries landlordQueries;


    @Autowired
    public LandlordService(LandlordQueries landlordQueries) {

        Assert.notNull(landlordQueries, "landlordQueries should not be null");

        this.landlordQueries = landlordQueries;
    }


    public Landlord getById(Long id) {

        Assert.notNull(id, "id should not be null");

        return landlordQueries.getById(id);
    }
}
