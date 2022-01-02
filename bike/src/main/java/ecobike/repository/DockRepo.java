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

    public static boolean checkDockFull(Dock dock) {
        Query bikeBeingRentedCountQuery = entityManager.createQuery("select count(bri) from BikeRentalInfo bri inner join Bike b on bri.endAt is null and b.dock = :dock");

        bikeBeingRentedCountQuery.setParameter("dock", dock);
        Long bikeBeingRentedCount = (Long) bikeBeingRentedCountQuery.getSingleResult();

        return dock.getCapacity() - dock.getBikes().size() + bikeBeingRentedCount == 0;
    }
}
