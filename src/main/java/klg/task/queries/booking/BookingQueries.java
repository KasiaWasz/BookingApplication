package klg.task.queries.booking;

import klg.task.dtos.BookingDto;
import klg.task.models.booking.Booking;

import java.util.List;
import java.util.Optional;

public interface BookingQueries {

    List<Optional<BookingDto>> getBookingDtoByTenantName(String name);

    List<Optional<BookingDto>> getBookingDtoByApartemntId(Long apartmentId);

    Booking getById(Long id);

    List<Booking> getAll();

    List<BookingDto> getAllBookingDtos();

    Optional<Booking> findById(Long id);
}
