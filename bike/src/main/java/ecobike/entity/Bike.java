package ecobike.entity;

import ecobike.db.DbConnection;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.security.Authentication;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Bike {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String  id;
    @Enumerated(EnumType.STRING)
    private BIKETYPE type;
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

    /**
     * Check if bike is currently being rented
     * @return
     */
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

    /**
     * Update bike position to a new dock
     * @param newDock selected dock
     */
    public void updateDock(Dock newDock) {
        EntityManager em = DbConnection.getEntityManager();

        em.getTransaction().begin();

        // remove bike from old dock bike list
        Dock oldDock = dock;
        List<Bike> bikeList = oldDock.getBikes();
        bikeList.remove(this);
        oldDock.setBikes(bikeList);

        // add dock to new dock bike list if not null
        if (newDock != null) {
            List<Bike> newDockBikeList = newDock.getBikes();
            newDockBikeList.add(this);
            newDock.setBikes(newDockBikeList);
            setDock(newDock);
        }

        em.getTransaction().commit();
    }

    /**
     * Get deposit amount to be paid for renting the bike
     * @return
     */
    public int getDepositAmount() {
        return (int) (value * 0.4);
    }

    /**
     * Check if bike type is electric
     * @return
     */
    public boolean isEBike() {
        return type == BIKETYPE.STANDARD_E_BIKE;
    }
}
