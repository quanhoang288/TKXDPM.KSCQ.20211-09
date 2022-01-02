package ecobike.subsystem;

import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UnrecognizedException;
import ecobike.entity.CreditCard;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.interbank.InterbankSubsystemController;

public class InterbankSubsystem implements InterbankInterface {

    private InterbankSubsystemController ctrl;

    public InterbankSubsystem() {
        this.ctrl = new InterbankSubsystemController();
    }

    @Override
    public PaymentTransaction performPayment(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        PaymentTransaction transaction = ctrl.performPayment(card, amount, contents);
        return transaction;
    }

    @Override
    public PaymentTransaction performRefund(CreditCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        PaymentTransaction transaction = ctrl.performRefund(card, amount, contents);
        return transaction;
    }
}
