package klg.task.repository.booking;

import klg.task.models.booking.Booking;

public interface BookingRepository {

    void saveOrUpdate(Booking booking);
}
