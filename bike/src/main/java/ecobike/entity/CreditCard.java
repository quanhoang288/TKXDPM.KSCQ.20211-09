package ecobike.entity;

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
}
