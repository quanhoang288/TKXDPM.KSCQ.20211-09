package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.Bike;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class BikeRepo implements IBikeRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    public  List<Bike> getAllByDock(String dockId) {
        Query q = entityManager.createQuery("select b from  Bike b where b.dock.id = :dockId");
        q.setParameter("dockId", dockId);
        List<Bike> bikes = (List<Bike>) q.getResultList();
        return bikes;
    }

    public  List<Bike> getPagingByDock(int start, int count, String dockId) {
        Query q = entityManager.createQuery("select b from  Bike b where b.dock.id = :dockId");
        q.setParameter("dockId", dockId);
        q.setFirstResult(start);
        q.setMaxResults(count);
        List<Bike> bikes = (List<Bike>) q.getResultList();

        return bikes;
    }

    public Bike findById(String id) throws NoResultException {
        Query q = entityManager.createQuery("select b from  Bike b where b.id = :id");
        q.setParameter("id", id);
        Bike bike = (Bike) q.getSingleResult();

        return bike;
    }

}


