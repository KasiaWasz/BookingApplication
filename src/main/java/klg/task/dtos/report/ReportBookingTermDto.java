package klg.task.dtos.report;

import org.springframework.util.Assert;

public class ReportBookingTermDto {

    private String apartment;
    private Long days;


    public ReportBookingTermDto(String apartment, Long days) {

        Assert.notNull(apartment, "apartment should not be null");
        Assert.notNull(days, "days should not be null");

        this.apartment = apartment;
        this.days = days;
    }


    public ReportBookingTermDto() {
    }


    public String getApartment() {
        return apartment;
    }

    public Long getDays() {
        return days;
    }
}
