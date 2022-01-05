package ecobike.entity;

import ecobike.db.DbConnection;
import ecobike.utils.Configs;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BikeRentalInfo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String id;

    @Column(name = "startAt")
    private Date startAt;

    @Column(name = "durationInSeconds")
    private int durationInSeconds;

    @Column(name = "resumeAt")
    private Date resumeAt;

    @Enumerated(EnumType.STRING)
    private RENTALSTATUS status;

    @ManyToOne
    @JoinColumn(name = "bikeID")
    private Bike bike;
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bikeRentalInfoId")
    private List<PaymentTransaction> transactions;


    /**
     * Calculate rental fee given current renting time
     * @param time current renting time in seconds
     * @return
     */
    public int calculateRentalFee(int time) {
        System.out.println("Calculating rental fee");
        double dTime = time;
        if (dTime <= 60) {
            return 0;
        }

        double amount = 10000;
        dTime -= 30 * 60;
        if (dTime > 0) {
            amount += 3000 * Math.ceil(dTime / (2 * 60));
        }

        if (bike.getType() == BIKETYPE.STANDARD_E_BIKE || bike.getType() == BIKETYPE.TWIN_BIKE) amount *= 1.5;

        return (int) amount;
    }

    /**
     * Update rental status and duration
     * @param status
     * @param durationInSeconds
     */
    public void updateStatus(RENTALSTATUS status, int durationInSeconds) {
        EntityManager em = DbConnection.getEntityManager();
        em.getTransaction().begin();
        setStatus(status);
        setDurationInSeconds(durationInSeconds);
        em.getTransaction().commit();
    }


}
