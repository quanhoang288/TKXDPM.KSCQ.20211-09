package eco.bike.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    private String startAt;
    @Column(name = "endAt")
    private String endAt;

    @ManyToOne
    @JoinColumn(name = "bikeID")
    private Bike bike;
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;



}
