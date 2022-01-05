package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * This class perform queries related to bike rental info
 */
public class BikeRentalInfoRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    /**
     * Create and persist new bike rental info
     * @param user
     * @param bike
     * @param startAt
     * @return {@link BikeRentalInfo}
     */
    public static BikeRentalInfo create(User user, Bike bike, Date startAt) {
        entityManager.getTransaction().begin();
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

    /**
     * Find rental info in progress given user id
     * @param userId
     * @return {@link BikeRentalInfo}
     */
    public static BikeRentalInfo findInProgressByUserId(String userId) {
        Query q = entityManager.createQuery("select i from  BikeRentalInfo i where i.user.id = :userId and i.status = :status");
        q.setParameter("userId", userId);
        q.setParameter("status", RENTALSTATUS.IN_PROGRESS);

        BikeRentalInfo rentalInfo = (BikeRentalInfo) q.getSingleResult();
        return rentalInfo;
    }

    /**
     * Find rental info in progress given user id and bike id
     * @param userId
     * @param bikeId
     * @return {@link BikeRentalInfo}
     */
    public static BikeRentalInfo findInProgressRentalInfo(String userId, String bikeId) {
        Query q = entityManager.createQuery("select i from  BikeRentalInfo i where i.user.id = :userId and i.bike.id = :bikeId and i.status = :status");
        q.setParameter("userId", userId);
        q.setParameter("bikeId", bikeId);
        q.setParameter("status", RENTALSTATUS.IN_PROGRESS);

        BikeRentalInfo rentalInfo = (BikeRentalInfo) q.getSingleResult();
        return rentalInfo;
    }

    /**
     * Update renting time with current time
     * @param userId
     * @return
     */
    public static int updateLatestDuration(String userId) {
        BikeRentalInfo rentalInfo = findInProgressByUserId(userId);

        // get old renting time
        int duration = rentalInfo.getDurationInSeconds();

        // update renting time.
        // If paused before, take the difference between current time and last resume time
        // Otherwise, take the difference between current time and start time
        Date now = new Date();
        if (rentalInfo.getResumeAt() != null) {
            duration += (int)((now.getTime() - rentalInfo.getResumeAt().getTime()) / 1000);
        } else {
            duration = (int)((now.getTime() - rentalInfo.getStartAt().getTime()) / 1000);
        }
        entityManager.getTransaction().begin();
        rentalInfo.setDurationInSeconds(duration);
        entityManager.getTransaction().commit();
        return duration;
    }

}
