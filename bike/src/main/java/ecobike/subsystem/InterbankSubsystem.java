package ecobike.subsystem;

import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UnrecognizedException;
import ecobike.entity.CreditCard;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.interbank.InterbankSubsystemController;
import ecobike.utils.MyMap;

/**
 * Subsystem class to communicate with interbank for perform payment transactions
 */
public class InterbankSubsystem implements InterbankInterface {

    /**
     * {@link InterbankSubsystemController} instance for performing communication logics with API
     */
    private InterbankSubsystemController ctrl;

    public InterbankSubsystem() {
        this.ctrl = new InterbankSubsystemController();
    }

    /**
     * Perform payment transaction
     * @param card information of user credit card
     * @param amount amount of money to perform transaction
     * @param contents description of transaction
     * @return {@link MyMap} object containing transaction response
     * @throws PaymentException if API responded with an error code
     * @throws UnrecognizedException if error code is not recognizable
     */
    @Override
    public MyMap performPayment(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        MyMap response = ctrl.performPayment(card, amount, contents);
        return response;
    }

    /**
     * Perform refund transaction
     * @param card information of user credit card
     * @param amount amount of money to perform transaction
     * @param contents description of transaction
     * @return {@link MyMap} object containing transaction response
     * @throws PaymentException if API responded with an error code
     * @throws UnrecognizedException if error code is not recognizable
     */
    @Override
    public MyMap performRefund(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        MyMap response = ctrl.performRefund(card, amount, contents);
        return response;
    }
}
