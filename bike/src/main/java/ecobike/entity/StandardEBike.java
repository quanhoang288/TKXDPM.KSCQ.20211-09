package ecobike.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StandardEBike extends Bike {
    @Column(name = "licensePlate")
    private String licensePlate;
    @Column(name = "batteryPercent")
    private int batteryPercent;

    @Builder
    public StandardEBike(String id, int value, Dock dock, List<BikeRentalInfo> rentedSession, String licensePlate, int batteryPercent) {
        super(id, value, dock, rentedSession);
        this.licensePlate = licensePlate;
        this.batteryPercent = batteryPercent;
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
        return "Standard E-bike";
    }

    @Override
    public Map<String, String> getExtraInfo() {
        Map<String, String> extra = new HashMap<>();
        extra.put("License plate", licensePlate);
        extra.put("Battery percentage:", batteryPercent +"%");
        return extra;
    }
}
