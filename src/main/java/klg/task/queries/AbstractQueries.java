package klg.task.queries;

import klg.task.models.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class AbstractQueries<M extends Model> {

    protected final SessionFactory sessionFactory;

    private final Class<M> modelClass;


    protected AbstractQueries(SessionFactory sessionFactory, Class<M> modelClass) {

        Assert.notNull(sessionFactory, "sessionFactory should not be null");
        Assert.notNull(modelClass, "modelClass should should be null");

        this.sessionFactory = sessionFactory;
        this.modelClass = modelClass;
    }

    public M getById(Long id) {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(modelClass);

            Root<M> root = criteriaQuery.from(modelClass);
            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));

            return (M) session.createQuery(criteriaQuery).uniqueResult();
        }
    }

    public Optional<M> findById(Long id) {

        try (Session session = sessionFactory.openSession()) {

            return Optional.ofNullable(getById(id));
        }
    }

    public List<M> getAll() {

        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(modelClass);

            Root<M> root = criteriaQuery.from(modelClass);
            criteriaQuery.select(root);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
