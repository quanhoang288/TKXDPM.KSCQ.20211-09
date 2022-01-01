package ecobike.utils;

import ecobike.entity.PaymentTransaction;

import java.util.List;

public interface PaymentObserver {
    public void update(List<PaymentTransaction> transactions);

}
