package ecobike.controller;

import ecobike.common.exception.InvalidCardException;
import ecobike.common.exception.PaymentException;
import ecobike.common.exception.UnrecognizedException;
import ecobike.controller.base.BaseController;
import ecobike.entity.CreditCard;
import ecobike.entity.PaymentTransaction;
import ecobike.subsystem.InterbankInterface;
import ecobike.subsystem.InterbankSubsystem;

import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class PaymentController extends BaseController {
    private CreditCard card;

    private InterbankInterface interbank;

    public void setInterbank(InterbankInterface interbank) {
        this.interbank = interbank;
    }

    public Map<String, String> performPayment(int amount, String contents, String cardNumber, String cardHolderName,
                                              String expirationDate, String securityCode) {
        Map<String, String> result = new Hashtable<>();

        try {
            this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
                    CreditCard.getExpirationDate(expirationDate));

            this.interbank = new InterbankSubsystem();
            PaymentTransaction transaction = interbank.performPayment(card, amount, contents);

        } catch (PaymentException | UnrecognizedException ex) {
            result.put("MESSAGE", ex.getMessage());
        }
        return result;
    }

    public Map<String, String> performRefund(int amount, String contents, String cardNumber, String cardHolderName,
                                              String expirationDate, String securityCode) {
        Map<String, String> result = new Hashtable<>();

        try {
            this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
                    CreditCard.getExpirationDate(expirationDate));

            this.interbank = new InterbankSubsystem();
            PaymentTransaction transaction = interbank.performRefund(card, amount, contents);

        } catch (PaymentException | UnrecognizedException ex) {
            result.put("MESSAGE", ex.getMessage());
        }
        return result;
    }
}
