package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.InterbankInterface;
import ecobike.utils.MyMap;
import java.util.HashMap;

public abstract class AbstractPaymentController extends BaseController {

    protected InterbankInterface interbank;
    protected PaymentTransaction paymentTransaction;

    public void setInterbank(InterbankInterface interbank) {
        this.interbank = interbank;
    }

    public abstract void performTransactions(HashMap<String, String> paymentInfo);

    protected abstract void handleTransactionComplete(MyMap transactionResponse);


    public PaymentTransaction getPaymentTransaction() {
        return this.paymentTransaction;
    }
}
