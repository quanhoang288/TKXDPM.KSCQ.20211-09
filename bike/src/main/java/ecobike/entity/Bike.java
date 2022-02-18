package ecobike.entity;

import ecobike.db.DbConnection;
import ecobike.repository.BikeRentalInfoRepo;
import ecobike.security.Authentication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@AllArgsConstructor
@NoArgsConstructor

@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Bike {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String id;


    @Column(name = "value")
    private int value;
    @ManyToOne
    @JoinColumn(name = "dockID")
    private Dock dock;
    @OneToMany(mappedBy = "bike")
    private List<BikeRentalInfo> rentedSession;

    /**
     * Check if bike is currently being rented
     *
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
<<<<<<< HEAD
     * Update bike position in new dock after successful return
     *
     * @param selectedDock selected dock for returning bike
=======
     * Update bike position to a new dock
     * @param newDock selected dock
>>>>>>> 57f51c1bbb72a32f44ace9449b6a008c6f89f220
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
     *
     * @return
     */

    public int getDepositAmount() {
        return (int) (getValue()*0.4);
    }


    public abstract int getInitialRentFee();

    public abstract int getRentFeePerTimePeriod();

    public abstract String getName();

    public abstract Map<String, String> getExtraInfo();



}
