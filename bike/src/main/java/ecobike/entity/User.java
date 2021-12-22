package ecobike.entity;


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
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String id;
    private String name;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    private String province;
    private String address;
    @Column(name = "dateOfBirth")
    private String dateOfBirth;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<BikeRentalInfo> rentingHistory;



}
