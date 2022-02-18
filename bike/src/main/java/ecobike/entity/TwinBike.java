package ecobike.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;
import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
public class TwinBike extends Bike{
    @Builder
    public TwinBike(String id, int value, Dock dock, List<BikeRentalInfo> rentedSession) {
        super(id, value, dock, rentedSession);
    }



    @Override
    public int getInitialRentFee() {
        return 4500;
    }

    @Override
    public int getRentFeePerTimePeriod() {
        return 15000;
    }

    @Override
    public String getName() {
        return "Twin bike";
    }

    @Override
    public Map<String, String> getExtraInfo() {
        return null;
    }
}
