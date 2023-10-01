package klg.task.queries.apartment;

import klg.task.models.apartment.Apartment;
import klg.task.queries.AbstractQueries;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ApartmentDatabaseQueries extends AbstractQueries<Apartment> implements ApartmentQueries {


    @Autowired
    ApartmentDatabaseQueries(SessionFactory sessionFactory) {

        super(sessionFactory, Apartment.class);
    }
}
