package klg.task.queries.booking;

import klg.task.dtos.booking.BookingDto;
import klg.task.models.booking.Booking;
import klg.task.models.tenant.Tenant;
import klg.task.queries.AbstractQueries;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
class BookingDatabaseQueries extends AbstractQueries<Booking> implements BookingQueries {


    @Autowired
    BookingDatabaseQueries(SessionFactory sessionFactory) {

        super(sessionFactory, Booking.class);
    }


    public List<Optional<BookingDto>> findBookingDtoByTenantName(String name) {

        Assert.notNull(name, "name should not be null");

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
            Root<Booking> root = cq.from(Booking.class);

            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<Tenant> subqueryRoot = subquery.from(Tenant.class);
            subquery.select(subqueryRoot.get("id"))
                    .where(cb.equal(subqueryRoot.get("fullName"), name));

            cq.select(root)
                    .where(cb.in(root.get("tenantId")).value(subquery));

            List<Booking> bookings = session.createQuery(cq).getResultList();

            return bookings.stream()
                    .map(booking -> Optional.of(toBookingDto(booking)))
                    .collect(Collectors.toList());
        }
    }

    public List<Optional<BookingDto>> findBookingDtoByApartemntId(Long apartmentId) {

        Assert.notNull(apartmentId, "apartmentId should not be null");

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
            Root<Booking> root = cq.from(Booking.class);

            cq.select(root)
                    .where(cb.equal(root.get("apartmentId"), apartmentId));

            return session.createQuery(cq)
                    .getResultList().stream()
                    .map(booking -> Optional.of(toBookingDto(booking)))
                    .collect(Collectors.toList());
        }
    }

    public List<BookingDto> getBookingByDate(LocalDate startDate, LocalDate endDate) {

        Assert.notNull(startDate, "startDate should not be null");
        Assert.notNull(endDate, "startDate should not be null");

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
            Root<Booking> root = cq.from(Booking.class);

            cq.select(root)
                    .where(
                            cb.greaterThanOrEqualTo(root.get("startDate"), startDate),
                            cb.lessThanOrEqualTo(root.get("endDate"), endDate)
                    );

            return session.createQuery(cq)
                    .getResultList().stream()
                    .map(this::toBookingDto)
                    .collect(Collectors.toList());
        }
    }

    public List<BookingDto> getAllBookingDtos() {

        return getAll().stream()
                .map(this::toBookingDto)
                .collect(Collectors.toList());
    }

    private BookingDto toBookingDto(Booking booking) {

        return new BookingDto(
                booking.getId(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getLandlordId(),
                booking.getTenantId(),
                booking.getApartmentId(),
                booking.getPrice()
        );
    }
}
