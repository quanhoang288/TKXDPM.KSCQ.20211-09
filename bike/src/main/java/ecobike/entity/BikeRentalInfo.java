package ecobike.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BikeRentalInfo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String id;
    @Column(name = "startAt")
    private String startAt;
    @Column(name = "endAt")
    private String endAt;

    @ManyToOne
    @JoinColumn(name = "bikeID")
    private Bike bike;
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bikeRentalInfoId")
    private List<PaymentTransaction> transactions;


    public void addTransaction(PaymentTransaction paymentTransaction) {
        if (transactions == null) transactions = new ArrayList<>();
        transactions.add(paymentTransaction);
    }


}
