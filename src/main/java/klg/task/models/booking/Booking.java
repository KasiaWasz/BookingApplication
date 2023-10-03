package klg.task.models.booking;

import klg.task.models.Model;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Booking implements Model {

    //dodałam też id wynajmowanego apartamentu
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private Long landlordId;
    @Column
    private Long tenantId;
    @Column
    private Long apartmentId;
    @Column
    private BigDecimal price;


    public Booking(LocalDate startDate,
       LocalDate endDate,
       Long landlordId,
       Long tenantId,
       Long apartmentId,
       BigDecimal price) {

        Assert.notNull(startDate, "startDate should not be null");
        Assert.notNull(endDate, "endDate should not be null");
        Assert.notNull(landlordId, "landlordId should not be null");
        Assert.notNull(tenantId, "tenantId should not be null");
        Assert.notNull(apartmentId, "apartmentId should not be null");
        Assert.notNull(price, "price should not be null");

        this.startDate = startDate;
        this.endDate = endDate;
        this.landlordId = landlordId;
        this.tenantId = tenantId;
        this.apartmentId = apartmentId;
        this.price = price;
    }


    public Booking() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(startDate, booking.startDate) && Objects.equals(endDate, booking.endDate) && Objects.equals(landlordId, booking.landlordId) && Objects.equals(tenantId, booking.tenantId) && Objects.equals(apartmentId, booking.apartmentId) && Objects.equals(price, booking.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, landlordId, tenantId, apartmentId, price);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", landlordId=" + landlordId +
                ", tenantId=" + tenantId +
                ", apartmentId=" + apartmentId +
                ", price=" + price +
                '}';
    }
}