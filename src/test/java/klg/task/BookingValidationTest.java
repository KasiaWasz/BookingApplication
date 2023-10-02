package klg.task;

import klg.task.cotrollers.booking.BookingForm;
import klg.task.cotrollers.booking.BookingValidator;
import klg.task.dtos.BookingDto;
import klg.task.service.booking.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingValidationTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingValidator bookingValidator;
    private BookingForm bookingForm;
    private Errors errors;

    @BeforeEach
    public void setup() {

        bookingForm = new BookingForm();
        errors = new BeanPropertyBindingResult(bookingForm, "BookingForm");
    }

    @Test
    void shouldCatchIncorrectStartDate() {

        //given
        bookingForm.setStartDate("2019-12-26");
        bookingForm.setEndDate("2019-12-24");
        bookingForm.setLandlordId(1L);
        bookingForm.setTenantId(1L);
        bookingForm.setApartmentId(1L);
        bookingForm.setPrice(BigDecimal.valueOf(200.00));

        //when
        bookingValidator.validate(bookingForm, errors);

        //then
        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(1);
    }

    @Test
    void shouldCatchNotAvailableApartment() {

        //given
        bookingForm.setStartDate("2023-09-01");
        bookingForm.setEndDate("2023-09-04");
        bookingForm.setLandlordId(1L);
        bookingForm.setTenantId(1L);
        bookingForm.setApartmentId(1L);
        bookingForm.setPrice(BigDecimal.valueOf(4502.85));
        List<BookingDto> bookings = new ArrayList<>();
        bookings.add(new BookingDto(1L, LocalDate.parse("2023-09-01"), LocalDate.parse("2023-09-04"), 1L, 1L, 1L, BigDecimal.valueOf(4502.85)));

        //when
        when(bookingService.getAllBookingsDto()).thenReturn(bookings);
        bookingValidator.validate(bookingForm, errors);

        //then
        assertThat(errors.hasErrors()).isTrue();
        assertThat(errors.getErrorCount()).isEqualTo(1);
    }

    @Test
    void shouldAddBooking() {

        //given
        bookingForm.setStartDate("2023-09-04");
        bookingForm.setEndDate("2023-09-10");
        bookingForm.setLandlordId(1L);
        bookingForm.setTenantId(1L);
        bookingForm.setApartmentId(1L);
        bookingForm.setPrice(BigDecimal.valueOf(4502.85));
        List<BookingDto> bookings = new ArrayList<>();
        bookings.add(new BookingDto(2L, LocalDate.parse("2023-09-01"), LocalDate.parse("2023-09-04"), 1L, 1L, 1L, BigDecimal.valueOf(4502.85)));

        //when
        when(bookingService.getAllBookingsDto()).thenReturn(bookings);
        bookingValidator.validate(bookingForm, errors);

        //then
        assertThat(errors.hasErrors()).isFalse();
        assertThat(errors.getErrorCount()).isZero();
    }
}
