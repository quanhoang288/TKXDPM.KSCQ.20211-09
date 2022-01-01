package ecobike.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class PaymentTransaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String  id;
    private String content;

    private double amount;

    @Enumerated(EnumType.STRING)
    private PAYCONTENT method;
    @Column(name = "createAt")
    private Date createAt;


}
