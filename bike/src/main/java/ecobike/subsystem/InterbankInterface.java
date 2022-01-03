package ecobike.subsystem;

import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UnrecognizedException;
import ecobike.entity.CreditCard;
import ecobike.entity.PaymentTransaction;
import ecobike.utils.MyMap;

public interface InterbankInterface {

    public abstract MyMap performPayment(CreditCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException;

    public abstract MyMap performRefund(CreditCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException;
}
