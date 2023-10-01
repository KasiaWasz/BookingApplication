package klg.task.cotrollers.booking;

import org.springframework.util.Assert;

import java.math.BigDecimal;

public class BookingForm {

    //dodane na potrzeby ewentualenej rozbudowy walidatora
    public static final String F_ID = "id";
    static final String F_START_DATE = "startDate";
    static final String F_END_DATE = "endDate";
    static final String F_LANDLORD_ID ="landlordId";
    static final String F_TENANT_ID = "tenantId";
    static final String F_APARTMENT_ID = "apartmentId";
    static final String F_PRICE = "price";

    private Long id;

    private String startDate;

    private String endDate;

    private Long landlordId;

    private Long tenantId;

    private Long apartmentId;

    private BigDecimal price;


    public BookingForm(Long id,
        String startDate,
        String endDate,
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


    public BookingForm() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
