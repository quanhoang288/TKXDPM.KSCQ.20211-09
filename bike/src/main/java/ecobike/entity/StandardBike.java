package ecobike.entity;

import lombok.Builder;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;


@Entity
public class StandardBike extends Bike {
    @Builder
    public StandardBike(String id, int value, Dock dock, List<BikeRentalInfo> rentedSession) {
        super(id, value, dock, rentedSession);
    }

    public StandardBike() {

    }




    @Override
    public int getInitialRentFee() {
        return 3000;
    }

    @Override
    public int getRentFeePerTimePeriod() {
        return 10000;
    }

    @Override
    public String getName() {
        return "Standard bike";
    }

    @Override
    public Map<String, String> getExtraInfo() {
        return null;
    }
}
