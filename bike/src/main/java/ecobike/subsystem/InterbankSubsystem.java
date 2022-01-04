package ecobike.subsystem;

import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UnrecognizedException;
import ecobike.entity.CreditCard;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.interbank.InterbankSubsystemController;
import ecobike.utils.MyMap;

public class InterbankSubsystem implements InterbankInterface {

    private InterbankSubsystemController ctrl;

    public InterbankSubsystem() {
        this.ctrl = new InterbankSubsystemController();
    }

    @Override
    public MyMap performPayment(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        MyMap response = ctrl.performPayment(card, amount, contents);
        return response;
    }

    @Override
    public MyMap performRefund(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        MyMap response = ctrl.performRefund(card, amount, contents);
        return response;
    }
}
