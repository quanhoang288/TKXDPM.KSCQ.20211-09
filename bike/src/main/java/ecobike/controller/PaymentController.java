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

    private String getExpirationDate(String date) throws InvalidCardException {
        String[] strs = date.split("/");
        if (strs.length != 2) {
            throw new InvalidCardException();
        }

        String expirationDate = null;
        int month = -1;
        int year = -1;

        try {
            month = Integer.parseInt(strs[0]);
            year = Integer.parseInt(strs[1]);
            if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
                throw new InvalidCardException();
            }
            expirationDate = strs[0] + strs[1];

        } catch (Exception ex) {
            throw new InvalidCardException();
        }

        return expirationDate;
    }

    public Map<String, String> payOrder(int amount, String contents, String cardNumber, String cardHolderName,
                                        String expirationDate, String securityCode) {
        Map<String, String> result = new Hashtable<>();
        result.put("RESULT", "PAYMENT FAILED!");
        try {
            this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
                    getExpirationDate(expirationDate));

            this.interbank = new InterbankSubsystem();
            PaymentTransaction transaction = interbank.payOrder(card, amount, contents);

            result.put("RESULT", "PAYMENT SUCCESSFUL!");
            result.put("MESSAGE", "You have succesffully paid the order!");
        } catch (PaymentException | UnrecognizedException ex) {
            result.put("MESSAGE", ex.getMessage());
        }
        return result;
    }
}
