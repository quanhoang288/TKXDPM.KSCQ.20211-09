package ecobike.entity;

import ecobike.db.DbConnection;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Dock {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String id;
    private String name;
    private String address;
    private double area;
    private int capacity;
    @OneToMany(mappedBy = "dock")
    private List<Bike> bikes;

    /**
     * Check if dock is currently full or not
     * @return
     */
    public boolean isFull() {
        EntityManager em = DbConnection.getEntityManager();
        Query bikeBeingRentedCountQuery = em.createQuery("select count(bri) from BikeRentalInfo bri inner join Bike b on bri.status is :status and b.dock = :dock");
        bikeBeingRentedCountQuery.setParameter("status", RENTALSTATUS.IN_PROGRESS);
        bikeBeingRentedCountQuery.setParameter("dock", this);
        Long bikeBeingRentedCount = (Long) bikeBeingRentedCountQuery.getSingleResult();
        return capacity - bikes.size() + bikeBeingRentedCount == 0;
    }

}
