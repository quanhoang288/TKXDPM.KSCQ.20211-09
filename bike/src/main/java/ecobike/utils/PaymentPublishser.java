package ecobike.utils;

import ecobike.entity.PaymentTransaction;

import java.util.List;

public interface PaymentPublishser {
    void attach(PaymentObserver paymentObserver);
    void detached(PaymentObserver paymentObserver);
    void notifyAllObserver(List<PaymentTransaction> transactions);
}
