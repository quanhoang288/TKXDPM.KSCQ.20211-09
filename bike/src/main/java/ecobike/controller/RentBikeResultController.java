package ecobike.controller;

import ecobike.controller.base.BaseController;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RentBikeResultController extends BaseController {
    private String transactionId;
    private String transactionDate;
    private String cardHolderName;
    private String bikeType;
    private String dockName;
    private String depositAmount;


}
