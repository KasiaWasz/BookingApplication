package klg.task.cotrollers.booking;

import klg.task.dtos.BookingDto;
import klg.task.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static liquibase.repackaged.org.apache.commons.lang3.StringUtils.isBlank;

@Component
class BookingValidator implements Validator {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String E_BOOKING_INVALID = "booking.invalid";

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

        validateStartDate(bookingForm.getStartDate(), errors);
        validateEndDate(bookingForm.getEndDate(), errors);
        validateTenant(bookingForm.getTenantId(), errors);
        validateApartment(bookingForm.getApartmentId(), errors);
        validateBooking(bookingForm.getApartmentId(), bookingForm.getStartDate(), bookingForm.getEndDate(), errors);
    }

    private void validateStartDate(String startDate, Errors errors) {

        if (isBlank(startDate)) {

            errors.rejectValue(BookingForm.F_START_DATE, "startDate is not correct");

            return;

        }
        try {

            LocalDate.parse(startDate, DATE_TIME_FORMATTER);

        } catch (Exception e) {

            errors.rejectValue(BookingForm.F_START_DATE, "startDate is not correct");
        }
    }

    private void validateEndDate(String endDate, Errors errors) {

        if (isBlank(endDate)) {

            errors.rejectValue(BookingForm.F_END_DATE, "endDate is not correct");

            return;

        }
        try {

            LocalDate.parse(endDate, DATE_TIME_FORMATTER);

        } catch (Exception e) {

            errors.rejectValue(BookingForm.F_END_DATE, "endDate is not correct");
        }
    }

    private void validateTenant(Long tenantId, Errors errors) {

        if (tenantId == null) {

            errors.rejectValue(BookingForm.F_TENANT_ID, "tenant must not be null");
        }
    }

    private void validateApartment(Long apartmentId, Errors errors) {

        if (apartmentId == null) {

            errors.rejectValue(BookingForm.F_APARTMENT_ID,"apartment must not be null");
        }
    }

    private void validateBooking(Long apartmentId, String startDate, String endDate, Errors errors) {

        List<BookingDto> bookings = bookingService.getAllBookingsDto();

        LocalDate targetStartDate = LocalDate.parse(startDate, DATE_TIME_FORMATTER);
        LocalDate targetEndDate = LocalDate.parse(endDate, DATE_TIME_FORMATTER);

        if (targetEndDate.isBefore(targetStartDate)) {

            errors.rejectValue(BookingForm.F_END_DATE, "endDate.must.be.after.startDate");
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
