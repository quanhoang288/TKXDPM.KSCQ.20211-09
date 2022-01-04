package ecobike.subsystem;

import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UnrecognizedException;
import ecobike.entity.CreditCard;
import ecobike.utils.MyMap;

public interface InterbankInterface {

    MyMap performPayment(CreditCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException;

    MyMap performRefund(CreditCard card, int amount, String contents)
            throws PaymentException, UnrecognizedException;
}
