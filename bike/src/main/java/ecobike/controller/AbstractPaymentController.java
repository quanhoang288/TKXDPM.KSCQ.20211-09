package ecobike.controller;

import ecobike.controller.base.BaseController;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.InterbankInterface;
import ecobike.utils.MyMap;
import java.util.HashMap;

/**
 * This class is an abstract class for performing interbank-related transactions.
 */
public abstract class AbstractPaymentController extends BaseController {

    protected InterbankInterface interbank;
    protected PaymentTransaction paymentTransaction;

    /**
     * Set interbank interface to perform transactions
     * @param interbank
     */
    public void setInterbank(InterbankInterface interbank) {
        this.interbank = interbank;
    }

    /**
     * Perform interbank-related transactions given payment info
     * @param paymentInfo contains pairs of key-value containing user's payment information
     */
    public abstract void performTransactions(HashMap<String, String> paymentInfo);

    /**
     * Process payment transaction response received from API and do some post-processing tasks
     * @param transactionResponse {@link MyMap} object representing transaction response from API
     */
    protected abstract void handleTransactionComplete(MyMap transactionResponse);

    /**
     * Get instance of payment transaction after successful payment transaction request
     * @return {@link PaymentTransaction}
     */
    public PaymentTransaction getPaymentTransaction() {
        return this.paymentTransaction;
    }
}
