package eco.bike.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PaymentTransaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String  id;
    private String content;
    private String method;
    @Column(name = "createAt")
    private Date createAt;
    @OneToOne
    @JoinColumn(name = "bikeRentalInfoID", referencedColumnName = "id")
    private BikeRentalInfo bikeRentalInfo;
}
