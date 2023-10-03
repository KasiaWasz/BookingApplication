package klg.task.queries.landlord;

import klg.task.models.landlord.Landlord;
import klg.task.queries.AbstractQueries;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class LandlordDatabaseQueries extends AbstractQueries<Landlord> implements LandlordQueries {


    @Autowired
    LandlordDatabaseQueries(SessionFactory sessionFactory) {

        super(sessionFactory, Landlord.class);
    }
}
