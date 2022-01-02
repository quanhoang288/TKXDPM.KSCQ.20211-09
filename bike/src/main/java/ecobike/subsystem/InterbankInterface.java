package ecobike.subsystem;

import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UnrecognizedException;
import ecobike.entity.CreditCard;
import ecobike.entity.PaymentTransaction;

public interface InterbankInterface {

    public abstract PaymentTransaction performPayment(CreditCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException;

    public abstract PaymentTransaction performRefund(CreditCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException;
}
