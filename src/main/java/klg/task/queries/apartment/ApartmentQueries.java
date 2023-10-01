package klg.task.queries.apartment;

import klg.task.models.apartment.Apartment;

import java.util.List;

public interface ApartmentQueries {

    Apartment getById(Long id);
    List<Apartment> getAll();
}
