package klg.task.dtos;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingDto {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long landlordId;

    private Long tenantId;

    private Long apartmentId;

    private BigDecimal price;


    public BookingDto(Long id,
        LocalDate startDate,
        LocalDate endDate,
        Long landlordId,
        Long tenantId,
        Long apartmentId,
        BigDecimal price) {

        Assert.notNull(id, "id should not be null");
        Assert.notNull(startDate, "startDate should not be null");
        Assert.notNull(endDate, "endDate should not be null");
        Assert.notNull(landlordId, "landlordId should not be null");
        Assert.notNull(tenantId, "tenantId should not be null");
        Assert.notNull(apartmentId, "apartmentId should not be null");
        Assert.notNull(price, "price should not be null");

        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.landlordId = landlordId;
        this.tenantId = tenantId;
        this.apartmentId = apartmentId;
        this.price = price;
    }

    public BookingDto() {
    }

    public Long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
