package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.Dock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * This class performs queries related to dock
 */
public class DockRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    /**
     * Get list of docks
     * @return {@link List<Dock>}
     */
    public static List<Dock> getAll() {
        Query q = entityManager.createQuery("select d from  Dock d ");
        List<Dock> docks = q.getResultList();
        return docks;
    }

    /**
     * Get dock with given id
     * @param id
     * @return {@link Dock}
     */
    public static Dock findById(String id) {
        Query q = entityManager.createQuery("select d from  Dock d where d.id = :id");
        q.setParameter("id", id);
        Dock dock = (Dock) q.getSingleResult();
        return dock;
    }
}
