package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.Bike;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * This class perform queries related to bike
 */
public class BikeRepo implements IBikeRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    /**
     * Get list of bikes of a dock given id with pagination
     * @param start start index for fetching from database
     * @param count number of records to fetch
     * @param dockId id of dock for fetching list of bikes
     * @return {@link List<Bike>}
     */
    public  List<Bike> getPagingByDock(int start, int count, String dockId) {
        Query q = entityManager.createQuery("select b from  Bike b where b.dock.id = :dockId");
        q.setParameter("dockId", dockId);
        q.setFirstResult(start);
        q.setMaxResults(count);
        List<Bike> bikes = (List<Bike>) q.getResultList();

        return bikes;
    }

    /**
     * Find bike with given id
     * @param id
     * @return {@link Bike}
     * @throws NoResultException if no bike is found with given id
     */
    public Bike findById(String id) throws NoResultException {
        Query q = entityManager.createQuery("select b from  Bike b where b.id = :id");
        q.setParameter("id", id);
        Bike bike = (Bike) q.getSingleResult();

        return bike;
    }

}


