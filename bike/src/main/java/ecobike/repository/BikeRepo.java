package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.Bike;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class BikeRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    public static List<Bike> getAllByDock(String dockId) {
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("select b from  Bike b where b.dock.id = :dockId");
        q.setParameter("dockId", dockId);
        List<Bike> bikes = (List<Bike>) q.getResultList();
        entityManager.getTransaction().commit();
        return bikes;
    }

    public static List<Bike> getPagingByDock(int start, int count, String dockId) {
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("select b from  Bike b where b.dock.id = :dockId");
        q.setParameter("dockId", dockId);


        q.setFirstResult(start);
        q.setMaxResults(count);
        List<Bike> bikes = (List<Bike>) q.getResultList();
        entityManager.getTransaction().commit();
        return bikes;
    }

    public static Bike findById(String id) throws NoResultException {
        entityManager.getTransaction().begin();
        Query q = entityManager.createQuery("select b from  Bike b where b.id = :id");
        q.setParameter("id", id);
        Bike bike = null;
        try {
            bike = (Bike) q.getSingleResult();
        } catch (NoResultException e) {
            throw e;
        } finally {
            entityManager.getTransaction().commit();
        }

        return bike;
    }

}


