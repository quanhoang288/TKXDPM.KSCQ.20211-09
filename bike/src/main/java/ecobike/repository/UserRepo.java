package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.Bike;
import ecobike.entity.BikeRentalInfo;
import ecobike.entity.RENTALSTATUS;
import ecobike.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * This class perform queries and operations related to user
 */
public class UserRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    /**
     * Find user with given id
     * @param id
     * @return {@link User}
     * @throws NoResultException if no user is found with given id
     */
    public static User findById(String id) throws NoResultException {
        Query q = entityManager.createQuery("select u from  User u where u.id = :id");
        q.setParameter("id", id);
        User user = (User) q.getSingleResult();
        return user;
    }

    /**
     * Check if user with given id is currently renting any bike
     * @param userId
     * @return
     */
    public static boolean isRentingBike(String userId) {
        Query q = entityManager.createQuery("select i from BikeRentalInfo i where i.user.id = :id and i.status = :status");
        q.setParameter("id", userId);
        q.setParameter("status", RENTALSTATUS.IN_PROGRESS);
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

}


