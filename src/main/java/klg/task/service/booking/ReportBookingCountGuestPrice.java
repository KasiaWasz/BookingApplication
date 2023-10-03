package klg.task.service.booking;

import klg.task.dtos.booking.BookingDto;
import klg.task.dtos.report.ReportBookingCountGuestPriceDto;
import klg.task.dtos.report.ReportBookingTermDto;
import klg.task.models.apartment.Apartment;
import klg.task.models.landlord.Landlord;
import klg.task.queries.booking.BookingQueries;
import klg.task.service.apartment.ApartmentService;
import klg.task.service.landlord.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class ReportBookingCountGuestPrice {

    private final BookingQueries bookingQueries;
    private final LandlordService landlordService;
    private final ApartmentService apartmentService;


    @Autowired
    public ReportBookingCountGuestPrice(BookingQueries bookingQueries,
        LandlordService landlordService,
        ApartmentService apartmentService) {

        Assert.notNull(bookingQueries, "bookingQueries should not be null");
        Assert.notNull(landlordService, "landlordService should not be null");
        Assert.notNull(apartmentService, "apartmentService should not be null");

        this.bookingQueries = bookingQueries;
        this.landlordService = landlordService;
        this.apartmentService = apartmentService;
    }


    public List<ReportBookingCountGuestPriceDto> getBookingCountGuestPriceReport(LocalDate startDate, LocalDate endDate) {

        List<BookingDto> bookings = bookingQueries.getBookingByDate(startDate, endDate);

        return bookings.stream()
                .collect(groupingBy(BookingDto::getLandlordId))
                .entrySet().stream()
                .map(this::createReportBookingCountGuestPriceDto)
                .collect(Collectors.toList());
    }

    private ReportBookingCountGuestPriceDto createReportBookingCountGuestPriceDto(Map.Entry<Long, List<BookingDto>> entry) {

        Long landlordId = entry.getKey();
        Landlord landlord = landlordService.getById(landlordId);

        Long apartmentId = entry.getKey();
        Apartment apartment = apartmentService.getById(apartmentId);

        Long apartmentCount = entry.getValue().stream()
                .mapToLong(BookingDto::getApartmentId)
                .count();

        Long tenantCount = entry.getValue().stream()
                .mapToLong(BookingDto::getTenantId)
                .count();

        long bookedDays = entry.getValue().stream()
                .mapToLong(bookingDto -> ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate()))
                .sum();

        BigDecimal pricePerDay = apartment.getPrice();

        BigDecimal price = pricePerDay.multiply(BigDecimal.valueOf(bookedDays));

        return new ReportBookingCountGuestPriceDto(
                landlord.getFullName(),
                apartmentCount,
                tenantCount,
                price
        );
    }
}
