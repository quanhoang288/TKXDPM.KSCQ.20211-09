package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class PaymentTransactionRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    public static PaymentTransaction create(String content, PAYCONTENT method, int amount, Date createAt, BikeRentalInfo rentalInfo) {
        entityManager.getTransaction().begin();
        PaymentTransaction paymentTransaction = PaymentTransaction
                .builder()
                .content(content)
                .method(method)
                .amount(amount)
                .createAt(createAt)
                .bikeRentalInfo(rentalInfo)
                .build();
        entityManager.persist(paymentTransaction);
        entityManager.getTransaction().commit();
        return paymentTransaction;
    }


}
