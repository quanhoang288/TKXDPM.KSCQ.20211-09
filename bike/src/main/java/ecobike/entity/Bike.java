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
     * Update bike position in new dock after successful return
     * @param selectedDock selected dock for returning bike
     */
    public void moveToNewDock(Dock selectedDock) {
        EntityManager em = DbConnection.getEntityManager();

        // remove bike from old dock
        Dock oldDock = dock;
        List<Bike> oldDockBikeList = oldDock.getBikes();
        oldDockBikeList.remove(this);

        em.getTransaction().begin();
        setDock(selectedDock);
        oldDock.setBikes(oldDockBikeList);
        em.getTransaction().commit();
    }

    /**
     * Get deposit amount to be paid for renting the bike
     * @return
     */
    public int getDepositAmount() {
        return (int) (value * 0.4);
    }
}
