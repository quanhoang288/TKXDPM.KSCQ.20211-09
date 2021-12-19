package eco.bike.payment;

public class CreditCard {
    private String cardCode;
    private String owner;
    private int cvv;
    private String dateExpired;

    public CreditCard(String cardCode, String owner, int cvv, String dateExpired) {
        this.cardCode = cardCode;
        this.owner = owner;
        this.cvv = cvv;
        this.dateExpired = dateExpired;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(String dateExpired) {
        this.dateExpired = dateExpired;
    }
}
