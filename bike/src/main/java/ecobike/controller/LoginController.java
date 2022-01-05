package ecobike.controller;

import ecobike.common.exception.InvalidCredentialException;
import ecobike.controller.base.BaseController;
import ecobike.db.DbConnection;
import ecobike.entity.User;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.security.Authentication;
import ecobike.utils.StopWatch;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class LoginController extends BaseController {
    public void login(String username, String password) throws InvalidCredentialException {
        EntityManager em = DbConnection.getEntityManager();
        Query query = em.createQuery("select u from User u where u.name = :username and u.password = :password ");
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            User user = (User) query.getSingleResult();

            //set principle
            Authentication.createInstance(username, user.getId());

            if (Authentication.isAlreadyRenting()) {
                // update latest duration for realtime stopwatch
                int latestDuration = BikeRentalInfoRepo.updateLatestDuration(user.getId());
                StopWatch.getInstance().startAt(latestDuration);
            }


        } catch (NoResultException e) {
            throw new InvalidCredentialException();

        }
    }
}
