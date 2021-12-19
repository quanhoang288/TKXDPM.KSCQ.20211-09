package eco.bike.subsystem;

import eco.bike.exception.PaymentException;
import eco.bike.payment.CreditCard;
import eco.bike.payment.PaymentTransaction;

/**
 * The {@code InterbankInterface} class is used to communicate with the
 * {@link eco.bike.subsystem.InterbankSubsystem} to make transaction
 *
 */
public interface InterbankInterface {

    /**
     *
     * @param card
     * @param amount
     * @param contents
     * @return
     * @throws PaymentException
     */
    public abstract PaymentTransaction performPayment(CreditCard card, int amount, String contents)
            throws PaymentException;

    /**
     *
     * @param card
     * @param amount
     * @param contents
     * @return
     * @throws PaymentException
     */
    public abstract PaymentTransaction performRefund(CreditCard card, int amount, String contents)
        throws PaymentException;
}
