package ecobike.entity;

import ecobike.common.exception.InvalidCardException;

import java.util.Calendar;

public class CreditCard {
    private String cardCode;
    private String owner;
    private int cvvCode;
    private String dateExpired;

    public CreditCard(String cardCode, String owner, int cvvCode, String dateExpired) {
        super();
        this.cardCode = cardCode;
        this.owner = owner;
        this.cvvCode = cvvCode;
        this.dateExpired = dateExpired;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getOwner() {
        return owner;
    }

    public int getCvvCode() {
        return cvvCode;
    }

    public String getDateExpired() {
        return dateExpired;
    }

    public static String getExpirationDate(String date) throws InvalidCardException {
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
