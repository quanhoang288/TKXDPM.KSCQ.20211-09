package ecobike.controller;


import ecobike.entity.PaymentTransaction;

import java.util.List;

public class RentBikePaymentController extends AbstractPaymentController {

    private int depositAmount;

    public RentBikePaymentController(int depositAmount) {
        this.depositAmount = depositAmount;
    }


    @Override
    public void performTransactions() {
        System.out.println("finish pay deposit of ,"+ this.depositAmount);
        notifyAllObserver(List.of(new PaymentTransaction()));
    }
}
