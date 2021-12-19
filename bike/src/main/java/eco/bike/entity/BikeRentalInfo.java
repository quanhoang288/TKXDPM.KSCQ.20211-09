package eco.bike.entity;

public class BikeRentalInfo {

    private String startAt;
    private String endAt;
    private int bikeId;
    private int userId;

    public BikeRentalInfo(String startAt, String endAt, int bikeId, int userId) {
        this.startAt = startAt;
        this.endAt = endAt;
        this.bikeId = bikeId;
        this.userId = userId;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(final String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(final String endAt) {
        this.endAt = endAt;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
