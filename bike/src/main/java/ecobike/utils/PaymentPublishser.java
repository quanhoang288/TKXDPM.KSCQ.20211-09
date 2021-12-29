package ecobike.utils;

public interface PaymentPublishser {
    void attach(PaymentObserver paymentObserver);
    void detached(PaymentObserver paymentObserver);
    void notifyAllObserver();
}
