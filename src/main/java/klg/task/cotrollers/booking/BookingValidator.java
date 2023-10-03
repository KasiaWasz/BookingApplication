package klg.task.cotrollers.booking;

import klg.task.dtos.booking.BookingDto;
import klg.task.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public
class BookingValidator implements Validator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String E_BOOKING_INVALID = "booking.invalid";
    private static final String E_DATE_INVALID = "date.invalid";

    private final BookingService bookingService;


    @Autowired
    private BookingValidator(BookingService bookingService) {

        Assert.notNull(bookingService, "bookingService should not be null");

        this.bookingService = bookingService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return BookingForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        BookingForm bookingForm = (BookingForm) target;

        validateBooking(bookingForm.getApartmentId(), bookingForm.getStartDate(), bookingForm.getEndDate(), errors);
    }


    private void validateBooking(Long apartmentId, String startDate, String endDate, Errors errors) {

        List<BookingDto> bookings = bookingService.getAllBookingsDto();

        LocalDate targetStartDate = LocalDate.parse(startDate, DATE_TIME_FORMATTER);
        LocalDate targetEndDate = LocalDate.parse(endDate, DATE_TIME_FORMATTER);

        if (targetEndDate.isBefore(targetStartDate)) {

            errors.rejectValue(BookingForm.F_END_DATE, E_DATE_INVALID);
            return;
        }

        boolean isBookingNotValid = isSApartmentAvailable(apartmentId, targetStartDate, targetEndDate, bookings);

        if (isBookingNotValid) {

            errors.rejectValue(BookingForm.F_APARTMENT_ID, E_BOOKING_INVALID);
        }

    }

    private boolean isSApartmentAvailable(Long apartmentId,
        LocalDate targetStartDate,
        LocalDate targetEndDate,
        List<BookingDto> bookings) {

        return bookings.stream()
                .anyMatch(booking ->
                    (booking.getStartDate().isBefore(targetEndDate)
                    && booking.getEndDate().isAfter(targetStartDate))
                    &&booking.getApartmentId().equals(apartmentId));

    }
}
