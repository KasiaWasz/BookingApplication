package klg.task.repository;

import klg.task.models.Model;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.util.Assert;

public class AbstractRepository<M extends Model> {

    protected final SessionFactory sessionFactory;

    protected final Class<M> modelClass;

    public AbstractRepository(SessionFactory sessionFactory, Class<M> modelClass) {

        Assert.notNull(sessionFactory, "sessionFactory must not be null");
        Assert.notNull(modelClass, "modelClass must not be null");

        this.sessionFactory = sessionFactory;
        this.modelClass = modelClass;
    }

    public void saveOrUpdate(M model) {

        Assert.notNull(model, "model must not be null");

        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            session.saveOrUpdate(model);

            transaction.commit();
        }
    }
}