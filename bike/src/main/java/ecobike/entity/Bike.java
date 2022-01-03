package ecobike.entity;

import ecobike.repository.BikeRentalInfoRepo;
import ecobike.security.Authentication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Bike {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String  id;
    @Column(name = "type")
    private String type;
    @Column(name = "licensePlate")
    private String licensePlate;
    @Column(name = "batteryPercent")
    private int batteryPercent;
    @Column(name = "value")
    private int value;
    @ManyToOne
    @JoinColumn(name = "dockID")
    private Dock dock;
    @OneToMany(mappedBy = "bike")
    private List<BikeRentalInfo> rentedSession;

    public boolean isBeingRented() {
        boolean res = true;
        String authenticatedUserId = Authentication.getInstance().getUserId();
        try {
            BikeRentalInfoRepo.findInProgressRentalInfo(authenticatedUserId, id);
        } catch (NoResultException e) {
            res = false;
        }
        return res;
    }
}
