package klg.task.dtos.report;

import org.springframework.util.Assert;

import java.math.BigDecimal;

public class ReportBookingCountGuestPriceDto {

    private String landlordName;
    private Long apartmentCount;
    private Long tenantCount;
    private BigDecimal price;


    public ReportBookingCountGuestPriceDto(String landlordName, Long apartmentCount, Long tenantCount, BigDecimal price) {

        Assert.notNull(landlordName, "landlordName should not be null");
        Assert.notNull(apartmentCount, "apartmentCount should not be null");
        Assert.notNull(tenantCount, "tenantCount should not be null");
        Assert.notNull(price, "price should not be null");

        this.landlordName = landlordName;
        this.apartmentCount = apartmentCount;
        this.tenantCount = tenantCount;
        this.price = price;
    }


    public ReportBookingCountGuestPriceDto() {
    }


    public String getLandlordName() {
        return landlordName;
    }

    public Long getApartmentCount() {
        return apartmentCount;
    }

    public Long getTenantCount() {
        return tenantCount;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
