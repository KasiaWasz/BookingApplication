package klg.task.service.booking;

import klg.task.cotrollers.booking.BookingForm;
import klg.task.dtos.BookingDto;
import klg.task.models.apartment.Apartment;
import klg.task.models.booking.Booking;
import klg.task.queries.booking.BookingQueries;
import klg.task.repository.booking.BookingRepository;
import klg.task.service.apartment.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final BookingQueries bookingQueries;
    private final BookingRepository bookingRepository;
    private final ApartmentService apartmentService;


    @Autowired
    private BookingService(BookingQueries bookingQueries,
        BookingRepository bookingRepository,
        ApartmentService apartmentService) {

        Assert.notNull(bookingQueries, "bookingQueries should not be null");
        Assert.notNull(bookingRepository, "bookingRepository should not be null");
        Assert.notNull(apartmentService, "apartmentService should not be null");

        this.bookingQueries = bookingQueries;
        this.bookingRepository = bookingRepository;
        this.apartmentService = apartmentService;
    }

    public Booking getById(Long id) {

        Assert.notNull(id, "id must not be null");

        return bookingQueries.getById(id);
    }

    public List<BookingDto> getAllBookingsDto() {

        return bookingQueries.getAllBookingDtos();
    }

    public List<Optional<BookingDto>> findBookingDtoByTenantName(String name) {

        Assert.notNull(name, "name should not be null");

        return bookingQueries.findBookingDtoByTenantName(name);
    }

    public List<Optional<BookingDto>> findBookingDtoByApartmentId(Long id) {

        Assert.notNull(id, "id must not be null");

        return bookingQueries.findBookingDtoByApartemntId(id);
    }

    public void updateBooking(Long id, BookingForm bookingForm) {

        LocalDate sDate = LocalDate.parse(bookingForm.getStartDate(), DATE_TIME_FORMATTER);
        LocalDate eDate = LocalDate.parse(bookingForm.getEndDate(), DATE_TIME_FORMATTER);

        Optional<Booking> existingBooking = bookingQueries.findById(id);

        if (existingBooking.isPresent()) {

            Booking booking = existingBooking.get();

            booking.setStartDate(LocalDate.parse(bookingForm.getStartDate()));
            booking.setEndDate(LocalDate.parse(bookingForm.getEndDate()));
            booking.setLandlordId(setLandlordId(bookingForm.getApartmentId()));
            booking.setTenantId(bookingForm.getTenantId());
            booking.setApartmentId(bookingForm.getApartmentId());
            booking.setPrice(calculatePrice(bookingForm.getApartmentId(), sDate, eDate));
            bookingRepository.saveOrUpdate(booking);
        }
    }

    public void addNewBooking(BookingForm bookingForm) {

        LocalDate sDate = LocalDate.parse(bookingForm.getStartDate(), DATE_TIME_FORMATTER);
        LocalDate eDate = LocalDate.parse(bookingForm.getEndDate(), DATE_TIME_FORMATTER);

        Booking booking = new Booking(
                LocalDate.parse(bookingForm.getStartDate()),
                LocalDate.parse(bookingForm.getEndDate()),
                setLandlordId(bookingForm.getApartmentId()),
                bookingForm.getTenantId(),
                bookingForm.getApartmentId(),
                calculatePrice(bookingForm.getApartmentId(), sDate, eDate)
        );
        bookingRepository.saveOrUpdate(booking);
    }

    private Long setLandlordId(Long apartmentId) {

        List<Apartment> apartments = apartmentService.getAll();

        return apartments.stream()
                .filter(apartment -> apartment.getId().equals(apartmentId))
                .findFirst()
                .map(Apartment::getLandlordId)
                .orElse(null);
    }

    private BigDecimal calculatePrice(Long apartmentId, LocalDate startDate, LocalDate endDate) {

        long totalNights = ChronoUnit.DAYS.between(startDate, endDate);

        BigDecimal totalNightsBigDecimal = BigDecimal.valueOf(totalNights);

        Apartment apartment = apartmentService.getById(apartmentId);
        BigDecimal totalPricePerNight = apartment.getPrice();

        return totalNightsBigDecimal.multiply(totalPricePerNight);
    }
}
