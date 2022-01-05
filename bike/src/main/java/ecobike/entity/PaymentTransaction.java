package ecobike.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;


@Entity
@Builder
@AllArgsConstructor
@Getter

public class PaymentTransaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "uuid2"
    )
    private String id;
    private String errorCode;
    private String content;
    @Enumerated(EnumType.STRING)
    private PAYCONTENT method;
    private int amount;
    @Column(name = "createAt")
    private Date createAt;
    @OneToOne
    @JoinColumn(name = "bikeRentalInfoID", referencedColumnName = "id")
    private BikeRentalInfo bikeRentalInfo;

    public PaymentTransaction(String errorCode, String transactionId, String transactionContent, int amount) {
        this.errorCode = errorCode;
        this.id = transactionId;
        this.content = transactionContent;
        this.amount = amount;
    }

    public PaymentTransaction() {

    }

}