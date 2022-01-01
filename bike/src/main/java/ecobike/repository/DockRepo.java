package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.Dock;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class DockRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    public static List<Dock> getAll() {
        Query q = entityManager.createQuery("select d from  Dock d ");
        List<Dock> docks = q.getResultList();
        return docks;


    }
}
