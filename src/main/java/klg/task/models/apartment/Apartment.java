package klg.task.models.apartment;

import klg.task.models.Model;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Apartment implements Model {

    //dodałam też id właściciela
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private BigDecimal price;
    @Column
    private BigDecimal area;
    @Column
    private String description;
    @Column
    private Long landlordId;


    public Apartment(String name, BigDecimal price, BigDecimal area, String description, Long landlordId) {

        Assert.notNull(name, "name should not be null");
        Assert.notNull(price, "price should not be null");
        Assert.notNull(area, "area should not be null");
        Assert.notNull(description, "description should not be null");
        Assert.notNull(landlordId, "landlordId should not be null");

        this.name = name;
        this.price = price;
        this.area = area;
        this.description = description;
        this.landlordId = landlordId;
    }


    public Apartment() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(Long landlordId) {
        this.landlordId = landlordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return Objects.equals(id, apartment.id) && Objects.equals(name, apartment.name) && Objects.equals(price, apartment.price) && Objects.equals(area, apartment.area) && Objects.equals(description, apartment.description) && Objects.equals(landlordId, apartment.landlordId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, area, description, landlordId);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", area=" + area +
                ", description='" + description + '\'' +
                ", landlordId=" + landlordId +
                '}';
    }
}