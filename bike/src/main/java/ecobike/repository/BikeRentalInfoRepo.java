package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BikeRentalInfoRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    public static BikeRentalInfo create(User user, Bike bike, String startAt) {
        BikeRentalInfo rentalInfo = BikeRentalInfo
                .builder()
                .user(user)
                .bike(bike)
                .startAt(startAt)
                .status(RENTALSTATUS.IN_PROGRESS)
                .build();
        entityManager.persist(rentalInfo);
        entityManager.getTransaction().commit();
        return rentalInfo;
    }

    public static BikeRentalInfo findInProgressRentalInfo(String userId, String bikeId) {
        Query q = entityManager.createQuery("select i from  BikeRentalInfo i where i.user.id = :userId and i.bike.id = :bikeId and i.status = :status");
        q.setParameter("userId", userId);
        q.setParameter("bikeId", bikeId);
        q.setParameter("status", RENTALSTATUS.IN_PROGRESS);

        BikeRentalInfo rentalInfo = (BikeRentalInfo) q.getSingleResult();
        return rentalInfo;
    }


}
