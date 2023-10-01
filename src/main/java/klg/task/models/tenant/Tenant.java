package klg.task.models.tenant;

import klg.task.models.Model;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Tenant implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String fullName;

    public Tenant(String fullName) {

        Assert.notNull(fullName, "fullName should not be null");

        this.fullName = fullName;
    }


    public Tenant() {
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
        Tenant tenant = (Tenant) o;
        return Objects.equals(id, tenant.id) && Objects.equals(fullName, tenant.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName);
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}