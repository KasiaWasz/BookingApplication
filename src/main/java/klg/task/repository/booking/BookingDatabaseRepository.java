package klg.task.repository.booking;

import klg.task.models.booking.Booking;
import klg.task.repository.AbstractRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
class BookingDatabaseRepository extends AbstractRepository<Booking> implements BookingRepository{


    @Autowired
    BookingDatabaseRepository(SessionFactory sessionFactory) {
        
        super(sessionFactory, Booking.class);
    }
}
