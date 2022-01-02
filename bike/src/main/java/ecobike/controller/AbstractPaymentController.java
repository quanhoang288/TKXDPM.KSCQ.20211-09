package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.InterbankInterface;
import ecobike.utils.PaymentObserver;
import ecobike.utils.PaymentPublishser;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPaymentController extends BaseController implements PaymentPublishser {
    List<PaymentObserver> observers = new ArrayList<>();

    protected InterbankInterface interbank;
    public void setInterbank(InterbankInterface interbank) {
        this.interbank = interbank;
    }

    @Override
    public void attach(PaymentObserver paymentObserver) {
        observers.add(paymentObserver);
    }

    @Override
    public void detached(PaymentObserver paymentObserver) {
        assert observers.contains(paymentObserver);
        observers.remove(paymentObserver);
    }

    @Override
    public void notifyAllObserver(List<PaymentTransaction> transactions) {
        for (PaymentObserver paymentObserver : observers){
            paymentObserver.update(transactions);
        }
    }
    public abstract void performTransactions();
}
