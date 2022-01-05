package ecobike.entity;

import ecobike.common.exception.InvalidCardException;

import java.util.Calendar;
import java.util.HashMap;

public class CreditCard {
    private String cardCode;
    private String owner;
    private String cvvCode;
    private String dateExpired;

    public CreditCard(HashMap<String, String> paymentInfo) throws InvalidCardException{
        this.cardCode = paymentInfo.get("cardNumber");
        this.owner = paymentInfo.get("cardHolder");
        this.cvvCode = paymentInfo.get("cvv");
        this.dateExpired = getExpirationDate(paymentInfo.get("expirationDate"));
    }

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
}
