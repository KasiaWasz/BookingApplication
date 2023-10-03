package klg.task;

import klg.task.cotrollers.booking.BookingForm;
import klg.task.dtos.booking.BookingDto;
import klg.task.models.apartment.Apartment;
import klg.task.models.booking.Booking;
import klg.task.models.tenant.Tenant;
import klg.task.queries.booking.BookingQueries;
import klg.task.repository.booking.BookingRepository;
import klg.task.service.apartment.ApartmentService;
import klg.task.service.booking.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingQueries bookingQueries;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ApartmentService apartmentService;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testGetAllBookingsDto() {

        //given
        List<BookingDto> bookingDtos = Arrays.asList(new BookingDto(), new BookingDto());

        //when
        when(bookingQueries.getAllBookingDtos()).thenReturn(bookingDtos);
        List<BookingDto> result = bookingService.getAllBookingsDto();

        //then
        assertEquals(2, result.size());
    }

    @Test
    void testFindBookingDtoByTenantName() {

        //given
        String tenantName = "Anna Nowak";
        Tenant tenant = new Tenant();
        tenant.setId(1L);
        tenant.setFullName(tenantName);
        List<Tenant> tenants = Collections.singletonList(tenant);

        List<Optional<BookingDto>> bookingDtos = Arrays.asList(
            Optional.of(new BookingDto(1L, LocalDate.parse("2023-09-01"), LocalDate.parse("2023-09-04"), 1L, 1L, 1L, BigDecimal.valueOf(4502.85))));

        //when
        when(bookingQueries.findBookingDtoByTenantName(tenantName)).thenReturn(bookingDtos);
        List<Optional<BookingDto>> result = bookingService.findBookingDtoByTenantName(tenantName);

        //then
        assertEquals(1, result.size());
        assertTrue(result.get(0).isPresent());
        assertEquals(1L, result.get(0).get().getId());
        assertEquals(1L, result.get(0).get().getTenantId());
        assertEquals(result.get(0).get().getTenantId(), tenants.get(0).getId());
        assertEquals(LocalDate.parse("2023-09-01"), result.get(0).get().getStartDate());
        assertEquals(LocalDate.parse("2023-09-04"), result.get(0).get().getEndDate());
        assertEquals(1L, result.get(0).get().getLandlordId());
        assertEquals(1L, result.get(0).get().getApartmentId());
        assertEquals(BigDecimal.valueOf(4502.85), result.get(0).get().getPrice());
    }

    @Test
    void testFindBookingDtoByApartmentId() {

        //given
        Long apartmentId = 1L;
        List<Optional<BookingDto>> bookingDtos = Arrays.asList(
            Optional.of(new BookingDto(1L, LocalDate.parse("2023-09-01"), LocalDate.parse("2023-09-04"), 1L, 1L, 1L, BigDecimal.valueOf(4502.85))));

        //when
        when(bookingQueries.findBookingDtoByApartemntId(apartmentId)).thenReturn(bookingDtos);
        List<Optional<BookingDto>> result = bookingService.findBookingDtoByApartmentId(apartmentId);

        //then
        assertEquals(1, result.size());
        assertTrue(result.get(0).isPresent());
        assertEquals(LocalDate.parse("2023-09-01"), result.get(0).get().getStartDate());
        assertEquals(LocalDate.parse("2023-09-04"), result.get(0).get().getEndDate());
        assertEquals(1L, result.get(0).get().getTenantId());
        assertEquals(1L, result.get(0).get().getLandlordId());
        assertEquals(apartmentId, result.get(0).get().getApartmentId());
        assertEquals(BigDecimal.valueOf(4502.85), result.get(0).get().getPrice());
    }

    @Test
    void testUpdateBooking() {

        //given
        Long id = 1L;
        List<Apartment> apartments = new ArrayList<>();
        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setName("nazwa");
        apartment.setLandlordId(1L);
        apartment.setPrice(BigDecimal.valueOf(100.00));
        apartment.setArea(BigDecimal.valueOf(25.00));
        apartment.setDescription("opis");
        apartments.add(apartment);

        Booking existingBooking = new Booking();
        existingBooking.setId(id);
        existingBooking.setStartDate(LocalDate.parse("2023-09-01"));
        existingBooking.setEndDate(LocalDate.parse("2023-09-10"));
        existingBooking.setLandlordId(1L);
        existingBooking.setTenantId(1L);
        existingBooking.setApartmentId(1L);
        existingBooking.setPrice(BigDecimal.valueOf(900.00));

        BookingForm bookingForm = new BookingForm();
        bookingForm.setStartDate("2023-09-04");
        bookingForm.setEndDate("2023-09-10");
        bookingForm.setTenantId(1L);
        bookingForm.setApartmentId(1L);

        // when
        when(apartmentService.getAll()).thenReturn(apartments);
        when(bookingQueries.findById(id)).thenReturn(Optional.of(existingBooking));
        when(apartmentService.getById(1L)).thenReturn(apartment);

        bookingService.updateBooking(id, bookingForm);

        // then
        assertEquals(LocalDate.parse("2023-09-04"), existingBooking.getStartDate());
        assertEquals(LocalDate.parse("2023-09-10"), existingBooking.getEndDate());
        assertEquals(Long.valueOf(1L), existingBooking.getLandlordId());
        assertEquals(Long.valueOf(1L), existingBooking.getTenantId());
        assertEquals(Long.valueOf(1L), existingBooking.getApartmentId());
        assertEquals(BigDecimal.valueOf(600.00), existingBooking.getPrice());
    }
}

