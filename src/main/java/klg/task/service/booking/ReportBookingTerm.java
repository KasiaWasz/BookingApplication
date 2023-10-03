package klg.task.service.booking;

import klg.task.dtos.booking.BookingDto;
import klg.task.dtos.report.ReportBookingTermDto;
import klg.task.models.apartment.Apartment;
import klg.task.service.apartment.ApartmentService;
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
public class ReportBookingTerm {

    private final BookingService bookingService;
    private final ApartmentService apartmentService;


    @Autowired
    public ReportBookingTerm(BookingService bookingService, ApartmentService apartmentService) {

        Assert.notNull(bookingService, "bookingService should not be null");
        Assert.notNull(apartmentService, "apartmentService should not be null");

        this.bookingService = bookingService;
        this.apartmentService = apartmentService;
    }


    public List<ReportBookingTermDto> getBookingTermReport(LocalDate startDate, LocalDate endDate) {

        List<BookingDto> bookings = bookingService.getAllBookingsDto();

        return bookings.stream()
                .collect(groupingBy(BookingDto::getApartmentId))
                .entrySet().stream()
                .map(this::createReportBookingTermDto)
                .collect(Collectors.toList());

    }

    private ReportBookingTermDto createReportBookingTermDto(Map.Entry<Long, List<BookingDto>> entry) {

        Long apartmentId = entry.getKey();
        Apartment apartment = apartmentService.getById(apartmentId);

        long bookedDays = entry.getValue().stream()
                .mapToLong(bookingDto -> ChronoUnit.DAYS.between(bookingDto.getStartDate(), bookingDto.getEndDate()))
                .sum();

        long bookingCount = entry.getValue().size();

        return new ReportBookingTermDto(
            apartment.getName(),
            bookedDays
            //bookingCount
        );
    }
}
