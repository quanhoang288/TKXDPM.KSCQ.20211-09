package ecobike.repository;

import ecobike.db.DbConnection;
import ecobike.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

/**
 * This class perform queries related to payment transaction
 */
public class PaymentTransactionRepo {
    private static EntityManager entityManager = DbConnection.getEntityManager();

    /**
     * Create and persist new payment transaction
     * @param content content of transaction
     * @param method method of transaction (pay/refund)
     * @param amount amount of money of transaction
     * @param createAt created time of transaction
     * @param rentalInfo {@link BikeRentalInfo} object representing rental info of transaction
     * @return {@link PaymentTransaction}
     */
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
