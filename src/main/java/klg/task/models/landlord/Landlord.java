package klg.task.models.landlord;

import klg.task.models.Model;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Landlord implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String fullName;


    public Landlord(String fullName, String lastName) {

        Assert.notNull(fullName, "fullName should not be null");

        this.fullName = fullName;
    }


    public Landlord() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Landlord landlord = (Landlord) o;
        return Objects.equals(id, landlord.id) && Objects.equals(fullName, landlord.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }

    @Override
    public String toString() {
        return "Landlord{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}