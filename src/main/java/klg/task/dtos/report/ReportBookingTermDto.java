package klg.task.dtos.report;

import org.springframework.util.Assert;

public class ReportBookingTermDto {

    private String apartment;
    private Long days;
    private Long bookingCount;


    public ReportBookingTermDto(String apartment, Long days, Long bookingCount) {

        Assert.notNull(apartment, "apartment should not be null");
        Assert.notNull(days, "days should not be null");
        Assert.notNull(bookingCount, "bookingCount should not be null");

        this.apartment = apartment;
        this.days = days;
        this.bookingCount = bookingCount;
    }


    public ReportBookingTermDto() {
    }


    public String getApartment() {
        return apartment;
    }

    public Long getDays() {
        return days;
    }

    public Long getBookingCount() {
        return bookingCount;
    }
}
